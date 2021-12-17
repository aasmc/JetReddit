package ru.aasmc.jetreddit.data.repository

import androidx.lifecycle.LiveData
import ru.aasmc.jetreddit.domain.model.PostModel

interface Repository {

    fun getAllPosts(): LiveData<List<PostModel>>

    fun getAllOwnedPosts(): LiveData<List<PostModel>>

    fun getAllSubreddits(searchedText: String): List<String>

    fun insert(postModel: PostModel)

    fun deleteAll()
}