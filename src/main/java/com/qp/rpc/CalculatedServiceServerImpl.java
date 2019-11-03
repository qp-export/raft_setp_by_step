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
    public long addTwo(Long x, Long y) {
        return x + y;
    }

    @Override
    public long multiplyThree(Long x, Long y, Long z) {
        return x * y * z;
    }
}

