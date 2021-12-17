package ru.aasmc.jetreddit.domain.model

import ru.aasmc.jetreddit.R

data class PostModel(
    val username: String,
    val subreddit: String,
    val title: String,
    val text: String,
    val likes: String,
    val comments: String,
    val type: PostType,
    val postedTime: String,
    val image: Int?
) {
    companion object {
        val DEFAULT_POST = PostModel(
            "raywenderlich",
            "androiddev",
            "This is a sample Jetpack Compose app",
            "",
            "5563",
            "523",
            PostType.IMAGE,
            "4h",
            R.drawable.compose_course
        )

        val EMPTY = PostModel(
            "raywenderlich",
            "raywenderlich.com",
            "",
            "",
            "0",
            "0",
            PostType.TEXT,
            "0h",
            R.drawable.compose_course
        )
    }
}