package com.leoapps.eggy.base.database.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.leoapps.eggy.logs.data.LogDao
import com.leoapps.eggy.logs.data.model.LogEntity

@Database(
    entities = [LogEntity::class],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(MyDatabaseConstructor::class)
abstract class EggyDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao

    companion object {
        const val DATABASE_NAME = "eggy_database.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object MyDatabaseConstructor : RoomDatabaseConstructor<EggyDatabase> {
    override fun initialize(): EggyDatabase
}