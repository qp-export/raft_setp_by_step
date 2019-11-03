package com.qp.rpc.proxy;

import com.qp.rpc.context.RpcContext;
import com.qp.rpc.protocol.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qp
 */
public class RpcClientHandler<T> implements InvocationHandler {

    private T target;
    private RpcContext context;

    public RpcClientHandler(T target, RpcContext context) {
        this.target = target;
        this.context = context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest r = makeRpcRequest(method, args);
        //encode the request
        byte[] reqData = context.getSerializer().encode(r);
        //send the request data
        byte[] respData = context.getTransport().doRequest(reqData,context.getSocket());
        //decode the response
        RpcResponse resp = (RpcResponse) context.getSerializer().decode(respData);
        return resp.getResult();
    }

    private RpcRequest makeRpcRequest(Method method, Object[] args) {
        RpcRequest r = new RpcRequest();
        r.setClassName(context.getInterfaceClassName())
                .setMethodName(method.getName())
                .setParams(args);
        String[] paramsClass = new String[args.length];
        int index = 0;
        for (Object arg : args) {
            paramsClass[index] = arg.getClass().getName();
            index++;
        }
        r.setParamsClass(paramsClass);
        return r;
    }
}


