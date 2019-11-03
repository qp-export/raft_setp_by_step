package com.qp.rpc;

public interface CalculatedService {
    /**
     * add two number
     *
     * @param x
     * @param y
     * @return
     */
    long addTwo(long x, long y);


    /**
     * multiply three number
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    long multiplyThree(long x, long y, long z);
}
