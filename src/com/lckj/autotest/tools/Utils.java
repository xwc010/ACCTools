package com.lckj.autotest.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;

public class Utils {
	private final static char[] mChars = "0123456789ABCDEF".toCharArray();
	private final static String mHexStr = "0123456789ABCDEF";

	public static void main(String[] args){
//		String code = checkcode_0007("060103");
		System.out.println(getCurrentTimeMills());
		System.out.println(getCurrentTimeMills());
		System.out.println(getCurrentTimeMills());
	}
	/**
	 * 检查16进制字符串是否有效
	 * @param sHex String 16进制字符串
	 * @return boolean
	 */
	public static boolean checkHexStr(String sHex){
		String sTmp = sHex.toString().trim().replace(" ", "").toUpperCase(Locale.US);
		int iLen = sTmp.length();

		if (iLen > 1 && iLen%2 == 0){
			for(int i=0; i<iLen; i++)
				if (!mHexStr.contains(sTmp.substring(i, i+1)))
					return false;
			return true;
		}
		else
			return false;
	}

	/**
	 * 字符串转换成十六进制字符串
	 * @param str String 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	 */
	public static String str2HexStr(String str){
		StringBuilder sb = new StringBuilder();
		byte[] bs = str.getBytes();

		for (int i = 0; i < bs.length; i++){
			sb.append(mChars[(bs[i] & 0xFF) >> 4]);
			sb.append(mChars[bs[i] & 0x0F]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	/**
	 * 十六进制字符串转换成 ASCII字符串
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr){
		hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int iTmp = 0x00;;

		for (int i = 0; i < bytes.length; i++){
			iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
			iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (iTmp & 0xFF);
		}
		return new String(bytes);
	}

	/**
	 * bytes转换成十六进制字符串
	 * @param b byte[] byte数组
	 * @param iLen int 取前N位处理 N=iLen
	 * @return String 每个Byte值之间空格分隔
	 */
	public static String byte2HexStr(byte[] b, int iLen){
		StringBuilder sb = new StringBuilder();
		for (int n=0; n<iLen; n++){
			sb.append(mChars[(b[n] & 0xFF) >> 4]);
			sb.append(mChars[b[n] & 0x0F]);
			sb.append(' ');
		}
		return sb.toString().trim().toUpperCase(Locale.US);
	}

	/**
	 * bytes字符串转换为Byte值
	 * @param src String Byte字符串，每个Byte之间没有分隔符(字符范围:0-9 A-F)
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String src){
        /*对输入值进行规范化整理*/
		src = src.trim().replace(" ", "").toUpperCase(Locale.US);
		//处理值初始化
		int m=0,n=0;
		int iLen=src.length()/2; //计算长度
		byte[] ret = new byte[iLen]; //分配存储空间

		for (int i = 0; i < iLen; i++){
			m=i*2+1;
			n=m+1;
			ret[i] = (byte)(Integer.decode("0x"+ src.substring(i*2, m) + src.substring(m,n)) & 0xFF);
		}
		return ret;
	}

	/**
	 * String的字符串转换成unicode的String
	 * @param strText String 全角字符串
	 * @return String 每个unicode之间无分隔符
	 * @throws Exception
	 */
	public static String strToUnicode(String strText)
			throws Exception
	{
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++){
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128)
				str.append("\\u");
			else // 低位在前面补00
				str.append("\\u00");
			str.append(strHex);
		}
		return str.toString();
	}

