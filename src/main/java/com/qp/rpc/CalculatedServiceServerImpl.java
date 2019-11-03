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
        try {
            byte[] data = new byte[Long.BYTES*2];
            socket.getInputStream().read(data);
            ByteBuffer bs = ByteBuffer.wrap(data);
            long x = bs.getLong();
            long y = bs.getLong(Long.BYTES);
            System.out.println("client send x=" + x +" y=" + y);
            CalculatedServiceServerImpl cs = new CalculatedServiceServerImpl();
            long result = cs.addTwo(x, y);
            ByteBuffer rbs = ByteBuffer.allocate(Long.BYTES);
            rbs.putLong(result);
            socket.getOutputStream().write(rbs.array());
            System.out.println("write result " + result);
        } catch (IOException e ) {
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

