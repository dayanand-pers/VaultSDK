package com.rappier.vaultsdk.vaultlib.utils;

/**
 * Created by dayanand on 31/3/17.
 * The Class contain all constants or final values used in VaultSDK
 */

public class Constants {

    //Tag used for logs in SDK
    public static final String TAG = "ValutSDK";

    //Date format of server to send and receive
    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //Shared preferences to store transaction id
    public static final String EVENT_INFO_SHARED_PREF ="EventInfoSharedPref";

    //enum for events ids in lib
    public static enum EventsIds {INSTALL(1),
        REGISTER(2), PURCHASE(3), OPEN(4);
        private final int value;

        EventsIds(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }};

    //OS type -- 1: IOS, 2 : Android
    public static final int OS_TYPE = 2;

    //All events constants
    public static final String INSTALL_EVENT = "install";
    public static final String REGISTER_EVENT = "register";
    public static final String PURCHASE_EVENT = "purches";
    public static final String OPEN_EVENT = "open";
}
