package com.autotest.acctools.Serial;

public abstract class BaseActionModle implements SerialObserver {

    public String[] shareObj = {"true"};
    public SerialClient mSerialClient;

    public BaseActionModle() {
        mSerialClient = SerialClient.getInstance();
        mSerialClient.setTeyAgainTime(500)
                .setMaxTryTimes(3);
        registerObserver();
    }

    @Override
    public void success(String response) {

    }

    @Override
    public void fail(int error) {
        switch (error){
            case SerialClient.ERROR_PORT_ISNULL:{

                break;
            }
            case SerialClient.ERROR_DISCONNECTED:{

                break;
            }
            case SerialClient.ERROR_STREAM_DATA:{

                break;
            }
            case SerialClient.ERROR_TIME_OUT:{

                break;
            }
            default:
                break;
        }
    }

    public boolean openSerialPort(String portName, int baudrate) {
        return mSerialClient.openSerialPort(portName, baudrate);
    }

    public boolean sendCMD(byte[] order) {
        return mSerialClient.sendToPort(order);
    }

    final public void unregisterObserver() {
        mSerialClient.unregister(this);
    }

    private void registerObserver() {
        mSerialClient.register(this);
    }
}
