package ru.aasmc.jetreddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.aasmc.jetreddit.R
import ru.aasmc.jetreddit.routing.BackButtonAction
import ru.aasmc.jetreddit.routing.JetRedditRouter
import ru.aasmc.jetreddit.viewmodel.MainViewModel

private const val SEARCH_DELAY_MILLIS = 300L

private val defaultCommunities = listOf(
    "raywenderlich",
    "androiddev",
    "puppies"
)

@Composable
fun ChooseCommunityScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val communities: List<String> by viewModel.subreddits.observeAsState(emptyList())
    var searchedText by remember {
        mutableStateOf("")
    }
    var currentJob by remember {
        mutableStateOf<Job?>(null)
    }
    val activeColor = MaterialTheme.colors.onSurface
    LaunchedEffect(Unit) {
        viewModel.searchCommunities(searchedText)
    }
    Column {
        ChooseCommunityTopBar()
        TextField(
            value = searchedText,
            onValueChange = {
                searchedText = it
                currentJob?.cancel()
                currentJob = scope.launch {
                    delay(SEARCH_DELAY_MILLIS)
                    viewModel.searchCommunities(searchedText)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.search))
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = activeColor,
                focusedLabelColor = activeColor,
                cursorColor = activeColor,
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        SearchedCommunities(communities = communities, viewModel = viewModel, modifier)
    }
    BackButtonAction {
        JetRedditRouter.goBack()
    }
}

@Composable
fun SearchedCommunities(
    communities: List<String>,
    viewModel: MainViewModel?,
    modifier: Modifier = Modifier
) {
    communities.forEach {
        Community(
            text = it,
            modifier = modifier,
            onCommunityClicked = {
                viewModel?.selectedCommunity?.postValue(it)
                JetRedditRouter.goBack()
            }
        )
    }
}

@Composable
fun ChooseCommunityTopBar(
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.choose_community),
                fontSize = 16.sp,
                color = colors.primaryVariant
            )
        },
        navigationIcon = {
            IconButton(onClick = { JetRedditRouter.goBack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = colors.primaryVariant
                )
            }
        },
        backgroundColor = colors.primary,
        elevation = 0.dp,
        modifier = modifier
            .height(48.dp)
            .background(Color.Blue)
    )
}

@Preview
@Composable
fun SearchedCommunitiesPreview() {
    Column {
        SearchedCommunities(
            defaultCommunities, null, Modifier
        )
    }
}













