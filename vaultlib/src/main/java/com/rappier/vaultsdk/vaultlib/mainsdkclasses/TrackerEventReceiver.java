package com.rappier.vaultsdk.vaultlib.mainsdkclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.rappier.vaultsdk.vaultlib.R;
import com.rappier.vaultsdk.vaultlib.entity.TrackerDataHHolder;
import com.rappier.vaultsdk.vaultlib.utils.Constants;

/**
 * Created by dayanand on 7/4/17.
 * This is broadcast receiver used to track install event from client
 */

public class TrackerEventReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(final Context context, Intent intent) {

        String referrer = intent.getStringExtra("referrer");
        Log.i("ClosrrCampaignReceiver", "referral link: " + referrer);


        // Below logic to get the transaction id from referral link
        if (referrer != null) {
            try {

                final TrackerDataHHolder trackerDataHolder = Vault.mTrackDataInteface.getTrackData();

                if (!isValidParam(trackerDataHolder.getAccessToken())){
                    Log.i(Constants.TAG, "Please provide Access token");
                    sendInCallBack("Please provide Access token");

                }if (!isValidParam(trackerDataHolder.getEventId())){
                    Log.i(Constants.TAG, "Please provide eventId for action");
                    sendInCallBack("Please provide eventId for action");
                } else{

                    String transactionId = "";

                    String[] slittedReferral = referrer.split("&");

                    if (slittedReferral.length > 0) {
                        String[] utmSourceArray = slittedReferral[0].split("=");
                        if (utmSourceArray.length > 1)
                            transactionId = utmSourceArray[1];
                    }

                    Log.i("Campaign", "Transaction id: " + transactionId);

                    //Storing transaction id in application data
                    SharedPreferences campaignDetails = context
                            .getSharedPreferences(Constants.EVENT_INFO_SHARED_PREF, 0);
                    SharedPreferences.Editor campaignDetailsEditor = campaignDetails.edit();
                    campaignDetailsEditor.putString("transaction_id", transactionId);
                    campaignDetailsEditor.commit();

                    trackerDataHolder.setTransactionId(transactionId);

                    if (MethodHelper.isNetworkAvailable(context)) {

                        // Sending install info to API
                        MethodHelper.callAPIFromBroadcast(context, trackerDataHolder);
                    }else {
                        Log.i(Constants.TAG, "Please check internet for action");
                        sendInCallBack("Please check internet for action");
                    }



                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * this method for validate string params
     * @param params String value of params
     * @return boolean
     */
    private boolean isValidParam(String params){

        if (params != null && params.length()>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * this method for validate int params
     * @param params int value of params
     * @return boolean
     */
    private boolean isValidParam(int params){

        if (String.valueOf(params).length()>0 && params>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * send issue/error in callback functions
     * @param message Error message
     */
    private void sendInCallBack(String message){
        if (Vault.mOntrackeventResult != null){
            Vault.mOntrackeventResult.onError(message);
        }
    }

}
