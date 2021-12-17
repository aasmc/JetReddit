package ru.aasmc.jetreddit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aasmc.jetreddit.data.repository.Repository
import ru.aasmc.jetreddit.domain.model.PostModel

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    val allPosts by lazy { repository.getAllPosts() }

    val myPosts by lazy { repository.getAllOwnedPosts() }

    val subreddits by lazy { MutableLiveData<List<String>>() }

    val selectedCommunity: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun searchCommunities(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            subreddits.postValue(repository.getAllSubreddits(searchText))
        }
    }

    fun savePost(postModel: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(postModel.copy(subreddit = selectedCommunity.value ?: ""))
        }
    }
}