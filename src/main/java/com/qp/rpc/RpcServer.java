package com.qp.rpc;

import com.qp.rpc.protocol.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcServer {

    //interface to serverImpl map
    private Map<String, String> servicesMap = new ConcurrentHashMap<String, String>();

    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.publishService(CalculatedService.class.getName(), CalculatedServiceServerImpl.class.getName());
        ServerSocket serverSocket = null;
        try {
            int port = 8888;
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("start listen " + port);
                final Socket socket = serverSocket.accept();
                server.handleRequest(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("error closing socket server" + e.getMessage());
            }
        }
    }

    public void publishService(String interfaceName, String serviceName) {
        this.servicesMap.put(interfaceName, serviceName);
    }

    public String getService(String interfaceName) {
        return this.servicesMap.get(interfaceName);
    }

    private void handleRequest(Socket socket) {
        System.out.println("client connected: " + socket.getRemoteSocketAddress());
        RpcTransport transport = new RpcTransport();
        byte[] reqData = transport.readRequest(socket);
        RpcSerializer serializer = new RpcSerializer();
        RpcRequest req = (RpcRequest) serializer.decode(reqData);
        RpcResponse resp = invokeMethod(req);
        byte[] respData = serializer.encode(resp);
        transport.writeResponse(respData, socket);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * call the method use the reflect
     */
    private RpcResponse invokeMethod(RpcRequest r) {
        Class<?> c = null;
        Object obj = null;
        Class<?>[] argClasses = new Class<?>[r.getParamsClass().length];
        RpcResponse response = new RpcResponse();
        response.setRequestId(r.getRequestId());
        try {
            //use the client interfaceName to map the serverImpl class name
            String serviceName = getService(r.getClassName());
            c = Class.forName(serviceName);
            int index = 0;
            for (String name : r.getParamsClass()) {
                argClasses[index] = Class.forName(name);
                index++;
            }
        } catch (ClassNotFoundException e) {
            response.setResponseCode(101).setErrMsg(e.getMessage());
            e.printStackTrace();
        }
        try {
            obj = c.newInstance();
            //use the Long, not long
            //java.lang.NoSuchMethodException: com.qp.rpc.CalculatedServiceServerImpl.addTwo(java.lang.Long, java.lang.Long)
            Method m = c.getDeclaredMethod(r.getMethodName(), argClasses);
            Object result = m.invoke(obj, r.getParams());
            response.setResult(result);
        } catch (InstantiationException e) {
            response.setResponseCode(102).setErrMsg(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            response.setResponseCode(103).setErrMsg(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            response.setResponseCode(104).setErrMsg(e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            response.setResponseCode(105).setErrMsg(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
