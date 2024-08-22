package com.example.multiplication

import com.example.multiplication.ActivityType.DivRepository
import com.example.multiplication.ActivityType.MultiRepository

object RepositoryFactory {
    fun createRepository(i:Boolean): Repository.BaseRepository {
        return if (i) {
            MultiRepository()
        } else {
            DivRepository()
        }
    }
}