package aop.interceptor;

import aop.MethodInvocation;

import java.lang.reflect.Method;

public class AfterMethodInterceptor implements MethodInterceptor{

    private Object aspectObj;
    private Method aspectMethod;

    public AfterMethodInterceptor(Object aspectObj, Method aspectMethod) {
        this.aspectObj = aspectObj;
        this.aspectMethod = aspectMethod;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable{
        Object result = mi.proceed();
        aspectMethod.setAccessible(true);
        aspectMethod.invoke(aspectObj);
        return result;
    }
}
