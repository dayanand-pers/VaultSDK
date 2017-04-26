package com.rappier.vaultsdk.vaultsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.rappier.vaultsdk.vaultlib.Config.Config;
import com.rappier.vaultsdk.vaultlib.mainsdkclasses.Vault;
import com.rappier.vaultsdk.vaultlib.sdkinterfaces.OnTrackEventResult;

public class MainActivity extends AppCompatActivity {

    private Button mAcceptOffer;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        mAcceptOffer = (Button) findViewById(R.id.btnAcceptOffer);

//        trackAppActions.setHeader("V1A2U3L4T5S6D7K8", 78, "admin123");

        mAcceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
                intent.putExtra("referrer", "?referrer=84512521&utm_source=google");
                sendBroadcast(intent);

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String googleId = ExampleApplication.getGoogleAdvertisementId(mContext);

                        Vault.setRegisterEvent(mContext, "V1A2U3L4T5S6D7K8", 78, "admin123", googleId);

                    }
                }).start();*/


            }
        });
    }


}
