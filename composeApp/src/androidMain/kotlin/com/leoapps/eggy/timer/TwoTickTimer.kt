package com.leoapps.eggy.timer

import android.os.CountDownTimer

/**
 * Creates a countdown timer that provides two types of tick intervals.
 *
 * @param millisInFuture The number of milliseconds in the future from the call to start() until the countdown is done.
 * @param shortTickInterval The interval in milliseconds for the short tick events.
 * @param longTickInterval The interval in milliseconds for the long tick events. This interval should be divisible by the short tick interval.
 * @param onShortTick A lambda function to be called on each short tick interval, passing the remaining time until finished.
 * @param onLongTick A lambda function to be called on each long tick interval, passing the remaining time until finished.
 * @param onTimerFinished A lambda function to be called when the countdown is complete.
 * @return A new CountDownTimer object with the specified intervals and callbacks.
 */
fun twoTicksTimer(
    millisInFuture: Long,
    shortTickInterval: Long,
    longTickInterval: Long,
    onShortTick: (Long) -> Unit,
    onLongTick: (Long) -> Unit,
    onTimerFinished: () -> Unit,
) = object : CountDownTimer(millisInFuture, shortTickInterval) {

    private val shortTicksInOneLong = (longTickInterval / shortTickInterval).toInt()
    private var ticks = 0

    override fun onTick(millisUntilFinished: Long) {
        onShortTick.invoke(millisUntilFinished)
        if (ticks % shortTicksInOneLong == 0) {
            onLongTick.invoke(millisUntilFinished)
            ticks = 0
        }

        ticks++
    }

    override fun onFinish() = onTimerFinished()
}