package com.example.testapp.db

import androidx.room.*

@Dao
abstract class BootInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBootInfo(bootInfo: BootInfo)

    @Query("SELECT * FROM $TABLE_NAME")
    abstract fun getBootInfoList(): List<BootInfo?>?


    companion object {
        const val TABLE_NAME = "BootInfo"
    }
}