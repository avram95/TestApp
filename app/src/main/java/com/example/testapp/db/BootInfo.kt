package com.example.testapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = BootInfoDao.TABLE_NAME)
data class BootInfo(@PrimaryKey(autoGenerate = true) val id: Int = 0, val timestamp: Long = 0)
