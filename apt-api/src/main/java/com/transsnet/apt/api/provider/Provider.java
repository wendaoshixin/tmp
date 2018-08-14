package com.transsnet.apt.api.provider;

import android.content.Context;
import android.view.View;

/**
 * Created by liuzhi on 18-4-16.
 */

public interface Provider {
    Context getContext(Object object);
    View findView(Object object, int id);
}
