package com.qp.rpc.context;

import com.qp.rpc.protocol.RpcSerializer;
import com.qp.rpc.protocol.RpcTransport;

import java.net.Socket;

/**
 * @author qp
 */
public class RpcContext {
    private Socket socket;
    private RpcSerializer serializer;
    private RpcTransport transport;
    private String interfaceClassName;

    public Socket getSocket() {
        return socket;
    }

    public RpcContext setSocket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public RpcSerializer getSerializer() {
        return serializer;
    }

    public RpcContext setSerializer(RpcSerializer serializer) {
        this.serializer = serializer;
        return this;
    }

    public RpcTransport getTransport() {
        return transport;
    }

    public RpcContext setTransport(RpcTransport transport) {
        this.transport = transport;
        return this;
    }

    public String getInterfaceClassName() {
        return interfaceClassName;
    }

    public RpcContext setInterfaceClassName(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
        return this;
    }
}
