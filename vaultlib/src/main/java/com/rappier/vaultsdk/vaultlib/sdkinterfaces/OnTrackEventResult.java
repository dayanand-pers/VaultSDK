package com.rappier.vaultsdk.vaultlib.sdkinterfaces;

/**
 * Created by dayanand on 22/3/17.
 * This interface is used as call back event for event tracker function in Client
 */

public interface OnTrackEventResult {

    //Event result by API
    void onEventResult(String result);

    //Error by API
    void onError(String message);
}
