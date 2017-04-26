package com.rappier.vaultsdk.vaultsdk;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.rappier.vaultsdk.vaultlib.mainsdkclasses.Vault;
import com.rappier.vaultsdk.vaultlib.sdkinterfaces.OnTrackEventResult;

/**
 * Created by dayanand on 13/4/17.
 */

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Context context = getApplicationContext();

        Vault.initEvent(context, "V1A2U3L4T5S6D7K8A9C1C2E3S4S5T6O7K8E9N78");

        Vault.setEventTrackListener(new OnTrackEventResult() {
            @Override
            public void onEventResult(String result) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

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
}
