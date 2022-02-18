package user50.sample.function.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import user50.sample.R
import user50.sample.function.init.Activity_Init
import user50.sample.util.Util
import java.util.*

/**
 * 권한 확인 및 요청 클래스.
 * Created by abyser on 2017-06-07.
 */

class Activity_Permission : Activity() {

    companion object {

        /** 로그 태그  */
        private val LOG_TAG = Activity_Permission::class.java.getSimpleName()

        /** 요청 권한들  */
        private val PERMISSIONS = arrayOf(
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.CHANGE_NETWORK_STATE,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.CHANGE_WIFI_STATE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_PHONE_STATE
        )

        /** 다음으로 진행 할 액티비티 클래스  */
        private val NEXT_ACTIVITY = Activity_Init::class.java

        /** 권한 요청 코드  */
        private val REQUEST_PERMISSION_CODE = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        initPermission()

    }

    /**
     * 초기 권한 확인 및 요청.
     */
    private fun initPermission() {

        val deniedPermissions = ArrayList<String>()
        for (i in PERMISSIONS.indices) {

            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(PERMISSIONS[i])
            }

        }

        if (deniedPermissions.isNotEmpty()) {

            val denied_permissions = arrayOfNulls<String>(deniedPermissions.size)
            requestPermissions(deniedPermissions.toArray(denied_permissions), REQUEST_PERMISSION_CODE)

        } else {
            startNext()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == REQUEST_PERMISSION_CODE) {

            for (i in permissions.indices) {

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    Util.showToast(this@Activity_Permission, permissions.get(i) + " 권한을 허용하여야 앱을 사용 가능합니다.", true)
                    finish()

                    return

                }

            }

            startNext()

        }

    }

    /**
     * 다음 화면으로 이동.
     */
    private fun startNext() {

        val i = Intent(this@Activity_Permission, NEXT_ACTIVITY)
        startActivity(i)
        finish()

    }

}
