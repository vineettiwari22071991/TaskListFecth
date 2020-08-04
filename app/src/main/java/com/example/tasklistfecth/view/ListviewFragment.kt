package com.example.tasklistfecth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklistfecth.adapter.ListviewAdapter
import com.example.tasklistfecth.model.Listmodel
import com.example.tasklistfecth.R
import com.example.tasklistfecth.util.isInternetAvailable
import com.example.tasklistfecth.viewmodel.MainActivityviewModel
import kotlinx.android.synthetic.main.listviewfragment.*

class ListviewFragment : Fragment(), ListviewAdapter.IListdata {


    private lateinit var viewModel: MainActivityviewModel

    private val listadapter = ListviewAdapter(arrayListOf(), this)

    private lateinit var mparentActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.listviewfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityviewModel::class.java)

        mparentActivity = activity as MainActivity


        //Check Internet Connection if true call server else call localDB
        if (isInternetAvailable(mparentActivity)) {
            viewModel.refresh()
        } else {
            viewModel.getdataOffline()
        }

        //Pull to Refresh
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            if (isInternetAvailable(mparentActivity)) {
                viewModel.refresh()
            } else {
                viewModel.getdataOffline()
            }
        }


        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listadapter
        }

        observerViewmodel()

    }


    private fun observerViewmodel() {

        //Get data from Obsever and set to RecyclerView
        viewModel.listdata.observe(this, Observer { Listdata: Listmodel? ->

                Listdata?.let {
                    swipe.visibility = View.VISIBLE
                    rv_list.visibility = View.VISIBLE
                    listadapter.updateList(Listdata.rows)
                    mparentActivity.settoobarTitle(Listdata.title)
                }


        })
        //Show Error Message
        viewModel.listerror.observe(this, Observer { isError: Boolean? ->

            isError?.let {

                if(it)
                {
                    progress.visibility=View.VISIBLE
                    swipe.visibility = View.VISIBLE
                    rv_list.visibility = View.GONE
                    Toast.makeText(activity, "Something went Wrong... Please check Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }else
                {
                    progress.visibility=View.GONE
                    rv_list.visibility = View.VISIBLE
                }



            }
        })
        //After Loading Complete
        viewModel.loading.observe(this, Observer { isLoading: Boolean? ->

            isLoading?.let {
                progress.visibility = if (it) View.VISIBLE else View.GONE
                {
                    if (it) {
                        swipe.visibility = View.GONE
                        rv_list.visibility = View.GONE

                    }
                }
            }
        })

    }

    override fun showlistdata(title: String) {

        Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
    }
}