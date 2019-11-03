package com.qp.rpc.protocol;
import java.io.Serializable;

/**
 * @author qp
 */
public class RpcRequest implements Serializable {
    private int requestId;
    private String className;
    private String methodName;
    private String[] paramsClass;
    private Object[] params;

    public int getRequestId() {
        return requestId;
    }

    public RpcRequest setRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public RpcRequest setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RpcRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String[] getParamsClass() {
        return paramsClass;
    }

    public RpcRequest setParamsClass(String[] paramsClass) {
        this.paramsClass = paramsClass;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public RpcRequest setParams(Object[] params) {
        this.params = params;
        return this;
    }
}
