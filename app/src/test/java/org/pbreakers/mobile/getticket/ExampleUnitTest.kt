package org.pbreakers.mobile.getticket

import org.junit.Test

import org.junit.Assert.*
import org.pbreakers.mobile.getticket.util.getDateFromString
import org.pbreakers.mobile.getticket.util.getFormattedDate
import org.pbreakers.mobile.getticket.util.getTimeFromString
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testDate() {
        val date: Date? = "23/04/2019".getDateFromString()

        assertEquals(date?.getFormattedDate("EEE dd/MM/yyyy"), "23/04/2019")
    }

    @Test
    fun testTime() {
        val date: Date? = "22:30".getTimeFromString()

        assertEquals(date?.getFormattedDate("HH:mm"), "22:30")
    }
}
