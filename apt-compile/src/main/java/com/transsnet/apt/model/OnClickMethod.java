package com.transsnet.apt.model;



import com.app.annotation.apt.OnClick;

import java.util.Arrays;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

/**
 * Created by liuzhi on 18-4-17.
 */

public class OnClickMethod {

    private ExecutableElement mExecutableElement;
    private int[] mResId;

    @Override
    public String toString() {
        return "OnClickMethod||" +
                "mExecutableElement=" + mExecutableElement +
                ", mResId=" + Arrays.toString(mResId) +
                ", mMethodName=" + mMethodName;
    }

    private Name mMethodName;
    public OnClickMethod(Element element) {
        if(element.getKind() == ElementKind.METHOD){
            mExecutableElement = (ExecutableElement)element;
            mResId = mExecutableElement.getAnnotation(OnClick.class).value();
            mMethodName = mExecutableElement.getSimpleName();
        }
    }

    public Name getmMethodName() {
        return mMethodName;
    }

    public int[] getResId() {
        return mResId;
    }
}
