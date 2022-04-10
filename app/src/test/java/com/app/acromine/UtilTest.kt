package com.app.acromine

import com.app.acromine.Util.Utils


import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UtilTest {

    @Test
    fun isEmail_ReturnsFalse() {
        assertFalse(Utils.isSfsOrIfsEmail("HMM"))
    }

    @Test
    fun isEmail_ReturnsTrue() {
        assertTrue(Utils.isSfsOrIfsEmail("HMM@tcs.com"))
    }

    @Test
    fun isNullOrBlank_ReturnsTrue() {
        assertTrue(Utils.isSfsOrIfsNullorBlank(null))
    }

    @Test
    fun isNullOrBlank_ReturnsTrue1() {
        assertTrue(Utils.isSfsOrIfsNullorBlank(""))
    }

    @Test
    fun isNullOrBlank_ReturnsFalse() {
        assertFalse(Utils.isSfsOrIfsNullorBlank("TMM"))
    }




}