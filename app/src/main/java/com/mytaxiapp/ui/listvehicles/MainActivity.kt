package com.mytaxiapp.ui.listvehicles

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.mytaxiapp.BaseApplication
import com.mytaxiapp.R
import com.mytaxiapp.data.model.Placemark
import com.mytaxiapp.data.remote.VehicleInterface
import com.mytaxiapp.di.component.VehicleComponent
import com.mytaxiapp.ui.base.BaseActivity
import com.mytaxiapp.util.Logger
import com.mytaxiapp.util.NetworkUtil
import com.mytaxiapp.util.ui.MaterialProgressBar

import java.util.ArrayList

import javax.inject.Inject

import rx.subscriptions.CompositeSubscription

class MainActivity : BaseActivity(), VehicleView {

    @field:[Inject]
    lateinit var presenter: VehiclePresenter

    @field:[Inject]
    lateinit var vehicleInterface: VehicleInterface

    private val logger = Logger.getLogger(javaClass)
    private var mCompositeSubscription: CompositeSubscription? = null
    private var mainLayout: LinearLayout? = null
    private var adapter: VehicleListAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: MaterialProgressBar? = null
    private var layoutManager: LinearLayoutManager? = null
    private var snackbarOffline: Snackbar? = null

    private var vehItemList: ArrayList<Placemark>? = null


    override fun setupActivity(component: VehicleComponent?, savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        BaseApplication.vehicleComponent.inject(this)
        presenter!!.attachView(this)

        mCompositeSubscription = CompositeSubscription()
        init()
        loadView()
    }

    // Initialize the view
    fun init() {

        mainLayout = findViewById(R.id.main_layout) as LinearLayout
        progressBar = findViewById(R.id.material_progress_bar) as MaterialProgressBar
        layoutManager = LinearLayoutManager(applicationContext)
        recyclerView = findViewById(R.id.vehicles_recyclerview) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = layoutManager
    }


    fun loadView() {
        if (NetworkUtil.isConnected(applicationContext)) {
            vehicleList()
            hideOfflineSnackBar()
        } else {
            displayOfflineSnackbar()
        }
    }


    fun vehicleList() {
        presenter!!.getVehicleList(vehicleInterface!!, mCompositeSubscription!!)
    }

    override fun setAdapter(vehicleItemList: ArrayList<Placemark>) {
        if (vehicleItemList.size > 0) {
            vehItemList = vehicleItemList
            adapter = VehicleListAdapter(applicationContext, vehicleItemList)
            recyclerView!!.adapter = adapter // set adapter on recyclerview
        }
    }


    fun displayOfflineSnackbar() {
        snackbarOffline = Snackbar.make(mainLayout!!, R.string.no_connection_snackbar, Snackbar.LENGTH_INDEFINITE)
        val snackbarText = snackbarOffline!!.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        snackbarText.setTextColor(applicationContext.resources.getColor(android.R.color.white))
        snackbarOffline!!.setAction(R.string.snackbar_action_retry) { loadView() }
        snackbarOffline!!.setActionTextColor(resources.getColor(R.color.colorPrimary))
        snackbarOffline!!.show()
    }


    fun hideOfflineSnackBar() {
        if (snackbarOffline != null && snackbarOffline!!.isShown) {
            snackbarOffline!!.dismiss()
        }
    }

    override fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.GONE
    }


    override fun onDestroy() {
        if (mCompositeSubscription!!.hasSubscriptions()) {
            mCompositeSubscription!!.unsubscribe()
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}
