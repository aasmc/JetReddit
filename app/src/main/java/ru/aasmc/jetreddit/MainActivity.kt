package ru.aasmc.jetreddit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.aasmc.jetreddit.viewmodel.MainViewModel
import ru.aasmc.jetreddit.viewmodel.MainViewModelFactory

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    @DelicateCoroutinesApi
    private val viewModel: MainViewModel by viewModels(
        factoryProducer = {
            MainViewModelFactory(
                this,
                (application as JetRedditApplication).dependencyInjector.repository
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetRedditApp(viewModel)
        }
    }
}

