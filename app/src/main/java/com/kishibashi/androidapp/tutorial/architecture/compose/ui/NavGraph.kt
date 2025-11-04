package com.kishibashi.androidapp.tutorial.architecture.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.chat.ChatRoom
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.chat.room.ChatRoomScreen
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.chat.roomlist.ChatRoomListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val chatRooms = listOf(
        ChatRoom(id = "chatroom_1", name = "Chat 1")
    )

    NavHost(
        navController = navController,
        startDestination = "chatroom_list"
    ) {
        composable("chatroom_list") {
            ChatRoomListScreen(
                chatRooms = chatRooms,
                onRoomClick = { room ->
                    navController.navigate("chatroom/${room.id}")
                }
            )
        }

        composable(
            route = "chatroom/{chatRoomId}",
            arguments = listOf(
                navArgument("chatRoomId") { type = NavType.StringType }
            )
        ) { backstackEntry ->
            val chatRoomId = backstackEntry.arguments?.getString("chatRoomId") ?: ""
            val chatRoom: ChatRoom? = chatRooms.find { it.id == chatRoomId }

            if (chatRoom == null) {
                // 現状は発生しないため、保留
            } else {
                ChatRoomScreen(
                    chatRoom = chatRoom,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
