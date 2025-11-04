package com.kishibashi.androidapp.tutorial.architecture.compose.ui.chat

import java.time.Instant

data class ChatMessage(
    val id: String,
    val text: String,
    val createdAt: Instant
)
