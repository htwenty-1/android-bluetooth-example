package com.andapp.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val requestEnableBt = 1
    private var bluetoothAdapter: BluetoothAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothPermissionChecker()

        val bluetoothOnBtn = findViewById<Button>(R.id.btnBluetoothOn)
        val bluetoothOffBtn = findViewById<Button>(R.id.btnBluetoothOff)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter!!.isEnabled) {
                bluetoothOnBtn.isClickable = true
            } else {
                bluetoothOffBtn.isClickable = true
            }
        }

        bluetoothOnBtn.setOnClickListener { bluetoothOn() }
        bluetoothOffBtn.setOnClickListener { bluetoothOff() }
    }

    private fun bluetoothPermissionChecker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT
                ),
                requestEnableBt
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH
                ),
                requestEnableBt
            )
        }
    }

    private fun bluetoothOn() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "블루투스를 지원하지 않는 기기입니다", Toast.LENGTH_LONG).show()
        } else {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, requestEnableBt)
        }
    }

    private fun bluetoothOff() {
        bluetoothAdapter?.disable() ?: Toast.makeText(this, "블루투스를 지원하지 않는 기기입니다", Toast.LENGTH_LONG).show()
    }
}