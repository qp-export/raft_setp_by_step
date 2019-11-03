package com.qp.rpc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author qp
 */
public class CalculatedServiceServerImpl implements CalculatedService {


    @Override
    public long addTwo(long x, long y) {
        return x + y;
    }

    @Override
    public long multiplyThree(long x, long y, long z) {
        return x * y * z;
    }

    public static void main(String args[]) {

        ServerSocket serverSocket = null;
        try {
            int port = 8888;
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("start listen " + port);
                final Socket socket = serverSocket.accept();
                handleRequest(socket);
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

    private static void handleRequest(Socket socket) {
        System.out.println("client connected: " + socket.getRemoteSocketAddress());
        long result = -1;
        CalculatedServiceServerImpl cs = new CalculatedServiceServerImpl();
        try {
            byte[] len = new byte[Integer.BYTES];
            socket.getInputStream().read(len);
            ByteBuffer lenBuffer = ByteBuffer.wrap(len);
            int size = lenBuffer.getInt();
            if (size > 0) {
                byte[] params = new byte[size];
                socket.getInputStream().read(params);
                ByteBuffer paramsBuffer = ByteBuffer.wrap(params);
                long mID = paramsBuffer.getLong();
                if (mID == MethodsNames.M_ADD_TOW.getID()) {
                    long x = paramsBuffer.getLong(Long.BYTES);
                    long y = paramsBuffer.getLong(Long.BYTES * 2);
                    result = cs.addTwo(x, y);
                    System.out.println(x + "+" + y + "=" + result);
                } else if (mID == MethodsNames.M_MULTIPLY_THREE.getID()) {
                    long x = paramsBuffer.getLong(Long.BYTES);
                    long y = paramsBuffer.getLong(Long.BYTES * 2);
                    long z = paramsBuffer.getLong(Long.BYTES * 3);
                    result = cs.multiplyThree(x, y, z);
                    System.out.println(x + "*" + y + "*" + "z" + "=" + result);
                } else {
                    System.out.println("unknow method");
                }
                ByteBuffer resultBuffer = ByteBuffer.allocate(Long.BYTES);
                resultBuffer.putLong(result);
                socket.getOutputStream().write(resultBuffer.array());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("error closing socket " + e.getMessage());
            }
        }
    }
}

