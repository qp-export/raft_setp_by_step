package com.qp.rpc.protocol;
import java.io.Serializable;

/**
 * @author qp
 */
public class RpcResponse implements Serializable {
    private int requestId;
    private int responseCode;
    private Object result;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public RpcResponse setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public int getRequestId() {
        return requestId;
    }

    public RpcResponse setRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public RpcResponse setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RpcResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
