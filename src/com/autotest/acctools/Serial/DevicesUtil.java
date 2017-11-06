package com.autotest.acctools.Serial;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;

public class DevicesUtil {

    /**
     * 查找所有可用端口
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPort() {

        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

        ArrayList<String> portNameList = new ArrayList<>();

        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portList.nextElement();
            String portName = portIdentifier.getName();
            System.out.println(portName);
            portNameList.add(portName);
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }

        return portNameList;

    }

    static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
}
