package com.transsnet.apt;


import com.squareup.javapoet.ClassName;

/**
 * Created by liuzhi on 18-4-16.
 */

public class TypeUtil {
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
    public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName VIEW_BIND = ClassName.get("com.transsnet.apt.api", "ViewBind");
    public static final ClassName PROVIDER = ClassName.get("com.transsnet.apt.api.provider", "Provider");



}
