package aop;

import aop.advice.*;
import aop.interceptor.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JdkAOPInvocationHandler implements InvocationHandler {

    private Object originObj;
    private Object aspectObj;

    public JdkAOPInvocationHandler(Object originObj, Object aspectObj) {
        this.originObj = originObj;
        this.aspectObj = aspectObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> aspectClass = aspectObj.getClass();
        List<MethodInterceptor> interceptors = new ArrayList<>();
        for(Method aspectMethod: aspectClass.getDeclaredMethods()) {
            for(Annotation ano: aspectMethod.getDeclaredAnnotations()) {
                MethodInterceptor methodInterceptor = null;
                if(ano.annotationType() == Before.class) {
                    methodInterceptor = new BeforeMethodInterceptor(aspectObj, aspectMethod);
                } else if(ano.annotationType() == After.class) {
                    methodInterceptor = new AfterMethodInterceptor(aspectObj, aspectMethod);
                } else if(ano.annotationType() == Around.class) {
                    methodInterceptor = new AroundMethodInterceptor(aspectObj, aspectMethod);
                } else if(ano.annotationType() == AfterThrow.class) {
                    methodInterceptor = new AfterThrowMethodInterceptor(aspectObj, aspectMethod);
                } else if(ano.annotationType() == PointCut.class) {
                    String targetMethod = aspectMethod.getAnnotation(PointCut.class).methodName();
                    String adviceType = aspectMethod.getAnnotation(PointCut.class).adviceType();
                    methodInterceptor = new PointCutMethodInterceptor(aspectObj, aspectMethod, method, targetMethod, adviceType);
                } else if(ano.annotationType() == AfterReturn.class) {
                    methodInterceptor = new AfterReturnMethodInterceptor(aspectObj, aspectMethod);
                }
                interceptors.add(methodInterceptor);
            }
        }
        MethodInvocation mi = new ProxyMethodInvocation(interceptors, originObj, method, args);
        return mi.proceed();
    }
}
