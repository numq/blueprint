package io.github.numq.blueprint.example.server

import java.util.concurrent.ConcurrentHashMap

object SessionManager {
    private val store = ConcurrentHashMap<String, MutableList<String>>()

    fun pushHash(sessionId: String, hash: String) {
        val stack = store.computeIfAbsent(sessionId) { mutableListOf() }

        stack.add(hash)
    }

    fun popHash(sessionId: String): String? {
        val stack = store[sessionId] ?: return null

        if (stack.size > 1) {
            stack.removeLast()
        }

        return stack.lastOrNull()
    }

    fun getCurrentHash(sessionId: String) = store[sessionId]?.lastOrNull()

    fun clearSession(sessionId: String) {
        store.remove(sessionId)
    }
}