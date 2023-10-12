package com.example.connectivitymanagertest
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

class NetworkModule(context: Context) {
    private val appContext = context;
    private val connectivityManager = appContext.getSystemService(ConnectivityManager::class.java);
    private val TAG = "NetworkModule";

     fun changeNetworkToCellular() {
        bindAppToCellularTransportNetwork();
    }

    fun changeNetworkToWifi() {
        bindAppToDefaultNetwork();
    }

    private fun bindAppToCellularTransportNetwork() {
        val cellularNetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build();

        connectivityManager.requestNetwork(cellularNetworkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network : Network) {
                printNetwork("Available Network: ", network)
                connectivityManager.bindProcessToNetwork(network)
                Log.d(TAG, "switched to cellular");
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                printNetwork("Losing Network: ", network)
            }

            override fun onLost(network : Network) {
                printNetwork("Lost Network: ", network)
            }

            override fun onUnavailable() {
                Log.d(TAG, "Unavailable Network")
            }

            override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
                printNetwork("Capabilities changed : ", network)
            }

            override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
                Log.d(TAG, "Link properties changed: $linkProperties")
            }
        }, 1000)
    }

    private fun bindAppToDefaultNetwork() {
        Log.d(TAG, "Listen to wifi")
        connectivityManager.bindProcessToNetwork(null);
    }

    private fun printNetwork(prefix: String, network: Network) {
        val currActiveNetworkCap = connectivityManager.getNetworkCapabilities(network);
        if(currActiveNetworkCap !== null) {
            val isCellular = currActiveNetworkCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
            val isWifi = currActiveNetworkCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            Log.d(TAG, "$prefix\nisCellular: $isCellular\nisWifi: $isWifi");
        }
    }
}