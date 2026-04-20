package it.unibo.trace.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.unibo.trace.data.TodoDaoImpl
import it.unibo.trace.data.TodoItem
import it.unibo.trace.supabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen() {
    val todoDao = remember { TodoDaoImpl(supabase) }
    var items by remember { mutableStateOf<List<TodoItem>>(listOf()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            items = todoDao.getAll()
        }
    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items,
                key = { item -> item.id },
            ) { item ->
                Text(
                    item.name,
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}
