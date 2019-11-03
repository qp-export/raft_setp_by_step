package com.qp.rpc.protocol;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author qp
 */
public class RpcTransport {

    public RpcTransport() {
    }

    /**
     * do the rpc request and get the response, use int the client side
     *
     * @param reqData the request byte data
     * @param socket  the rpc socket
     * @return the response byte data
     */
    public byte[] doRequest(byte[] reqData, Socket socket) {
        byte[] respData = new byte[0];
        try {
            //first add the data length when send the request data
            int reqLen = reqData.length;
            ByteBuffer bReqLen = ByteBuffer.allocate(Integer.BYTES);
            bReqLen.putInt(reqLen);
            socket.getOutputStream().write(bReqLen.array());
            socket.getOutputStream().write(reqData);
            //get the data length when read the response data
            byte[] bRespLen = new byte[Integer.BYTES];
            socket.getInputStream().read(bRespLen);
            int respLen = ByteBuffer.wrap(bRespLen).getInt();
            if (respLen > 0) {
                respData = new byte[respLen];
                socket.getInputStream().read(respData);
            }
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respData;
    }

    /**
     * read the request data from the socket, use in the server side
     *
     * @param socket the rpc socket
     * @return request byte data
     */
    public byte[] readRequest(Socket socket) {
        byte[] result = new byte[0];
        if (socket.isConnected()) {
            byte[] len = new byte[Integer.BYTES];
            try {
                socket.getInputStream().read(len);
                int reqLen = ByteBuffer.wrap(len).getInt();
                if (reqLen > 0) {
                    result = new byte[reqLen];
                    socket.getInputStream().read(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * write response data in the socket, use in the server side
     * @param respData the response byte data
     * @param socket   the rpc socket
     * @return 0 success
     */
    public int writeResponse(byte[] respData, Socket socket) {
        if (socket.isConnected()) {
            int respLen = respData.length;
            ByteBuffer bRespLen = ByteBuffer.allocate(Integer.BYTES);
            bRespLen.putInt(respLen);
            try {
                socket.getOutputStream().write(bRespLen.array());
                socket.getOutputStream().write(respData);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }
}
