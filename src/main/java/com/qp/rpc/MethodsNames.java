package com.qp.rpc;

public enum MethodsNames {

    M_ADD_TOW(10001), M_MULTIPLY_THREE(10002);

    private long id;

    MethodsNames(long id) {
        this.id = id;
    }

    public long getID() {
        return id;
    }
}
