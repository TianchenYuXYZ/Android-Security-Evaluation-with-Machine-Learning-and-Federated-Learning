package com.example.securityevaluationwithexpertsystems;
import android.os.Build;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInfoUtil {

    public static String getOSVersion(){
        /*This method returns the version of the Android OS as a string. */
        return Build.VERSION.RELEASE;
    }

    /*Method to check if device is rooted*/
    public static boolean isDeviceRooted(){
        return checkRootSol1() || checkRootSol2();
    }

    /*Solution 1: Official Android builds released by device manufacturers are signed with release keys.
     Rooted device is more likely to run run custom ROMs or modified system images, both of which may use test keys*/
    private static boolean checkRootSol1(){
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");

    }

    /*Solution 2: The su binary allows users to switch to the superuser (root) mode and execute commands with elevated privileges.
    * For root check, we can check if the "su" binary exists on the system.*/
    private static boolean checkRootSol2(){
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader((process.getInputStream())));
            if (in.readLine() != null) return true; //it means the "su" was found
            return false;
        } catch (Exception e) {
            return false; //In case of an exception, we return false, meaning we didn't find the su binary.
        } finally {
            if (process != null) process.destroy(); // to clean up and terminate
        }
    }

    /*Method to Get Bootloader Version*/
    public static String getBootloaderVersion(){
        return Build.BOOTLOADER;
    }

    /*Method to Get Security Patch Level*/
    public static String getSecurityPatchLevel(){
        return Build.VERSION.SECURITY_PATCH;
    }

    /*Method to Get Manufacture*/
    public static String getManufacture(){
        return Build.MANUFACTURER;
    }

    /*Method to Get Model*/
    public static String getModel() {
        return Build.MODEL;
    }
}
