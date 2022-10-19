package team.solar.bookstorebackend.websocket

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/websocket/{userId}")
@Component
@Scope("singleton")
class WebSocketServer {
    companion object {
        var count: AtomicInteger = AtomicInteger()
        var sessions: ConcurrentHashMap<String, Session> = ConcurrentHashMap()
    }

    private fun sendMessage(session: Session?, message: String) {
        println("send to session($session): $message from Websocket $this")
        println("current sessions($count): $sessions")
        if (session != null) {
            try {
                session.basicRemote.sendText(message)
                println("message send over")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        else {
            println("session is null")
        }
    }

    fun sendMessageToUser(user: String, message: String) {
        println("send to user($user): $message $this")
        val session = sessions[user]
        sendMessage(session, message)
    }

    @OnMessage
    fun onMessage(message: String, @PathParam("userId") userId: String) {
        println("receive from user($userId): $message $this")
    }

    @OnOpen
    fun onOpen(session: Session, @PathParam("userId") userId: String) {
        if (sessions[userId] != null) {
            return
        }
        sessions[userId] = session
        count.incrementAndGet()
        println("$this 有连接加入，当前连接数为：${count.get()}")
    }

    @OnClose
    fun onClose(@PathParam("userId") userId: String) {
        if (sessions[userId] == null) {
            return
        }
        sessions.remove(userId)
        count.decrementAndGet()
        println("$this 有连接关闭，当前连接数为：${count.get()}")
    }

    @OnError
    fun onError(session: Session, throwable: Throwable) {
        throwable.printStackTrace()
    }
}