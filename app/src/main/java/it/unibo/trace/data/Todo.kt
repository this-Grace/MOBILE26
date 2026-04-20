package it.unibo.trace.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(val id: Int, val name: String)

interface TodoDao {
    suspend fun getAll(): List<TodoItem>
    suspend fun upsert(item: TodoItem)
    suspend fun delete(id: Int)
}

class TodoDaoImpl(private val supabase: SupabaseClient) : TodoDao {
    private val table = supabase.from("Todos")

    override suspend fun getAll(): List<TodoItem> = 
        table.select().decodeList<TodoItem>()

    override suspend fun upsert(item: TodoItem) {
        table.upsert(item)
    }

    override suspend fun delete(id: Int) {
        table.delete {
            filter {
                TodoItem::id eq id
            }
        }
    }
}
