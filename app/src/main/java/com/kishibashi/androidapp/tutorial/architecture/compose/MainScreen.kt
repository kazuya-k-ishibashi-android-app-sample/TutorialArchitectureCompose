package com.kishibashi.androidapp.tutorial.architecture.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val messages = remember { mutableStateListOf<String>() }

    // TextFieldValue
    // カーソル位置やテキスト選択状態も保持できる
    var inputMessage by remember { mutableStateOf(TextFieldValue("")) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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

                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .wrapContentWidth(Alignment.End)
                        ) {

                            Text(
                                text = message,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(12.dp, 8.dp)
                            )
                        }
                    }
                }
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
                            // 最下部へスクロール
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.lastIndex)
                            }
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
