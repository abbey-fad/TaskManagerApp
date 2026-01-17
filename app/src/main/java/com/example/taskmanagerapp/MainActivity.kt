package com.example.taskmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taskmanagerapp.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerAppTheme {
                TaskManagerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerApp() {
    val context = LocalContext.current
    var screen by remember { mutableStateOf("list") }

    val tasks = remember {
        mutableStateListOf<String>().apply {
            addAll(TaskStorage.loadTasks(context))
        }
    }

    when (screen) {
        "list" -> TaskListScreen(
            tasks = tasks,
            onAddClick = { screen = "add" },
            onDelete = { task ->
                tasks.remove(task)
                TaskStorage.saveTasks(context, tasks)
            }
        )
        "add" -> AddTaskScreen(
            onSave = { task ->
                tasks.add(task)
                TaskStorage.saveTasks(context, tasks)
                screen = "list"
            },
            onCancel = { screen = "list" }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<String>,
    onAddClick: () -> Unit,
    onDelete: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Manager") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = PrimaryColor,
                    titleContentColor = OnPrimaryColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = SecondaryColor
            ) {
                Text("+", color = OnSecondaryColor)
            }
        },
        containerColor = Background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Background)
        ) {
            items(tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(task)
                    Button(
                        onClick = { onDelete(task) },
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
                    ) {
                        Text("Delete", color = OnSecondaryColor)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Background),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Add New Task", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Task name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
            ) {
                Text("Cancel", color = OnSecondaryColor)
            }
            Button(
                onClick = { if (text.isNotBlank()) onSave(text) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text("Save", color = OnPrimaryColor)
            }
        }
    }
}
