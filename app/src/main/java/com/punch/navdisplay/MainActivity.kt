package com.punch.navdisplay

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.punch.navdisplay.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("punch_nav_prefs", Context.MODE_PRIVATE)

        // Load ASCII preference
        binding.switchAsciiMode.isChecked = prefs.getBoolean("use_ascii", false)
        binding.switchAsciiMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("use_ascii", isChecked).apply()
            Toast.makeText(this, if (isChecked) "ASCII Mode Enabled" else "Unicode Mode Enabled", Toast.LENGTH_SHORT).show()
        }

        // Grant permission button
        binding.btnGrantPermission.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_DETAIL_SETTINGS).apply {
                putExtra(
                    Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME,
                    ComponentName(this@MainActivity, PunchNavService::class.java).flattenToString()
                )
            })
        }

        // Send test preview
        binding.btnSendTest.setOnClickListener {
            val service = PunchNavService.instance
            if (service != null) {
                val useAscii = binding.switchAsciiMode.isChecked
                service.sendTestPreview(useAscii)
                Toast.makeText(this, "Sent test maneuver to car Bluetooth!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Service not running. Please grant Notification Access first.", Toast.LENGTH_LONG).show()
            }
        }

        // Listen for live updates
        PunchNavService.onDataUpdated = { line1, line2 ->
            runOnUiThread {
                binding.tvPreviewLine1.text = line1
                binding.tvPreviewLine2.text = line2
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updatePermissionStatus()
    }

    private fun updatePermissionStatus() {
        val isGranted = isNotificationServiceEnabled()
        if (isGranted) {
            binding.tvPermissionStatus.text = "● Active & Ready"
            binding.tvPermissionStatus.setTextColor(Color.parseColor("#4CAF50"))
            binding.btnGrantPermission.isEnabled = false
            binding.btnGrantPermission.text = "Notification Access Granted"
            binding.btnGrantPermission.setBackgroundColor(Color.parseColor("#2E7D32"))
        } else {
            binding.tvPermissionStatus.text = "● Permission Required"
            binding.tvPermissionStatus.setTextColor(Color.parseColor("#FF5252"))
            binding.btnGrantPermission.isEnabled = true
            binding.btnGrantPermission.text = "Grant Notification Access"
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(pkgName)
    }
}
