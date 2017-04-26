package com.rappier.vaultsdk.vaultlib.mainsdkclasses;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.rappier.vaultsdk.vaultlib.utils.Constants;
import com.rappier.vaultsdk.vaultlib.entity.TrackerDataHHolder;

import org.json.JSONObject;

/**
 * Created by dayanand on 22/3/17.
 * This Calass/AsyncTask is used to call tracker API in background and get result
 */

class TrackEventsAsync extends AsyncTask {

    private TrackerDataHHolder trackerDataHolder;
    private int mStatusCode = 0;
    private String mTimestamp = "", mStatusMessage = "";
    private Context context;

    public TrackEventsAsync(Context context, TrackerDataHHolder trackerDataHolder){
        this.trackerDataHolder = trackerDataHolder;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String trackerResult = "";

            JSONObject inputParamsObj = new JSONObject();
            inputParamsObj.put("event_id",trackerDataHolder.getEventId());
            inputParamsObj.put("phone_number", trackerDataHolder.getPhoneNumber());
            inputParamsObj.put("username", trackerDataHolder.getUserName());
            inputParamsObj.put("ip", trackerDataHolder.getIp());
            inputParamsObj.put("os_type", trackerDataHolder.getOsType());
            inputParamsObj.put("os_version", trackerDataHolder.getOsVersion());
            inputParamsObj.put("timestamp", MethodHelper.getCurrentUTCTime());
            inputParamsObj.put("device_name", trackerDataHolder.getDeviceName());
            inputParamsObj.put("device_model_no", trackerDataHolder.getDeviceModelNumber());
            inputParamsObj.put("language", trackerDataHolder.getLanguage());
            inputParamsObj.put("timezone", trackerDataHolder.getTimeZone());
            inputParamsObj.put("locale_identifier", trackerDataHolder.getLocalIdentifier());
            inputParamsObj.put("carrier_name", trackerDataHolder.getCarrierName());
            inputParamsObj.put("carrier_code", trackerDataHolder.getCarrierCode());
            inputParamsObj.put("carrier_network_code", trackerDataHolder.getCarrierNetworkCode());
            inputParamsObj.put("iso_country_code", trackerDataHolder.getIsoCountryCode());
            inputParamsObj.put("supports_voip", trackerDataHolder.getSupportVoip());
            inputParamsObj.put("app_name", trackerDataHolder.getAppName());
            inputParamsObj.put("network_type", trackerDataHolder.getNetworkType());
            inputParamsObj.put("app_version", trackerDataHolder.getAppVersion());
            inputParamsObj.put("app_bundle_id", trackerDataHolder.getAndroidAppPackage());
            inputParamsObj.put("device_id", trackerDataHolder.getDeviceId());
            inputParamsObj.put("android_transaction_id", trackerDataHolder.getTransactionId());
            inputParamsObj.put("android_imei_no", trackerDataHolder.getImeiNumber());
            inputParamsObj.put("android_uuid", trackerDataHolder.getAndroidUuid());
            inputParamsObj.put("android_gaid", MethodHelper.getGoogleAdvertisementId(context));

            Log.i(Constants.TAG, "Input : "+inputParamsObj.toString());

            //Network call to call API and get result
            trackerResult = MethodHelper.apiNetworkCall("tracker", inputParamsObj.toString(), trackerDataHolder);

            if (trackerResult != null){
                JSONObject resultObject = new JSONObject(trackerResult);

                JSONObject resultDisctionary = resultObject.getJSONObject("result");

                mStatusCode = Integer.parseInt(resultDisctionary.getString("status_code"));
                mTimestamp = resultDisctionary.getString("timestamp");
                mStatusMessage = resultDisctionary.getString("status_message");

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(mStatusCode == 0) {
            //Put result in callback of parent method
            if (Vault.mOntrackeventResult != null) {
                Vault.mOntrackeventResult.onEventResult(mStatusMessage);
            }
        }else {
            if (Vault.mOntrackeventResult != null) {
                Vault.mOntrackeventResult.onEventResult(mStatusMessage);
            }
        }
    }
}
