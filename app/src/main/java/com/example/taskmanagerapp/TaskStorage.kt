package com.example.taskmanagerapp

import android.content.Context

object TaskStorage {

    private const val PREFS_NAME = "task_manager_prefs"
    private const val TASKS_KEY = "tasks_key"

    /** Load saved tasks from SharedPreferences */
    fun loadTasks(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val tasksString = prefs.getString(TASKS_KEY, "") ?: ""
        return if (tasksString.isEmpty()) {
            emptyList()
        } else {
            tasksString.split("|").map { it }
        }
    }

    /** Save tasks to SharedPreferences */
    fun saveTasks(context: Context, tasks: List<String>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val tasksString = tasks.joinToString("|") { it }
        prefs.edit().putString(TASKS_KEY, tasksString).apply()
    }
}
