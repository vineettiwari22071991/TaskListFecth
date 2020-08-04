package com.example.tasklistfecth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklistfecth.model.Row
import com.example.tasklistfecth.R
import com.example.tasklistfecth.util.getProgressDrawable
import com.example.tasklistfecth.util.loadimage
import kotlinx.android.synthetic.main.item_rv.view.*


class ListviewAdapter(private var datalist: ArrayList<Row>, private var iListdata: IListdata) :
    RecyclerView.Adapter<ListviewAdapter.MyViewHolder>() {


    //Get a list data an refresh the recyclerView
    fun updateList(newdatalist: ArrayList<Row>) {
        datalist.clear()
        datalist.addAll(newdatalist)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))


    override fun getItemCount() = datalist.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.loadView(datalist[position], iListdata)
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val header = view.tv_header
        private val subhead = view.tv_sub
        private val image = view.img
        private val listparent = view.list_parent
        private val progressDrawable = getProgressDrawable(view.context)

        //load data to view
        fun loadView(listdata: Row, iListdata: IListdata) {
            header.text = listdata.title
            subhead.text = listdata.description
            image.loadimage(listdata.imageHref, progressDrawable)
            listparent.setOnClickListener {
                iListdata.showlistdata(listdata.title)

            }
        }


    }


    interface IListdata {
        fun showlistdata(title: String)
    }
}