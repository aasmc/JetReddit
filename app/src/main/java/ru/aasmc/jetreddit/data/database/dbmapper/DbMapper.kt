package ru.aasmc.jetreddit.data.database.dbmapper

import ru.aasmc.jetreddit.data.database.model.PostDbModel
import ru.aasmc.jetreddit.domain.model.PostModel

interface DbMapper {
    fun mapPost(dbPostModel: PostDbModel): PostModel

    fun mapDbPost(postModel: PostModel): PostDbModel
}