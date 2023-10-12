package com.example.connectivitymanagertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var networkModule: NetworkModule;
    private val TAG = "NetworkModule";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkModule = NetworkModule(applicationContext);
        setContentView(R.layout.activity_main);
        val switchToCellularButton = findViewById<Button>(R.id.switchToCellularBtn);
        val switchToDefaultButton = findViewById<Button>(R.id.switchToDefaultBtn);

        switchToCellularButton.setOnClickListener({
            Log.d(TAG, "SwitchToCellular clicked");
            networkModule.changeNetworkToCellular();
        })
        switchToDefaultButton.setOnClickListener({
            Log.d(TAG, "switchToDefaultButton clicked");
            networkModule.changeNetworkToWifi();
        })
    }
}