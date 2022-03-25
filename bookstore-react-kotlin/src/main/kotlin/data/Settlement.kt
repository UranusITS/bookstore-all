package data

import react.State
import kotlinx.serialization.Serializable


@Serializable
data class SettlementState(var books: List<Pair<Book, Int>>): State
