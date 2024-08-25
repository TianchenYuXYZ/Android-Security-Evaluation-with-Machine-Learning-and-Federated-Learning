package com.example.securityevaluationwithexpertsystems;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomDataGenerator {
    public static Facts generateRandomFacts() {
        HashMap<String, Object> randomInputs = RandomInputGenerator.generateRandomInputs();

        Facts device = new Facts();
        device.setOsVersion(randomInputs.get("os_version").toString());
        device.setRoot((String) randomInputs.get("root_status"));
        device.setBootloader((String) randomInputs.get("bootloader_status"));
        device.setSecurityPatch((String) randomInputs.get("security_patch_level"));
        device.setSpecialPermissions(get_permission_list());
        device.setPermission_protections(get_protection_levels());
        device.setInstalledApps(make_random_apps(device.getPermission_protections()));
        device.setNetworkInfo((String) randomInputs.get("network_info"));
        device.setMeteredNetwork(((String) randomInputs.get("network_info")).split(" ")[0]);
        device.setTrustedNetwork(((String) randomInputs.get("network_info")).split(" ")[1]);
        device.setEncryption((String) randomInputs.get("encryption"));
        device.setNetwork_results((Map<String, String>) randomInputs.get("network_results"));

        return device;
    }

    private static HashMap<String, AppData> convertAppPermissions(List<HashMap<String, AppPermission>> appPermissionsList) {
        HashMap<String, AppData> installedApps = new HashMap<>();
        for (HashMap<String, AppPermission> appPermissions : appPermissionsList) {
            for (String appName : appPermissions.keySet()) {
                AppData appData = new AppData(appName, getRandomCategory(), getRandomDangerousLevels());
                appData.AppPermissions.putAll(appPermissions);
                installedApps.put(appName, appData);
            }
        }
        return installedApps;
    }

    private static int getRandomCategory() {
        Random random = new Random();
        return random.nextInt(7); // Assuming there are 7 categories
    }

    private static HashMap<String, Integer> getRandomDangerousLevels() {
        HashMap<String, Integer> dangerousLevels = new HashMap<>();
        // Add dangerous levels for different permissions
        dangerousLevels.put("ACCESS_FINE_LOCATION", PermissionInfo.PROTECTION_DANGEROUS);
        dangerousLevels.put("RECORD_AUDIO", PermissionInfo.PROTECTION_DANGEROUS);
        // Add more permissions as needed
        return dangerousLevels;
    }
    private static List<String> get_permission_list(){
        List<String> permission_list =  Arrays.asList("ACCESS_BACKGROUND_LOCATION", " ACCESS_COARSE_LOCATION", " ACCESS_FINE_LOCATION", " ACCESS_MEDIA_LOCATION", " ANSWER_PHONE_CALLS", " BLUETOOTH_ADVERTISE", " BLUETOOTH_CONNECT", " BLUETOOTH_SCAN", " BODY_SENSORS", " CALL_PHONE", " CAMERA", " GET_ACCOUNTS", " NEARBY_WIFI_DEVICES", " PROCESS_OUTGOING_CALLS", " READ_CALENDAR", " READ_CONTACTS", " READ_EXTERNAL_STORAGE", " READ_PHONE_NUMBERS", " READ_SMS", " RECEIVE_SMS", " RECORD_AUDIO", " SEND_SMS", " WRITE_CALENDAR", " WRITE_CONTACTS", " WRITE_EXTERNAL_STORAGE");
//        List<String> permission_list =  Arrays.asList("ACCESS_BACKGROUND_LOCATION","ACCESS_COARSE_LOCATION","ACCESS_FINE_LOCATION","ACCESS_MEDIA_LOCATION","ACCESS_NETWORK_STATE","ACCESS_WIFI_STATE","ANSWER_PHONE_CALLS","BLUETOOTH","BLUETOOTH_CONNECT","BLUETOOTH_ADVERTISE","BLUETOOTH_SCAN","BODY_SENSORS","BROADCAST_SMS","CALL_PHONE","CAMERA","CAPTURE_AUDIO_OUTPUT","CHANGE_NETWORK_STATE","GET_ACCOUNTS","NEARBY_WIFI_DEVICES","LOCATION_HARDWARE","PROCESS_OUTGOING_CALLS","READ_CALENDAR","READ_CONTACTS","READ_EXTERNAL_STORAGE","READ_PHONE_NUMBERS","READ_SMS","REBOOT","RECEIVE_BOOT_COMPLETED","RECEIVE_SMS","RECORD_AUDIO","SEND_SMS","SMS_FINANCIAL_TRANSACTIONS","USE_BIOMETRIC","USE_FINGERPRINT","WRITE_APN_SETTINGS","WRITE_CALENDAR","WRITE_CONTACTS","WRITE_EXTERNAL_STORAGE","WRITE_GSERVICES","WRITE_SECURE_SETTINGS","WRITE_SETTINGS","WRITE_VOICEMAIL");
        return permission_list;
    }
    private static HashMap<String,Integer> get_protection_levels(){
        List<String> permission_list =  Arrays.asList("ACCESS_BACKGROUND_LOCATION", " ACCESS_COARSE_LOCATION", " ACCESS_FINE_LOCATION", " ACCESS_MEDIA_LOCATION", " ANSWER_PHONE_CALLS", " BLUETOOTH_ADVERTISE", " BLUETOOTH_CONNECT", " BLUETOOTH_SCAN", " BODY_SENSORS", " CALL_PHONE", " CAMERA", " GET_ACCOUNTS", " NEARBY_WIFI_DEVICES", " PROCESS_OUTGOING_CALLS", " READ_CALENDAR", " READ_CONTACTS", " READ_EXTERNAL_STORAGE", " READ_PHONE_NUMBERS", " READ_SMS", " RECEIVE_SMS", " RECORD_AUDIO", " SEND_SMS", " WRITE_CALENDAR", " WRITE_CONTACTS", " WRITE_EXTERNAL_STORAGE");
        List<Integer> proto_levels= Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
//        List<String> permission_list =  Arrays.asList("ACCESS_BACKGROUND_LOCATION","ACCESS_COARSE_LOCATION","ACCESS_FINE_LOCATION","ACCESS_MEDIA_LOCATION","ACCESS_NETWORK_STATE","ACCESS_WIFI_STATE","ANSWER_PHONE_CALLS","BLUETOOTH","BLUETOOTH_CONNECT","BLUETOOTH_ADVERTISE","BLUETOOTH_SCAN","BODY_SENSORS","BROADCAST_SMS","CALL_PHONE","CAMERA","CAPTURE_AUDIO_OUTPUT","CHANGE_NETWORK_STATE","GET_ACCOUNTS","NEARBY_WIFI_DEVICES","LOCATION_HARDWARE","PROCESS_OUTGOING_CALLS","READ_CALENDAR","READ_CONTACTS","READ_EXTERNAL_STORAGE","READ_PHONE_NUMBERS","READ_SMS","REBOOT","RECEIVE_BOOT_COMPLETED","RECEIVE_SMS","RECORD_AUDIO","SEND_SMS","SMS_FINANCIAL_TRANSACTIONS","USE_BIOMETRIC","USE_FINGERPRINT","WRITE_APN_SETTINGS","WRITE_CALENDAR","WRITE_CONTACTS","WRITE_EXTERNAL_STORAGE","WRITE_GSERVICES","WRITE_SECURE_SETTINGS","WRITE_SETTINGS","WRITE_VOICEMAIL");
//        List<Integer> proto_levels= Arrays.asList(1 ,1 ,1 ,1 ,0 ,0 ,1 ,0 ,1 ,1 ,1 ,1 ,2 ,1 ,1 ,2 ,0 ,1 ,2 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,2 ,0 ,1 ,1 ,1 ,2 ,0 ,0 ,2 ,1 ,1 ,1 ,2 ,2 ,2 ,2);
        HashMap<String,Integer> permission_protections = new HashMap<>();
        for(int i = 0;i<permission_list.size();i++){
            int protection = proto_levels.get(i);
            permission_protections.put(permission_list.get(i),protection);
        }
        return permission_protections;
    }
    private static HashMap<String,AppData> make_random_apps(HashMap<String,Integer> protection_levels){
        int[] categories = new int[]{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8};
        HashMap<String,AppData> Apps = new HashMap<>();
        for(int i = 0; i < 10;i++){
            String packageName = "App"+String.valueOf(i+1);
            Random rand = new Random();
            int app_category = categories[rand.nextInt(categories.length)];
            AppData App = new AppData(packageName,app_category);
            HashMap<String, AppPermission> permissions = App.AppPermissions;
            String[] perms = {"ACCESS_BACKGROUND_LOCATION", " ACCESS_COARSE_LOCATION", " ACCESS_FINE_LOCATION", " ACCESS_MEDIA_LOCATION", " ANSWER_PHONE_CALLS", " BLUETOOTH_ADVERTISE", " BLUETOOTH_CONNECT", " BLUETOOTH_SCAN", " BODY_SENSORS", " CALL_PHONE", " CAMERA", " GET_ACCOUNTS", " NEARBY_WIFI_DEVICES", " PROCESS_OUTGOING_CALLS", " READ_CALENDAR", " READ_CONTACTS", " READ_EXTERNAL_STORAGE", " READ_PHONE_NUMBERS", " READ_SMS", " RECEIVE_SMS", " RECORD_AUDIO", " SEND_SMS", " WRITE_CALENDAR", " WRITE_CONTACTS", " WRITE_EXTERNAL_STORAGE"};
//            String[] perms = {"ACCESS_BACKGROUND_LOCATION","ACCESS_COARSE_LOCATION","ACCESS_FINE_LOCATION","ACCESS_MEDIA_LOCATION","ACCESS_NETWORK_STATE","ACCESS_WIFI_STATE","ANSWER_PHONE_CALLS","BLUETOOTH","BLUETOOTH_CONNECT","BLUETOOTH_ADVERTISE","BLUETOOTH_SCAN","BODY_SENSORS","BROADCAST_SMS","CALL_PHONE","CAMERA","CAPTURE_AUDIO_OUTPUT","CHANGE_NETWORK_STATE","GET_ACCOUNTS","NEARBY_WIFI_DEVICES","LOCATION_HARDWARE","PROCESS_OUTGOING_CALLS","READ_CALENDAR","READ_CONTACTS","READ_EXTERNAL_STORAGE","READ_PHONE_NUMBERS","READ_SMS","REBOOT","RECEIVE_BOOT_COMPLETED","RECEIVE_SMS","RECORD_AUDIO","SEND_SMS","SMS_FINANCIAL_TRANSACTIONS","USE_BIOMETRIC","USE_FINGERPRINT","WRITE_APN_SETTINGS","WRITE_CALENDAR","WRITE_CONTACTS","WRITE_EXTERNAL_STORAGE","WRITE_GSERVICES","WRITE_SECURE_SETTINGS","WRITE_SETTINGS","WRITE_VOICEMAIL"};
            for(int j=0;j<10;j++){
                int permKey = rand.nextInt(perms.length);
                String perm = perms[permKey];
                permissions.put(perm,new AppPermission(0,0,protection_levels.get(perm)));
                String[] temp_array = new String[perms.length-1];
                for(int k=0, l=0;k<perms.length;k++){
                    if(k!=permKey){
                        temp_array[l]=perms[k];
                        l++;
                    }
                }
                perms = temp_array;
            }

            App.AppPermissions = permissions;
            for(String key: App.AppPermissions.keySet()){
                App.AppPermissions.get(key).setExist(rand.nextInt(2));
                if(App.AppPermissions.get(key).getExist()==1){
                    App.AppPermissions.get(key).setGranted(rand.nextInt(2));
                }
            }
            Apps.put(packageName, App);
        }
        return Apps;
    }

}
