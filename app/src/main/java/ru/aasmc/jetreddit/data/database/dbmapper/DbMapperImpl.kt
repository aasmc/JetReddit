package ru.aasmc.jetreddit.data.database.dbmapper

import ru.aasmc.jetreddit.data.database.model.PostDbModel
import ru.aasmc.jetreddit.domain.model.PostModel
import ru.aasmc.jetreddit.domain.model.PostType
import java.util.concurrent.TimeUnit

class DbMapperImpl : DbMapper {
    override fun mapPost(dbPostModel: PostDbModel): PostModel {
        with(dbPostModel) {
            return PostModel(
                username,
                subreddit,
                title,
                text,
                likes.toString(),
                comments.toString(),
                PostType.fromType(type),
                getPostedDate(datePosted),
                image
            )
        }
    }

    override fun mapDbPost(postModel: PostModel): PostDbModel {
        with(postModel) {
            return PostDbModel(
                null,
                "raywenderlich",
                subreddit,
                title,
                text,
                0,
                0,
                type.type,
                System.currentTimeMillis(),
                false,
                image
            )
        }
    }

    private fun getPostedDate(date: Long): String {
        val hoursPassed =
            TimeUnit.HOURS.convert(System.currentTimeMillis() - date, TimeUnit.MILLISECONDS)
        if (hoursPassed > 24) {
            val daysPassed = TimeUnit.DAYS.convert(hoursPassed, TimeUnit.HOURS)
            if (daysPassed > 30) {
                if (daysPassed > 365) {
                    return (daysPassed / 365).toString() + "y"
                }
                return (daysPassed / 30).toString() + "mo"
            }
            return daysPassed.toString() + "d"
        }
        return hoursPassed.inc().toString() + "h"
    }

}