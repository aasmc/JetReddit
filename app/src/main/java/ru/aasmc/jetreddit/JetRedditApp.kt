package ru.aasmc.jetreddit

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.aasmc.jetreddit.appdrawer.AppDrawer
import ru.aasmc.jetreddit.routing.JetRedditRouter
import ru.aasmc.jetreddit.routing.Screen
import ru.aasmc.jetreddit.screens.*
import ru.aasmc.jetreddit.theme.JetRedditTheme
import ru.aasmc.jetreddit.viewmodel.MainViewModel

@ExperimentalAnimationApi
@Composable
fun JetRedditApp(viewModel: MainViewModel) {
    JetRedditTheme {
        AppContent(viewModel)
    }
}

@ExperimentalAnimationApi
@Composable
private fun AppContent(viewModel: MainViewModel) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Crossfade(targetState = JetRedditRouter.currentScreen) { screenState: MutableState<Screen> ->
        Scaffold(
            topBar = getTopBar(screenState.value, scaffoldState, coroutineScope),
            drawerContent = {
                AppDrawer(
                    closeDrawerAction = { coroutineScope.launch { scaffoldState.drawerState.close() } }
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationComponent(screenState = screenState)
            },
            content = {
                MainScreenContainer(
                    modifier = Modifier.padding(bottom = 56.dp),
                    screenState = screenState,
                    viewModel = viewModel
                )
            }
        )
    }
}

fun getTopBar(
    screenState: Screen,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
): @Composable (() -> Unit) {
    if (screenState == Screen.MyProfile) {
        return {}
    } else {
        return {
            TopAppBar(scaffoldState = scaffoldState, coroutineScope = coroutineScope)
        }
    }
}

@Composable
fun TopAppBar(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {
    val colors = MaterialTheme.colors
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = JetRedditRouter.currentScreen.value.titleResId),
                color = colors.primaryVariant
            )
        },
        backgroundColor = colors.surface,
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(id = R.string.account),
                    tint = Color.LightGray
                )
            }
        }
    )

}

@ExperimentalAnimationApi
@Composable
private fun MainScreenContainer(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>,
    viewModel: MainViewModel
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        when (screenState.value) {
            Screen.Home -> HomeScreen(viewModel)
            Screen.MyProfile -> MyProfileScreen(viewModel)
            Screen.NewPost -> AddScreen(viewModel)
            Screen.Subscriptions -> SubredditsScreen()
            Screen.ChooseCommunity -> ChooseCommunityScreen(viewModel)
        }
    }

}

@Composable
private fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
    var selectedItem by remember {
        mutableStateOf(0)
    }

    // todo - consider moving the list of navigation items out of the composable function to
    // prevent creating the list over and over again on recompositions...
    val items = listOf(
        NavigationItem(0, R.drawable.ic_baseline_home_24, R.string.home_icon, Screen.Home),
        NavigationItem(
            1,
            R.drawable.ic_baseline_format_list_bulleted_24,
            R.string.subscriptions_icon,
            Screen.Subscriptions
        ),
        NavigationItem(2, R.drawable.ic_baseline_add_24, R.string.post_icon, Screen.NewPost)
    )
    BottomNavigation(modifier = modifier) {
        items.forEach { navigationItem ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = navigationItem.vectorResourceId),
                        contentDescription = stringResource(id = navigationItem.contentDescriptionResourceId)
                    )
                },
                selected = selectedItem == navigationItem.index,
                onClick = {
                    selectedItem = navigationItem.index
                    screenState.value = navigationItem.screen
                }
            )
        }
    }
}

private data class NavigationItem(
    val index: Int,
    val vectorResourceId: Int,
    val contentDescriptionResourceId: Int,
    val screen: Screen
)





















