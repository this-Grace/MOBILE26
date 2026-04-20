package it.unibo.trace

import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(val id: Int, val name: String)
