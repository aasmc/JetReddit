package ru.aasmc.jetreddit.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ru.aasmc.jetreddit.R
import ru.aasmc.jetreddit.components.ImagePost
import ru.aasmc.jetreddit.components.JoinedToast
import ru.aasmc.jetreddit.components.TextPost
import ru.aasmc.jetreddit.domain.model.PostModel
import ru.aasmc.jetreddit.domain.model.PostType
import ru.aasmc.jetreddit.viewmodel.MainViewModel
import ru.aasmc.jetreddit.views.TrendingTopicView
import java.util.*
import kotlin.concurrent.schedule

private val trendingTopics = listOf(
    TrendingTopicModel(
        "Compose Tutorial",
        R.drawable.jetpack_composer
    ),
    TrendingTopicModel(
        "Compose Animations",
        R.drawable.jetpack_compose_animations
    ),
    TrendingTopicModel(
        "Compose Migration",
        R.drawable.compose_migration_crop
    ),
    TrendingTopicModel(
        "DataStore Tutorial",
        R.drawable.data_storage
    ),
    TrendingTopicModel(
        "Android Animations",
        R.drawable.android_animations
    ),
    TrendingTopicModel(
        "Deep Links in Android",
        R.drawable.deeplinking
    ),
)

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

    val homeScreenItems = mapHomeScreenItems(posts)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.background(
                color = MaterialTheme.colors.secondary
            ),
            content = {
                items(
                    items = homeScreenItems,
                    itemContent = { item ->
                        if (item.type == HomeScreenItemType.TRENDING) {
                            TrendingTopics(
                                trendingTopics = trendingTopics,
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                        } else if (item.post != null) {
                            val post = item.post
                            if (post.type == PostType.TEXT) {
                                TextPost(
                                    post = post,
                                    onJoinedButtonClicked = onJoinClickAction
                                )
                            } else {
                                ImagePost(
                                    post = post,
                                    onJoinedButtonClicked = onJoinClickAction
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                )
            }
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 16.dp)
        ) {
            JoinedToast(visible = isToastVisible)
        }
    }
}

private fun mapHomeScreenItems(
    posts: List<PostModel>
): List<HomeScreenItem> {
    val homeScreenItems = mutableListOf<HomeScreenItem>()
    homeScreenItems.add(
        HomeScreenItem(HomeScreenItemType.TRENDING)
    )
    posts.forEach { postModel ->
        homeScreenItems.add(
            HomeScreenItem(
                HomeScreenItemType.POST,
                postModel
            )
        )
    }
    return homeScreenItems
}

@Composable
private fun TrendingTopics(
    trendingTopics: List<TrendingTopicModel>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Blue,
                    contentDescription = stringResource(id = R.string.star_icon)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.trending_today),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp
                ),
                content = {
                    itemsIndexed(
                        items = trendingTopics,
                        itemContent = { index, trendingModel ->
                            TrendingTopic(trendingTopic = trendingModel)
                            if (index != trendingTopics.lastIndex) {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun TrendingTopicsPreview() {
    TrendingTopics(trendingTopics = trendingTopics)
}

@Composable
private fun TrendingTopic(
    trendingTopic: TrendingTopicModel
) {
    AndroidView({ context ->
        TrendingTopicView(context).apply {
            text = trendingTopic.text
            image = trendingTopic.imageRes
        }
    })
}

@Preview
@Composable
private fun TrendingTopicPreview() {
    TrendingTopic(
        trendingTopic = TrendingTopicModel(
            "Compose Animations",
            R.drawable.jetpack_compose_animations
        )
    )
}

private data class HomeScreenItem(
    val type: HomeScreenItemType,
    val post: PostModel? = null
)

private enum class HomeScreenItemType {
    TRENDING,
    POST
}

private data class TrendingTopicModel(
    val text: String,
    @DrawableRes val imageRes: Int = 0
)
























