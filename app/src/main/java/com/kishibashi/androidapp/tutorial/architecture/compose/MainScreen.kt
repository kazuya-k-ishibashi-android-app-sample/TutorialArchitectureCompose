package com.kishibashi.androidapp.tutorial.architecture.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val messageSentDateTimeFormatter = remember {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .withZone(ZoneId.systemDefault())
    }

    val messages = remember { mutableStateListOf<ChatMessage>() }

    // TextFieldValue
    // カーソル位置やテキスト選択状態も保持できる
    var inputMessage by remember { mutableStateOf(TextFieldValue("")) }

    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    fun isInputMessageEmpty(): Boolean =
        inputMessage.text.trim().isEmpty()

    fun sendMessage() {
        if (isInputMessageEmpty()) return

        val newMessage = ChatMessage(
            id = "chat_message_" + System.currentTimeMillis().toString(),
            text = inputMessage.text,
            createdAt = Instant.now()
        )
        messages.add(newMessage)

        // 入力欄をクリア
        inputMessage = TextFieldValue("")
        // フォーカスを外してキーボードを閉じる
        focusManager.clearFocus()
        keyboardController?.hide()
        // 最下部へスクロール
        coroutineScope.launch {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp, 8.dp),
                state = listState
            ) {

                items(messages) { message ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {

                            // 送信日時
                            Text(
                                text = messageSentDateTimeFormatter.format(message.createdAt),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(bottom = 2.dp)
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End)
                            )

                            // 吹き出し
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .wrapContentWidth(Alignment.End)
                            ) {

                                Text(
                                    text = message.text,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(12.dp, 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // メッセージ入力・送信欄
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {

                OutlinedTextField(
                    value = inputMessage,
                    onValueChange = { inputMessage = it },
                    modifier = Modifier
                        .weight(1f),
                    placeholder = { Text("メッセージを入力") },
                    singleLine = false,
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = { sendMessage() }
                    )
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { sendMessage() },
                    enabled = !isInputMessageEmpty(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(16.dp, 12.dp),
                    modifier = Modifier.padding(0.dp, 4.dp)
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
