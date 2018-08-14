package com.transsnet.apt.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.transsnet.apt.TypeUtil;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;



/**
 * Created by liuzhi on 18-4-16.
 */

public class AnnotatedClass {

    private final TypeElement mTypeElement;
    private final Elements mElements;
    private List<BindViewField> mFields;
    private List<OnClickMethod> mMethods;

    public AnnotatedClass(TypeElement typeElement, Elements elements) {
        mTypeElement = typeElement;
        mElements = elements;
        mFields = new ArrayList<>();
        mMethods = new ArrayList<>();
    }

    String getFullClassName() {
        return mTypeElement.getQualifiedName().toString();
    }

    public void adField(BindViewField bindViewField) {
        mFields.add(bindViewField);
    }

    public void addMethod(OnClickMethod onClickMethod) {
        mMethods.add(onClickMethod);
    }

    public JavaFile generateFile() {

        //generate Method
        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("onBingding")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(TypeUtil.PROVIDER, "provider");


        for (BindViewField bindViewField : mFields) {
            methodSpecBuilder.addStatement("host.$N = ($T) (provider.findView(source, $L))",
                    bindViewField.getFieldName(),
                    ClassName.get(bindViewField.getFiledType()),
                    bindViewField.getResId());
        }

        for (OnClickMethod method : mMethods) {
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ANDROID_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("host.$N(view)", method.getmMethodName())
                            .build())
                    .build();

            methodSpecBuilder.addStatement("View.OnClickListener $Llistener= $L ", method.getmMethodName(), listener);
            for(int id:method.getResId()){
                methodSpecBuilder.addStatement("provider.findView(source, $L).setOnClickListener($Llistener)", id, method.getmMethodName());
            }
        }


        //generate Class
        ParameterizedTypeName superTypeName = ParameterizedTypeName.get(TypeUtil.VIEW_BIND, TypeName.get(mTypeElement.asType()));

        PackageElement packageElement = mElements.getPackageOf(mTypeElement);
        // 如 com.np.ioc
        String packageName = packageElement.getQualifiedName().toString();
        // MainActivity$A
        String className = getClassName(packageName, mTypeElement);
        // 代理类名: MainActivity$A_ViewInject
        String proxyClassName = className + "$$ViewBind";
        methodSpecBuilder.addStatement("/*"+"packageElement=$L packageName=$L,className=$L*/",
                packageElement.toString(),getFullClassName(),className);

        TypeSpec bindClass = TypeSpec.classBuilder(proxyClassName /*className + "$$ViewBind"*/)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(superTypeName)
                .addMethod(methodSpecBuilder.build())
//                .addJavadoc("packageElement="+packageElement.toString())
                //.addJavadoc("packageName="+getFullClassName())
//                .addJavadoc("className="+className)
                .build();

        String packeName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();

        return JavaFile.builder(packeName, bindClass).build();
    }

    /** 获取类名 */
    private String getClassName(String packageName, TypeElement typeElement) {
        int length = packageName.length() + 1;
        // 如 com.np.ioc.MainActivity.A
        String classQualifiedName = typeElement.getQualifiedName().toString();
        // 如 MainActivity.A
        String name = classQualifiedName.substring(length);
        // 需要返回的是 MainActivity$A
        return name.replace(".", "$");
    }


    @Override
    public String toString() {
        return "AnnotatedClass{" +
                "mTypeElement=" + mTypeElement +
                ", mElements=" + mElements +
                ", mFields=" + mFields +
                ", mMethods=" + mMethods +
                '}';
    }
}
