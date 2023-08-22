package com.javier.proyecto_final_kotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.javier.proyecto_final_kotlin.model.Comida

@Database(entities = [Comida::class], version = 1)
abstract class ComidaDataBase : RoomDatabase() {
    abstract fun comidaDao(): ComidaDao

    companion object {
        @Volatile
        private var instance: ComidaDataBase? = null

        fun getDatabase(context: Context): ComidaDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context): ComidaDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ComidaDataBase::class.java,
                "comida_database"
            ).build()
        }
    }
}
