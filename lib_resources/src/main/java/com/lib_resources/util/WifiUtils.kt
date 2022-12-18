package com.lib_resources.util

import android.content.Context
import android.net.wifi.WifiManager
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


/**
 * Created by Jordan on 2022/12/18.
 */
class WifiUtils {

    /**
     * 获取ip地址和本机地址
     * @param isWifi 是否为wifi
     */
    fun getIPAddress(context: Context, isWifi: Boolean): String {
        if (isWifi) {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            return intToIp(wifiInfo.ipAddress)
        } else {
            try {
                val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf: NetworkInterface = en.nextElement()
                    val enumIpAddr: Enumeration<InetAddress> = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress: InetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (e: SocketException) {
                e.printStackTrace()
                return ""
            }
        }
        return ""
    }

    /**
     * 获取mac地址
     */
    fun getMacAddress(context: Context): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.macAddress
    }

    fun intToIp(ipInt: Int): String {
        if (ipInt == 0) return ""
        return try {
            val sb = StringBuilder()
            sb.append(ipInt and 0xFF).append('.')
            sb.append(ipInt shr 8 and 0xFF).append('.')
            sb.append(ipInt shr 16 and 0xFF).append('.')
            sb.append(ipInt shr 24 and 0xFF)
            sb.toString()
        } catch (e: Exception) {
            ""
        }
    }
}