package com.example.tasklistfecth.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklistfecth.model.Listmodel
import com.example.tasklistfecth.model.Row
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.example.tasklistfecth.database.DatabaseRepo
import com.example.tasklistfecth.api.ListDataService

//ViewModel Class
class MainActivityviewModel : ViewModel() {

    private val databaseRepo = DatabaseRepo()

    private val listDataService = ListDataService()

    private val disposable = CompositeDisposable()

    val listdata = MutableLiveData<Listmodel>()

    val listerror = MutableLiveData<Boolean>()

    val loading = MutableLiveData<Boolean>()

//Refresh Server Call
    fun refresh() {

        fecthdatafromserver()
    }

//Load data from Server and Save to database also
    private fun fecthdatafromserver() {

        loading.value = true

        disposable.add(
            listDataService.getList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                    DisposableSingleObserver<Listmodel>() {
                    override fun onSuccess(t: Listmodel) {
                        loading.value = false
                        val toRemove = ArrayList<Row>()
                        for (item in t.rows) {
                            if (item.description.isNullOrBlank() && item.imageHref.isNullOrBlank() && item.title.isNullOrBlank()) {
                                toRemove.add(item)
                            }
                        }


                        t.rows.removeAll(toRemove)
                        databaseRepo.additem(t)
                        listdata.value = t
                        listerror.value = false

                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        listerror.value = true
                    }
                })
        )

    }

    //Clear Disposable
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    //Fetch data from Server when network is not available
    fun getdataOffline() {

        if(databaseRepo.getListAll()==null || databaseRepo.getListAll()?.rows.isNullOrEmpty())
        {
            listdata.value = null
            listerror.value = true
            loading.value = false
        }else {

            listdata.value = databaseRepo.getListAll()
            listerror.value = false
            loading.value = false
        }

    }


}