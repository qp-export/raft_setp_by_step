package com.qp.rpc;

public interface CalculatedService {
    /**
     * add two number
     *
     * @param x
     * @param y
     * @return
     */
    long addTwo(Long x, Long y);


    /**
     * multiply three number
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    long multiplyThree(Long x, Long y, Long z);
}
