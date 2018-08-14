package com.transsnet.apt.api.provider;

import android.content.Context;
import android.view.View;


/**
 * Created by liuzhi on 18-4-17.
 */

public class ViewProvider implements Provider {
    @Override
    public Context getContext(Object object) {
        return ((View)object).getContext();
    }

    @Override
    public View findView(Object object, int id) {
        return ((View)object).findViewById(id);
    }
}
