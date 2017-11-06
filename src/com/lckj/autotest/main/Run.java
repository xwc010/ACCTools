package com.lckj.autotest.main;

import com.lckj.autotest.tools.*;
import com.lckj.autotest.ui.MainLayout;
import com.lckj.autotest.ui.Main_Layout;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.lckj.autotest.exception.NoSuchPort;
import com.lckj.autotest.exception.NotASerialPort;
import com.lckj.autotest.exception.PortInUse;
import com.lckj.autotest.exception.ReadDataFromSerialPortFailure;
import com.lckj.autotest.exception.SendDataToSerialPortFailure;
import com.lckj.autotest.exception.SerialPortInputStreamCloseFailure;
import com.lckj.autotest.exception.SerialPortOutputStreamCloseFailure;
import com.lckj.autotest.exception.SerialPortParameterFailure;
import com.lckj.autotest.exception.TooManyListeners;

public class Run {
	static MainLayout ui ;
	ArrayList<String> commList ;
	private static SerialPort serialPort = null;	//保存串口对象
	private static SerialListener serialListener = null;
	boolean portOpen = false;
	static String return_result = "";
	static boolean isNeedAsk = false;
	List<String> deviceList;
	static String activityName="";
	String serial = "";
	static ACCSwingWorker ACCSwingworker;
	static BSwingWorker BSwingworker;
	static CCDSwingWorker CCDSwingworker;
	static BACCSwingWorker BACCSwingworker;
	static ACCOningCCDSwingWorker ACCOningCCDSwingworker;
	static ACCCCDSwingWorker ACCCCDSwingworker;
	static logcatThread logcat_t = null;
	private PropertyUtil propertyUtil = new PropertyUtil();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Run run = new Run();
		ui = new MainLayout();
		run.start();
	}

	public void start(){
		commList = SerialTool.findPort();	//扫描有效串口
		//检查是否有可用串口，有则加入选项中
		for (String s : commList) {
			ui.cb_com_select.addItem(s);
		}
		//设置设备列表内容显示
		deviceList = Utils.getDevices();
		for(int i=0;i<deviceList.size();i++){
			ui.cb_device_select.addItem(deviceList.get(i));
		}
		addViewListener();

		//创建保存结果信息文件夹
		File f = new File("result/");
		if(!f.exists()){
			f.mkdir();
		}

	}

	public void addViewListener(){

		ui.btn_start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//点击停止测试操作
				if(ui.btn_start.getText().equals("停止测试")){
					ui.btn_start.setText("开始测试");
					ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
					ui.btn_start.setForeground(Color.white);
					if(ACCSwingworker != null && !ACCSwingworker.isCancelled()){
						ACCSwingworker.stopTask();
						Utils.stopProcess(serial, "uiautomator");
						ACCSwingworker.cancel(true);
					}
					if(BSwingworker != null && !BSwingworker.isCancelled()){
						BSwingworker.stopTask();
						Utils.stopProcess(serial, "uiautomator");
						BSwingworker.cancel(true);
					}
					if(CCDSwingworker != null && !CCDSwingworker.isCancelled()){
						CCDSwingworker.stopTask();
						CCDSwingworker.cancel(true);
					}
					if(BACCSwingworker != null && !BACCSwingworker.isCancelled()){
						BACCSwingworker.stopTask();
						Utils.stopProcess(serial, "uiautomator");
						BACCSwingworker.cancel(true);
					}
					if(ACCOningCCDSwingworker != null && !ACCOningCCDSwingworker.isCancelled()){
						ACCOningCCDSwingworker.stopTask();
						Utils.stopProcess(serial, "uiautomator");
						ACCOningCCDSwingworker.cancel(true);
					}
					if(ACCCCDSwingworker != null && !ACCCCDSwingworker.isCancelled()){
						ACCCCDSwingworker.stopTask();
						Utils.stopProcess(serial, "uiautomator");
						ACCCCDSwingworker.cancel(true);
					}
					if(logcat_t != null){
						logcat_t.close();
						Utils.stopProcess(serial, "logcat");
					}
					return;
				}

				Parse.createExcel();
				//点击开始测试后，先与串口小板通信
				if(!portOpen){
					JOptionPane.showMessageDialog(null, "请打开串口通信！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				boolean isNeedScript = false;
				boolean isStop = false;
				boolean isCheckVoice = false;
				String batName = "";

				//未连接设备时提示框
				if (deviceList.size() == 0){
					JOptionPane.showMessageDialog(null, "设备列表为空，请先连接设备再进行测试","温馨提示", JOptionPane.WARNING_MESSAGE); // 警告提示框。
					return;
				}
				serial = ui.cb_device_select.getSelectedItem()+"";

				Map<String, String> logMap = new HashMap<>();
				logMap.put("need_script", ui.cb_isneed_script.getSelectedIndex() +"");
				if (ui.cb_isneed_script.getSelectedItem().equals("是")){
					isNeedScript = true;
					if(ui.tf_bat_path.getText().equals("") || ui.tf_bat_path.getText().equals("null")){
						JOptionPane.showMessageDialog(null, "请选择测试的bat文件","温馨提示", JOptionPane.WARNING_MESSAGE); // 警告提示框。
						return;
					}
					batName = ui.tf_bat_path.getText();
					logMap.put("batName", batName);
				}

				logMap.put("need_logcat", ui.cb_isneed_logcat.getSelectedIndex() +"");
				if (ui.cb_isneed_logcat.getSelectedItem().equals("是")){
					if(ui.tf_logcat_path.getText().equals("") || ui.tf_logcat_path.getText().equals("null")){
						JOptionPane.showMessageDialog(null, "请选择logcat.bat文件","温馨提示", JOptionPane.WARNING_MESSAGE); // 警告提示框。
						return;
					}
					String logcatpath = ui.tf_logcat_path.getText();
					//创建保存log的文件夹
					File f = new File("");
					File file = new File(f.getAbsolutePath()+"\\logcat");
					if(!file.exists()){
						file.mkdirs();
					}
					//启动抓logcat的线程
					logcat_t = new logcatThread(logcatpath, serial);
					logcat_t.start();

					logMap.put("logcatpath", logcatpath);
				}
				propertyUtil.saveProperties(G.LOG_PROPERTY, logMap, false);

				if (ui.cb_acc_stop.getSelectedItem().equals("是")){
					isStop = true;
				}

				if (ui.cb_voice.getSelectedItem().equals("是")){
					isCheckVoice = true;
				}

				int type = ui.cb_type.getSelectedIndex();
				ui.textarea.setText("开始测试：" + Utils.getCurrentTime()+"\r\n");

				if(type == 0){
					//ACC测试
					String min = ui.tf_acc_min.getText();
					String max = ui.tf_acc_max.getText();
					String on = ui.tf_acc_on.getText();
					String num = ui.tf_num.getText();
					String sleep = ui.tf_sleep.getText();
					String step = ui.tf_step.getText();
					int remember = ui.cb_acc_if_remeber.getSelectedIndex();
					Map<String, String> map = new HashMap<>();
					map.put("min", min);
					map.put("max", max);
					map.put("on", on);
					map.put("num", num);
					map.put("sleep", sleep);
					map.put("step", step);
					map.put("remember", remember+"");
					propertyUtil.saveProperties(G.ACC_PROPERTY, map, false);

					if(min.equals("") || max.equals("") || on.equals("") || num.equals("") || sleep.equals("") || step.equals("")){
						JOptionPane.showMessageDialog(null, "请先设置ACC参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int acc_off_min = Integer.parseInt(min)*1000;
					int acc_off_max = Integer.parseInt(max)*1000;
					int acc_on = Integer.parseInt(on)*1000;
					int acc_num = Integer.parseInt(num);
					int acc_step = Integer.parseInt(step);

					String data_byte = Utils.dataToSeedForm(sleep,4, false);
					String startCMD = G.START_CMD + data_byte;
					boolean send_data_status = sendCMDToPort(startCMD,G.ASK_START_OK,G.ASK_START_UNKNOW,G.ASK_START_CHECKERROR);
					if(!send_data_status){
						JOptionPane.showMessageDialog(null, "与串口小板通信失败，请检查串口小板连接是否正确", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					//启动ACC测试
					ACCSwingworker = new ACCSwingWorker(acc_off_min, acc_off_max, acc_on, acc_step, acc_num, ui.textarea, serial, isNeedScript, batName, isStop, remember, isCheckVoice);
					ACCSwingworker.execute();

				} else if(type == 1){
					//B+测试
					String off_min = ui.tf_B_min.getText();
					String off_max = ui.tf_B_max.getText();
					String on = ui.tf_B_on.getText();
					String num = ui.tf_B_num.getText();
					String step = ui.tf_B_step.getText();
					if(off_min.equals("") || off_max.equals("") || on.equals("") || num.equals("") || step.equals("")){
						JOptionPane.showMessageDialog(null, "请先设置B+参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int B_off_min = Integer.parseInt(off_min)*1000;
					int B_off_max= Integer.parseInt(off_max)*1000;
					int B_on = Integer.parseInt(on)*1000;
					int B_num = Integer.parseInt(num);
					int B_step = Integer.parseInt(step);
					int remember = ui.cb_b_remember.getSelectedIndex();

					//保存设置的参数
					Map<String, String> map = new HashMap<>();
					map.put("min", off_min);
					map.put("max", off_max);
					map.put("on", on);
					map.put("num", num);
					map.put("step", step);
					map.put("remember", remember+"");
					propertyUtil.saveProperties(G.B_PROPERTY, map, false);

					//启动B+测试
					BSwingworker = new BSwingWorker(B_off_min, B_off_max, B_on, B_num, B_step, remember, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice);
					BSwingworker.execute();
				}
				else if(type == 2){
					//倒车测试
					String on_min = ui.tf_ccd_min.getText();
					String on_max= ui.tf_ccd_max.getText();
					String off = ui.tf_ccd_off.getText();
					String num = ui.tf_ccd_num.getText();
					String step = ui.tf_ccd_step.getText();
					if(on_min.equals("") || on_max.equals("") || off.equals("")|| num.equals("") || step.equals("")){
						JOptionPane.showMessageDialog(null, "请先设置倒车参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int ccd_on_min = Integer.parseInt(on_min)*1000;
					int ccd_on_max = Integer.parseInt(on_max)*1000;
					int ccd_off = Integer.parseInt(off)*1000;
					int ccd_num = Integer.parseInt(num);
					int ccd_step = Integer.parseInt(step);
					int ccd_type = ui.cb_ccd_type.getSelectedIndex();

					Map<String, String> map = new HashMap<>();
					map.put("min", on_min);
					map.put("max", on_max);
					map.put("off", off);
					map.put("num", num);
					map.put("step", step);
					map.put("type", ccd_type+"");

					//硬件倒车
					if(ccd_type == 0){
						//启动倒车测试
						CCDSwingworker = new CCDSwingWorker(ccd_on_min, ccd_on_max, ccd_off, ccd_num, ccd_step, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice);
					}else{
						//协议倒车
						String baudrate = ui.tf_ccd_baudrate.getText();
						String id = ui.tf_ccd_id.getText();
						String ccd_on_data = ui.tf_ccd_on_data.getText();
						String ccd_off_data = ui.tf_ccd_off_data.getText();
						int index = ui.cb_ccd_cantype.getSelectedIndex();

						map.put("baudrate", baudrate+"");
						map.put("id", id+"");
						map.put("ccd_on_data", ccd_on_data+"");
						map.put("ccd_off_data", ccd_off_data+"");
						map.put("index", index+"");

						String cantype;
						if(index==0){
							cantype = "00";
						}else if(index == 1){
							cantype = "01";
						}else{
							cantype = "02";
						}
						if(on_min.equals("") || on_max.equals("") || off.equals("")|| num.equals("") || step.equals("")){
							JOptionPane.showMessageDialog(null, "请先设置倒车参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						//启动倒车测试
						CCDSwingworker = new CCDSwingWorker(ccd_on_min, ccd_on_max, ccd_off, ccd_num, ccd_step, baudrate, id, ccd_on_data, ccd_off_data,cantype, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice);
					}

					//保存设置的参数
					propertyUtil.saveProperties(G.CCD_PROPERTY, map, false);
					CCDSwingworker.execute();

				}else if(type == 3){
					//B+ACC Test
					String acc_min = ui.tf_bacc_acc_min.getText();
					String acc_max = ui.tf_bacc_acc_max.getText();
					String acc_on = ui.tf_bacc_acc_on.getText();
					String b_min = ui.tf_bacc_b_min.getText();
					String b_max = ui.tf_bacc_b_max.getText();
					String b_on = ui.tf_bacc_b_on.getText();
					String acc_step = ui.tf_bacc_acc_step.getText();
					String b_step = ui.tf_bacc_b_step.getText();
					String num = ui.tf_bacc_num.getText();
					if(acc_min.equals("") || acc_max.equals("") || acc_on.equals("") || b_min.equals("") || b_max.equals("") || b_on.equals("") || acc_step.equals("") || b_step.equals("") || num.equals("")){
						JOptionPane.showMessageDialog(null, "请先设置B+ACC参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					int ba_acc_min = Integer.parseInt(acc_min)*1000;
					int ba_acc_max = Integer.parseInt(acc_max)*1000;
					int ba_acc_on = Integer.parseInt(acc_on)*1000;
					int ba_b_min = Integer.parseInt(b_min)*1000;
					int ba_b_max = Integer.parseInt(b_max)*1000;
					int ba_b_on = Integer.parseInt(b_on)*1000;
					int ba_acc_step = Integer.parseInt(acc_step);
					int ba_b_step = Integer.parseInt(b_step);
					int ba_num = Integer.parseInt(num);
					int remember = ui.cb_bacc_remember.getSelectedIndex();

					//save settings properties
					Map<String, String> map = new HashMap<>();
					map.put("acc_min", acc_min);
					map.put("acc_min", acc_min);
					map.put("acc_on", acc_on);
					map.put("b_min", b_min);
					map.put("b_max", b_max);
					map.put("b_on", b_on);
					map.put("num", num);
					map.put("acc_step", acc_step);
					map.put("b_step", b_step);
					map.put("remember", remember+"");
					propertyUtil.saveProperties(G.B_ACC_PROPERTY, map, false);

					//启动B+ACC Test
					BACCSwingworker = new BACCSwingWorker(ba_acc_min, ba_acc_max, ba_acc_on, ba_b_min, ba_b_max, ba_b_on, ba_acc_step, ba_b_step, ba_num, remember, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice);
					BACCSwingworker.execute();
				}else {
					//ACC+CDD Test
					int test_type = ui.cb_test_type.getSelectedIndex();
					int random = ui.cb_random.getSelectedIndex();
					String acc_min = ui.tf_cacc_acc_min.getText();
					String acc_max = ui.tf_cacc_acc_max.getText();
					String ccd_min = ui.tf_cacc_ccd_min.getText();
					String ccd_max = ui.tf_cacc_ccd_max.getText();
					String ccd_off = ui.tf_cacc_ccd_off.getText();
					String acc_on = ui.tf_cacc_acc_on.getText();
					String acc_step = ui.tf_cacc_acc_step.getText();
					String ccd_step = ui.tf_cacc_ccd_step.getText();
					String ccd_num = ui.tf_cacc_ccd_num.getText();
					String num = ui.tf_cacc_num.getText();
					boolean isRandom = false;

					if (acc_min.equals("") || acc_max.equals("") || ccd_min.equals("") || ccd_max.equals("") || ccd_off.equals("") || acc_on.equals("") || acc_step.equals("") || ccd_step.equals("") || ccd_num.equals("") || num.equals("")) {
						JOptionPane.showMessageDialog(null, "请先设置ACC+CCD参数！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					if (random == 1) {
						isRandom = true;
					}

					int ac_acc_min = Integer.parseInt(acc_min) * 1000;
					int ac_acc_max = Integer.parseInt(acc_max) * 1000;
					int ac_acc_on = Integer.parseInt(acc_on) * 1000;
					int ac_ccd_min = Integer.parseInt(ccd_min) * 1000;
					int ac_ccd_max = Integer.parseInt(ccd_max) * 1000;
					int ac_ccd_off = Integer.parseInt(ccd_off) * 1000;
					int ac_acc_step = Integer.parseInt(acc_step);
					int ac_ccd_step = Integer.parseInt(ccd_step);
					int ac_ccd_num = Integer.parseInt(ccd_num);
					int ac_num = Integer.parseInt(num);
					int ccd_type = ui.cb_cacc_ccd_type.getSelectedIndex();
					int index = ui.cb_cacc_cantype.getSelectedIndex();
					int remember = ui.cb_cacc_remember.getSelectedIndex();

					//save settings properties
					Map<String, String> map = new HashMap<>();
					map.put("test_type", test_type + "");
					map.put("random", random + "");
					map.put("acc_min", acc_min);
					map.put("acc_max", acc_max);
					map.put("acc_on", acc_on);
					map.put("ccd_min", ccd_min);
					map.put("ccd_max", ccd_max);
					map.put("ccd_off", ccd_off);
					map.put("ccd_num", ccd_num);
					map.put("num", num);
					map.put("acc_step", acc_step);
					map.put("ccd_step", ccd_step);
					map.put("ccd_type", ccd_type + "");
					map.put("can_type", index+"");
					map.put("remember", remember+"");

					String cantype;
					if (index == 0) {
						cantype = "00";
					} else if (index == 1) {
						cantype = "01";
					} else {
						cantype = "02";
					}

					if (ccd_type == 0) {
						if (test_type == 0) {
							//ACC启动过程中硬件倒车
							ACCOningCCDSwingworker = new ACCOningCCDSwingWorker(isRandom, ac_acc_min, ac_acc_max, ac_acc_on, ac_ccd_min, ac_ccd_max, ac_ccd_off, ac_acc_step, ac_ccd_step, ac_ccd_num, ac_num, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice, remember);
							ACCOningCCDSwingworker.execute();
						} else {
							//ACC启动后硬件倒车
							ACCCCDSwingworker = new ACCCCDSwingWorker(isRandom, ac_acc_min, ac_acc_max, ac_acc_on, ac_ccd_min, ac_ccd_max, ac_ccd_off, ac_acc_step, ac_ccd_step, ac_ccd_num, ac_num, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice, remember);
							ACCCCDSwingworker.execute();
						}
					} else {
						String baudrate = ui.tf_cacc_baudrate.getText();
						String id = ui.tf_cacc_id.getText();
						String ccd_on_data = ui.tf_cacc_on_data.getText();
						String ccd_off_data = ui.tf_cacc_off_data.getText();

						map.put("baudrate", baudrate);
						map.put("id", id);
						map.put("ccd_on_data", ccd_on_data);
						map.put("ccd_off_data", ccd_off_data);
						if (test_type == 0) {
							//ACC启动过程中协议倒车
							ACCOningCCDSwingworker = new ACCOningCCDSwingWorker(isRandom, ac_acc_min, ac_acc_max, ac_acc_on, ac_ccd_min, ac_ccd_max, ac_ccd_off, ac_acc_step, ac_ccd_step, ac_ccd_num, ac_num, baudrate, id, ccd_on_data, ccd_off_data, cantype, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice, remember);
							ACCOningCCDSwingworker.execute();
						} else {
							//ACC启动后协议倒车
							ACCCCDSwingworker = new ACCCCDSwingWorker(isRandom, ac_acc_min, ac_acc_max, ac_acc_on, ac_ccd_min, ac_ccd_max, ac_ccd_off, ac_acc_step, ac_ccd_step, ac_ccd_num, ac_num, baudrate, id, ccd_on_data, ccd_off_data, cantype, ui.textarea, serial, isNeedScript, batName, isStop, isCheckVoice, remember);
							ACCCCDSwingworker.execute();
						}
					}
					propertyUtil.saveProperties(G.ACC_CCD_PROPERTY, map, false);
				}
				ui.btn_start.setText("停止测试");
				ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
				ui.btn_start.setForeground(Color.white);
			}
		});

		ui.cb_isneed_script.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getItem().equals("是")){
					ui.btn_bat_path.setVisible(true);
					ui.label_bat_path.setVisible(true);
					ui.tf_bat_path.setVisible(true);
				}else{
					ui.btn_bat_path.setVisible(false);
					ui.label_bat_path.setVisible(false);
					ui.tf_bat_path.setVisible(false);
				}
			}
		});

		ui.cb_isneed_logcat.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getItem().equals("是")){
					ui.btn_logcat_path.setVisible(true);
					ui.label_logcat_path.setVisible(true);
					ui.tf_logcat_path.setVisible(true);
				}else{
					ui.btn_logcat_path.setVisible(false);
					ui.label_logcat_path.setVisible(false);
					ui.tf_logcat_path.setVisible(false);
				}
			}
		});

		ui.cb_ccd_type.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem().equals("协议倒车")){
					ui.label_ccd_id.setVisible(true);
					ui.tf_ccd_id.setVisible(true);
					ui.label_ccd_on_data.setVisible(true);
					ui.tf_ccd_on_data.setVisible(true);
					ui.label_ccd_off_data.setVisible(true);
					ui.tf_ccd_off_data.setVisible(true);
					ui.label_ccd_baudrate.setVisible(true);
					ui.tf_ccd_baudrate.setVisible(true);
					ui.label_ccd_cantype.setVisible(true);
					ui.cb_ccd_cantype.setVisible(true);
				}else{
					ui.label_ccd_id.setVisible(false);
					ui.tf_ccd_id.setVisible(false);
					ui.label_ccd_on_data.setVisible(false);
					ui.tf_ccd_on_data.setVisible(false);
					ui.label_ccd_off_data.setVisible(false);
					ui.tf_ccd_off_data.setVisible(false);
					ui.label_ccd_baudrate.setVisible(false);
					ui.tf_ccd_baudrate.setVisible(false);
					ui.label_ccd_cantype.setVisible(false);
					ui.cb_ccd_cantype.setVisible(false);
				}
			}
		});

		ui.cb_cacc_ccd_type.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem().equals("协议倒车")){
					ui.label_cacc_baudrate.setVisible(true);
					ui.label_cacc_id.setVisible(true);
					ui.label_cacc_on_data.setVisible(true);
					ui.tf_cacc_id.setVisible(true);
					ui.tf_cacc_on_data.setVisible(true);
					ui.label_cacc_off_data.setVisible(true);
					ui.tf_cacc_baudrate.setVisible(true);
					ui.tf_cacc_off_data.setVisible(true);
					ui.label_cacc_cantype.setVisible(true);
					ui.cb_cacc_cantype.setVisible(true);
				}else{
					ui.label_cacc_baudrate.setVisible(false);
					ui.label_cacc_id.setVisible(false);
					ui.label_cacc_on_data.setVisible(false);
					ui.tf_cacc_id.setVisible(false);
					ui.tf_cacc_on_data.setVisible(false);
					ui.label_cacc_off_data.setVisible(false);
					ui.tf_cacc_baudrate.setVisible(false);
					ui.tf_cacc_off_data.setVisible(false);
					ui.label_cacc_cantype.setVisible(false);
					ui.cb_cacc_cantype.setVisible(false);
				}
			}
		});

		//添加打开串口按钮的事件监听
		ui.btn_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (ui.btn_open.getText().equals("打开串口")){
					//获取串口名称
					String commName = ui.cb_com_select.getSelectedItem()+"";
					//获取波特率
					String bpsStr = ui.cb_baudrate_select.getSelectedItem()+"";
					//检查串口名称是否获取正确
					if (commName == null || commName.equals("")) {
						JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						//检查波特率是否获取正确
						if (bpsStr == null || bpsStr.equals("")) {
							JOptionPane.showMessageDialog(null, "波特率获取错误！", "错误", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							//串口名、波特率均获取正确时
							int bps = Integer.parseInt(bpsStr);
							try {
								//获取指定端口名及波特率的串口对象
								serialPort = SerialTool.openPort(commName, bps);
								//在该串口对象上添加监听器
								serialListener = new SerialListener();
								SerialTool.addListener(serialPort, serialListener);
								//监听成功进行提示
								JOptionPane.showMessageDialog(null, "监听成功，稍后将显示监测数据！", "提示", JOptionPane.INFORMATION_MESSAGE);

								portOpen = true;
								ui.btn_open.setText("关闭串口");

							}catch(SerialPortParameterFailure e1){
								//发生错误时使用一个Dialog提示具体的错误信息
								JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
							}catch (NotASerialPort e1) {
								JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
							}catch (NoSuchPort e1) {
								JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
							}catch (PortInUse e1) {
								JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
							}catch (TooManyListeners e1) {
								JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}

				}else{
					SerialTool.closePort(serialPort);
					ui.btn_open.setText("打开串口");
				}
			}

		});


		ui.cb_com_select.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				commList = SerialTool.findPort();	//扫描有效串口
				ui.cb_com_select.removeAllItems();
				//检查是否有可用串口，有则加入选项中
				for (String s : commList) {
					ui.cb_com_select.addItem(s);
				}
			}
		});

		ui.cb_device_select.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ui.cb_device_select.removeAllItems();
				//设置设备列表内容显示
				deviceList = Utils.getDevices();
				for(int i=0;i<deviceList.size();i++){
					ui.cb_device_select.addItem(deviceList.get(i));
				}
			}
		});
	}

	//ACC线程
	static class ACCSwingWorker extends SwingWorker<List<String>, String>{
		private int acc_off_min;
		private int acc_off_max;
		private int acc_off;
		private int acc_on;
		private int step;
		private int num;
		private String serial;
		private JTextArea text;
		private boolean canRun = true;
		private boolean isVariable = false;
		private boolean isNeedScript;
		private String batName;
		private boolean isStop;
		private int isRemeber;
		private boolean isCheckVoice;

		public void stopTask() {
			canRun = false;
		}

		public ACCSwingWorker(int acc_off_min, int acc_off_max, int acc_on, int step, int num, JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop, int isRemeber, boolean isCheckVoice) {
			super();
			this.acc_off_min = acc_off_min;
			this.acc_off_max = acc_off_max;
			this.acc_on = acc_on;
			this.step = step;
			this.num = num;
			this.text = text;
			this.serial = serial;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isStop = isStop;
			this.isRemeber = isRemeber;
			this.isCheckVoice = isCheckVoice;
			acc_off = acc_off_min;
			if (acc_off_min != acc_off_max){
				isVariable = true;
			}
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// TODO Auto-generated method stub
			for(int i=0;i<num;i++){

				if(!canRun){
					System.out.println("停止执行canRun=False");
					break;
				}

				System.out.println("当前次数:"+i);
				publish("当前次数："+i);

				if (isVariable && (acc_off + step <= acc_off_max) && i>0){
					acc_off = acc_off + step;
				}else{
					acc_off = acc_off_min;
				}

				activityName = Utils.getCurrentActivity(serial);
				//发送acc off的命令
				boolean send_acc_status = sendCMDToPort(G.ACC_OFF_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_acc_status){
					publish("acc off时间："+Utils.getCurrentTime());
					Thread.sleep(acc_off);
				}else{
					System.out.println("发送命令给串口小板失败，停止测试");
					break;
				}

				//发送acc on的命令，让机器起来
				send_acc_status = sendCMDToPort(G.ACC_ON_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_acc_status){
					publish("acc on时间："+Utils.getCurrentTime());
					//检查设备起来后是否正常
					boolean check;
					System.out.println("是否有记忆功能:"+isRemeber);
					if (isRemeber == 0){
						check = Utils.checkDeviceIsOK(serial, activityName);
					}else{
						check = Utils.checkDeviceOnLaucher(serial);
					}
					if(check){
						if (isCheckVoice) {
							//1.发送声音检测的命令
							boolean send_mute_status = sendCMDToPort(G.MUTE, G.ASK_MUTE_OK, "", G.ASK_MUTE_CHECKERROR);
							if (send_mute_status) {
								String returnData = return_result;
								return_result = "";
								if (returnData.contains(G.ASK_MUTE_OK_VOICED)) {
									System.out.println("有声音");
								} else if (returnData.contains(G.ASK_MUTE_OK_NOVOICE)) {
									System.out.println("无声音");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "无声音");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								} else {
									System.out.println("返回的数据未知");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "检查声音时返回数据未知");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								}
							}
						}
						System.out.println("是否需要跑脚本:"+isNeedScript);
						if(isNeedScript){
							//需要运行脚本的情况
							System.out.println("开始跑脚本");
							Utils.runbat(batName,i+"", serial);
						}else{
							//不需要运行脚本
							Thread.sleep(acc_on);
						}
					}else{
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(),"设备恢复不正常");
						if(isStop){
							//设备出现异常后停止测试
							System.out.println("设备出现异常停止测试");
							break;
						}
					}

				}else{
					System.out.println("发送命令给串口小板失败，停止测试");
					break;
				}

			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			for(String info: chunks) {
				text.append(info+"\r\n");
				text.paintImmediately(text.getBounds());
				int length = text.getText().length();
				text.setCaretPosition(length);
			}
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			if(ACCSwingworker != null && !ACCSwingworker.isCancelled()){
				ACCSwingworker.stopTask();
				ACCSwingworker.cancel(true);
			}
			if(logcat_t != null){
				logcat_t.close();
				Utils.stopProcess(serial, "logcat");
			}
			ui.btn_start.setText("开始测试");
			ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			ui.btn_start.setForeground(Color.white);
			ui.textarea.append("测试结束："+Utils.getCurrentTime()+"\r\n");
			File file = new File("result");
			if(file.exists()){
				Parse.updateExcel(Parse.parse());
			}

		}
	}


	//B+线程
	static class BSwingWorker extends SwingWorker<List<String>, String>{
		private int B_off_min;
		private int B_off_max;
		private int B_off;
		private int B_on;
		private int num;
		private int step;
		private String serial;
		private JTextArea text;
		private boolean canRun = true;
		private boolean isStop;
		private boolean isNeedScript;
		private String batName;
		private boolean isVariable;
		private int remember;
		private boolean isCheckVoice;

		public void stopTask() {
			canRun = false;
		}

		public BSwingWorker(int B_off_min, int B_off_max, int B_on, int num, int step, int remember,JTextArea text, String serial,boolean isNeedScript, String batName,boolean isStop, boolean isCheckVoice) {
			super();
			this.B_off_min = B_off_min;
			this.B_off_max = B_off_max;
			this.B_on = B_on;
			this.num = num;
			this.serial = serial;
			this.text = text;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isStop = isStop;
			this.step = step;
			this.remember = remember;
			this.isCheckVoice = isCheckVoice;
			B_off = B_off_min;
			if (B_off_min != B_off_max){
				isVariable = true;
			}
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// TODO Auto-generated method stub
			for(int i=0;i<num;i++){
				if(!canRun){
					break;
				}
				System.out.println("当前次数:"+i);
				publish("当前次数："+i);

				if (isVariable && (B_off + step <= B_off_max) && i>0){
					B_off = B_off + step;
				}else{
					B_off = B_off_min;
				}

				//发送B+ off的命令
				boolean send_B_status = sendCMDToPort(G.B_OFF_CMD, G.ASK_B_OK, G.ASK_B_UNKNOW, G.ASK_B_CHECKERROR);
				System.out.println(send_B_status);
				if(send_B_status){
					publish("B+ off时间："+Utils.getCurrentTime());
					Thread.sleep(B_off);
				}else{
					break;
				}

				//发送B+ on的命令，让机器起来
				send_B_status = sendCMDToPort(G.B_ON_CMD, G.ASK_B_OK, G.ASK_B_UNKNOW, G.ASK_B_CHECKERROR);
				if(send_B_status){
					publish("B+ on时间："+Utils.getCurrentTime());
					//检查设备起来后是否正常
					boolean check;
					if (remember == 0){
						check = Utils.checkDeviceOnLaucher(serial);
					}else{
						check = Utils.checkDeviceIsOK(serial, activityName);
					}
					if(check){
						if (isCheckVoice) {
							//1.发送声音检测的命令
							boolean send_mute_status = sendCMDToPort(G.MUTE, G.ASK_MUTE_OK, "", G.ASK_MUTE_CHECKERROR);
							if (send_mute_status) {
								String returnData = return_result;
								return_result = "";
								if (returnData.contains(G.ASK_MUTE_OK_VOICED)) {
									System.out.println("有声音");
								} else if (returnData.contains(G.ASK_MUTE_OK_NOVOICE)) {
									System.out.println("无声音");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "无声音");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								} else {
									System.out.println("返回的数据未知");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "检查声音时返回数据未知");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								}
							}
						}
						if(isNeedScript){
							//需要运行脚本的情况
							Utils.runbat(batName,i+"", serial);
						}else{
							//不需要运行脚本
							Thread.sleep(B_on);
						}
					}else{
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "设备恢复不正常");
						if(isStop){
							//设备出现异常后停止测试
							break;
						}
					}

				}else{
					break;
				}

			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			for(String info: chunks) {
				text.append(info+"\r\n");
				text.paintImmediately(text.getBounds());
				int length = text.getText().length();
				text.setCaretPosition(length);
			}
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			if(BSwingworker != null && !BSwingworker.isCancelled()){
				BSwingworker.stopTask();
				BSwingworker.cancel(true);
			}
			if(logcat_t != null){
				logcat_t.close();
				Utils.stopProcess(serial, "logcat");
			}
			ui.btn_start.setText("开始测试");
			ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			ui.btn_start.setForeground(Color.white);
			ui.textarea.append("测试结束："+Utils.getCurrentTime()+"\r\n");
			File file = new File("result");
			if(file.exists()){
				Parse.updateExcel(Parse.parse());
			}
		}
	}

	//倒车线程
	static class CCDSwingWorker extends SwingWorker<List<String>, String>{
		private int ccd_on;
		private int ccd_on_min;
		private int ccd_on_max;
		private int ccd_off;
		private int num;
		private int step;
		private String serial;
		private JTextArea text;
		private boolean canRun = true;
		private boolean isStop;
		private boolean isNeedScript;
		private String batName;
		private boolean isVariable = false;
		private String baudrate;
		private String id;
		private String ccd_on_data;
		private String ccd_off_data;
		private boolean isScanCCd = false;
		private String canType;
		private boolean isCheckVoice;

		public void stopTask() {
			canRun = false;
		}

		public CCDSwingWorker(int ccd_on_min, int ccd_on_max, int ccd_off, int num, int step, JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop, boolean isCheckVoice) {
			super();
			this.ccd_on_min = ccd_on_min;
			this.ccd_on_max = ccd_on_max;
			this.ccd_off = ccd_off;
			this.num = num;
			this.step = step;
			this.text = text;
			this.serial = serial;
			this.isStop = isStop;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isCheckVoice = isCheckVoice;
			ccd_on = ccd_on_min;
			if (ccd_on_min != ccd_on_max){
				isVariable = true;
			}
		}

		public CCDSwingWorker(int ccd_on_min, int ccd_on_max, int ccd_off, int num, int step, String baudrate,String id, String ccd_on_data,String ccd_off_data, String cantype, JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop, boolean isCheckVoice) {
			super();
			this.ccd_on_min = ccd_on_min;
			this.ccd_on_max = ccd_on_max;
			this.ccd_off = ccd_off;
			this.num = num;
			this.step = step;
			this.baudrate = baudrate;
			this.id = id;
			this.ccd_on_data = ccd_on_data;
			this.ccd_off_data = ccd_off_data;
			this.text = text;
			this.serial = serial;
			this.isStop = isStop;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.canType = cantype;
			this.isCheckVoice = isCheckVoice;
			ccd_on = ccd_on_min;
			if (ccd_on_min != ccd_on_max){
				isVariable = true;
			}
			isScanCCd = true;
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// TODO Auto-generated method stub
			for(int i=0;i<num;i++){
				if(!canRun){
					break;
				}
				System.out.println("当前次数:"+i);
				publish("当前次数："+i);

				if (isVariable && (ccd_on + step <= ccd_on_max) && i>0){
					ccd_on = ccd_on + step;
				}else{
					ccd_on = ccd_on_min;
				}

				activityName = Utils.getCurrentActivity(serial);
				//发送进入倒车的命令
				boolean send_ccd_status;
				if(isScanCCd){
					String ccd_on_cmd = Utils.getCCDCMD(canType, baudrate, id, ccd_on_data);
					send_ccd_status = sendCMDToPort(ccd_on_cmd, G.ASK_CAN_CCD_OK, G.ASK_CAN_CCD_UNKNOW, G.ASK_CAN_CCD_CHECKERROR);
				}else{
					send_ccd_status = sendCMDToPort(G.CCD_ON_CMD, G.ASK_CCD_OK, G.ASK_CCD_UNKNOW, G.ASK_CCD_CHECKERROR);
				}
				if(send_ccd_status){
					publish("进入倒车时间："+Utils.getCurrentTime());
					Thread.sleep(ccd_on);
					//截图比对
					Utils.runCMD("adb -s " + serial + " shell screencap -p /data/local/tmp/"+i+".png");
					Utils.runCMD("adb -s " + serial + " pull /data/local/tmp/"+i+".png" + " result/"+i+".png");
					Utils.runCMD("adb -s " + serial + " shell rm -rf /data/local/tmp/"+i+".png");
					double similar = new ImageComparer("result/0.png", "result/"+i+".png").modelMatch()*100;
					if(similar >= 60){
						System.out.println("进入倒车摄像成功，图片比对通过："+similar);
					}else{
						System.out.println("进入倒车摄像失败，图片比对不通过："+similar);
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "进入倒车摄像失败，图片比对不通过："+similar);
						if(isStop){
							//设备出现异常后停止测试
							break;
						}
					}
				}else{
					break;
				}

				//发送退出倒车的命令
				if(isScanCCd){
					String ccd_off_cmd = Utils.getCCDCMD(canType,baudrate, id, ccd_off_data);
					send_ccd_status = sendCMDToPort(ccd_off_cmd, G.ASK_CAN_CCD_OK, G.ASK_CAN_CCD_UNKNOW, G.ASK_CAN_CCD_CHECKERROR);
				}else{
					send_ccd_status = sendCMDToPort(G.CCD_OFF_CMD, G.ASK_CCD_OK, G.ASK_CCD_UNKNOW, G.ASK_CCD_CHECKERROR);
				}
				if(send_ccd_status){
					publish("退出倒车时间："+Utils.getCurrentTime());
					Thread.sleep(ccd_off);
					if(Utils.getCurrentActivity(serial).equals(activityName)){
						System.out.println("机器已恢复到进入倒车前的状态");
						if (isCheckVoice) {
							//1.发送声音检测的命令
							boolean send_mute_status = sendCMDToPort(G.MUTE, G.ASK_MUTE_OK, "", G.ASK_MUTE_CHECKERROR);
							if (send_mute_status) {
								String returnData = return_result;
								return_result = "";
								if (returnData.contains(G.ASK_MUTE_OK_VOICED)) {
									System.out.println("有声音");
								} else if (returnData.contains(G.ASK_MUTE_OK_NOVOICE)) {
									System.out.println("无声音");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "无声音");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								} else {
									System.out.println("返回的数据未知");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "检查声音时返回数据未知");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								}
							}
						}
						if(isNeedScript){
							//需要运行脚本的情况
							Utils.runbat(batName,i+"", serial);
						}
					}else{
						System.out.println("机器没有恢复到进入倒车前的状态");
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "机器没有恢复到进入倒车前的状态");
						if(isStop){
							//设备出现异常后停止测试
							break;
						}
					}
				}else{
					break;
				}

			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			for(String info: chunks) {
				text.append(info+"\r\n");
				text.paintImmediately(text.getBounds());
				int length = text.getText().length();
				text.setCaretPosition(length);
			}
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			if(CCDSwingworker != null && !CCDSwingworker.isCancelled()){
				CCDSwingworker.stopTask();
				CCDSwingworker.cancel(true);
			}
			ui.btn_start.setText("开始测试");
			ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			ui.btn_start.setForeground(Color.white);
			ui.textarea.append("测试结束："+Utils.getCurrentTime()+"\r\n");
			File file = new File("result");
			if(file.exists()){
				Parse.updateExcel(Parse.parse());
			}
		}
	}


	//B+ACC线程
	static class BACCSwingWorker extends SwingWorker<List<String>, String>{
		private int acc_off;
		private int b_off;
		private int acc_min;
		private int acc_max;
		private int acc_on;
		private int b_min;
		private int b_max;
		private int b_on;
		private int acc_step;
		private int b_step;
		private int num;
		private String serial;
		private JTextArea text;
		private boolean canRun = true;
		private boolean isStop;
		private boolean isNeedScript;
		private String batName;
		private boolean acc_isVariable;
		private boolean b_isVariable;
		private int remember;
		private boolean isCheckVoice;

		public void stopTask() {
			canRun = false;
		}

		public BACCSwingWorker(int acc_min, int acc_max, int acc_on, int b_min, int b_max, int b_on, int acc_step, int b_step, int  num, int remember, JTextArea text, String serial,boolean isNeedScript, String batName,boolean isStop, boolean isCheckVoice) {
			super();
			this.acc_min = acc_min;
			this.acc_max = acc_max;
			this.acc_on = acc_on;
			this.b_min = b_min;
			this.b_max = b_max;
			this.b_on = b_on;
			this.acc_step = acc_step;
			this.b_step = b_step;
			this.remember = remember;
			this.num = num;
			this.serial = serial;
			this.text = text;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isStop = isStop;
			this.isCheckVoice = isCheckVoice;
			acc_off = acc_min;
			b_off = b_min;
			if (acc_min != acc_max){
				acc_isVariable = true;
			}
			if (b_min != b_max){
				b_isVariable = true;
			}
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// TODO Auto-generated method stub
			for(int i=0;i<num;i++){
				if(!canRun){
					break;
				}
				System.out.println("当前次数:"+i);
				publish("当前次数："+i);

				if (acc_isVariable && (acc_off + acc_step <= acc_max) && i>0){
					acc_off = acc_off + acc_step;
				}else{
					acc_off = acc_min;
				}

				if (b_isVariable && (b_off + b_step <= b_max) && i>0){
					b_off = b_off + b_step;
				}else{
					b_off = b_min;
				}

				activityName = Utils.getCurrentActivity(serial);
				//1.发送ACC OFF的命令
				boolean send_ACC_status = sendCMDToPort(G.ACC_OFF_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				System.out.println(send_ACC_status);
				if(send_ACC_status){
					publish("ACC off时间："+Utils.getCurrentTime());
					Thread.sleep(acc_off);
				}else{
					break;
				}

				//2.发送B+ off的命令
				boolean send_B_status = sendCMDToPort(G.B_OFF_CMD, G.ASK_B_OK, G.ASK_B_UNKNOW, G.ASK_B_CHECKERROR);
				System.out.println(send_B_status);
				if(send_B_status){
					publish("B+ off时间："+Utils.getCurrentTime());
					Thread.sleep(b_off);
				}else{
					break;
				}

				//3.发送B+ on的命令
				send_B_status = sendCMDToPort(G.B_ON_CMD, G.ASK_B_OK, G.ASK_B_UNKNOW, G.ASK_B_CHECKERROR);
				System.out.println(send_B_status);
				if(send_B_status){
					publish("B+ on时间："+Utils.getCurrentTime());
					Thread.sleep(b_on);
				}else{
					break;
				}

				//发送ACC on的命令，让机器起来
				send_ACC_status = sendCMDToPort(G.ACC_ON_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_ACC_status){
					publish("ACC on时间："+Utils.getCurrentTime());
					//检查设备起来后是否正常
					boolean check;
					if (remember == 0){
						check = Utils.checkDeviceOnLaucher(serial);
					}else{
						check = Utils.checkDeviceIsOK(serial, activityName);
					}
					if(check){
						if (isCheckVoice) {
							//1.发送声音检测的命令
							boolean send_mute_status = sendCMDToPort(G.MUTE, G.ASK_MUTE_OK, "", G.ASK_MUTE_CHECKERROR);
							if (send_mute_status) {
								String returnData = return_result;
								return_result = "";
								if (returnData.contains(G.ASK_MUTE_OK_VOICED)) {
									System.out.println("有声音");
								} else if (returnData.contains(G.ASK_MUTE_OK_NOVOICE)) {
									System.out.println("无声音");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "无声音");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								} else {
									System.out.println("返回的数据未知");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "检查声音时返回数据未知");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								}
							}
						}
						if(isNeedScript){
							//需要运行脚本的情况
							Utils.runbat(batName,i+"", serial);
						}else{
							//不需要运行脚本
							Thread.sleep(acc_on);
						}
					}else{
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "设备恢复不正常");
						if(isStop){
							//设备出现异常后停止测试
							break;
						}
					}

				}else{
					break;
				}

			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			for(String info: chunks) {
				text.append(info+"\r\n");
				text.paintImmediately(text.getBounds());
				int length = text.getText().length();
				text.setCaretPosition(length);
			}
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			if(BACCSwingworker != null && !BACCSwingworker.isCancelled()){
				BACCSwingworker.stopTask();
				BACCSwingworker.cancel(true);
			}
			if(logcat_t != null){
				logcat_t.close();
				Utils.stopProcess(serial, "logcat");
			}
			ui.btn_start.setText("开始测试");
			ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			ui.btn_start.setForeground(Color.white);
			ui.textarea.append("测试结束："+Utils.getCurrentTime()+"\r\n");
			File file = new File("result");
			if(file.exists()){
				Parse.updateExcel(Parse.parse());
			}
		}
	}

	//ACC倒车线程
	static class ACCOningCCDSwingWorker extends SwingWorker<List<String>, String>{
		private boolean isRandom;
		private int acc_off;
		private int acc_off_min;
		private int acc_off_max;
		private int acc_on;
		private int ccd_on;
		private int ccd_on_min;
		private int ccd_on_max;
		private int ccd_off;
		private int acc_step;
		private int ccd_step;
		private int ccd_num;
		private int num;
		private String serial;
		private JTextArea text;
		private boolean canRun = true;
		private boolean isStop;
		private boolean isNeedScript;
		private String batName;
		private boolean acc_isVariable = false;
		private boolean ccd_isVariable = false;
		private String baudrate;
		private String id;
		private String ccd_on_data;
		private String ccd_off_data;
		private boolean isScanCCD = false;
		private String canType;
		private boolean isCheckVoice;
		private int remember;

		public void stopTask() {
			canRun = false;
		}

		public ACCOningCCDSwingWorker( boolean isRandom, int acc_off_min,int acc_off_max, int acc_on, int ccd_on_min, int ccd_on_max, int ccd_off,int acc_step, int ccd_step, int ccd_num, int num, JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop, boolean isCheckVoice, int remember) {
			super();
			this.isRandom = isRandom;
			this.acc_off_min = acc_off_min;
			this.acc_off_max = acc_off_max;
			this.acc_on = acc_on;
			this.ccd_on_min = ccd_on_min;
			this.ccd_on_max = ccd_on_max;
			this.ccd_off = ccd_off;
			this.acc_step = acc_step;
			this.ccd_step = ccd_step;
			this.ccd_num = ccd_num;
			this.num = num;
			this.text = text;
			this.serial = serial;
			this.isStop = isStop;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isCheckVoice = isCheckVoice;
			this.remember = remember;
			acc_off = acc_off_min;
			ccd_on = ccd_on_min;
			if(acc_off_min != acc_off_max){
				acc_isVariable = true;
			}
			if (ccd_on_min != ccd_on_max){
				ccd_isVariable = true;
			}
		}

		public ACCOningCCDSwingWorker(boolean isRandom, int acc_off_min,int acc_off_max, int acc_on, int ccd_on_min, int ccd_on_max, int ccd_off,int acc_step, int ccd_step, int ccd_num, int num, String baudrate,String id, String ccd_on_data,String ccd_off_data, String cantype,JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop, boolean isCheckVoice, int remember) {
			super();
			this.isRandom = isRandom;
			this.acc_off_min = acc_off_min;
			this.acc_off_max = acc_off_max;
			this.acc_on = acc_on;
			this.ccd_on_min = ccd_on_min;
			this.ccd_on_max = ccd_on_max;
			this.ccd_off = ccd_off;
			this.acc_step = acc_step;
			this.ccd_step = ccd_step;
			this.ccd_num = ccd_num;
			this.num = num;
			this.baudrate = baudrate;
			this.id = id;
			this.ccd_on_data = ccd_on_data;
			this.ccd_off_data = ccd_off_data;
			this.text = text;
			this.serial = serial;
			this.isStop = isStop;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.canType = cantype;
			this.isCheckVoice = isCheckVoice;
			this.remember = remember;
			acc_off = acc_off_min;
			ccd_on = ccd_on_min;
			if (acc_off_min != acc_off_max) {
				acc_isVariable = true;
			}
			if (ccd_on_min != ccd_on_max) {
				ccd_isVariable = true;
			}
			isScanCCD = true;
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// TODO Auto-generated method stub
			for(int i=0;i<num;i++){
				if(!canRun){
					break;
				}
				System.out.println("当前次数:"+i);
				publish("当前次数："+i);

				if (acc_isVariable && (acc_off + acc_step <= acc_off_max) && i>0){
					acc_off = acc_off + acc_step;
				}else{
					acc_off = acc_off_min;
				}

				if (ccd_isVariable && (ccd_on + ccd_step <= ccd_on_max) && i>0){
					ccd_on = ccd_on + ccd_step;
				}else{
					ccd_on = ccd_on_min;
				}

				activityName = Utils.getCurrentActivity(serial);
				//1.发送acc off的命令
				boolean send_acc_status = sendCMDToPort(G.ACC_OFF_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_acc_status){
					publish("ACC OFF时间："+Utils.getCurrentTime());
					Thread.sleep(acc_off);
				}else{
					System.out.println("发送命令给串口小板失败，停止测试");
					break;
				}

				//2.发送acc on的命令
				send_acc_status = sendCMDToPort(G.ACC_ON_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_acc_status){
					publish("ACC ON时间："+Utils.getCurrentTime());

					if(isRandom){
						int random = (int)(Math.random()*5000);
						Thread.sleep(random);
					}

					for(int k=0;k<ccd_num;k++){
						//3.发送进入倒车的命令
						boolean send_ccd_status;
						if(isScanCCD){
							String ccd_on_cmd = Utils.getCCDCMD(canType,baudrate, id, ccd_on_data);
							send_ccd_status = sendCMDToPort(ccd_on_cmd, G.ASK_CAN_CCD_OK, G.ASK_CAN_CCD_UNKNOW, G.ASK_CAN_CCD_CHECKERROR);
						}else{
							send_ccd_status = sendCMDToPort(G.CCD_ON_CMD, G.ASK_CCD_OK, G.ASK_CCD_UNKNOW, G.ASK_CCD_CHECKERROR);
						}
						if(send_ccd_status){
							publish("CCD ON时间："+Utils.getCurrentTime());
							Thread.sleep(ccd_on);
						}else{
							break;
						}

						//4.发送退出倒车的命令
						if(isScanCCD){
							String ccd_off_cmd = Utils.getCCDCMD(canType,baudrate, id, ccd_off_data);
							send_ccd_status = sendCMDToPort(ccd_off_cmd, G.ASK_CAN_CCD_OK, G.ASK_CAN_CCD_UNKNOW, G.ASK_CAN_CCD_CHECKERROR);
						}else{
							send_ccd_status = sendCMDToPort(G.CCD_OFF_CMD, G.ASK_CCD_OK, G.ASK_CCD_UNKNOW, G.ASK_CCD_CHECKERROR);
						}
						if(send_ccd_status){
							publish("CCD OFF时间："+Utils.getCurrentTime());
							Thread.sleep(ccd_off);
						}else{
							break;
						}
					}

					//5.检查设备起来后是否正常
					boolean check;
					if(remember == 0){
						check = Utils.checkDeviceIsOK(serial, activityName);
					}else{
						check = Utils.checkDeviceOnLaucher(serial);
					}
					if(check){
						if (isCheckVoice) {
							//6.发送声音检测的命令
							boolean send_mute_status = sendCMDToPort(G.MUTE, G.ASK_MUTE_OK, "", G.ASK_MUTE_CHECKERROR);
							if (send_mute_status) {
								String returnData = return_result;
								return_result = "";
								if (returnData.contains(G.ASK_MUTE_OK_VOICED)) {
									System.out.println("有声音");
								} else if (returnData.contains(G.ASK_MUTE_OK_NOVOICE)) {
									System.out.println("无声音");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "无声音");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								} else {
									System.out.println("返回的数据未知");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "检查声音时返回数据未知");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								}
							}
						}
						//7.是否需要运行脚本
						if(isNeedScript){
							//需要运行脚本的情况
							Utils.runbat(batName,i+"", serial);
						}else{
							//不需要运行脚本
							Thread.sleep(acc_on);
						}
					}else{
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "设备恢复不正常");
						if(isStop){
							//设备出现异常后停止测试
							break;
						}
					}

				}else{
					System.out.println("发送命令给串口小板失败，停止测试");
					break;
				}
			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			for(String info: chunks) {
				text.append(info+"\r\n");
				text.paintImmediately(text.getBounds());
				int length = text.getText().length();
				text.setCaretPosition(length);
			}
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			if(ACCOningCCDSwingworker != null && !ACCOningCCDSwingworker.isCancelled()){
				ACCOningCCDSwingworker.stopTask();
				ACCOningCCDSwingworker.cancel(true);
			}
			if(logcat_t != null){
				logcat_t.close();
				Utils.stopProcess(serial, "logcat");
			}
			ui.btn_start.setText("开始测试");
			ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			ui.btn_start.setForeground(Color.white);
			ui.textarea.append("测试结束："+Utils.getCurrentTime()+"\r\n");
			File file = new File("result");
			if(file.exists()){
				Parse.updateExcel(Parse.parse());
			}
		}
	}

	//ACC+倒车线程
	static class ACCCCDSwingWorker extends SwingWorker<List<String>, String>{
		private boolean isRandom;
		private int acc_off;
		private int acc_off_min;
		private int acc_off_max;
		private int acc_on;
		private int ccd_on;
		private int ccd_on_min;
		private int ccd_on_max;
		private int ccd_off;
		private int acc_step;
		private int ccd_step;
		private int ccd_num;
		private int num;
		private String serial;
		private JTextArea text;
		private boolean canRun = true;
		private boolean isStop;
		private boolean isNeedScript;
		private String batName;
		private boolean acc_isVariable = false;
		private boolean ccd_isVariable = false;
		private String baudrate;
		private String id;
		private String ccd_on_data;
		private String ccd_off_data;
		private boolean isScanCCD = false;
		private String canType;
		private boolean isCheckVoice;
		private int remember;
		private boolean ccdError = false;

		public void stopTask() {
			canRun = false;
		}

		public ACCCCDSwingWorker( boolean isRandom, int acc_off_min,int acc_off_max, int acc_on, int ccd_on_min, int ccd_on_max, int ccd_off,int acc_step, int ccd_step, int ccd_num, int num, JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop,boolean isCheckVoice, int remember) {
			super();
			this.isRandom = isRandom;
			this.acc_off_min = acc_off_min;
			this.acc_off_max = acc_off_max;
			this.acc_on = acc_on;
			this.ccd_on_min = ccd_on_min;
			this.ccd_on_max = ccd_on_max;
			this.ccd_off = ccd_off;
			this.acc_step = acc_step;
			this.ccd_step = ccd_step;
			this.ccd_num = ccd_num;
			this.num = num;
			this.text = text;
			this.serial = serial;
			this.isStop = isStop;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isCheckVoice = isCheckVoice;
			this.remember = remember;
			acc_off = acc_off_min;
			ccd_on = ccd_on_min;
			if(acc_off_min != acc_off_max){
				acc_isVariable = true;
			}
			if (ccd_on_min != ccd_on_max) {
				ccd_isVariable = true;
			}
		}

		public ACCCCDSwingWorker(boolean isRandom, int acc_off_min,int acc_off_max, int acc_on, int ccd_on_min, int ccd_on_max, int ccd_off,int acc_step, int ccd_step, int ccd_num, int num, String baudrate,String id, String ccd_on_data,String ccd_off_data, String cantype, JTextArea text, String serial, boolean isNeedScript, String batName, boolean isStop, boolean isCheckVoice, int remember) {
			super();
			this.isRandom = isRandom;
			this.acc_off_min = acc_off_min;
			this.acc_off_max = acc_off_max;
			this.acc_on = acc_on;
			this.ccd_on_min = ccd_on_min;
			this.ccd_on_max = ccd_on_max;
			this.ccd_off = ccd_off;
			this.acc_step = acc_step;
			this.ccd_step = ccd_step;
			this.ccd_num = ccd_num;
			this.num = num;
			this.baudrate = baudrate;
			this.id = id;
			this.ccd_on_data = ccd_on_data;
			this.ccd_off_data = ccd_off_data;
			this.text = text;
			this.serial = serial;
			this.isStop = isStop;
			this.isNeedScript = isNeedScript;
			this.batName = batName;
			this.isCheckVoice = isCheckVoice;
			this.remember = remember;
			acc_off = acc_off_min;
			ccd_on = ccd_on_min;
			this.canType = cantype;
			if (acc_off_min != acc_off_max) {
				acc_isVariable = true;
			}
			if (ccd_on_min != ccd_on_max) {
				ccd_isVariable = true;
			}
			isScanCCD = true;
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// TODO Auto-generated method stub
			for(int i=0;i<num;i++){
				if(!canRun){
					break;
				}
				System.out.println("当前次数:"+i);
				publish("当前次数："+i);

				if (acc_isVariable && (acc_off + acc_step <= acc_off_max) && i>0){
					acc_off = acc_off + acc_step;
				}else{
					acc_off = acc_off_min;
				}

				if (ccd_isVariable && (ccd_on + ccd_step <= ccd_on_max) && i>0){
					ccd_on = ccd_on + ccd_step;
				}else{
					ccd_on = ccd_on_min;
				}

				activityName = Utils.getCurrentActivity(serial);
				//1.发送acc off的命令
				boolean send_acc_status = sendCMDToPort(G.ACC_OFF_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_acc_status){
					publish("ACC OFF时间："+Utils.getCurrentTime());
					Thread.sleep(acc_off);
				}else{
					System.out.println("发送命令给串口小板失败，停止测试");
					break;
				}

				//2.发送acc on的命令
				send_acc_status = sendCMDToPort(G.ACC_ON_CMD, G.ASK_ACC_OK, G.ASK_ACC_UNKNOW, G.ASK_ACC_CHECKERROR);
				if(send_acc_status){
					publish("ACC ON时间："+Utils.getCurrentTime());

					//3.检查设备起来后是否正常
					boolean check;
					if(remember == 0){
						check = Utils.checkDeviceIsOK(serial, activityName);
					}else{
						check = Utils.checkDeviceOnLaucher(serial);
					}
					if(check){

						if(isRandom){
							int random = (int)(Math.random()*5000);
							Thread.sleep(random);
						}

						for(int k=0;k<ccd_num;k++){
							activityName = Utils.getCurrentActivity(serial);
							//4.发送进入倒车的命令
							boolean send_ccd_status;
							if(isScanCCD){
								String ccd_on_cmd = Utils.getCCDCMD(canType,baudrate, id, ccd_on_data);
								send_ccd_status = sendCMDToPort(ccd_on_cmd, G.ASK_CAN_CCD_OK, G.ASK_CAN_CCD_UNKNOW, G.ASK_CAN_CCD_CHECKERROR);
							}else{
								send_ccd_status = sendCMDToPort(G.CCD_ON_CMD, G.ASK_CCD_OK, G.ASK_CCD_UNKNOW, G.ASK_CCD_CHECKERROR);
							}
							if(send_ccd_status){
								publish("CCD ON时间："+Utils.getCurrentTime());
								Thread.sleep(ccd_on);
								//截图比对
								String picName = i+"_"+k+".png";
								Utils.runCMD("adb -s " + serial + " shell screencap -p /data/local/tmp/"+picName);
								Utils.runCMD("adb -s " + serial + " pull /data/local/tmp/"+picName + " result/"+picName);
								Utils.runCMD("adb -s " + serial + " shell rm -rf /data/local/tmp/"+picName);
								double similar = new ImageComparer("result/0_0.png", "result/"+picName).modelMatch()*100;
								if(similar >= 60){
									System.out.println("进入倒车摄像成功，图片比对通过："+similar);
								}else{
									System.out.println("进入倒车摄像失败，图片比对不通过："+similar);
									publish("测试结果fail："+Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "进入倒车摄像失败，图片比对不通过："+similar);
									if(isStop){
										ccdError = true;
										//设备出现异常后停止测试
										break;
									}
								}
							}else{
								ccdError = true;
								break;
							}

							//5.发送退出倒车的命令
							if(isScanCCD){
								String ccd_off_cmd = Utils.getCCDCMD(canType,baudrate, id, ccd_off_data);
								send_ccd_status = sendCMDToPort(ccd_off_cmd, G.ASK_CAN_CCD_OK, G.ASK_CAN_CCD_UNKNOW, G.ASK_CAN_CCD_CHECKERROR);
							}else{
								send_ccd_status = sendCMDToPort(G.CCD_OFF_CMD, G.ASK_CCD_OK, G.ASK_CCD_UNKNOW, G.ASK_CCD_CHECKERROR);
							}
							if(send_ccd_status){
								publish("CCD OFF时间："+Utils.getCurrentTime());
								Thread.sleep(ccd_off);
								if(Utils.getCurrentActivity(serial).equals(activityName)){
									System.out.println("机器已恢复到进入倒车前的状态");
								}else{
									System.out.println("机器没有恢复到进入倒车前的状态");
									publish("测试结果fail："+Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "设备恢复不正常");
									if(isStop){
										ccdError = true;
										//设备出现异常后停止测试
										break;
									}
								}
							}else{
								ccdError = true;
								break;
							}
						}

						//进入倒车异常后退出外部循环
						if(isStop && ccdError){
							break;
						}

						if (isCheckVoice) {
							//6.发送声音检测的命令
							boolean send_mute_status = sendCMDToPort(G.MUTE, G.ASK_MUTE_OK, "", G.ASK_MUTE_CHECKERROR);
							if (send_mute_status) {
								String returnData = return_result;
								return_result = "";
								if (returnData.contains(G.ASK_MUTE_OK_VOICED)) {
									System.out.println("有声音");
								} else if (returnData.contains(G.ASK_MUTE_OK_NOVOICE)) {
									System.out.println("无声音");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "无声音");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								} else {
									System.out.println("返回的数据未知");
									publish("测试结果fail：" + Utils.getCurrentTime());
									//将异常结果记录到文件
									Parse.updateExcel(i + "", "fail", Utils.getCurrentTime(), "检查声音时返回数据未知");
									if (isStop) {
										//设备出现异常后停止测试
										break;
									}
								}
							}
						}

						//7.是否需要运行脚本
						if(isNeedScript){
							//需要运行脚本的情况
							Utils.runbat(batName,i+"", serial);
						}else{
							//不需要运行脚本
							Thread.sleep(acc_on);
						}
					}else{
						publish("测试结果fail："+Utils.getCurrentTime());
						//将异常结果记录到文件
						Parse.updateExcel(i+"","fail",Utils.getCurrentTime(), "设备恢复不正常");
						if(isStop){
							//设备出现异常后停止测试
							break;
						}
					}

				}else{
					System.out.println("发送命令给串口小板失败，停止测试");
					break;
				}
			}
			return null;
		}

		@Override
		protected void process(List<String> chunks) {
			// TODO Auto-generated method stub
			for(String info: chunks) {
				text.append(info+"\r\n");
				text.paintImmediately(text.getBounds());
				int length = text.getText().length();
				text.setCaretPosition(length);
			}
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			if(ACCCCDSwingworker != null && !ACCCCDSwingworker.isCancelled()){
				ACCCCDSwingworker.stopTask();
				ACCCCDSwingworker.cancel(true);
			}
			if(logcat_t != null){
				logcat_t.close();
				Utils.stopProcess(serial, "logcat");
			}
			ui.btn_start.setText("开始测试");
			ui.btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			ui.btn_start.setForeground(Color.white);
			ui.textarea.append("测试结束："+Utils.getCurrentTime()+"\r\n");
			File file = new File("result");
			if(file.exists()){
				Parse.updateExcel(Parse.parse());
			}
		}
	}


	/**
	 * 以内部类形式创建一个串口监听类
	 * @author zhong
	 *
	 */
	private class SerialListener extends Observable implements SerialPortEventListener {
		/**
		 * 处理监控到的串口事件
		 */
		public void serialEvent(SerialPortEvent serialPortEvent) {
			switch (serialPortEvent.getEventType()) {
				case SerialPortEvent.BI: // 10 通讯中断
					JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
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
						if (serialPort == null) {
							JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							data = SerialTool.readFromPort(serialPort);	//读取数据，存入字节数组
							String data_hex = Utils.byte2HexStr(data, data.length);
							return_result += data_hex.replace(" ", "");
							System.out.println("0000return_result="+return_result+",时间:"+ Utils.getCurrentTimeMills()+",isNeedAsk="+isNeedAsk);
							if(isNeedAsk){
								System.out.println("通知收到应答");
								setChanged();
								notifyObservers();
							}
						}

					} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
						JOptionPane.showMessageDialog(null, e, "错误", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);	//发生读取错误时显示错误信息后退出系统
					}

					break;

			}

		}

	}

	class logcatThread extends Thread{
		private boolean isRun = true;
		private String path;
		private String serial;

		public logcatThread(String path, String serial) {
			this.path = path;
			this.serial = serial;
		}


		@Override
		public void run() {
			while(isRun){
				System.out.println("start logcat process");
				Utils.runbat(path, serial,"");
				System.out.println("stop logcat process");
			}
		}

		public void close(){
			this.isRun = false;
		}

	}

	public static boolean sendCMDToPort(String cmd, String ask_ok, String ask_unknow, String ask_error){
		boolean send_acc_status;
		SObserver s = new SObserver(serialListener, cmd, ask_ok, ask_unknow, ask_error);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		send_acc_status = s.getSendStatus();
		serialListener.deleteObserver(s);
		return send_acc_status;
	}

	static class SObserver implements Observer{
		String cmd;
		String ask_ok;
		String ask_unknow;
		String ask_error;
		String data;
		boolean send_acc_status = false;
		int times = 5;

		public SObserver(SerialListener sm, String cmd, String ask_ok, String ask_unknow, String ask_error) {
			super();
			this.cmd = cmd;
			this.ask_ok = ask_ok;
			this.ask_unknow = ask_unknow;
			this.ask_error = ask_error;
			String code = Utils.checkcode_0007(cmd);
			this.data=G.HEADER_NEED_ASK+cmd+code;
			sm.addObserver(this); //注册加入观察者
			sendCMD();
		}

		@Override
		public void update(Observable o, Object arg) {
			isNeedAsk = false;
			System.out.println("update="+Utils.getCurrentTimeMills());
			try {
				Thread.sleep(10);   //sleep 10ms,wait data receive complete.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return_result = "FF" + StringUtils.substringAfter(return_result, "FF");
			if(return_result.startsWith("FF55") || return_result.startsWith("FFBB")){
				if(return_result.contains(ask_ok)){
					send_acc_status = true;
					System.out.println("接收数据:ok");
				}else {
					if(return_result.equals(ask_unknow)){
						System.out.println("接收数据:未知命令");
					}else if(return_result.equals(ask_error)){
						System.out.println("接收数据:数据校验不通过");
					}else{
						System.out.println("接收数据:接收的数据不全");
					}
					if (times > 0){
						sendCMD();
						times = times - 1;
					}
					if (times == 0){
						JOptionPane.showMessageDialog(null, "发送数据给串口小板失败，请检查端口号是否正确！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}else{
				System.out.println("返回的数据头格式不对！！！");
				if (times > 0){
					sendCMD();
					times = times - 1;
				}
			}
		}

		public void sendCMD(){
			return_result = "";
			try {
				//发送acc off的命令
				isNeedAsk = true;
				System.out.println("发送数据:"+cmd+",时间:"+Utils.getCurrentTimeMills());
				SerialTool.sendToPort(serialPort, Utils.hexStr2Bytes(data));
			} catch (SendDataToSerialPortFailure e) {
				e.printStackTrace();
			} catch (SerialPortOutputStreamCloseFailure serialPortOutputStreamCloseFailure) {
				serialPortOutputStreamCloseFailure.printStackTrace();
			}
		}

		public boolean getSendStatus(){
			return send_acc_status;
		}

	}

}