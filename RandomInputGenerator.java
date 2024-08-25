package com.example.securityevaluationwithexpertsystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomInputGenerator {

    private static final int[] OS_VERSIONS = {10, 11, 12, 13, 14};
    private static final String[] ROOT_STATUS = {"Rooted", "Not Rooted"};
    private static final String[] BOOTLOADER_STATUS = {"bluejay-1.2-8893284", ""}; //"bluejay-1.1-8893284"
    private static final String[] SECURITY_PATCH_LEVELS = {"2024-03-01", "2023-09-05", "2024-01-01"};
    private static final String[] NETWORK_INFO={"NOT_METERED TRUSTED", "NOT_METERED NOT_TRUSTED", "METERED NOT_TRUSTED", "METERED TRUSTED"};
    private static final String[] ENCRYPTION = {"true", "false"};
    private static final Random RANDOM = new Random();

    public static HashMap<String, Object> generateRandomInputs() {
        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("os_version", OS_VERSIONS[RANDOM.nextInt(OS_VERSIONS.length)]);
        inputData.put("root_status", ROOT_STATUS[RANDOM.nextInt(ROOT_STATUS.length)]);
        inputData.put("bootloader_status", BOOTLOADER_STATUS[RANDOM.nextInt(BOOTLOADER_STATUS.length)]);
        inputData.put("security_patch_level", SECURITY_PATCH_LEVELS[RANDOM.nextInt(SECURITY_PATCH_LEVELS.length)]);
        inputData.put("app_permissions", generateRandomAppPermissions(10));  // Assume 10 apps for simplicity
        inputData.put("encryption", ENCRYPTION[RANDOM.nextInt(ENCRYPTION.length)]);
        inputData.put("network_info",NETWORK_INFO[RANDOM.nextInt(NETWORK_INFO.length)]);
        inputData.put("network_results",generate_network_results(ENCRYPTION[RANDOM.nextInt(ENCRYPTION.length)],NETWORK_INFO[RANDOM.nextInt(NETWORK_INFO.length)]));
        return inputData;
    }

    private static List<HashMap<String, AppPermission>> generateRandomAppPermissions(int numApps) {
        List<HashMap<String, AppPermission>> appPermissionsList = new ArrayList<>();
        for (int i = 0; i < numApps; i++) {
            HashMap<String, AppPermission> appPermissions = new HashMap<>();
            appPermissions.put("App_" + (i + 1), new AppPermission(RANDOM.nextInt(2), RANDOM.nextInt(2), RANDOM.nextInt(2)));
            appPermissionsList.add(appPermissions);
        }
        return appPermissionsList;
    }
    private  static Map generate_network_results(String encryption,String network_info){
        Map<String,String> map = new HashMap<>();
        map.put("communication Encrypted", encryption);
        map.put("active network info", network_info);
        return  map;
    }

}
