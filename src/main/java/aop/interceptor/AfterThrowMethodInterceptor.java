package aop.interceptor;

import aop.MethodInvocation;

import java.lang.reflect.Method;

public class AfterThrowMethodInterceptor implements MethodInterceptor{
    private Object aspectObj;
    private Method aspectMethod;
    public AfterThrowMethodInterceptor(Object aspectObj, Method aspectMethod) {
        this.aspectMethod = aspectMethod;
        this.aspectObj = aspectObj;
    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }
        catch (Exception e) {
            aspectMethod.setAccessible(true);
            aspectMethod.invoke(aspectObj);
        }
        return null;
    }
}
