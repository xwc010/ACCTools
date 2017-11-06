package com.autotest.acctools.Serial;

import com.lckj.autotest.exception.*;
import com.lckj.autotest.tools.Utils;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

public class SerialClient {

    public static final int ERROR_PORT_ISNULL = 400; // SerialPort为空
    public static final int ERROR_DISCONNECTED = 401; // 断开连接
    public static final int ERROR_STREAM_DATA = 402; // 输入输出流或数据异常
    public static final int ERROR_TIME_OUT = 403; // 多次重试后超时

    private static SerialClient mSerialClient = new SerialClient();
    private SerialClient() {}

    public static SerialClient getInstance() {
        return mSerialClient;
    }

    private SerialPort mSerialPort;
    //    private Map<String, > // 观察者管理
    private SerialObserver mObserver;

    /**
     * 打开串口
     *
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     * @throws SerialPortParameterFailure 设置串口参数失败
     * @throws NotASerialPort             端口指向设备不是串口类型
     * @throws NoSuchPort                 没有该端口对应的串口设备
     * @throws PortInUse                  端口已被占用
     */
    public boolean openSerialPort(String portName, int baudrate) {
        System.out.println("SerialClient Has openSerialPort Back!!!!!");
        try {
            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            //判断是不是串口
            if (commPort instanceof SerialPort) {

                try {
                    mSerialPort = (SerialPort) commPort;
                    //设置一下串口的波特率等参数
                    mSerialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    //System.out.println("Open " + portName + " sucessfully !");
                    //给串口添加监听器
                    mSerialPort.addEventListener(creatSerialPortEventListener());
                    //设置当有数据到达时唤醒监听接收线程
                    mSerialPort.notifyOnDataAvailable(true);
                    //设置当通信中断时唤醒中断线程
                    mSerialPort.notifyOnBreakInterrupt(true);
                    return true;
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                }
            } else {
                //不是串口
                System.out.println(portName + " 不是串口 !");
//                throw new NotASerialPort();
            }

        } catch (NoSuchPortException e1) {
            e1.printStackTrace();
        } catch (PortInUseException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private SerialPortEventListener creatSerialPortEventListener() {
        return new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                System.out.println("SerialClient Has Data Back!!!!!");
                synchronized (shareObj) {
                    shareObj[0] = "false";
                    shareObj.notifyAll();
                }


                switch (serialPortEvent.getEventType()) {
                    case SerialPortEvent.BI: // 10 通讯中断
//                        JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
                        if (mObserver != null) mObserver.fail(ERROR_DISCONNECTED);
                        break;
                    case SerialPortEvent.OE: // 7 溢位（溢出）错误
                    case SerialPortEvent.FE: // 9 帧错误
                    case SerialPortEvent.PE: // 8 奇偶校验错误
                    case SerialPortEvent.CD: // 6 载波检测
                    case SerialPortEvent.CTS: // 3 清除待发送数据
                    case SerialPortEvent.DSR: // 4 待发送数据准备好了
                    case SerialPortEvent.RI: // 5 振铃指示
                    case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                        break;
                    case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                        byte[] data = null;
                        try {
                            if (mSerialPort == null) {
//                                JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
                                if (mObserver != null) mObserver.fail(ERROR_PORT_ISNULL);
                            } else {
                                data = readFromPort();    //读取数据，存入字节数组
                                String data_hex = Utils.byte2HexStr(data, data.length);
//                                return_result += data_hex.replace(" ", "");
//                                System.out.println("0000return_result="+return_result+",时间:"+ Utils.getCurrentTimeMills()+",isNeedAsk="+isNeedAsk);
//                                if(isNeedAsk){
//                                    System.out.println("通知收到应答");
//                                    setChanged();
//                                    notifyObservers();
//                                }
                                if (mObserver != null) mObserver.success(data_hex);
                            }

                        } catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
//                            JOptionPane.showMessageDialog(null, e, "错误", JOptionPane.INFORMATION_MESSAGE);
//                            System.exit(0);	//发生读取错误时显示错误信息后退出系统
                            if (mObserver != null) mObserver.fail(ERROR_STREAM_DATA);
                        }

                        break;

                }
            }
        };
    }


    /**
     * 往串口发送数据
     *
     * @param order 待发送数据
     * @return boolean
     */
    public boolean sendToPort(byte[] order) {
        System.out.println("SerialClient do sendToPort method!!!!");

        if(mSerialPort == null){
            return false;
        }

        OutputStream out = null;
        int tag = -1;
        try {
            out = mSerialPort.getOutputStream();
            out.write(order);
            out.flush();
            tag = 1;
            startTimeOutWartch(order);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("往串口发送数据失败！");
//            throw new SendDataToSerialPortFailure();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("关闭串口对象的输出流（OutputStream）时出错！");
//                throw new SerialPortOutputStreamCloseFailure();
            }

            return tag == 1;
        }

    }

    /**
     * 从串口读取数据
     *
     * @return 读取到的数据
     * @throws ReadDataFromSerialPortFailure     从串口读取数据时出错
     * @throws SerialPortInputStreamCloseFailure 关闭串口对象输入流出错
     */
    private byte[] readFromPort() throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure {
        System.out.println("SerialClient do readFromPort method!!!!!");
        InputStream in = null;
        byte[] bytes = null;

        try {

            in = mSerialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长

            while (bufflenth != 0) {
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            }

        } catch (IOException e) {
            throw new ReadDataFromSerialPortFailure();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                throw new SerialPortInputStreamCloseFailure();
            }

        }
        return bytes;

    }

    public void register(SerialObserver observer) {
        System.out.println("SerialClient do register method!!!!!");
        mObserver = observer;
    }

    public void unregister(SerialObserver observer) {
        System.out.println("SerialClient do unregister method!!!!!");
        mObserver = null;
    }

    private int MAX_TRY_TIMES = 3; // 最大重发次数
    private int currentTimes = 0;
    public SerialClient setMaxTryTimes(int maxTimes){
        MAX_TRY_TIMES = maxTimes;
        return mSerialClient;
    }

    private long TeyAgainTime = 500; // 微秒
    public SerialClient setTeyAgainTime(long timeout){
        System.out.println("SerialClient do setTeyAgainTime method!!!!");
        TeyAgainTime = timeout;
        return mSerialClient;
    }

    private String[] shareObj = {"true"};
    private void startTimeOutWartch(final byte[] order) {
        System.out.println("SerialClient do startTimeOutWartch method!!!!");
        currentTimes ++;
        new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (shareObj) {
                    shareObj[0] = "true";
                    while ("true".equals(shareObj[0])) {
                        try {
                            shareObj.wait(TeyAgainTime);

                            if ("true".equals(shareObj[0])) {
                                System.out.println("timeout!");
                                if(currentTimes < MAX_TRY_TIMES){
                                    sendToPort(order);
                                }else {
                                    if(mObserver != null) mObserver.fail(ERROR_TIME_OUT);
                                }
                            }
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
