// CustomSerialProber.kt
package com.example.myapplication.hardware

import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.driver.ProbeTable
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver

object CustomSerialProber {
    fun getCustomProber(): UsbSerialProber {
        val table = ProbeTable()
        // Nayax VPOS Touch
        table.addProduct(0x26f1, 0x5650, CdcAcmSerialDriver::class.java)
        return UsbSerialProber(table)
    }
}