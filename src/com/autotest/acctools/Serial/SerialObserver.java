package com.autotest.acctools.Serial;

public interface SerialObserver {

    public void success(String response);
    public void fail(int error);
}
