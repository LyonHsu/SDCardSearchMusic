package lyon.sdcard.searchmusic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Permission {
    public static final int ACCESS_FINE_LOCATION = 4;
    public static boolean checReadExternalStoragePermission(Context context) {
        boolean checkStatus = false;
        String permissionS = Manifest.permission.READ_EXTERNAL_STORAGE;
        String[] str_permissionModule = {permissionS};
        int permission = ActivityCompat.checkSelfPermission(context, permissionS);
        if (permission == PERMISSION_GRANTED){
            checkStatus = true;
        }else{
            ActivityCompat.requestPermissions((Activity) context, str_permissionModule, ACCESS_FINE_LOCATION);
        }
        return checkStatus;
    }
}
