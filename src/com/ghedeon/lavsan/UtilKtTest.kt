package com.ghedeon.lavsan

import org.junit.Test

class UtilKtTest{

    @Test
    fun foo(){
        val camelCase = snakeToCamel("__aas___bsdf_csdf___")

        println(camelCase)
    }
}