package com.kishibashi.androidapp.tutorial.architecture.compose.ui.chat.roomlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.chat.ChatRoom
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun ChatRoomListScreenPreview() {
    AppTheme {
        ChatRoomListScreen(
            chatRooms = listOf(
                ChatRoom(id = "chatroom_1", name = "Chat 1"),
                ChatRoom(id = "chatroom_2", name = "Chat 2"),
                ChatRoom(id = "chatroom_3", name = "Chat 3")
            ),
            onRoomClick = {}
        )
    }
}
