package com.qp.rpc.protocol;

import java.io.*;

/**
 * use java default object serialize
 * @author qp
 */
public class RpcSerializer {
    /**
     * encode object to byte message
     * @param obj
     * @return
     */
    public byte[] encode(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return bytes;
    }

    /**
     * decode from byte message to object
     * @param msg
     * @return
     */
    public Object decode(byte[] msg) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (msg);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getStackTrace());
        }
        return obj;
    }
}
