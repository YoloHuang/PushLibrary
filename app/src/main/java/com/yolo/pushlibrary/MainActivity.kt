package com.yolo.pushlibrary

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.kidosc.pushlibrary.PushTargetManager
import com.kidosc.pushlibrary.handle.PushAction
import com.kidosc.pushlibrary.model.ReceiverInfo
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.*

class MainActivity : AppCompatActivity() {


    var infomation: String = ""

    val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updatePushData(intent)
        }
    }
    val mFilter = object : IntentFilter(TestPushReceiver.ACTION_BROADCAST) {

    }


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_log.text = infomation
        initEvent()
        bt_loginOut.isEnabled = false
        bt_loginOut.isClickable = false
        bt_loginIn.setOnClickListener {
            PushTargetManager.getInstance().loginIn(this)
            bt_loginOut.isEnabled = true
            bt_loginOut.isClickable = true
        }
        bt_loginOut.setOnClickListener {
            PushTargetManager.getInstance().loginOut()
            bt_loginOut.isEnabled = false
            bt_loginOut.isClickable = false
        }
        bt_setAlias.setOnClickListener {
            if (et_alias.text.toString().isNotEmpty()) {
                PushTargetManager.getInstance().setAlias(et_alias.text.toString())
            } else {
                Toast.makeText(this, getString(R.string.tip_alias), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requestAllPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    private fun initEvent() {
        mFilter.addCategory(packageName)
        registerReceiver(mReceiver, mFilter)
    }

    private fun updatePushData(intent: Intent) {
        var stringExtra = ""
        val info = intent.getSerializableExtra(TestPushReceiver.PUSH_LOG) as ReceiverInfo
        when(info.type){
            PushAction.RECEIVE_INIT_RESULT -> {
                stringExtra +=getString(R.string.title)+info.pushTarget
                stringExtra +=(System.getProperty("line.separator"))
                infomation += (System.getProperty("line.separator"))
                stringExtra +=info.title
            }
            PushAction.RECEIVE_SET_ALIAS -> {
                stringExtra +=info.title + info.content
            }
            PushAction.RECEIVE_LOGIN_OUT -> {
                stringExtra +=info.title + info.content
            }
            PushAction.RECEIVE_NOTIFICATION_CLICK -> {
                stringExtra +=getString(R.string.notification_click) + info.title
            }
            PushAction.RECEIVE_NOTIFICATION -> {
                stringExtra +=getString(R.string.notification_receive) + info.title
            }
            PushAction.RECEIVE_TOKEN_SETED ->{
                stringExtra += getString(R.string.token_seted)+info.content
            }
            PushAction.RECEIVE_MESSAGE -> {
                stringExtra +=getString(R.string.message_receive) + info.title
            }
            else ->{
                stringExtra += info.toString()
            }
        }
        if (stringExtra.isNotEmpty()) {
            infomation += stringExtra
            infomation += (System.getProperty("line.separator"))
            infomation += (System.getProperty("line.separator"))
            runOnUiThread { tv_log.text = infomation }
        }
    }

    @NeedsPermission(value = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION))
    fun requestAllPermission() {

    }

    @OnShowRationale(value = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION))
    internal fun showRationale(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setMessage("请同意app权限，否则app，将不能使用")
                .setPositiveButton("继续") { dialog, button -> request.proceed() }
                .setNegativeButton("取消") { dialog, button -> request.cancel() }
                .show()
    }

    @OnPermissionDenied(value = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION))
    internal fun showDenied() {
        Toast.makeText(this, "拒绝，需要到系统设置，自己设定，否者有可能导致消息推送不成功！", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(value = *arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION))
    internal fun showNeverAskAgain() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}
