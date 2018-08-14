package com.transsnet.apt.api;


import com.transsnet.apt.api.provider.Provider;

/**
 * Created by liuzhi on 18-4-16.
 */

public interface ViewBind<T> {
    public void onBingding(T host, Object view, Provider provider);
}
