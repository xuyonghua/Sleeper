package com.xyh.sleeper.permission;

/**
 * author: baiiu
 * date: on 16/12/20 19:32
 * description:
 */
public interface PermissionCallBackM {
    void onPermissionGrantedM(int requestCode, String... perms);

    void onPermissionDeniedM(int requestCode, String... perms);
}
