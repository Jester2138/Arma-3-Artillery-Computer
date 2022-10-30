package com.armaApp.armaFDC

import android.util.Log
import kotlin.math.*

// calc quadrant elevation in mils from range, velocity, and elevation difference
fun getQuadElevHigh (range: Int, velocity: Float, elevDiff: Int): Int {
    return (
        atan(
            (velocity.pow(2) + sqrt(velocity.pow(4) - 9.81 * (9.81 * range.toFloat().pow(2) + 2 * elevDiff * velocity.pow(2))))
            /
            (9.81 * range)
            ) * 180 / Math.PI * 17.777778
    ).toInt()
}
fun getQuadElevLow (range: Int, velocity: Float, elevDiff: Int): Int {
    return (
        atan(
            (velocity.pow(2) - sqrt(velocity.pow(4) - 9.81 * (9.81 * range.toFloat().pow(2) + 2 * elevDiff * velocity.pow(2))))
                    /
                    (9.81 * range)
        ) * 180 / Math.PI * 17.777778
    ).toInt()
}

// calc time of flight from range, velocity, and quadrant elevation in mils
fun getTOF (range: Int, velocity: Float, quadElev: Int): Int {
    Log.i("Jesse", "getTOF in range: $range velocity: $velocity quadElev: $quadElev")
    return range / (velocity * cos((quadElev * 0.000982))).toInt()
}

// find azimuth between point p and q
fun getAzimuth (p: List<Float>, q: List<Float>): Float {
    var azimuthDeg = (atan2(((q[0]-p[0]).toDouble()),((q[1]-p[1]).toDouble()))/Math.PI*180F).toFloat()
    if (azimuthDeg < 0F) {
        azimuthDeg += 360F
    }
    Log.i("Jesse", "getRange in p: $p q: $q")
    return azimuthDeg/360F*6400F
}

// find range between point p and q
fun getRange (p: List<Float>, q: List<Float>): Float {
    val x1 = p[0]; val y1 = p[1]
    val x2 = q[0]; val y2 = q[1]
    Log.i("Jesse", "getRange in p: $p q: $q")
    return sqrt(
        ((x2 - x1).pow(2) + (y2 - y1).pow(2))
    )
}

// sanitize raw grid input to 10-digit grid standard corrected for keypad
fun readGrid (x: String, y: String, k: String, mX: String, mY: String): List<Float> {

    // map corr always default to 00000, which is the default map bottom-left in 6 digit format
    val mXIn = mX.ifEmpty { "000" }
    val mYIn = mY.ifEmpty { "000" }

    val gridIn = arrayOf (x, y, mXIn, mYIn)
    val gridOut = mutableListOf<Float>()
    var error = false

    // convert x and y of grid and map corr to 5 digit value
    gridIn.forEach {
        when ( it.length ) {
            3 -> { gridOut.add(it.toFloat() * 100F) }
            4 -> { gridOut.add(it.toFloat() * 10F) }
            5 -> { gridOut.add(it.toFloat()) }
            else -> {
                Log.i("Jesse", "readGrid received invalid grid or map corr input.")
                error = true
            }
        }
    }

    // correct for keypad
    if ( !error && k.trim().isNotEmpty() ) {
        when ( k.toFloat() ) {
            1F -> { gridOut[0] += 17F; gridOut[1] += 17F }
            2F -> { gridOut[0] += 50F; gridOut[1] += 17F }
            3F -> { gridOut[0] += 83F; gridOut[1] += 17F }
            4F -> { gridOut[0] += 17F; gridOut[1] += 50F }
            5F -> { gridOut[0] += 50F; gridOut[1] += 50F }
            6F -> { gridOut[0] += 83F; gridOut[1] += 50F }
            7F -> { gridOut[0] += 17F; gridOut[1] += 83F }
            8F -> { gridOut[0] += 50F; gridOut[1] += 83F }
            9F -> { gridOut[0] += 83F; gridOut[1] += 83F }
            else -> {
                Log.i("Jesse", "readGrid not adjusting for keypad.")
            }
        }
    }

    // correct for map not having the 0,0 in the bottom left
    if ( !error ) {
        if (gridOut[0] < gridOut[2]) {
            gridOut[0] += 100000F
            Log.i("Jesse", "readGrid correcting X axis.")
        }
        if (gridOut[1] < gridOut[3]) {
            gridOut[1] += 100000F
            Log.i("Jesse", "readGrid correcting Y axis.")
        }
    }

    return if ( !error ) {
        listOf(gridOut[0], gridOut[1])
    } else {
        listOf(0F, 0F, 0F)
    }
}