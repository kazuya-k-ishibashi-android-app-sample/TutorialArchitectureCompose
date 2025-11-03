package com.kishibashi.androidapp.tutorial.architecture.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val messages = remember { mutableStateListOf<String>() }

    // TextFieldValue
    // カーソル位置やテキスト選択状態も保持できる
    var inputMessage by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // メッセージ表示欄
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
            }

            // メッセージ入力・送信欄
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = inputMessage,
                    onValueChange = { inputMessage = it },
                    modifier = Modifier
                        .weight(1f),
                    placeholder = { Text("メッセージを入力") },
                    singleLine = true
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (inputMessage.text.isNotBlank()) {
                            messages.add(inputMessage.text)
                            // 入力欄をクリア
                            inputMessage = TextFieldValue("")
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(16.dp, 12.dp)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_send_24dp),
                        contentDescription = "送信"
                    )
                }
            }
        }
    }
}
