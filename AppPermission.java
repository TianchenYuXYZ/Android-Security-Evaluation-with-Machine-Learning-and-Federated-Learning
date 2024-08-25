package com.example.securityevaluationwithexpertsystems;
import android.content.pm.PermissionInfo;

public class AppPermission {
    private int exist;
    private int granted;
    private int dangerous;
    private Double permscore;
    private double existScore;
    private double grantedScore;
    private Boolean used;
    AppPermission(int exists,int granted,int dangerous){
        this.exist = exists;
        this.granted = granted;
        this.permscore = 0.0;
        this.grantedScore=0;
        this.existScore=0;
        this.used = Boolean.FALSE;
        if(dangerous== PermissionInfo.PROTECTION_DANGEROUS){
            this.dangerous = dangerous;
        }
        else{
            this.dangerous = 0;
        }
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public int getGranted() {
        return granted;
    }

    public void setGranted(int granted) {
        this.granted = granted;
    }

    public int getDangerous() {
        return dangerous;
    }

    public void setDangerous(int dangerous) {
        this.dangerous = dangerous;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Double getPermscore() {
        return permscore;
    }

    public void setPermscore(Double permscore) {
        this.permscore = permscore;
    }

    public double getGrantedScore() {
        return grantedScore;
    }

    public void setGrantedScore(double grantedScore) {
        this.grantedScore = grantedScore;
    }

    public double getExistScore() {
        return existScore;
    }

    public void setExistScore(double existScore) {
        this.existScore = existScore;
    }
//    public String getformattedData(){
//        return getExist()+" "+getGranted()+" "+getDangerous()+" "+getPermscore();
//    }
}
