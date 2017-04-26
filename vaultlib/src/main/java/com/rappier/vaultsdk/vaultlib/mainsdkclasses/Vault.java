package com.rappier.vaultsdk.vaultlib.mainsdkclasses;

import android.content.Context;
import android.util.Log;

import com.rappier.vaultsdk.vaultlib.entity.TrackerDataHHolder;
import com.rappier.vaultsdk.vaultlib.sdkinterfaces.OnTrackEventResult;
import com.rappier.vaultsdk.vaultlib.sdkinterfaces.TrackInterface;
import com.rappier.vaultsdk.vaultlib.utils.Constants;

/**
 * Created by dayanand on 22/3/17.
 * Tracking event main class. It will take all mandatory/non-mandatory input and produce result by calling APIs
 */

public class Vault implements TrackInterface{

    //Interface for valult call back
    protected static OnTrackEventResult mOntrackeventResult;

    //Data holder of all related params
    private static TrackerDataHHolder mTrackDataObject;

    //Interface to get holded data in other class
    protected static TrackInterface mTrackDataInteface;

    private static volatile Vault vault;

    protected Vault(){

        mTrackDataInteface = this;
    }


    /**
     * init header info of API
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     */
    public static synchronized Vault initEvent(Context context, String AccessToken){

        return init(context, AccessToken, Constants.INSTALL_EVENT);
    }




    /**
     * This method is used to set optional parameter call username
     *
     * @param username user name of user
     */
    public static synchronized void setUserName(String username){
        mTrackDataObject.setUserName(username);
    }


    /**
     * This method is used to set optional parameter call phone number
     *
     * @param phoneNumber phone number of user
     */
    public static synchronized void setPhoneNumber(String phoneNumber){
        mTrackDataObject.setPhoneNumber(phoneNumber);
    }


    /**
     * This method give trigger after register in app
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     */
    public static synchronized Vault setRegisterEvent(Context context, String AccessToken){

        return init(context, AccessToken, Constants.REGISTER_EVENT);
    }

    /**
     * This method give trigger after parches in app
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     */
    public static synchronized Vault setPurchaseEvent(Context context, String AccessToken){

        return init(context, AccessToken, Constants.PURCHASE_EVENT);
    }


    /**
     * This method give trigger after open in app
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     */
    public static synchronized Vault setOpenEvent(Context context, String AccessToken){

        return init(context, AccessToken, Constants.OPEN_EVENT);
    }


    /**
     * This method give trigger after custom event
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     * @param GoogleAddId google advertisement id related gmail account
     * @param customEventId custom event id by advertiser
     */
    public static synchronized Vault setCustomEvent(Context context, String AccessToken, String GoogleAddId, String customEventId){

        return initCustomEvent(context, AccessToken, GoogleAddId, customEventId);
    }

    /**
     * This method give trigger after register in app
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     * @param whichEvent Event is Install/register/purches
     */
    private static synchronized Vault init(Context context, String AccessToken, String whichEvent){

        if (vault == null) {
            vault = new Vault();
        }

            mTrackDataObject = new TrackerDataHHolder();

            mTrackDataObject.setAccessToken(AccessToken);
            mTrackDataObject.setDeviceId(MethodHelper.getDeviceId(context));
            if (whichEvent.equals(Constants.REGISTER_EVENT)) {
                mTrackDataObject.setEventId(Constants.EventsIds.REGISTER.getValue());
            }else if (whichEvent.equals(Constants.PURCHASE_EVENT)){
                mTrackDataObject.setEventId(Constants.EventsIds.PURCHASE.getValue());
            } else if (whichEvent.equals(Constants.OPEN_EVENT)) {
                mTrackDataObject.setEventId(Constants.EventsIds.OPEN.getValue());
            }else {
                    mTrackDataObject.setEventId(Constants.EventsIds.INSTALL.getValue());

            }
            mTrackDataObject.setImeiNumber(MethodHelper.getImeiNumber(context));
            mTrackDataObject.setOsType(Constants.OS_TYPE);
            mTrackDataObject.setIp(MethodHelper.getIpAddress(context));
            mTrackDataObject.setOsVersion(MethodHelper.getOsVersion());
            mTrackDataObject.setDeviceName(MethodHelper.getDeviceName());
            mTrackDataObject.setDeviceModelNumber(MethodHelper.getDeviceModelNumber());
            mTrackDataObject.setLanguage(MethodHelper.getLanguageOfDevice());
            mTrackDataObject.setTimeZone(MethodHelper.getCurrentTimeZone());
            mTrackDataObject.setCarrierName(MethodHelper.getNetworkCarrierName(context));
            mTrackDataObject.setCarrierNetworkCode(MethodHelper.getNetworkCarrierCode(context));
            mTrackDataObject.setCarrierCode(MethodHelper.getCarrierCountryCode(context));
            mTrackDataObject.setIsoCountryCode(MethodHelper.getIsoCountryCode(context));
            mTrackDataObject.setAppName(MethodHelper.getApplicationName(context));
            mTrackDataObject.setNetworkType(MethodHelper.getNetworkType(context));
            mTrackDataObject.setAppVersion(MethodHelper.getAppVersion(context));
            mTrackDataObject.setAndroidAppPackage(MethodHelper.getAppBundleName(context));
            mTrackDataObject.setAndroidUuid(MethodHelper.getUUID(context));

            Log.e(Constants.TAG, MethodHelper.getAppVersion(context) + " , " + MethodHelper.getIpAddress(context));


        if (!whichEvent.equals(Constants.INSTALL_EVENT)) {
            if (MethodHelper.isNetworkAvailable(context)) {
                new TrackEventsAsync(context, mTrackDataObject).execute();
            }else {
                Log.i(Constants.TAG, "Please check internet for action");
            }
        }

        return vault;
    }


