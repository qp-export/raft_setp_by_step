package com.qp.rpc;

import com.qp.rpc.context.RpcContext;
import com.qp.rpc.protocol.RpcSerializer;
import com.qp.rpc.protocol.RpcTransport;
import com.qp.rpc.proxy.RpcClientHandler;
import com.qp.rpc.proxy.RpcProxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * @author qp
 */
public class CalculatedServiceClientImpl implements CalculatedService {

    @Override
    public long addTwo(Long x, Long y) {
        //proxy by rpc proxy
        return 0;
    }

    @Override
    public long multiplyThree(Long x, Long y, Long z) {
        //proxy by rpc proxy
        return 0;
    }

    public static void main(String[] args) {
        int port = 8888;
        String ip = "127.0.0.1";
        CalculatedService c = new CalculatedServiceClientImpl();
        RpcSerializer serializer = new RpcSerializer();
        try {
            Socket socket = new Socket(ip, port);
            RpcTransport transport = new RpcTransport();
            RpcContext context = new RpcContext().setSocket(socket)
                    .setTransport(transport)
                    .setSerializer(serializer)
                    .setInterfaceClassName(CalculatedService.class.getName());
            InvocationHandler h = new RpcClientHandler<CalculatedService>(c, context);
            RpcProxy proxy = new RpcProxy();
            CalculatedService client = proxy.getProxy(CalculatedService.class, h);
            long x = 10, y = 1003, z = 2;
            System.out.println(x + "*" + y + "*" + z + "=" + client.multiplyThree(x, y, z));
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
