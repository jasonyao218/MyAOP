package aop.interceptor;

import aop.MethodInvocation;

import java.lang.reflect.Method;

public class AfterReturnMethodInterceptor implements MethodInterceptor {

    private Object aspectObj;
    private Method aspectMethod;

    public AfterReturnMethodInterceptor(Object aspectObj, Method aspectMethod) {
        this.aspectMethod = aspectMethod;
        this.aspectObj = aspectObj;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            Object result = mi.proceed();
            aspectMethod.setAccessible(true);
            aspectMethod.invoke(aspectObj);
            return result;
        }
        catch (Exception e) {
            System.out.println("Exception caught. Execute AfterThrow annotation.");
            return new AfterThrowMethodInterceptor(aspectObj, aspectMethod);
        }
    }
}
