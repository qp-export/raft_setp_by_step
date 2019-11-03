package com.qp.rpc;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author qp
 */
public class CalculatedServiceClientImpl implements CalculatedService {

    private Socket socket;
    private String ip;
    private int port;

    public CalculatedServiceClientImpl(String ip, int port) {
        super();
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
            socket = null;
        }
    }

    @Override
    protected void finalize() {
        try {
            super.finalize();
            if (null != socket) {
                socket.close();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public long addTwo(long x, long y) {
        long result = -1;
        if (null == socket) {
            System.out.println("can't connet remote.");
        }
        //网络连接和调用
        try {
            int paramsLen = Long.BYTES * 3;
            ByteBuffer data = ByteBuffer.allocate(Integer.BYTES + paramsLen);
            //len, methodID, params
            data.putInt(paramsLen);
            data.putLong(MethodsNames.M_ADD_TOW.getID());
            data.putLong(x);
            data.putLong(y);
            socket.getOutputStream().write(data.array());
            socket.getOutputStream().flush();
            byte[] rb = new byte[Long.BYTES];
            socket.getInputStream().read(rb);
            ByteBuffer rbb = ByteBuffer.wrap(rb);
            result = rbb.getLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long multiplyThree(long x, long y, long z) {
        long result = -1;
        if (null == socket) {
            System.out.println("can't connet remote.");
        }
        //网络连接和调用
        try {
            int paramsLen = Long.BYTES * 4;
            ByteBuffer data = ByteBuffer.allocate(Integer.BYTES + paramsLen);
            //len, methodID, params
            data.putInt(paramsLen);
            data.putLong(MethodsNames.M_MULTIPLY_THREE.getID());
            data.putLong(x);
            data.putLong(y);
            data.putLong(z);
            socket.getOutputStream().write(data.array());
            socket.getOutputStream().flush();
            byte[] rb = new byte[Long.BYTES];
            socket.getInputStream().read(rb);
            ByteBuffer rbb = ByteBuffer.wrap(rb);
            result = rbb.getLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String args[]) {
        CalculatedServiceClientImpl cc = new CalculatedServiceClientImpl("127.0.0.1", 8888);
        long x = 10, y = 1003, z = 2;
        System.out.println(x + "+" + y + "=" + cc.addTwo(x, y));
        CalculatedServiceClientImpl cc2 = new CalculatedServiceClientImpl("127.0.0.1", 8888);
        System.out.println(x + "*" + y + "*" + z + "=" + cc2.multiplyThree(x, y, z));
    }
}
