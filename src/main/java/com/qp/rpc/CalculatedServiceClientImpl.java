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
            ByteBuffer data = ByteBuffer.allocate(Long.BYTES * 2);
            data.putLong(x);
            data.putLong(y);
            socket.getOutputStream().write(data.array());
            byte[] rb = new byte[Long.BYTES];
            socket.getInputStream().read(rb);
            ByteBuffer rbs = ByteBuffer.wrap(rb);
            result = rbs.getLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String args[]) {
        CalculatedServiceClientImpl cc = new CalculatedServiceClientImpl("127.0.0.1", 8888);
        long x = 10, y = 1003;
        System.out.println(x + "+" + y + "=" + cc.addTwo(x, y));
    }
}
