package team.solar.bookstorebackend.websocket

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.COUNT
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@Component
@ServerEndpoint("/websocket/{userId}")
class WebSocketServer(
    var count: AtomicInteger = AtomicInteger(),
    var sessions: ConcurrentHashMap<String, Session> = ConcurrentHashMap()
) {
    private fun sendMessage(session: Session?, message: String) {
        if (session != null) {
            try {
                session.basicRemote.sendText(message);
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
    }

    fun sendMessageToUser(user: String, message: String) {
        val session = sessions[user]
        sendMessage(session, message)
    }

    /**
    @OnMessage
    fun onMessage(message: String) {
        val messageBody: MessageBody = JSON.parseObject(message, MessageBody::class.java)
        val userId: String = messageBody.getUserId()
        sendMessageToUser(userId, messageBody.getMessage())
    }
    */

    @OnOpen
    fun onOpen(session: Session, @PathParam("userId") userId: String) {
        if (sessions[userId] != null) {
            return
        }
        sessions[userId] = session
        count.incrementAndGet()
    }

    @OnClose
    fun onClose(@PathParam("userId") userId: String) {
        sessions.remove(userId)
        count.decrementAndGet()
    }


    @OnError
    fun onError(session: Session, throwable: Throwable) {
        throwable.printStackTrace()
    }
}