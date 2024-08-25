package com.example.securityevaluationwithexpertsystems;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        // Code meant for dataset generation only. NOT TO BE UNCOMMENTED.
//        generate_database();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Uncomment the line below to use random data instead
//                Facts device = RandomDataGenerator.generateRandomFacts();
                //collect system information using utility class
                Facts device = getSystemInfo();
                String systemInfo = formatSystemInfo(device);
                //textView.setText(systemInfo);
                PackageManager pm = getPackageManager();
                // Get info about Apps and their permissions
                getAppInfo(device,pm);
                Log.i("Facts",device.getInstalledApps().keySet().toString());
                HashMap<String,AppData> apps = device.getInstalledApps();
                //String[] appnames = apps.keySet().toString().replace("[","").replace("]","").split(",");
                //listView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, appnames));

                // Evaluate security
                Rules engine = new Rules();
                engine.evaluate(device);
                //textView.append("\n\n" + getString(R.string.security_evaluation_score, device.getTotalScore()));
                //textView.setText(formatScores(device));
                Facts network = processNetwork();
                engine.process(network);
                device.setNetwork_results(network.getNetwork_results());
                String a = formatScores(device) + "\n" + formatNetworkScores(network);
                int score = device.getTotalScore();
                String b = a  +"\n"+ "Total score :" + score;
                String d = engine.recommendation.toString();
                String result = b + " \n" + d;
                textView.setText(result);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("devices").document(Build.MODEL).set(device);
                //db.collection("devices").document(Build.MODEL).set(devic)

            }
        });
    }
    @NonNull
    private Facts getSystemInfo() {
        Facts device = new Facts();
        device.setOsVersion(SystemInfoUtil.getOSVersion());
        device.setRoot(SystemInfoUtil.isDeviceRooted() ? "Rooted" : "Not Rooted");
        device.setBootloader(SystemInfoUtil.getBootloaderVersion());
        device.setSecurityPatch(SystemInfoUtil.getSecurityPatchLevel());
        device.setManufacture(SystemInfoUtil.getManufacture());
        device.setModel(SystemInfoUtil.getModel());

        return device;

    }

    @NonNull
    private String formatSystemInfo(Facts device){
        return "OS Version: " + device.getOsVersion() + "\n" + "Root Status: " + device.getRoot() + "\n" +
                "Bootloader Version: " + device.getBootloader() + "\n" + "Security Patch Level: " + device.getSecurityPatch() + "\n" +
                "Manufacture: " + device.getManufacture() + "\n" + "Model: " + device.getModel();
    }
    private String formatScores(Facts device){
        return "OS Version: " + device.getOsScore() + "\n" + "Root Status: " + device.getRootScore() + "\n" +
                "Bootloader Version: " + device.getBootloaderScore() + "\n" + "Security Patch Level: " + device.getPatchScore() + "\n" +
                "Apps: " + device.getAppsScore() ;
    }

    private String formatNetworkScores(Facts network){
        return "Encryption : " + network.getEncryptionScore() + "\n" + "Metered network : " + network.getNetworkMeteredScore()+ "\n" +
                "Trusted : " + network.getNetworkTrustedScore();
    }


    @NonNull
    private Facts processNetwork(){
        Context context = getApplicationContext();
        NetworkChecking networkChecking = new NetworkChecking();
        Facts network = new Facts();
        Map a = networkChecking.checkNetworkStatus(context);
        network.setNetwork_results(a);
        return network;
    }
    private void getAppInfo(Facts device,PackageManager pm){
        device.setSpecialPermissions(ScanInstalledApps.get_permission_list());
        device.setPermission_protections(ScanInstalledApps.get_protection_levels(device.getSpecialPermissions()));
        device.setInstalledApps(ScanInstalledApps.scanApps(pm,device.getPermission_protections()));

    }
    private HashMap<String,Object> makeAppsInputs(Facts dummy){
        HashMap<String,Object> Apps = new HashMap<>();
        for(String appKey:dummy.getInstalledApps().keySet()){
            HashMap<String,Object> appInfo = new HashMap<>();
            appInfo.put("name",dummy.getInstalledApps().get(appKey).name);
            appInfo.put("category",dummy.getInstalledApps().get(appKey).category);
            HashMap<String,Object> perms = new HashMap<>();
            Set<String> permKeys = dummy.getInstalledApps().get(appKey).AppPermissions.keySet();
            for(String permKey:permKeys){
                HashMap<String,Object> perm_info = new HashMap<>();
                perm_info.put("exist",dummy.getInstalledApps().get(appKey).AppPermissions.get(permKey).getExist());
                perm_info.put("granted",dummy.getInstalledApps().get(appKey).AppPermissions.get(permKey).getGranted());
                perm_info.put("dangerous",dummy.getInstalledApps().get(appKey).AppPermissions.get(permKey).getDangerous());
                perms.put(permKey,perm_info);
            }
            appInfo.put("AppPermissions",perms);
            Apps.put(appKey,appInfo);
        }
        return Apps;
    }
    private HashMap<String,Object> makeAppsOutputs(Facts dummy){
        HashMap<String,Object> Apps = new HashMap<>();
        for(String appKey:dummy.getInstalledApps().keySet()){
            HashMap<String,Object> appInfo = new HashMap<>();
            appInfo.put("score",dummy.getInstalledApps().get(appKey).getScore());
            HashMap<String,Object> perms = new HashMap<>();
            Set<String> permKeys = dummy.getInstalledApps().get(appKey).AppPermissions.keySet();
            for(String permKey:permKeys){
                HashMap<String,Object> perm_info = new HashMap<>();
                perm_info.put("permscore",dummy.getInstalledApps().get(appKey).AppPermissions.get(permKey).getPermscore());
                perm_info.put("used",dummy.getInstalledApps().get(appKey).AppPermissions.get(permKey).getUsed());
                perms.put(permKey,perm_info);
            }
            appInfo.put("AppPermissions",perms);
            Apps.put(appKey,appInfo);
        }
        return Apps;
    }
    private void generate_database(){
        Rules rule_set = new Rules();
        for(int icnt=0; icnt < 100;icnt++){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String deviceName="device"+ (icnt + 1);
            Facts dummy = RandomDataGenerator.generateRandomFacts();
            rule_set.evaluate(dummy);
            rule_set.process(dummy);
            //create hashmap of inputs
            HashMap<String,Object> inputs = new HashMap<>();
            inputs.put("root",dummy.getRoot());
            inputs.put("osVersion",dummy.getOsVersion());
            inputs.put("bootLoader",dummy.getBootloader());
            inputs.put("patch",dummy.getSecurityPatch());
            inputs.put("encryption",dummy.getEncryption());
            inputs.put("meteredNetwork",dummy.getMeteredNetwork());
            inputs.put("trustedNetwork",dummy.getTrustedNetwork());
            for(String appKey: dummy.getInstalledApps().keySet()){
                AppData app = dummy.getInstalledApps().get(appKey);
                int count = 1;
                for(String permKey:app.AppPermissions.keySet()){
                    inputs.put(appKey+"Perm"+ count +"_exists",app.AppPermissions.get(permKey).getExist());
                    inputs.put(appKey+"Perm"+ count +"_granted",app.AppPermissions.get(permKey).getGranted());
                    count++;
                }
            }
            //create outputs
            HashMap<String,Object> outputs = new HashMap<>();
            outputs.put("rootScore",dummy.getRootScore());
            outputs.put("osScore",dummy.getOsScore());
            outputs.put("bootloaderScore",dummy.getBootloaderScore());
            outputs.put("patchScore",dummy.getPatchScore());
            outputs.put("encryptionScore",dummy.getEncryptionScore());
            outputs.put("meteredNetworkScore",dummy.getNetworkMeteredScore());
            outputs.put("trustedNetworkScore",dummy.getNetworkTrustedScore());
            for(String appKey: dummy.getInstalledApps().keySet()){
                AppData app = dummy.getInstalledApps().get(appKey);
                int count = 1;
                for(String permKey:app.AppPermissions.keySet()){
                    outputs.put(appKey+"Perm"+ count +"_existsScore",app.AppPermissions.get(permKey).getExistScore());
                    outputs.put(appKey+"Perm"+ count +"_grantedScore",app.AppPermissions.get(permKey).getGrantedScore());
                    count++;
                }
            }

            if(icnt<400){
                db.collection("trainingInputs").document(deviceName).set(inputs);
                db.collection("trainingOutputs").document(deviceName).set(outputs);
                Log.i("Firebase Operation","Moved inputs and outputs for "+deviceName);

            }
            else {
                db.collection("testingInputs").document(deviceName).set(inputs);
                db.collection("testingOutputs").document(deviceName).set(outputs);
                Log.i("Firebase Operation","Moved inputs and outputs for "+deviceName);
            }
//            db.collection("devices").document(deviceName).set(dummy);
        }
    }
}