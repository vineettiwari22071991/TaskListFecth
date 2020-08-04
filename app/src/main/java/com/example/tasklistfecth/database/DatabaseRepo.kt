package com.example.tasklistfecth.database

import com.example.tasklistfecth.core.TaskApplication
import com.example.tasklistfecth.model.Listmodel


class DatabaseRepo {

    private val db = AppDatabase(TaskApplication.instance)

    fun additem(listitem: Listmodel) {
        val listdata = TitleDb(1, listitem.title, listitem.rows)

        db.ListDao().deleteAll(listdata)
        db.ListDao().insertAll(listdata)

    }

    fun getListAll(): Listmodel? {
        return db.ListDao().getAll()?.row?.let {
            db.ListDao().getAll()?.title?.let { it1 ->
                Listmodel(
                    it,
                    it1
                )
            }
        }
    }

}