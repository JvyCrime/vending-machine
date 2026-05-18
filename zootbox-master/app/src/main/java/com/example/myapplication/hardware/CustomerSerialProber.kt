// CustomSerialProber.kt
package com.example.myapplication.hardware

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver
import com.hoho.android.usbserial.driver.FtdiSerialDriver
import com.hoho.android.usbserial.driver.ProbeTable
import com.hoho.android.usbserial.driver.UsbSerialProber

object CustomSerialProber {
    fun getCustomProber(): UsbSerialProber {
        val table = ProbeTable()
        // Nayax VPOS Touch via XChiPi-X adapter (FTDI FT231X — USE THIS, returns Marshall packets)
        table.addProduct(0x0403, 0x6015, FtdiSerialDriver::class.java)
        // Nayax VPOS Touch CDC-ACM fallback (returns NAK only)
        table.addProduct(0x26f1, 0x5650, CdcAcmSerialDriver::class.java)
        return UsbSerialProber(table)
    }
}
