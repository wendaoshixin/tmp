package com.transsnet.apt.model;



import com.app.annotation.apt.BindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by liuzhi on 18-4-16.
 */

public class BindViewField {

    private VariableElement mVariableElement;
    private int mResId;
    public BindViewField(Element element) {
        if (element.getKind() == ElementKind.FIELD){
            mVariableElement = (VariableElement) element;
            BindView bindView = mVariableElement.getAnnotation(BindView.class);
            mResId = bindView.value();

        }
    }

    public Object getFieldName() {
        return mVariableElement.getSimpleName();
    }

    public TypeMirror getFiledType() {
        return mVariableElement.asType();
    }

    public Object getResId() {
        return mResId;
    }
}
