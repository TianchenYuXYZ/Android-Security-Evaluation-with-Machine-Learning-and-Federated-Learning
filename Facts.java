package com.example.securityevaluationwithexpertsystems;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facts {
    private HashMap<String,AppData> InstalledApps;
    private List<String> SpecialPermissions;
    private HashMap<String,Integer> permission_protections;
    private Map<String,String> network_results;
    private String root;
    private String osVersion;
    private String bootloader;
    private String securityPatch;
    private String model;
    private String manufacture;

    private String encryption;

    private String networkInfo;
    private String meteredNetwork;
    private String trustedNetwork;

    private int rootScore;
    private int osScore;
    private int bootloaderScore;
    private int patchScore;
    private int AppsScore;
    private int totalScore;

    private int encryptionScore;
    private int networkMeteredScore;
    private int networkTrustedScore;
    private int networkScore;


    public HashMap<String,AppData> getInstalledApps(){
        return this.InstalledApps;
    }

    public void setInstalledApps(HashMap<String, AppData> installedApps) {
        InstalledApps = installedApps;
    }

    public String getRoot(){
        return root;
    }

    public void setRoot(String root){
        this.root = root;
    }

    public String getOsVersion(){
        return osVersion;
    }

    public void setOsVersion(String osVersion){
        this.osVersion = osVersion;
    }

    public String getBootloader() {
        return bootloader;
    }

    public void setBootloader(String bootloader){
        this.bootloader = bootloader;
    }

    public String getSecurityPatch() {
        return securityPatch;
    }

    public void setSecurityPatch(String securityPatch) {
        this.securityPatch = securityPatch;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRootScore() {
        return rootScore;
    }

    public void setRootScore(int rootScore) {
        this.rootScore = rootScore;
    }

    public int getOsScore() {
        return osScore;
    }

    public void setOsScore(int osScore) {
        this.osScore = osScore;
    }

    public int getBootloaderScore() {
        return bootloaderScore;
    }

    public void setBootloaderScore(int bootloaderScore) {
        this.bootloaderScore = bootloaderScore;
    }

    public int getPatchScore() {
        return patchScore;
    }

    public void setPatchScore(int patchScore) {
        this.patchScore = patchScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }


    public List<String> getSpecialPermissions() {
        return SpecialPermissions;
    }

    public void setSpecialPermissions(List<String> specialPermissions) {
        SpecialPermissions = specialPermissions;
    }

    public HashMap<String, Integer> getPermission_protections() {
        return permission_protections;
    }

    public void setPermission_protections(HashMap<String, Integer> permission_protections) {
        this.permission_protections = permission_protections;
    }

    public int getAppsScore() {
        return AppsScore;
    }

    public void setAppsScore(int appsScore) {
        AppsScore = appsScore;
    }

    public Map<String, String> getNetwork_results() {
        return this.network_results;
    }

    public void setNetwork_results(Map<String, String> network_results) {
        this.network_results = network_results;
    }


    public int getEncryptionScore() {
        return encryptionScore;
    }

    public void setEncryptionScore(int encryptionScore) {
        this.encryptionScore = encryptionScore;
    }
    public int getNetworkMeteredScore() {
        return networkMeteredScore;
    }

    public void setNetworkMeteredScore(int networkMeteredScore) {
        this.networkMeteredScore = networkMeteredScore;
    }

    public int getNetworkTrustedScore() {
        return networkTrustedScore;
    }

    public void setNetworkTrustedScore(int networkTrustedScore) {
        this.networkTrustedScore = networkTrustedScore;
    }

    public int getNetworkScore() {
        return networkScore;
    }

    public void setNetworkScore(int networkScore) {
        this.networkScore = networkScore;
    }


    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public void setNetworkInfo(String networkInfo) {
        this.networkInfo = networkInfo;
    }

    public String getMeteredNetwork() {
        return meteredNetwork;
    }

    public void setMeteredNetwork(String meteredNetwork) {
        this.meteredNetwork = meteredNetwork;
    }

    public String getTrustedNetwork() {
        return trustedNetwork;
    }

    public void setTrustedNetwork(String trustedNetwork) {
        this.trustedNetwork = trustedNetwork;
    }

    public String getEncryption() {
        return encryption;
    }
}