    /**
     * This method used to call custom event from app
     *
     * @param context application context
     * @param AccessToken Unique key provided by portal for each register client
     * @param GoogleAddId google advertisement id related gmail account
     * @param customEvent Custom event developer defined
     */
    private static synchronized Vault initCustomEvent(Context context, String AccessToken, String GoogleAddId, String customEvent){

        if (vault == null) {
            vault = new Vault();
        }

            mTrackDataObject = new TrackerDataHHolder();

            mTrackDataObject.setAccessToken(AccessToken);
            mTrackDataObject.setDeviceId(MethodHelper.getDeviceId(context));
            mTrackDataObject.setEventId(Integer.parseInt(customEvent));
            mTrackDataObject.setImeiNumber(MethodHelper.getImeiNumber(context));
            mTrackDataObject.setOsType(Constants.OS_TYPE);
            mTrackDataObject.setIp(MethodHelper.getIpAddress(context));
            mTrackDataObject.setOsVersion(MethodHelper.getOsVersion());
            mTrackDataObject.setDeviceName(MethodHelper.getDeviceName());
            mTrackDataObject.setDeviceModelNumber(MethodHelper.getDeviceModelNumber());
            mTrackDataObject.setLanguage(MethodHelper.getLanguageOfDevice());
            mTrackDataObject.setTimeZone(MethodHelper.getCurrentTimeZone());
            mTrackDataObject.setCarrierName(MethodHelper.getNetworkCarrierName(context));
            mTrackDataObject.setCarrierNetworkCode(MethodHelper.getNetworkCarrierCode(context));
            mTrackDataObject.setCarrierCode(MethodHelper.getCarrierCountryCode(context));
            mTrackDataObject.setIsoCountryCode(MethodHelper.getIsoCountryCode(context));
            mTrackDataObject.setAppName(MethodHelper.getApplicationName(context));
            mTrackDataObject.setNetworkType(MethodHelper.getNetworkType(context));
            mTrackDataObject.setAppVersion(MethodHelper.getAppVersion(context));
            mTrackDataObject.setAndroidAppPackage(MethodHelper.getAppBundleName(context));
            mTrackDataObject.setAndroidUuid(MethodHelper.getUUID(context));
            mTrackDataObject.setAndroidGaId(GoogleAddId);

            Log.e(Constants.TAG, MethodHelper.getAppVersion(context) + " , " + MethodHelper.getDeviceModelNumber());


        if (MethodHelper.isNetworkAvailable(context)) {
            new TrackEventsAsync(context, mTrackDataObject).execute();
        }else {
            Log.i(Constants.TAG, "Please check internet for action");
        }


        return vault;
    }


    /**
     * This method used to initialize listener of event
     *
     * @param onTrackEventResult event interface object
     */
    public static void setEventTrackListener(OnTrackEventResult onTrackEventResult){
        mOntrackeventResult = onTrackEventResult;
    }


    @Override
    public TrackerDataHHolder getTrackData() {
        return mTrackDataObject;
    }

}
