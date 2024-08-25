package com.example.securityevaluationwithexpertsystems;
import java.util.HashMap;

public class AppData {
    public String name;
    public int category;
    public HashMap<String,AppPermission> AppPermissions;
    private double score;
    AppData(String name,int category,HashMap<String,Integer> dangerouslevels){
        this.name = name;
        this.category = category;
        this.score = 100;
        AppPermissions = new HashMap<String,AppPermission>();
        for(String key : dangerouslevels.keySet()){
            AppPermissions.put(key,new AppPermission(0,0,dangerouslevels.get(key)));
        }
    }

    public AppData(String name, int category) {
        this.name = name;
        this.category = category;
        this.score = 100;
        AppPermissions = new HashMap<String,AppPermission>();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
