package com.rappier.vaultsdk.vaultlib.sdkinterfaces;

import com.rappier.vaultsdk.vaultlib.entity.TrackerDataHHolder;

/**
 * Created by dayanand on 10/4/17.
 *
 * This class used to pass reference to other class
 *
 */

public interface TrackInterface {

    /**
     * Provide data to async task while API calling from broadcast receiver
     *
     * @return TrackerDataHHolder
     */
    TrackerDataHHolder getTrackData();
}
