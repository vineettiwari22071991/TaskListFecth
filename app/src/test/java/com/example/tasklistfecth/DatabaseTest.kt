package com.example.tasklistfecth

import androidx.room.Room
import com.example.tasklistfecth.core.TaskApplication
import com.example.tasklistfecth.database.AppDatabase
import com.example.tasklistfecth.database.ListDao
import com.example.tasklistfecth.database.TitleDb
import com.example.tasklistfecth.model.Row
import com.example.tasklistfecth.viewmodel.MainActivityviewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException


class DatabaseTest {
    private lateinit var Listdao: ListDao
    private lateinit var db: AppDatabase
    private lateinit var rowdatalist: ArrayList<Row>

    @InjectMocks
    var listviewmodel = MainActivityviewModel()

    private var testsingle: TitleDb? = null

    @Before
    fun createDb() {
        MockitoAnnotations.initMocks(this)

        db = Room.inMemoryDatabaseBuilder(
            TaskApplication.instance, AppDatabase::class.java
        ).build()
        Listdao = db.ListDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {

        val rowdata = Row("description", "", "Title")
        rowdatalist.add(rowdata)
        val listdata = TitleDb(1, "title", rowdatalist)
        Listdao.insertAll(listdata)
        testsingle = listdata

        Mockito.`when`(Listdao.getAll()).thenReturn(testsingle)


        listviewmodel.getdataOffline()
        Assert.assertEquals(1, listviewmodel.listdata.value)

    }


}
