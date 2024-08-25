package com.example.securityevaluationwithexpertsystems;


import static android.content.pm.PackageManager.GET_PERMISSIONS;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ScanInstalledApps {

        public static List<String> get_permission_list(){
            List<String> permission_list =  Arrays.asList("ACCESS_BACKGROUND_LOCATION","ACCESS_COARSE_LOCATION","ACCESS_FINE_LOCATION","ACCESS_MEDIA_LOCATION","ACCESS_NETWORK_STATE","ACCESS_WIFI_STATE","ANSWER_PHONE_CALLS","BLUETOOTH","BLUETOOTH_CONNECT","BLUETOOTH_ADVERTISE","BLUETOOTH_SCAN","BODY_SENSORS","BROADCAST_SMS","CALL_PHONE","CAMERA","CAPTURE_AUDIO_OUTPUT","CHANGE_NETWORK_STATE","GET_ACCOUNTS","NEARBY_WIFI_DEVICES","LOCATION_HARDWARE","PROCESS_OUTGOING_CALLS","READ_CALENDAR","READ_CONTACTS","READ_EXTERNAL_STORAGE","READ_PHONE_NUMBERS","READ_SMS","REBOOT","RECEIVE_BOOT_COMPLETED","RECEIVE_SMS","RECORD_AUDIO","SEND_SMS","SMS_FINANCIAL_TRANSACTIONS","USE_BIOMETRIC","USE_FINGERPRINT","WRITE_APN_SETTINGS","WRITE_CALENDAR","WRITE_CONTACTS","WRITE_EXTERNAL_STORAGE","WRITE_GSERVICES","WRITE_SECURE_SETTINGS","WRITE_SETTINGS","WRITE_VOICEMAIL");
            return permission_list;
        }
        public static HashMap<String,Integer> get_protection_levels(List<String> permission_list){
            List<Integer> proto_levels= Arrays.asList(1 ,1 ,1 ,1 ,0 ,0 ,1 ,0 ,1 ,1 ,1 ,1 ,2 ,1 ,1 ,2 ,0 ,1 ,2 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,2 ,0 ,1 ,1 ,1 ,2 ,0 ,0 ,2 ,1 ,1 ,1 ,2 ,2 ,2 ,2);
            HashMap<String,Integer> permission_protections = new HashMap<>();
            for(int i = 0;i<permission_list.size();i++){
                int protection = proto_levels.get(i);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    try {
//                        protection = pm.getPermissionInfo("android.permission."+permission_list.get(i),0).getProtection();
//                    } catch (PackageManager.NameNotFoundException e) {
//                        Log.e("error","android.permission."+permission_list.get(i),e);
//                    }
//                }
                permission_protections.put(permission_list.get(i),protection);
            }
            return permission_protections;
        }
        public static HashMap<String,AppData> make_random_apps(PackageManager pm,HashMap<String,Integer> protection_levels){

            int[] categories = new int[]{ApplicationInfo.CATEGORY_UNDEFINED,ApplicationInfo.CATEGORY_ACCESSIBILITY,
                    ApplicationInfo.CATEGORY_AUDIO,ApplicationInfo.CATEGORY_GAME,ApplicationInfo.CATEGORY_IMAGE,
                    ApplicationInfo.CATEGORY_MAPS,ApplicationInfo.CATEGORY_NEWS,ApplicationInfo.CATEGORY_PRODUCTIVITY,
                    ApplicationInfo.CATEGORY_SOCIAL,ApplicationInfo.CATEGORY_VIDEO};
            HashMap<String,AppData> Apps = new HashMap<>();
            for(int i = 0; i < 50;i++){
                String packageName = "package"+String.valueOf(i);
                Random rand = new Random();

                int app_category = categories[rand.nextInt(categories.length)];
                new AppData(packageName, app_category, protection_levels);
                AppData App = new AppData(packageName,app_category,protection_levels);
                for(String key: App.AppPermissions.keySet()){
                    App.AppPermissions.get(key).setExist(rand.nextInt(1));
                    if(App.AppPermissions.get(key).getExist()==1){
                        App.AppPermissions.get(key).setGranted(rand.nextInt(1));
                    }
                }
                Apps.put(packageName, App);
            }
            return Apps;

        }
        public static HashMap<String,AppData> scanApps(PackageManager pm,HashMap<String,Integer> protection_levels){
            // Loop each package requesting <manifest> permissions
            //Create list of permissions
            List<PackageInfo> appInfos = pm.getInstalledPackages(GET_PERMISSIONS);
            int size = 0;
            for (PackageInfo info : appInfos) {
                if (!(info.packageName.startsWith("com.android")) && !(info.packageName.startsWith("com.google.android"))) {
                    size++;
                }
            }
            HashMap<String, AppData> Apps = new HashMap<>();
            // add all the app name in string list
            for (PackageInfo info : appInfos) {
                if (!(info.packageName.startsWith("com.android")) && !(info.packageName.startsWith("com.google.android"))) {
                    HashMap<String, Integer> appPermValues = new HashMap<>();
                    List<String> appPermissions = Arrays.asList("ACCESS_BACKGROUND_LOCATION", "ACCESS_COUARSE_LOCATION", "ACCESS_FINE_LOCATION", "ACCESS_MEDIA_LOCATION", "ACCESS_NETWORK_STATE", "ACCESS_WIFI_STATE", "ANSWER_PHONE_CALLS", "BLUETOOTH", "BLUETOOTH_CONNECT", "BLUETOOTH_ADVERTISE", "BLUETOOTH_SCAN", "BODY_SENSORS", "BROADCAST_SMS", "CALL_PHONE", "CAMERA", "CAPTURE_AUDIO_OUTPUT", "CHANGE_NETWORK_STATE", "GET_ACCOUNTS", "NEARBY_WIFI_DEVICES", "LOCATION_HARDWARE", "PROCESS_OUTGOING_CALLS", "READ_CALENDAR", "READ_CONTACTS", "READ_EXTERNAL_STORAGE", "READ_PHONE_NUMBERS", "READ_SMS", "REBOOT", "RECEIVE_BOOT_COMPLETED", "RECEIVE_SMS", "RECORD_AUDIO", "SEND_SMS", "SMS_FINANCIAL_TRANSACTIONS", "USE_BIOMETRIC", "USE_FINGERPRINT", "WRITE_APN_SETTINGS", "WRITE_CALENDAR", "WRITE_CONTACTS", "WRITE_EXTERNAL_STORAGE", "WRITE_GSERVICES", "WRITE_SECURE_SETTINGS", "WRITE_SETTINGS", "WRITE_VOICEMAIL", "ACCESS_BACKGROUND_LOCATION_GRANTED", "ACCESS_COUARSE_LOCATION_GRANTED", "ACCESS_FINE_LOCATION_GRANTED", "ACCESS_MEDIA_LOCATION_GRANTED", "ACCESS_NETWORK_STATE_GRANTED", "ACCESS_WIFI_STATE_GRANTED", "ANSWER_PHONE_CALLS_GRANTED", "BLUETOOTH_GRANTED", "BLUETOOTH_CONNECT_GRANTED", "BLUETOOTH_ADVERTISE_GRANTED", "BLUETOOTH_SCAN_GRANTED", "BODY_SENSORS_GRANTED", "BROADCAST_SMS_GRANTED", "CALL_PHONE_GRANTED", "CAMERA_GRANTED", "CAPTURE_AUDIO_OUTPUT_GRANTED", "CHANGE_NETWORK_STATE_GRANTED", "GET_ACCOUNTS_GRANTED", "NEARBY_WIFI_DEVICES_GRANTED", "LOCATION_HARDWARE_GRANTED", "PROCESS_OUTGOING_CALLS_GRANTED", "READ_CALENDAR_GRANTED", "READ_CONTACTS_GRANTED", "READ_EXTERNAL_STORAGE_GRANTED", "READ_PHONE_NUMBERS_GRANTED", "READ_SMS_GRANTED", "REBOOT_GRANTED", "RECEIVE_BOOT_COMPLETED_GRANTED", "RECEIVE_SMS_GRANTED", "RECORD_AUDIO_GRANTED", "SEND_SMS_GRANTED", "SMS_FINANCIAL_TRANSACTIONS_GRANTED", "USE_BIOMETRIC_GRANTED", "USE_FINGERPRINT_GRANTED", "WRITE_APN_SETTINGS_GRANTED", "WRITE_CALENDAR_GRANTED", "WRITE_CONTACTS_GRANTED", "WRITE_EXTERNAL_STORAGE_GRANTED", "WRITE_GSERVICES_GRANTED", "WRITE_SECURE_SETTINGS_GRANTED", "WRITE_SETTINGS_GRANTED", "WRITE_VOICEMAIL_GRANTED");
                    for (String key : appPermissions) {
                        appPermValues.put(key, 0);
                    }
                    String[] requestedPermissions = info.requestedPermissions;
                    Log.i("Package Info", info.packageName);
                    int app_category = ApplicationInfo.CATEGORY_UNDEFINED;
                    if (Build.VERSION.SDK_INT >= 26) {
                        try {
                            app_category = pm.getApplicationInfo(info.packageName, 0).category;
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.e("Package Error", "Could not get category", e);
                        }
                    }
                    Apps.put(info.packageName, new AppData(info.packageName, app_category, protection_levels));
                    if (requestedPermissions != null) {
                        for (String requestedPerm : requestedPermissions) {
                            String[] splitName = requestedPerm.split("\\.");

                            String trimmedPermName = splitName[splitName.length - 1];
                            if (appPermValues.containsKey(trimmedPermName)) {
                                appPermValues.put(trimmedPermName, 1);
                                AppData tempApp = Apps.get(info.packageName);
                                AppPermission tempPerm = tempApp.AppPermissions.get(trimmedPermName);
                                tempPerm.setExist(1);
                                if (pm.checkPermission(requestedPerm, info.packageName) == PackageManager.PERMISSION_GRANTED) {
                                    appPermValues.put(trimmedPermName + "_GRANTED", 1);
                                    tempPerm.setGranted(1);
                                }
                                tempApp.AppPermissions.put(trimmedPermName, tempPerm);
                                Apps.put(info.packageName, tempApp);
                            }
                        }
                    }
                }
            }
            return Apps;
        }

}

