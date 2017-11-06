package com.autotest.acctools.Serial;

import com.lckj.autotest.exception.NoSuchPort;
import com.lckj.autotest.exception.NotASerialPort;
import com.lckj.autotest.exception.PortInUse;
import com.lckj.autotest.exception.SerialPortParameterFailure;
import gnu.io.*;

import java.util.Map;
import java.util.TooManyListenersException;

public class SerialClient {

    private static SerialClient mSerialClient = new SerialClient();

    private SerialClient(){

    }

    public static SerialClient getInstance(){
        return mSerialClient;
    }

    private SerialPort mSerialPort;
//    private Map<String, > // 观察者管理

    /**
     * 打开串口
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     * @throws SerialPortParameterFailure 设置串口参数失败
     * @throws NotASerialPort 端口指向设备不是串口类型
     * @throws NoSuchPort 没有该端口对应的串口设备
     * @throws PortInUse 端口已被占用
     */
    public boolean initSerialPort(String portName, int baudrate)  {
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
                } catch (TooManyListenersException e){
                    e.printStackTrace();
                }
            }else {
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

    private SerialPortEventListener creatSerialPortEventListener(){
        return new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {

            }
        };
    }


}
