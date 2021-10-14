package com.kalevet.pixagetapp.data.repositories.local.room

import android.content.Context
import androidx.room.Room


object PixabayCashDatabaseProvider {

    private var _pixabayCashDatabase: PixabayCashDatabase? = null

    fun getPixabayCashDatabase(app: Context): PixabayCashDatabase {
        return _pixabayCashDatabase ?: buildPixabayCashDatabase(app)
    }


    private fun buildPixabayCashDatabase(app: Context): PixabayCashDatabase {
        return Room.databaseBuilder(
            app,
            PixabayCashDatabase::class.java,
            "PixabayCashDB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}