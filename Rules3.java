package com.example.securityevaluationwithexpertsystems;

import static java.lang.Boolean.TRUE;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rules3 {
    private Facts device;
    public List<String> recommendation = new ArrayList<>();
    public void evaluate(Facts device){
        this.device = device;
        checkRoot(device);
        checkOsVersion(device);
        checkBootloader(device);
        checkSecurityPatch(device);
        checkAppSecurity(device);
        calculateTotalScore(device);
    }

    private void checkRoot(Facts device) {
        if ("Rooted".equals(device.getRoot())){
            device.setRootScore(0);
        }
        else {
            device.setRootScore(1);
        }
    }

    private void checkOsVersion(Facts device) {
        switch(device.getOsVersion()){
            case "14":
                device.setOsScore(4);
                break;
            case"13":
                device.setOsScore(3);
                break;
            case "12":
                device.setOsScore(2);
                break;
            case"11":
                device.setOsScore(1);
                break;
            default:device.setOsScore(0);
                break;
        }
    }

    private void checkBootloader(Facts device) {
        if("bluejay-1.2-8893284".equals(device.getBootloader())){
            device.setBootloaderScore(1);
        } else {
            device.setBootloaderScore(0);
        }
    }

    private void checkSecurityPatch(Facts device) {
        switch (device.getSecurityPatch()) {
            case "2024-03-01":
                device.setPatchScore(2);
                break;
            case "2023-09-05":
                device.setPatchScore(1);
                break;
            default:
                device.setPatchScore(0);
                break;
        }
    }
    private void checkAppSecurity(Facts device){
        HashMap<String,AppData> InstalledApps= device.getInstalledApps();
        for(String appKey:InstalledApps.keySet()){
            AppData this_app = InstalledApps.get(appKey);
            for(String permKey:this_app.AppPermissions.keySet()){
                checkPermissionExist(permKey,appKey,InstalledApps);
                checkPermissionGranted(permKey,appKey,InstalledApps);
                checkPermissionDangerous(permKey,appKey,InstalledApps);
                checkPermissionOk(permKey,appKey,InstalledApps);
                updateAppScore(permKey,appKey,InstalledApps);
                fixAppScore(appKey,InstalledApps);
            }
        }
        device.setInstalledApps(InstalledApps);
        calculate_total_app_score(device);

    }
    private void checkPermissionExist(String permKey,String appKey, HashMap<String,AppData> Apps){
        if (Apps.get(appKey).AppPermissions.get(permKey).getExist()==1){
            Apps.get(appKey).AppPermissions.get(permKey).setUsed(TRUE);
            Apps.get(appKey).AppPermissions.get(permKey).setPermscore(2.5);
            Apps.get(appKey).AppPermissions.get(permKey).setExistScore(2.5);
        }
    }
    private void checkPermissionGranted(String permKey,String appKey, HashMap<String,AppData> Apps){
        if (Apps.get(appKey).AppPermissions.get(permKey).getUsed() == TRUE &&
                Apps.get(appKey).AppPermissions.get(permKey).getGranted()==1){
            Apps.get(appKey).AppPermissions.get(permKey).setUsed(TRUE);
            Apps.get(appKey).AppPermissions.get(permKey).setPermscore(5.0);
            Apps.get(appKey).AppPermissions.get(permKey).setGrantedScore(2.5);
        }
    }
    private void checkPermissionDangerous(String permKey,String appKey, HashMap<String,AppData> Apps){
        if (Apps.get(appKey).AppPermissions.get(permKey).getUsed() == TRUE &&
                Apps.get(appKey).AppPermissions.get(permKey).getDangerous()==1){
            Apps.get(appKey).AppPermissions.get(permKey).setUsed(TRUE);
            Apps.get(appKey).AppPermissions.get(permKey).setExistScore(2*Apps.get(appKey).AppPermissions.get(permKey).getExistScore());
            Apps.get(appKey).AppPermissions.get(permKey).setGrantedScore(2*Apps.get(appKey).AppPermissions.get(permKey).getGrantedScore());
            Apps.get(appKey).AppPermissions.get(permKey).setPermscore(
                    Apps.get(appKey).AppPermissions.get(permKey).getPermscore()*2);
        }
    }
    private void checkPermissionOk(String permKey,String appKey, HashMap<String,AppData> Apps){
        AppData appInfo = Apps.get(appKey);
        if (Apps.get(appKey).AppPermissions.get(permKey).getUsed() == TRUE) {
            if (((Apps.get(appKey).category == 4 || Apps.get(appKey).category == 5 || Apps.get(appKey).category == 6) && (permKey.equals("ACCESS_BACKGROUND_LOCATION") || permKey.equals("ACCESS_COARSE_LOCATION")))
                    ||
                    ((Apps.get(appKey).category == 6) && (permKey.equals("ACCESS_FINE_LOCATION")))
                    ||
                    ((Apps.get(appKey).category == 3) && (permKey.equals("ACCESS_MEDIA_LOCATION") || permKey.equals("CAMERA")))
                    ||
                    ((Apps.get(appKey).category == 2) && (permKey.equals("RECORD_AUDIO") || permKey.equals("CAMERA")))
                    ||
                    ((Apps.get(appKey).category == 1) && (permKey.equals("RECORD_AUDIO")))
            ){
                Apps.get(appKey).AppPermissions.get(permKey).setPermscore(
                        Apps.get(appKey).AppPermissions.get(permKey).getPermscore()-2.5);
            }
        }
    }
    private void updateAppScore(String permKey,String appKey, HashMap<String,AppData> Apps){
        if((Apps.get(appKey).getScore()>0) && (Apps.get(appKey).AppPermissions.get(permKey).getPermscore() > 0)){
            Apps.get(appKey).setScore(
                    Apps.get(appKey).getScore()
                            -
                            Apps.get(appKey).AppPermissions.get(permKey).getPermscore());
        }
    }
    private void fixAppScore(String appKey, HashMap<String,AppData> Apps){
        if(Apps.get(appKey).getScore()<0){
            Apps.get(appKey).setScore(0);
        }
    }

    private void calculateTotalScore(Facts device) {
        int totalScore = device.getRootScore() + device.getOsScore() + device.getBootloaderScore() + device.getPatchScore()+device.getAppsScore() + device.getNetworkScore();
        device.setTotalScore(totalScore);
    }
    private void calculate_total_app_score(Facts device){
        HashMap<String,AppData> Apps= device.getInstalledApps();
        int AppCount = Apps.size();
        double tempScore = 0;
        for(String Key: Apps.keySet()){
            tempScore += Apps.get(Key).getScore();
        }
        tempScore = tempScore/AppCount;
        tempScore = tempScore/100;
        //Log.i("Score", String.valueOf(tempScore));
        for(String appKey:Apps.keySet()){
            AppData this_app = Apps.get(appKey);
            for(String permKey:this_app.AppPermissions.keySet()){
                //Log.i("App permissions",appKey + " "+this_app.AppPermissions.get(permKey).getformattedData());
            }
            //Log.i("App Score", String.valueOf(Apps.get(appKey).getScore()));
        }
         device.setAppsScore((int) round(tempScore)*3);
    }

    public void process(Facts network){
        checkEncryption(network);
        checkMetered(network);
        checkTrusted(network);
        calculateNetworkTotalScore(network);
        calculateTotalScore(device);
        //device.setTotalScore(device.getTotalScore() + network.getNetworkScore());
    }

    private void checkEncryption(Facts network){
        String a = network.getNetwork_results().get("communication Encrypted");
        if (a == "false"){
            network.setEncryptionScore(0);
            device.setEncryptionScore(0);
            recommendation.add("Communication should be encrypted. Your device is in trouble");
        }
        else {
            network.setEncryptionScore(1);
            device.setEncryptionScore(1);
        }

    }

    private void checkMetered(Facts network){
        String a = network.getNetwork_results().get("active network info");
        if (a.contains("NOT_METERED")){
            network.setNetworkMeteredScore(0);
            device.setNetworkMeteredScore(0);
            recommendation.add("The network should be metered. Metered networks might enhance security");

        }
        else{
            network.setNetworkMeteredScore(1);
            device.setNetworkMeteredScore(1);}

    }

    private void checkTrusted(Facts network){
        String a = network.getNetwork_results().get("active network info");
        if (a.contains("NOT_TRUSTED")){
            network.setNetworkTrustedScore(0);
            device.setNetworkTrustedScore(0);
        }
        else{
            network.setNetworkTrustedScore(1);
            device.setNetworkTrustedScore(1);}
    }

    private void calculateNetworkTotalScore(Facts network) {
        int totalScore = network.getEncryptionScore() + network.getNetworkMeteredScore() + network.getNetworkTrustedScore() ;
        network.setNetworkScore(totalScore);
        device.setNetworkScore(totalScore);
    }

}
