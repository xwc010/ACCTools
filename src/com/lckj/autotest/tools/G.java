package com.lckj.autotest.tools;

public class G {

	public static final String HEADER_NEED_ASK     = "FFAA";
	public static final String START_CMD            = "0202";
	public static final String ACC_OFF_CMD          = "010100"; //断ACC
	public static final String ACC_ON_CMD           = "010101"; //上ACc
	public static final String B_OFF_CMD            = "030100"; //断B+
	public static final String B_ON_CMD             = "030101"; //上B+
	public static final String CCD_ON_CMD           = "040101"; //进入倒车
	public static final String CCD_OFF_CMD          = "040100"; //退出倒车
	public static final String CMD_PROTOCOL_CCD     = "050F";   //协议倒车
	public static final String MUTE                 = "0600";   //声音检测

	//acc命令应答
	public static final String ASK_ACC_OK           = "FF550101";
	public static final String ASK_ACC_UNKNOW       = "FF550102";
	public static final String ASK_ACC_CHECKERROR   = "FF550100";
	//开始命令应答
	public static final String ASK_START_OK         = "FF550201";
	public static final String ASK_START_UNKNOW     = "FF550202";
	public static final String ASK_START_CHECKERROR = "FF550200";
	//B+命令应答
	public static final String ASK_B_OK             = "FF550301";
	public static final String ASK_B_UNKNOW         = "FF550302";
	public static final String ASK_B_CHECKERROR     = "FF550300";
	//硬件倒车应答
	public static final String ASK_CCD_OK           = "FF550401";
	public static final String ASK_CCD_UNKNOW       = "FF550402";
	public static final String ASK_CCD_CHECKERROR   = "FF550400";
	//协议倒车应答
	public static final String ASK_CAN_CCD_OK           = "FF550501";
	public static final String ASK_CAN_CCD_UNKNOW       = "FF550502";
	public static final String ASK_CAN_CCD_CHECKERROR   = "FF550500";
	//声音检测应答
	public static final String ASK_MUTE_OK           = "FF550601";
	public static final String ASK_MUTE_CHECKERROR   = "FF550600";
	public static final String ASK_MUTE_OK_VOICED    = "FFBB06010205";
	public static final String ASK_MUTE_OK_NOVOICE   = "FFBB06010304";

	//配置文件名称
	public static final String ACC_PROPERTY     = "acc.properties";
	public static final String B_PROPERTY       = "b.properties";
	public static final String CCD_PROPERTY     = "ccd.properties";
	public static final String B_ACC_PROPERTY   = "b_acc.properties";
	public static final String ACC_CCD_PROPERTY = "acc_ccd.properties";
	public static final String LOG_PROPERTY     = "logcat.properties";
}
