package com.transsnet.apt.api;

import android.app.Activity;
import android.view.View;

import com.transsnet.apt.api.provider.ActivityProvider;
import com.transsnet.apt.api.provider.ViewProvider;


/**
 * Created by liuzhi on 18-4-16.
 */

public class ButterKnife {

    private static final ActivityProvider activityProvider = new ActivityProvider();
    private static final ViewProvider viewProvider = new ViewProvider();


    public static void bind(Activity host) {
        ViewBind viewBind = findProxyClass(host);
        if(viewBind != null)
            viewBind.onBingding(host, host, activityProvider);
    }

    private static ViewBind findProxyClass(Object object) {
        try {
            Class<?> aClass = object.getClass();
            Class<?> proxyClass = Class.forName(aClass.getName() + "$$ViewBind");
            return (ViewBind) proxyClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static void bind(Object host, View view) {
        ViewBind viewBind = findProxyClass(host);
        if(viewBind != null)
            viewBind.onBingding(host, view, viewProvider);
    }
}
