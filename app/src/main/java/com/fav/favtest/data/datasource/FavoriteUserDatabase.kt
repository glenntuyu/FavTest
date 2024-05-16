package com.fav.favtest.data.datasource

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fav.favtest.data.model.UserDataView

/**
 * Created by glenntuyu on 15/05/2024.
 */
@Database(
    entities = [UserDataView::class],
    version = 2,
    exportSchema = false
)
abstract class FavoriteUserDatabase : RoomDatabase() {

    abstract fun favoriteUsersDao(): FavoriteUserDao

    companion object {

        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null

        fun getInstance(context: Context): FavoriteUserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteUserDatabase::class.java, "favoriteUser.db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}