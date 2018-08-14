package com.transsnet.aop;

import android.util.Log;

import com.app.annotation.TimeTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TimeTraceAspect {
    private static final String TAG = "TimeTraceAspect";

    // 语法：execution(@注解 访问权限 返回值的类型 包名.函数名(参数))
    // 表示：使用TimeTrace注解的任意类型返回值任意方法名（任意参数）
    @Pointcut("execution(@com.app.annotation.TimeTrace * *(..))")
    public void myPointCut(){

    }

//    @Pointcut("@annotation(com.aop.aspectj.TimeTrace)")
//    public void myPointCut2(){
//
//    }

    @Pointcut("execution(* add(..))")
    public void methodAddTest(){

    }

    // Advance比较常用的有：Before():方法执行前,After():方法执行后,Around():代替原有逻辑
    @Around("myPointCut() || methodAddTest()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable {
        // 方法执行前先记录时间
        long start=System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取注解
        TimeTrace annotation = methodSignature.getMethod().getAnnotation(TimeTrace.class);
        String value = annotation.value();

        StringBuffer sb = new StringBuffer();
        sb.append("\npoint.getKind()="+point.getKind());
        sb.append("\npoint.getArgs()="+point.getArgs());
        sb.append("\npoint.getTarget()="+point.getTarget());
        sb.append("\npoint.getClass()="+point.getClass());
        sb.append("\npoint.getThis()="+point.getThis());
        sb.append("\npoint.getSignature()="+point.getSignature());
        sb.append("\nmethodSignature.getName()="+methodSignature.getName());
        sb.append("\nmethodSignature.getDeclaringType()="+methodSignature.getDeclaringType());
        sb.append("\nmethodSignature.getReturnType()="+methodSignature.getReturnType());
        sb.append("\nmethodSignature.getName()="+methodSignature.getName());
        sb.append("\nmethodSignature.getDeclaringType()="+methodSignature.getDeclaringType());
        sb.append("\nmethodSignature.getParameterNames()="+methodSignature.getParameterNames());
        sb.append("\nmethodSignature.getParameterTypes()="+methodSignature.getParameterTypes());

        sb.append("\nmethodSignature.getMethod()="+methodSignature.getMethod());
        sb.append("\nmethodSignature.getDeclaringTypeName()="+methodSignature.getDeclaringTypeName());
        sb.append("\nmethodSignature.getModifiers()="+methodSignature.getModifiers());


        sb.append("\n------------------------------getArgs------------------------------");
        Object[] objects = point.getArgs();
        for (int i=0; i<objects.length; i++){
            Object object = objects[i];
            String typeName = object.getClass().getSimpleName();
            sb.append(String.format("\ngetArgs[%d]=%s, type=%s",i, object.toString(), typeName));

        }

        sb.append("\n------------------------------getParameterTypes------------------------------");
        Class[] parameterTypes = methodSignature.getParameterTypes();
        for (int i=0; i<parameterTypes.length; i++){
            Class parameterType = parameterTypes[i];
            String typeName = parameterType.getSimpleName();
            sb.append(String.format("\nparameterTypes[%d]=%s",i, parameterType.toString()));
        }
        sb.append("\n------------------------------getParameterNames------------------------------");
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i=0; i<parameterTypes.length; i++){
            String parameterName = parameterNames[i];

            sb.append(String.format("\nparameterTypes[%d]=%s",i, parameterName.toString()));
        }

        Log.e(TAG,sb.toString());

        // 执行原方法体
        Object proceed = point.proceed();

        // 方法执行完成后，记录时间，打印日志
        long end = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        if (proceed instanceof Boolean){
            // 返回的是boolean
            if ((Boolean)proceed){
                stringBuffer.append(value)
                        .append("成功，耗时：")
                        .append(end - start);
            }else{
                stringBuffer.append(value)
                        .append("失败，耗时：")
                        .append(end - start);
            }
        }
        Log.e(TAG,stringBuffer.toString());
        return proceed;
    }

}