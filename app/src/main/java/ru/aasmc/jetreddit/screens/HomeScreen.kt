package ru.aasmc.jetreddit.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.aasmc.jetreddit.components.ImagePost
import ru.aasmc.jetreddit.components.JoinedToast
import ru.aasmc.jetreddit.components.TextPost
import ru.aasmc.jetreddit.domain.model.PostModel
import ru.aasmc.jetreddit.domain.model.PostType
import ru.aasmc.jetreddit.viewmodel.MainViewModel
import java.util.*
import kotlin.concurrent.schedule

@ExperimentalAnimationApi
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val posts: List<PostModel> by
    viewModel.allPosts.observeAsState(listOf())

    var isToastVisible by remember {
        mutableStateOf(false)
    }

    val onJoinClickAction: (Boolean) -> Unit = { joined ->
        isToastVisible = joined
        if (isToastVisible) {
            Timer().schedule(3000) {
                isToastVisible = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.background(
                color = MaterialTheme.colors.secondary
            )
        ) {
            items(posts) {
                if (it.type == PostType.TEXT) {
                    TextPost(post = it, onJoinedButtonClicked = onJoinClickAction)
                } else {
                    ImagePost(post = it, onJoinedButtonClicked = onJoinClickAction)
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 16.dp)
        ) {
            JoinedToast(visible = isToastVisible)
        }
    }
}












