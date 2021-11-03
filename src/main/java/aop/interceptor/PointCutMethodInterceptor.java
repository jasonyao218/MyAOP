package aop.interceptor;

import aop.MethodInvocation;

import java.lang.reflect.Method;

public class PointCutMethodInterceptor implements MethodInterceptor{
    private Object aspectObj;
    private Method aspectMethod;
    private Method originalMethod;
    private String targetMethod;
    private String adviceType;

    public PointCutMethodInterceptor(Object aspectObj, Method aspectMethod, Method originalMethod, String targetMethod, String adviceType) {
        this.aspectObj = aspectObj;
        this.aspectMethod = aspectMethod;
        this.originalMethod = originalMethod;
        this.targetMethod = targetMethod;
        this.adviceType = adviceType;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if(targetMethod.equals(originalMethod.getName())) {
            if(adviceType.equals("After")) {
                Object result = mi.proceed();
                aspectMethod.setAccessible(true);
                aspectMethod.invoke(aspectObj);
                return result;
            }
            else if(adviceType.equals("Before")) {
                aspectMethod.setAccessible(true);
                aspectMethod.invoke(aspectObj);
                return mi.proceed();
            }
        }
        return mi.proceed();
    }
}
