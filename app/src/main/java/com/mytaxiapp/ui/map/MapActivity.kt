package com.mytaxiapp.ui.map

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.google.android.gms.maps.model.LatLng
import com.mytaxiapp.BaseApplication
import com.mytaxiapp.R
import com.mytaxiapp.data.model.Placemark
import com.mytaxiapp.data.remote.VehicleInterface
import com.mytaxiapp.di.component.VehicleComponent
import com.mytaxiapp.ui.base.BaseActivity
import com.mytaxiapp.ui.listvehicles.VehiclePresenter
import com.mytaxiapp.ui.listvehicles.VehicleView
import com.mytaxiapp.util.Logger
import com.mytaxiapp.util.NetworkUtil
import com.mytaxiapp.util.ui.MaterialProgressBar

import java.util.ArrayList

import javax.inject.Inject

import rx.subscriptions.CompositeSubscription

class MapActivity : BaseActivity(), VehicleView {

    @field:[Inject]
    lateinit var presenter: VehiclePresenter

    @field:[Inject]
    lateinit var vehicleInterface: VehicleInterface

    private val logger = Logger.getLogger(javaClass)
    private var mCompositeSubscription: CompositeSubscription? = null
    private var progressBar: MaterialProgressBar? = null
    private var snackbarOffline: Snackbar? = null
    private var mainLayout: LinearLayout? = null
    private var recyclerView: RecyclerView? = null

    private val LATLNG = "latlng"
    private val ADDRESS = "address"
    private val NAMES = "names"
    private val POSITION = "position"
    private var adapterPosition = 0

    override fun setupActivity(component: VehicleComponent?, savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_map)
        BaseApplication.vehicleComponent.inject(this)
        presenter!!.attachView(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mCompositeSubscription = CompositeSubscription()
        init()
        loadView()
        setAdapterPosition()
    }

    // Initialize the view
    fun init() {
        mainLayout = findViewById(R.id.map_layout) as LinearLayout
        progressBar = findViewById(R.id.material_progress_bar) as MaterialProgressBar
        recyclerView = findViewById(R.id.item_list) as RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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

    private fun setAdapterPosition() {
        val extras = intent.extras
        if (extras != null) {
            adapterPosition = extras.getInt("position")
        }
    }

    fun vehicleList() {
        presenter!!.getVehicleList(vehicleInterface!!, mCompositeSubscription!!)
    }

    override fun setAdapter(vehicleItemList: ArrayList<Placemark>) {
        if (vehicleItemList.size > 0) {
            recyclerView!!.adapter = MapItemListAdapter(vehicleItemList, this@MapActivity)
            recyclerView!!.layoutManager.scrollToPosition(adapterPosition)


            // fragment setup
            val latlngs = ArrayList<LatLng>()
            val address = ArrayList<String>()
            val names = ArrayList<String>()

            for (placemark in vehicleItemList) {
                latlngs.add(LatLng(placemark.coordinates!![1], placemark.coordinates!![0]))
                address.add(placemark.address!!.toString())
                names.add(placemark.name!!.toString())
            }

            val arguments = Bundle()
            arguments.putParcelableArrayList(LATLNG, latlngs)
            arguments.putStringArrayList(ADDRESS, address)
            arguments.putStringArrayList(NAMES, names)
            arguments.putInt(POSITION, adapterPosition)
            val fragment = MapFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
