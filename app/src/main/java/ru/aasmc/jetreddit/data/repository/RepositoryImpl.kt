package ru.aasmc.jetreddit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.aasmc.jetreddit.data.database.dao.PostDao
import ru.aasmc.jetreddit.data.database.dbmapper.DbMapper
import ru.aasmc.jetreddit.data.database.model.PostDbModel
import ru.aasmc.jetreddit.domain.model.PostModel

@DelicateCoroutinesApi
class RepositoryImpl(
    private val postDao: PostDao, private val mapper: DbMapper
) : Repository {

    private var searchedText = ""

    private val allPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
        MutableLiveData<List<PostModel>>()
    }

    private val ownedPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
        MutableLiveData<List<PostModel>>()
    }

    init {
        initDatabase(this::updatePostLiveData)
    }

    private fun initDatabase(postInitAction: () -> Unit) {
        GlobalScope.launch {
            val posts = PostDbModel.DEFAULT_POSTS.toTypedArray()
            val dbPosts = postDao.getAllPosts()
            if (dbPosts.isNullOrEmpty()) {
                postDao.insertAll(*posts)
            }

            postInitAction.invoke()
        }
    }

    private fun getAllPostsFromDatabase(): List<PostModel> =
        postDao.getAllPosts().map(mapper::mapPost)

    private fun getAllOwnedPostsFromDatabase(): List<PostModel> =
        postDao.getAllOwnedPosts("raywenderlich.com").map(mapper::mapPost)

    override fun getAllPosts(): LiveData<List<PostModel>> = allPostsLiveData

    override fun getAllOwnedPosts(): LiveData<List<PostModel>> = ownedPostsLiveData
    override fun getAllSubreddits(searchedText: String): List<String> {
        this.searchedText = searchedText
        if (searchedText.isNotEmpty()) {
            return postDao.getAllSubreddits().filter { it.contains(searchedText) }
        }
        return postDao.getAllSubreddits()
    }

    override fun insert(postModel: PostModel) {
        postDao.insert(mapper.mapDbPost(postModel))
        updatePostLiveData()
    }

    override fun deleteAll() {
        postDao.deleteAll()
        updatePostLiveData()
    }

    private fun updatePostLiveData() {
        allPostsLiveData.postValue(getAllPostsFromDatabase())
        ownedPostsLiveData.postValue(getAllOwnedPostsFromDatabase())
    }
}