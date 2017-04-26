package com.rappier.vaultsdk.vaultlib.mainsdkclasses;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.rappier.vaultsdk.vaultlib.Config.Config;
import com.rappier.vaultsdk.vaultlib.entity.TrackerDataHHolder;
import com.rappier.vaultsdk.vaultlib.utils.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by dayanand on 30/3/17.
 * This calss is used to centralize common methods used in VaultSDK
 */

class MethodHelper {

    /**
     * Method to call API in background
     *
     * @param methodName Its API name
     * @param inputParams Input prams of API
     * @return result Result from API in string json format
     */
    public static String apiNetworkCall(String methodName, String inputParams, TrackerDataHHolder trackerDataHolder){


        URL url = null;
        String result = null;
        try {
            url = new URL(Config.API_BASE_URL + Config.API_VERSION + methodName);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(20000 /* milliseconds */);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("X-API-KEY", Config.API_KEY);
            urlConnection.setRequestProperty("access_token", trackerDataHolder.getAccessToken());
//            urlConnection.setRequestProperty("password", trackerDataHolder.getPassword());
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);

            // Send Request
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(methodName + "=" + inputParams);
            writer.flush();
            writer.close();
            os.close();

            // Get Response
            Log.i("response code", "Status code:"
                    + urlConnection.getResponseCode());
            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            result = total.toString();
            Log.i("Response:", "" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }


    /**
     * This method gets current time in milli seconds.
     *
     * @return currentTime Get current time with server format and UTC time zone
     */
    public static String getCurrentUTCTime() {
        String currentTimeStamp = "";
        try {
            SimpleDateFormat dateTimeFormate = new SimpleDateFormat(
                    Constants.SERVER_DATE_FORMAT, Locale.US);
            dateTimeFormate.setTimeZone(TimeZone.getTimeZone("UTC"));
            currentTimeStamp = dateTimeFormate.format(new Date(System
                    .currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("current time stamp:", "" + currentTimeStamp);

        return currentTimeStamp;
    }

    /**
     * This method is used to call API from broadcast receiver
     *
     * @param mTrackDataObject holder of all data to pass in API
     */
    public static void callAPIFromBroadcast(Context context, TrackerDataHHolder mTrackDataObject){

        if (mTrackDataObject != null) {
            new TrackEventsAsync(context,mTrackDataObject).execute();
        }
    }

    /**
     * This method is used for get device id from WIFI state
     * NEED to add permission : android.permission.ACCESS_WIFI_STATE
     *
     * @param context context provided by user
     * @return device id
     */
    public static String getDeviceId(Context context){

        if (hasPermissions(context, Manifest.permission.ACCESS_WIFI_STATE))
        {
            String wifiMacAddress = "";

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    String interfaceName = "wlan0";
                    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface intf : interfaces) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                            continue;
                        }

                        byte[] mac = intf.getHardwareAddress();
                        if (mac == null) {
                            wifiMacAddress = "";
                        }

                        StringBuilder buf = new StringBuilder();
                        for (byte aMac : mac) {
                            buf.append(String.format("%02X:", aMac));
                        }
                        if (buf.length() > 0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        wifiMacAddress = buf.toString();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                /**
                 * Getting device identifier that is MAC identifier
                 */
                WifiManager m_wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
                wifiMacAddress = m_wm.getConnectionInfo().getMacAddress();
            }


            return wifiMacAddress;
        }else {
            return "";
        }
    }

    /**
     * This method is used for get IMEI number of device
     * NEED to add permission : android.permission.READ_PHONE_STATE
     *
     * @param context context provided by user
     * @return imei number
     */
    public static String getImeiNumber(Context context){
        TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mngr.getDeviceId();
    }


    /**
     * This method is used for get IP address
     * NEED to add permission : android.permission.ACCESS_WIFI_STATE
     *
     * @param context context provided by user
     * @return ip address
     */
    public static String getIpAddress(Context context){

        String IPaddress = "";

        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if(WIFI == true)

        {
            IPaddress = GetDeviceipWiFiData(context);


        }

        if(MOBILE == true)
        {

            IPaddress = GetDeviceipMobileData();

        }

        return IPaddress;
    }

    /**
     * This method is used to get os version of device
     *
     * @return os version of device
     */
    public static String getOsVersion(){
        String osVersion = android.os.Build.VERSION.RELEASE;
        return osVersion;
    }

    /**
     * This method is used to get os name of device
     *
     * @return os name of device
     */
    public static String getDeviceName(){
        return android.os.Build.MANUFACTURER;
    }

    /**
     * This method is used to get model number of device
     *
     * @return String
     */
    public static String getDeviceModelNumber(){

        return android.os.Build.MODEL;
    }

    /**
     * This method return current language of device
     *
     * @return String
     */
    public static String getLanguageOfDevice(){

        return Locale.getDefault().getDisplayLanguage();
    }

    /**
     * This method return current timezone of device
     *
     * @return String
     */
    public static String getCurrentTimeZone(){
        return TimeZone.getDefault().getDisplayName();
    }

    /**
     * This method is used for get network carrier name of device
     * NEED to add permission : android.permission.READ_PHONE_STATE
     *
     * @param context context provided by user
     * @return String
     */
    public static String getNetworkCarrierName(Context context){
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        return carrierName;
    }

    /**
     * This method is used for get mobile network carrier code of device
     * NEED to add permission : android.permission.READ_PHONE_STATE
     *
     * @param context context provided by user
     * @return
     */
    public static String getNetworkCarrierCode(Context context){



        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();
        //MNC : Mobile network code, MCC: Mobile country code
        int mnc = 0;
        try {
            if (networkOperator != null) {
//            mcc = Integer.parseInt(networkOperator.substring(0, 3));
                mnc = Integer.parseInt(networkOperator.substring(3));
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return String.valueOf(mnc);
    }

    /**
     * This method is used for get mobile country carrier code of device
     * NEED to add permission : android.permission.READ_PHONE_STATE
     *
     * @param context context provided by user
     * @return
     */
    public static String getCarrierCountryCode(Context context){


        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();
        //MCC: Mobile country code
        int mcc = 0;
        try {


            if (networkOperator != null) {
                mcc = Integer.parseInt(networkOperator.substring(0, 3));
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return String.valueOf(mcc);
    }

    /**
     * This method is used for get iso country  code of device
     * NEED to add permission : android.permission.READ_PHONE_STATE
     *
     * @param context context provided by user
     * @return
     */
    public static String getIsoCountryCode(Context context){
        TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tel.getSimCountryIso();
        return countryCode;
    }

    /**
     * This method is used for get application name
     *
     * @param context context provided by user
     * @return
     */
    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    /**
     * This method is used for get internet type whether Wifi or Cellular
     *
     * @param context application context
     * @return
     */
    public static int getNetworkType(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        int networkType = 0;

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                networkType = 1;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data
                networkType = 2;
            }
        }

        return networkType;
    }

    /**
     * This method used to get application version
     *
     * @param context application context
     * @return
     */
    public static String getAppVersion(Context context){

        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * This method used to get package name
     *
     * @param context application context
     * @return
     */
    public static String getAppBundleName(Context context){
        return context.getApplicationContext().getPackageName();
    }

    /**
     * This method used to get app UUID
     *
     * @param context application context
     * @return
     */
    public static String getUUID(Context context){
        TelephonyManager tManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tManager.getDeviceId();

        return uuid;
    }


    /**
     * This method gets the google advertisement id, Note: Shoul call from thread only
     *
     * @param context of the application
     * @return advertisement id
     */
    /*public static String getGoogleAdvertisementId(Context context) {
        AdvertisingIdClient.Info idInfo = null;
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String advertId = "";
        try {
            advertId = idInfo.getId();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return advertId;
    }*/


    /**
     * This method is used to check wether permissions are granted for greater than Marshmallow version of android
     * @param context application context
     * @param permissions permission which should check
     * @return boolean
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param context application context
     * @return
     */
    private static TelephonyManager getTelephonyManager(Context context){
        return (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }


    public static String GetDeviceipMobileData(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public static String GetDeviceipWiFiData(Context context)
    {

        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }


    /**
     * This method gets the google advertisement id, Note: Shoul call from thread only
     *
     * @param context of the application
     * @return advertisement id
     */
    public static String getGoogleAdvertisementId(Context context) {
        AdvertisingIdClient.Info idInfo = null;
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String advertId = "";
        try {
            advertId = idInfo.getId();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return advertId;
    }


    /**
     * To check network available or not
     *
     * @param context
     * @return the network connection is ON return true or it is OFF return
     * false
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();

            return activeNetworkInfo != null
                    && activeNetworkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