	/**
	 * unicode的String转换成String的字符串
	 * @param hex String 16进制值字符串 （一个unicode为2byte）
	 * @return String 全角字符串
	 */
	public static String unicodeToString(String hex){
		int t = hex.length() / 6;
		int iTmp = 0;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++){
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 将16进制的string转为int
			iTmp = (Integer.valueOf(s.substring(2, 4), 16) << 8) | Integer.valueOf(s.substring(4), 16);
			// 将int转换为字符
			str.append(new String(Character.toChars(iTmp)));
		}
		return str.toString();
	}

	private static String xorString(String strHex_X,String strHex_Y){
		//将x、y转成二进制形式
		String anotherBinary=Integer.toBinaryString(Integer.valueOf(strHex_X,16));
		String thisBinary=Integer.toBinaryString(Integer.valueOf(strHex_Y,16));
		String result = "";
		//判断是否为8位二进制，否则左补零
		if(anotherBinary.length() != 8){
			for (int i = anotherBinary.length(); i <8; i++) {
				anotherBinary = "0"+anotherBinary;
			}
		}
		if(thisBinary.length() != 8){
			for (int i = thisBinary.length(); i <8; i++) {
				thisBinary = "0"+thisBinary;
			}
		}
		//异或运算
		for(int i=0;i<anotherBinary.length();i++){
			//如果相同位置数相同，则补0，否则补1
			if(thisBinary.charAt(i)==anotherBinary.charAt(i))
				result+="0";
			else{
				result+="1";
			}
		}
		String hex_str = Integer.toHexString(Integer.parseInt(result, 2));
		if (hex_str.length() == 1){
			hex_str = "0" + hex_str;
		}
		return hex_str;
	}

	public static String checkcode_0007(String para){
		String[] dateArr = new String[para.length()/2];
		String code = "";
		for (int i = 0; i < dateArr.length-1; i++) {
			dateArr[i] = para.substring(i*2, i*2+2);
			dateArr[i+1] = para.substring((i+1)*2, (i+1)*2+2);
			if(i == 0){
				code = xorString(dateArr[i], dateArr[i+1]);
			}else{
				code = xorString(code, dateArr[i+1]);
			}
		}
		return code;
	}

	public static String dataToSeedForm(String data, int len, boolean isHex){
		//不是十六进制格式先转十六进制
		if(!isHex){
			data = Integer.toHexString(Integer.parseInt(data));
		}
		String str="0000000000000000";
		int add_len = len - data.length();
		if (add_len > 0){
			str = str.substring(0,add_len);
			data = str + data;
		}else if(add_len < 0){
			JOptionPane.showMessageDialog(null, "请设置有效的参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
		}
		return reverseData(data);
	}

	public static String  reverseData(String str){
		if(str.length() == 4){
			str = str.substring(2,4)+str.substring(0,2);
		}else if(str.length() == 8){
			str = str.substring(6,8)+str.substring(4,6)+str.substring(2,4)+str.substring(0,2);
		}
		return str;
	}

	/**
	 * @Description 获取设备列表
	 * @return List<String> 返回设备列表信息
	 * @throws
	 */
	public static List<String> getDevices() {
		List<String> deviceList = new ArrayList<String>();

		String cmdReturn = runCMD("adb devices");
		String[] str = cmdReturn.split("\r\n");
		for (int i = 0; i < str.length; i++) {
			if (str[i].contains("List of devices attached")) {
				continue;
			}
			if (str[i].contains("device")) {
				deviceList.add(str[i].split("device")[0]);
			}
		}
		return deviceList;
	}

	/**
	 * @Description 执行命令行;返回命令执行结果，每行数据以“\r\n”分隔
	 * @param cmdString
	 * @param
	 * @return String
	 * @throws
	 */
	public static String runCMD(String cmdString) {
		StringBuilder sb = new StringBuilder();
		String line = "";

		try {
			Process process = Runtime.getRuntime().exec(cmdString);
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while (((line = br.readLine()) != null)) {
				line = line.trim();
				if (line.equals("")) {
					continue;
				}
				sb.append(line);
				sb.append("\r\n");
			}
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("执行CMD wait操作失败");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("执行CMD失败");
			System.out.println(e.getMessage());
			return null;
		}
		System.out.println(sb.toString());
		return sb.toString();
	}


	public static String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	public static String getCurrentTimeMills(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	public static String getCurrentActivity(String serial){
		String result = runCMD("adb -s "+serial+ " shell dumpsys activity | grep mFocusedActivity");
		int num = 1;
		while (!result.contains("mFocusedActivity:") && num<30){
			num += 1;
			try {
				Thread.sleep(1000);
				result = runCMD("adb -s "+serial+ " shell dumpsys activity | grep mFocusedActivity");
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		if(!result.contains("mFocusedActivity:")){
			System.out.println("获取不到当前activity名称");
			return "";
		}else{
			String[] list = result.split("\\s+");
			return list[3].split("}")[0];
		}
	}

	public static boolean checkDeviceIsOK(String serial, String activityName){
		boolean check = false;
		boolean isConnectDevice = connectDevice(serial);

		if(isConnectDevice){
			System.out.println("设备已连上");
			try {
				Thread.sleep(2000); //等待设备恢复断ACC前状态
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String result="";
			int num = 1;
			while (num<30){
				num += 1;
				try {
					Thread.sleep(1000);
					result = runCMD("adb -s "+serial+ " shell dumpsys activity | grep mFocusedActivity");
				}catch (InterruptedException e1){
					e1.printStackTrace();
				}
				if(result.contains(activityName)){
					check = true;
					break;
				}
			}
		}
		return check;

	}

	public static boolean checkDeviceOnLaucher(String serial){
		boolean check = false;
		boolean isConnectDevice = connectDevice(serial);

		if(isConnectDevice){
			System.out.println("设备已连上");
			try {
				Thread.sleep(2000); //等待设备恢复断ACC前状态
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(getCurrentActivity(serial).contains("auncher")){
				System.out.println("设备已进入Launcher界面");
				check = true;
			}else{
				System.out.println("设备未进入Launcher界面");
			}
		}else{
			System.out.println("设备没有连接上");
		}
		return check;

	}

	public static boolean connectDevice(String serial){
		boolean isConnectDevice = false;
		int num=0;
		while(num < 30){
			num += 1;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String cmdReturn = runCMD("adb devices");
			if(cmdReturn.contains(serial)){
				isConnectDevice = true;
				break;
			}
		}
		return isConnectDevice;
	}

	public static void runbat(String batName, String para1, String para2) {
		 Runtime r = Runtime.getRuntime();
	     Process p = null;
	     try{
	     	if (isWindowsSystem()){
				p = r.exec("cmd.exe /k  "+batName+ " " + para1 +" "+para2);
			}else{
				p = r.exec("/bin/sh  "+batName+ " " + para1 +" "+para2);
			}
	         StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
	         errorGobbler.start();
	         StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");
	         outGobbler.start();
	         p.waitFor();
	    }catch(Exception e){
	         System.out.println("运行错误:"+e.getMessage());
	         e.printStackTrace();
	   }
		System.out.println("child thread done");
	}

	public static boolean isWindowsSystem(){
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")){
			return true;
		}else{
			return false;
		}

	}

	public static void stopProcess(String serial, String process) {
		String result = runCMD("adb -s " + serial +  " shell ps | grep " + process);
		String[] list = result.split("\\s+");
		for (int i=0;i<list.length;i++){
			if(i%9 == 1){
				String killcmd = "adb -s " + serial + " shell kill -9 " + list[i].toString();
				System.out.println(killcmd);
				runCMD(killcmd);
			}
		}

	}


	public static void writeContentToFile(String filePath, String content, boolean append){
		File file = new File(filePath);
		try{
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file, append);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content);
			bufferedWriter.close();
			System.out.println("write done");
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public static String getCCDCMD(String canType,String baudrate,String id, String ccd_data){
		String baudrate_form = dataToSeedForm(baudrate,4, false);
		String id_form = dataToSeedForm(id, 8, true);
		String data_form = dataToSeedForm(ccd_data, 16, true);
		String cmd = G.CMD_PROTOCOL_CCD+canType+baudrate_form+id_form+data_form;
		System.out.println(cmd);
		return cmd;
	}

}  
