package com.example.tasklistfecth.database

import androidx.room.*

@Dao
interface ListDao {
    @Query("SELECT * FROM Titletable")
    fun getAll(): TitleDb?


    @Insert
    fun insertAll(vararg item: TitleDb)

    @Delete
    fun deleteAll(vararg item: TitleDb)

}