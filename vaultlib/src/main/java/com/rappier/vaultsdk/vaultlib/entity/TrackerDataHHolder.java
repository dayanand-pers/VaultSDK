package com.rappier.vaultsdk.vaultlib.entity;

/**
 * Created by dayanand on 30/3/17.
 * this entity class is usded to hold all input values provided by client in single object
 */

public class TrackerDataHHolder {

    private String AccessToken="", phoneNumber="", userName="", ip="", osVersion="", timestamp="", deviceName="",
                            deviceModelNumber="", language="", timeZone="", localIdentifier="", carrierName="",carrierCode="",
                            carrierNetworkCode="", isoCountryCode="", appName="", appVersion="", appBuildId="",
                            deviceId="", transactionId="", imeiNumber="", androidUuid="", androidGaId="", androidAppPackage = "";
    private int networkType, supportVoip, osType, eventId;



    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModelNumber() {
        return deviceModelNumber;
    }

    public void setDeviceModelNumber(String deviceModelNumber) {
        this.deviceModelNumber = deviceModelNumber;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocalIdentifier() {
        return localIdentifier;
    }

    public void setLocalIdentifier(String localIdentifier) {
        this.localIdentifier = localIdentifier;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierNetworkCode() {
        return carrierNetworkCode;
    }

    public void setCarrierNetworkCode(String carrierNetworkCode) {
        this.carrierNetworkCode = carrierNetworkCode;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppBuildId() {
        return appBuildId;
    }

    public void setAppBuildId(String appBuildId) {
        this.appBuildId = appBuildId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getAndroidUuid() {
        return androidUuid;
    }

    public void setAndroidUuid(String androidUuid) {
        this.androidUuid = androidUuid;
    }

    public String getAndroidGaId() {
        return androidGaId;
    }

    public void setAndroidGaId(String androidGaId) {
        this.androidGaId = androidGaId;
    }

    public int getNetworkType() {
        return networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }

    public int getSupportVoip() {
        return supportVoip;
    }

    public void setSupportVoip(int supportVoip) {
        this.supportVoip = supportVoip;
    }

    public String getAndroidAppPackage() {
        return androidAppPackage;
    }

    public void setAndroidAppPackage(String androidAppPackage) {
        this.androidAppPackage = androidAppPackage;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}
