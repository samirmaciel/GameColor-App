package com.sm.gamecolor
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sm.gamecolor.domain.ScreenSelection
import com.sm.gamecolor.ui.theme.GameColorTheme
import com.sm.gamecolor.view.drawScreen.DrawingScreen
import com.sm.gamecolor.view.drawScreen.initialScreen.InitialScreen
import com.sm.gamecolor.view.drawScreen.viewModel.DrawScreenViewModel

class MainActivity : ComponentActivity() {

    private val bluetoothManager by lazy {
        applicationContext.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { /* Not needed */ }

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            val canEnableBluetooth = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                perms[Manifest.permission.BLUETOOTH_CONNECT] == true
            } else true

            if(canEnableBluetooth && !isBluetoothEnabled) {
                enableBluetoothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            permissionLauncher.launch(
//                arrayOf(
//                    Manifest.permission.BLUETOOTH_SCAN,
//                    Manifest.permission.BLUETOOTH_CONNECT,
//                )
//            )
//        }

        setContent {
            GameColorTheme {

                val viewModel by viewModels<DrawScreenViewModel>()
                val state by viewModel.stateUI.collectAsState()

                when(state){
                    ScreenSelection.INITIAL -> {
                        InitialScreen(viewModel)
                    }

                    ScreenSelection.DRAW -> {
                        DrawingScreen(viewModel)
                    }
                }

            }
        }
    }
}




