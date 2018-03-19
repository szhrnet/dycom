package com.szhrnet.dotcom.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;


import com.szhrnet.dotcom.view.DialogCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZCZ on 2017/5/18.
 */
public class PermissionUtils {

  public static final int CODE_READ_PHONE_STATE = 0;
  public static final int CODE_READ_EXTERNAL_STORAGE = 7;
  public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;

  public static final int CODE_MULTI_PERMISSION = 100;

  public static final String PERMISSION_READ_PHONT_STATE = Manifest.permission.READ_PHONE_STATE;
  public static final String PERMISSION_READ_EXTERNAL_STORAGE =
      Manifest.permission.READ_EXTERNAL_STORAGE;
  public static final String PERMISSION_WRITE_EXTERNAL_STORAGE =
      Manifest.permission.WRITE_EXTERNAL_STORAGE;

  private static final String[] PERMISSIONS = new String[] {
      PERMISSION_READ_PHONT_STATE, PERMISSION_READ_EXTERNAL_STORAGE,
      PERMISSION_WRITE_EXTERNAL_STORAGE
  };

  private static final String[] MESSAGES = new String[] {
      "请开启读取电话权限!", "请开启读取SD卡权限!", "请开启写入SD卡权限!"
  };

  public interface PermissionGrant {
    void onPermissionGranted(int requestCode);
  }

  public static void requestPermission(final Activity activity, final int requestCode,
      PermissionGrant permissionGrant) {
    if (activity == null) {
      return;
    }

    if (Build.VERSION.SDK_INT >= 23) {
      int checkCallPhonePermission =
          ContextCompat.checkSelfPermission(activity, PERMISSIONS[requestCode]);
      if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
            PERMISSIONS[requestCode])) {
          showDialog(activity, requestCode);
        } else {
          ActivityCompat.requestPermissions(activity, new String[] { PERMISSIONS[requestCode] },
              requestCode);
        }
      } else {
        permissionGrant.onPermissionGranted(requestCode);
      }
    } else {
      permissionGrant.onPermissionGranted(requestCode);
    }
  }

  public static void requestPermissionsResult(final Activity activity, final int requestCode,
      @NonNull String[] permissions, @NonNull int[] grantResults, PermissionGrant permissionGrant) {

    if (requestCode == CODE_MULTI_PERMISSION) {
      requestMultiResult(activity, permissions, grantResults, permissionGrant);
      return;
    }

    if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      permissionGrant.onPermissionGranted(requestCode);
    } else {
      showDialogTwo(activity);
    }
  }

  /**
   * 一次申请多个权限
   */
  public static void requestMultiPermissions(final Activity activity, PermissionGrant grant) {

    if (activity == null) {
      return;
    }

    if (Build.VERSION.SDK_INT >= 23) {
      final List<String> permissionsList = getNoGrantedPermission(activity, false);
      final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);

      if (permissionsList == null || shouldRationalePermissionsList == null) {
        return;
      }

      if (permissionsList.size() > 0) {
        ActivityCompat.requestPermissions(activity,
            permissionsList.toArray(new String[permissionsList.size()]), CODE_MULTI_PERMISSION);
      } else if (shouldRationalePermissionsList.size() > 0) {
        //showTipsDialog(activity, CODE_MULTI_PERMISSION);

        DialogCustom dialogCustom = new DialogCustom(activity);
        dialogCustom.setTextTitle("")
            .setTextTip("请开启请求权限!")
            .setDialogCustomListener(new DialogCustom.DialogCustomListener() {
              @Override public void onCancel(View v, DialogCustom dialog) {
                ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(
                    new String[shouldRationalePermissionsList.size()]), CODE_MULTI_PERMISSION);
              }

              @Override public void onConfirm(View v, DialogCustom dialog) {
                ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(
                    new String[shouldRationalePermissionsList.size()]), CODE_MULTI_PERMISSION);
              }

              @Override public void onDismiss(DialogCustom dialog) {
              }
            })
            .setCanceledOnTouchOutside(false);
        dialogCustom.show();

      } else {
        grant.onPermissionGranted(CODE_MULTI_PERMISSION);
      }
    } else {
      grant.onPermissionGranted(CODE_MULTI_PERMISSION);
    }
  }

  private static void requestMultiResult(Activity activity, String[] permissions,
      int[] grantResults, PermissionGrant permissionGrant) {

    if (activity == null) {
      return;
    }

    Map<String, Integer> perms = new HashMap<>();

    ArrayList<String> notGranted = new ArrayList<>();
    for (int i = 0; i < permissions.length; i++) {
      perms.put(permissions[i], grantResults[i]);
      if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
        notGranted.add(permissions[i]);
      }
    }

    if (notGranted.size() == 0) {
      //Toast.makeText(activity, "all permission success" + notGranted, Toast.LENGTH_SHORT).show();
      permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
    } else {
      showDialogTwo(activity);
    }
  }

  /**
   * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale
   * permissions, false:return no granted and !shouldShowRequestPermissionRationale
   */
  public static ArrayList<String> getNoGrantedPermission(Activity activity,
      boolean isShouldRationale) {

    ArrayList<String> permissions = new ArrayList<>();

    for (int i = 0; i < PERMISSIONS.length; i++) {
      String requestPermission = PERMISSIONS[i];

      int checkSelfPermission = -1;
      try {
        checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
      } catch (RuntimeException e) {
        Toast.makeText(activity, "请开启请求权限!", Toast.LENGTH_SHORT).show();
        return null;
      }

      if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
          if (isShouldRationale) {
            permissions.add(requestPermission);
          }
        } else {
          if (!isShouldRationale) {
            permissions.add(requestPermission);
          }
        }
      }
    }

    return permissions;
  }

  private static void showDialog(final Activity activity, final int requestCode) {
    DialogCustom dialogCustom = new DialogCustom(activity);
    dialogCustom.setTextTitle("")
        .setTextTip(MESSAGES[requestCode])
        .setDialogCustomListener(new DialogCustom.DialogCustomListener() {
          @Override public void onCancel(View v, DialogCustom dialog) {
            ActivityCompat.requestPermissions(activity, new String[] { PERMISSIONS[requestCode] },
                requestCode);
          }

          @Override public void onConfirm(View v, DialogCustom dialog) {
            ActivityCompat.requestPermissions(activity, new String[] { PERMISSIONS[requestCode] },
                requestCode);
          }

          @Override public void onDismiss(DialogCustom dialog) {

          }
        })
        .setCanceledOnTouchOutside(false);
    dialogCustom.show();
  }

  private static void showDialogTwo(final Activity activity) {
    DialogCustom dialogCustom = new DialogCustom(activity);
    dialogCustom.setTextTitle("")
        .setTextTip("请开启请求权限!")
        .setDialogCustomListener(new DialogCustom.DialogCustomListener() {
          @Override public void onCancel(View v, DialogCustom dialog) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
          }

          @Override public void onConfirm(View v, DialogCustom dialog) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
          }

          @Override public void onDismiss(DialogCustom dialog) {

          }
        })
        .setCanceledOnTouchOutside(false);
    dialogCustom.show();

  }
}
