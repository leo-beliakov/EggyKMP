package com.leoapps.eggy.base.notification


/**
 * Interface for managing notification channels.
 */
interface NotificationChannelsManager {

    /**
     * Creates notification channels required by the feature.
     *
     * This method should be called prior to sending notifications
     * to ensure that all necessary notification channels are created and available for use.
     */
    fun createChannels()
}