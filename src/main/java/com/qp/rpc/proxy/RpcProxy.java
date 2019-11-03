package com.qp.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class RpcProxy {
    public <T> T getProxy(Class<T> interfaceClass, InvocationHandler handler) {
        T proxyObj = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, handler);
        return proxyObj;
    }
}
