package com.lckj.autotest.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.lckj.autotest.tools.G;
import com.lckj.autotest.tools.PropertyUtil;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.lckj.autotest.tools.FileFilterTest;
import org.omg.PortableInterceptor.INACTIVE;

public class Main_Layout {
	public JFrame frame = null;
	public JLabel acc_sleep;
	public JButton btn_open;
	public JButton btn_start;
	public JButton btn_bat_path;
	public JComboBox<String> cb_com_select;
	public JComboBox<String> cb_baudrate_select;
	public JComboBox<String> cb_acc_stop;
	public JComboBox<String> cb_voice;
	public JComboBox<String> cb_acc_if_remeber;
	public JComboBox<String> cb_device_select;
	private JLabel label_device_select;
	private JScrollPane scrollPane;
	private JLabel label_acc_max;
	private JLabel label_acc_min;
	private JLabel label_acc_on;
	private JLabel label_acc_step;
	private JLabel label_baudrate;
	private JLabel label_com;
	private JLabel label_num;
	private JLabel label_result;
	private JLabel label_acc_stop;
	private JLabel label_acc_if_remeber;
	public JLabel label_bat_path;
	private JPanel tab_acc;
	private JPanel tab_main;
	private JPanel tab_script;
	private JTabbedPane tabbedpanel;
	public JTextArea textarea;
	public DigitOnlyField tf_acc_max;
	public DigitOnlyField tf_acc_min;
	public DigitOnlyField tf_acc_on;
	public DigitOnlyField tf_num;
	public DigitOnlyField tf_sleep;
	public DigitOnlyField tf_step;
	public JTextField tf_bat_path;
	public JComboBox<String> cb_isneed_script;
	public JLabel label_isneed_script;
	public JLabel label_isneed_logcat;
	public JComboBox<String> cb_isneed_logcat;
	public JLabel label_logcat_path;
	public JButton btn_logcat_path;
	public JTextField tf_logcat_path;
	public JComboBox<String> cb_type;
	private JLabel label_B_on;
	private JLabel label_type;
	public JLabel label_voice;
	private JPanel tab_B;
	private JPanel tab_ccd;
	public JTextField tf_B_on;
	public JLabel label_ccd_num;
	public JTextField tf_ccd_num;
	public JLabel label_B_num;
	public JTextField tf_B_num;
	public javax.swing.JComboBox<String> cb_random;
	public javax.swing.JComboBox<String> cb_test_type;
	private javax.swing.JLabel label_B_max;
	private javax.swing.JLabel label_B_min;
	private javax.swing.JLabel label_B_step;
	private javax.swing.JLabel label_attation;
	private javax.swing.JLabel label_bacc_acc_max;
	private javax.swing.JLabel label_bacc_acc_min;
	private javax.swing.JLabel label_bacc_acc_on;
	private javax.swing.JLabel label_bacc_acc_step;
	private javax.swing.JLabel label_bacc_b_max;
	private javax.swing.JLabel label_bacc_b_min;
	private javax.swing.JLabel label_bacc_b_on;
	private javax.swing.JLabel label_bacc_b_step;
	private javax.swing.JLabel label_bacc_num;
	private javax.swing.JLabel label_cacc_acc_max;
	private javax.swing.JLabel label_cacc_acc_min;
	private javax.swing.JLabel label_cacc_acc_on;
	private javax.swing.JLabel label_cacc_acc_step;
	private javax.swing.JLabel label_cacc_ccd_max;
	private javax.swing.JLabel label_cacc_ccd_num;
	private javax.swing.JLabel label_cacc_ccd_off;
	private javax.swing.JLabel label_cacc_ccd_step;
	private javax.swing.JLabel label_cacc_num;
	private javax.swing.JLabel label_ccd_max;
	private javax.swing.JLabel label_ccd_min;
	private javax.swing.JLabel label_ccd_step;
	private javax.swing.JLabel label_random;
	private javax.swing.JLabel label_test_type;
	private javax.swing.JPanel tab_acc_B;
	private javax.swing.JPanel tab_acc_ccd;
	public DigitOnlyField tf_B_max;
	public DigitOnlyField tf_B_min;
	public DigitOnlyField tf_B_step;
	public DigitOnlyField tf_bacc_acc_max;
	public DigitOnlyField tf_bacc_acc_min;
	public DigitOnlyField tf_bacc_acc_on;
	public DigitOnlyField tf_bacc_acc_step;
	public DigitOnlyField tf_bacc_b_max;
	public DigitOnlyField tf_bacc_b_min;
	public DigitOnlyField tf_bacc_b_on;
	public DigitOnlyField tf_bacc_b_step;
	public DigitOnlyField tf_bacc_num;
	public DigitOnlyField tf_cacc_acc_max;
	public DigitOnlyField tf_cacc_acc_min;
	public DigitOnlyField tf_cacc_acc_on;
	public DigitOnlyField tf_cacc_acc_step;
	public DigitOnlyField tf_cacc_ccd_max;
	public DigitOnlyField tf_cacc_ccd_min;
	public DigitOnlyField tf_cacc_ccd_num;
	public DigitOnlyField tf_cacc_ccd_off;
	public DigitOnlyField tf_cacc_ccd_step;
	public javax.swing.JLabel tf_cacc_cdd_min;
	public javax.swing.JTextField tf_cacc_num;
	public javax.swing.JTextField tf_ccd_max;
	public javax.swing.JTextField tf_ccd_min;
	public javax.swing.JTextField tf_ccd_step;
	public javax.swing.JComboBox<String> cb_cacc_ccd_type;
	public javax.swing.JComboBox<String> cb_ccd_type;
	public javax.swing.JLabel label_cacc_baudrate;
	public javax.swing.JLabel label_cacc_ccd_type;
	public javax.swing.JLabel label_cacc_id;
	public javax.swing.JLabel label_cacc_off_data;
	public javax.swing.JLabel label_cacc_on_data;
	public javax.swing.JLabel label_ccd_attation;
	public javax.swing.JLabel label_ccd_baudrate;
	public javax.swing.JLabel label_ccd_id;
	public javax.swing.JLabel label_ccd_off_data;
	public javax.swing.JLabel label_ccd_on_data;
	public javax.swing.JLabel label_ccd_type;
	public DigitOnlyField tf_cacc_baudrate;
	public javax.swing.JTextField tf_cacc_id;
	public javax.swing.JTextField tf_cacc_off_data;
	public javax.swing.JTextField tf_cacc_on_data;
	public DigitOnlyField tf_ccd_baudrate;
	public javax.swing.JTextField tf_ccd_id;
	public javax.swing.JTextField tf_ccd_off_data;
	public javax.swing.JTextField tf_ccd_on_data;
	public javax.swing.JLabel label_ccd_off;
	public javax.swing.JTextField tf_ccd_off;
	public javax.swing.JLabel label_b_remenber;
	public JComboBox cb_b_remember;
	public javax.swing.JLabel label_bacc_remember;
	public JComboBox cb_bacc_remember;
	public javax.swing.JLabel label_cacc_cantype;
	public javax.swing.JLabel label_ccd_cantype;
	public javax.swing.JComboBox<String> cb_cacc_cantype;
	public javax.swing.JComboBox<String> cb_ccd_cantype;
	private PropertyUtil propertyUtil = new PropertyUtil();

	public Main_Layout() {
		try {
			// 设置frame样式
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
			UIManager.put("TabbedPane.tabAreaInsets",
					new javax.swing.plaf.InsetsUIResource(3, 10, 3, 10));
			frame = new JFrame("路畅ACC测试工具");

			JPanel wholePanel = createWholePanel(); // 整个界面面板
			frame.add(wholePanel);
			frame.setUndecorated(true);
			frame.setVisible(true); // 设置可见
			frame.setSize(925, 748);
			frame.setLocationRelativeTo(null); // 再加上这一句就可以把Frame放在最中间了
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//整个面板
	public JPanel createWholePanel() {
		return initComponents();
	}

	private JPanel initComponents() {
		JPanel panel = new JPanel();

		tabbedpanel = new javax.swing.JTabbedPane();
		tab_main = new javax.swing.JPanel();
		label_com = new javax.swing.JLabel();
		cb_com_select = new javax.swing.JComboBox<>();
		label_baudrate = new javax.swing.JLabel();
		cb_baudrate_select = new javax.swing.JComboBox<>();
		btn_open = new javax.swing.JButton();
		label_device_select = new javax.swing.JLabel();
		cb_device_select = new javax.swing.JComboBox<>();
		btn_start = new javax.swing.JButton();
		label_result = new javax.swing.JLabel();
		scrollPane = new javax.swing.JScrollPane();
		textarea = new javax.swing.JTextArea();
		label_acc_stop = new javax.swing.JLabel();
		cb_acc_stop = new javax.swing.JComboBox<>();
		cb_voice = new JComboBox<>();
		label_acc_if_remeber = new javax.swing.JLabel();
		cb_acc_if_remeber = new javax.swing.JComboBox<>();
		label_type = new javax.swing.JLabel();
		label_voice = new JLabel();
		cb_type = new javax.swing.JComboBox<>();
		tab_acc = new javax.swing.JPanel();
		tf_sleep = new DigitOnlyField(2);
		tf_acc_min = new DigitOnlyField(2);
		label_acc_max = new javax.swing.JLabel();
		tf_acc_max = new DigitOnlyField(2);
		label_num = new javax.swing.JLabel();
		tf_num = new DigitOnlyField(2);
		label_acc_step = new javax.swing.JLabel();
		tf_step = new DigitOnlyField(2);
		label_acc_on = new javax.swing.JLabel();
		label_acc_min = new javax.swing.JLabel();
		tf_acc_on = new DigitOnlyField(2);
		acc_sleep = new javax.swing.JLabel();
		tab_B = new javax.swing.JPanel();
		label_B_min = new javax.swing.JLabel();
		tf_B_min = new DigitOnlyField(2);
		label_B_on = new javax.swing.JLabel();
		tf_B_on = new javax.swing.JTextField();
		label_B_num = new javax.swing.JLabel();
		tf_B_num = new javax.swing.JTextField();
		label_B_max = new javax.swing.JLabel();
		tf_B_max = new DigitOnlyField(2);
		label_B_step = new javax.swing.JLabel();
		tf_B_step = new DigitOnlyField(2);
		tab_ccd = new javax.swing.JPanel();
		label_ccd_min = new javax.swing.JLabel();
		tf_ccd_min = new javax.swing.JTextField();
		tf_ccd_off = new javax.swing.JTextField();
		label_ccd_num = new javax.swing.JLabel();
		tf_ccd_num = new javax.swing.JTextField();
		label_ccd_max = new javax.swing.JLabel();
		label_ccd_step = new javax.swing.JLabel();
		tf_ccd_max = new javax.swing.JTextField();
		tf_ccd_step = new javax.swing.JTextField();
		tab_acc_B = new javax.swing.JPanel();
		label_bacc_acc_min = new javax.swing.JLabel();
		tf_bacc_acc_min = new DigitOnlyField(2);
		label_bacc_acc_max = new javax.swing.JLabel();
		tf_bacc_acc_max = new DigitOnlyField(2);
		label_bacc_b_min = new javax.swing.JLabel();
		label_bacc_b_max = new javax.swing.JLabel();
		tf_bacc_b_max = new DigitOnlyField(2);
		label_bacc_b_on = new javax.swing.JLabel();
		label_bacc_acc_on = new javax.swing.JLabel();
		tf_bacc_acc_on = new DigitOnlyField(2);
		tf_bacc_b_min = new DigitOnlyField(2);
		tf_bacc_b_on = new DigitOnlyField(2);
		label_bacc_acc_step = new javax.swing.JLabel();
		tf_bacc_acc_step = new DigitOnlyField(2);
		label_bacc_b_step = new javax.swing.JLabel();
		tf_bacc_b_step = new DigitOnlyField(2);
		label_bacc_num = new javax.swing.JLabel();
		tf_bacc_num = new DigitOnlyField(2);
		tab_acc_ccd = new javax.swing.JPanel();
		label_test_type = new javax.swing.JLabel();
		cb_test_type = new javax.swing.JComboBox<>();
		label_random = new javax.swing.JLabel();
		cb_random = new javax.swing.JComboBox<>();
		label_cacc_acc_min = new javax.swing.JLabel();
		tf_cacc_acc_min = new DigitOnlyField(2);
		label_cacc_acc_max = new javax.swing.JLabel();
		tf_cacc_acc_max = new DigitOnlyField(2);
		tf_cacc_cdd_min = new javax.swing.JLabel();
		tf_cacc_ccd_min = new DigitOnlyField(2);
		label_cacc_ccd_max = new javax.swing.JLabel();
		tf_cacc_ccd_max = new DigitOnlyField(2);
		label_cacc_ccd_off = new javax.swing.JLabel();
		tf_cacc_ccd_off = new DigitOnlyField(2);
		label_cacc_acc_on = new javax.swing.JLabel();
		tf_cacc_acc_on = new DigitOnlyField(2);
		label_cacc_acc_step = new javax.swing.JLabel();
		tf_cacc_acc_step = new DigitOnlyField(2);
		label_cacc_ccd_step = new javax.swing.JLabel();
		tf_cacc_ccd_step = new DigitOnlyField(2);
		label_cacc_ccd_num = new javax.swing.JLabel();
		tf_cacc_ccd_num = new DigitOnlyField(2);
		label_cacc_num = new javax.swing.JLabel();
		tf_cacc_num = new javax.swing.JTextField();
		label_attation = new javax.swing.JLabel();
		tab_script = new javax.swing.JPanel();
		btn_bat_path = new javax.swing.JButton();
		label_bat_path = new javax.swing.JLabel();
		tf_bat_path = new javax.swing.JTextField();
		tf_logcat_path = new javax.swing.JTextField();
		btn_logcat_path = new javax.swing.JButton();
		label_isneed_script = new javax.swing.JLabel();
		cb_isneed_script = new javax.swing.JComboBox<>();
		label_isneed_logcat = new javax.swing.JLabel();
		cb_isneed_logcat = new javax.swing.JComboBox<>();
		label_logcat_path = new javax.swing.JLabel();
		label_ccd_id = new javax.swing.JLabel();
		tf_ccd_id = new javax.swing.JTextField();
		label_ccd_on_data = new javax.swing.JLabel();
		tf_ccd_on_data = new javax.swing.JTextField();
		label_ccd_off_data = new javax.swing.JLabel();
		tf_ccd_off_data = new javax.swing.JTextField();
		label_ccd_baudrate = new javax.swing.JLabel();
		tf_ccd_baudrate = new DigitOnlyField(2);
		label_ccd_type = new javax.swing.JLabel();
		cb_ccd_type = new javax.swing.JComboBox<>();
		label_ccd_attation = new javax.swing.JLabel();
		label_cacc_ccd_type = new javax.swing.JLabel();
		cb_cacc_ccd_type = new javax.swing.JComboBox<>();
		label_cacc_baudrate = new javax.swing.JLabel();
		tf_cacc_baudrate = new DigitOnlyField(2);
		label_cacc_id = new javax.swing.JLabel();
		tf_cacc_id = new javax.swing.JTextField();
		label_cacc_on_data = new javax.swing.JLabel();
		tf_cacc_on_data = new javax.swing.JTextField();
		label_cacc_off_data = new javax.swing.JLabel();
		tf_cacc_off_data = new javax.swing.JTextField();
		label_ccd_off = new JLabel();
		tf_ccd_off = new JTextField();
		label_b_remenber = new javax.swing.JLabel();
		cb_b_remember = new javax.swing.JComboBox<>();
		label_bacc_remember = new javax.swing.JLabel();
		cb_bacc_remember = new javax.swing.JComboBox<>();
		label_ccd_cantype = new javax.swing.JLabel();
		cb_ccd_cantype = new javax.swing.JComboBox<>();
		label_cacc_cantype = new javax.swing.JLabel();
		cb_cacc_cantype = new javax.swing.JComboBox<>();

		label_com.setText("串口选择：");
		label_baudrate.setText("波特率：");
		cb_baudrate_select.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "115200","19200", "14400", "9600","921600"}));
		btn_open.setText("打开串口");
		btn_open.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		btn_open.setForeground(Color.white);
		label_device_select.setText("设备选择：");
		btn_start.setText("开始测试");
		btn_start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		btn_start.setForeground(Color.white);
		label_result.setText("运行日志");
		textarea.setColumns(20);
		textarea.setRows(5);
		scrollPane.setViewportView(textarea);
		label_acc_stop.setText("异常停止测试：");
		cb_acc_stop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "是", "否"}));
		cb_voice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "否", "是"}));
		label_type.setText("测试类型：");
		label_voice.setText("是否检测声音：");
		cb_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACC测试", "B+测试", "CCD测试","B+ACC测试","ACC+CCD测试"}));


		javax.swing.GroupLayout tab_mainLayout = new javax.swing.GroupLayout(tab_main);
		tab_main.setLayout(tab_mainLayout);
		tab_mainLayout.setHorizontalGroup(
				tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_mainLayout.createSequentialGroup()
								.addComponent(label_result)
								.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(scrollPane)
						.addGroup(tab_mainLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(tab_mainLayout.createSequentialGroup()
												.addComponent(label_type)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(cb_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(tab_mainLayout.createSequentialGroup()
												.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_mainLayout.createSequentialGroup()
																.addComponent(label_com)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
														.addGroup(tab_mainLayout.createSequentialGroup()
																.addComponent(label_device_select)
																.addGap(14, 14, 14)))
												.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(cb_com_select, 0, 117, Short.MAX_VALUE)
														.addComponent(cb_device_select, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
								.addGap(27, 27, 27)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label_baudrate, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_acc_stop)
										.addComponent(label_voice))
								.addGap(27, 27, 27)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(cb_baudrate_select, 0, 115, Short.MAX_VALUE)
										.addComponent(cb_acc_stop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_voice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(27, 27, 27)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(btn_open, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(215, Short.MAX_VALUE))
		);
		tab_mainLayout.setVerticalGroup(
				tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_mainLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(cb_com_select, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_baudrate)
										.addComponent(cb_baudrate_select, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_open, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_com))
								.addGap(27, 27, 27)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(cb_device_select, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_device_select)
										.addComponent(label_acc_stop)
										.addComponent(cb_acc_stop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(cb_type, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_type)
										.addComponent(label_voice)
								        .addComponent(cb_voice)
										.addComponent(btn_start, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
								.addGap(27, 27, 27)
								.addComponent(label_result)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
		);

		tabbedpanel.addTab("主界面", tab_main);

		label_acc_max.setText("ACC OFF最长时间(s)：");
		label_num.setText("测试总次数：");
		label_acc_step.setText("测试步进(ms)：");
		label_acc_on.setText("ACC ON的时长(s)：");
		label_acc_min.setText("ACC OFF最短时间(s)：");
		acc_sleep.setText("机器休眠时间(s)：");
		label_acc_if_remeber.setText("是否有记忆ACC功能：");
		cb_acc_if_remeber.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "否", "是"}));
		Map<String, String> map = propertyUtil.readProperties(G.ACC_PROPERTY);
		if(map.size() == 0){
			tf_sleep.setText("60");
			tf_acc_min.setText("10");
			tf_acc_max.setText("10");
			tf_num.setText("5");
			tf_step.setText("50");
			tf_acc_on.setText("5");
		}else {
			tf_acc_min.setText(map.get("min"));
			tf_acc_max.setText(map.get("max"));
			tf_sleep.setText(map.get("sleep"));
			tf_num.setText(map.get("num"));
			tf_step.setText(map.get("step"));
			tf_acc_on.setText(map.get("on"));
			cb_acc_if_remeber.setSelectedIndex(Integer.parseInt(map.get("isRemeber")));
		}

		javax.swing.GroupLayout tab_accLayout = new javax.swing.GroupLayout(tab_acc);
		tab_acc.setLayout(tab_accLayout);
		tab_accLayout.setHorizontalGroup(
				tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_accLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(label_num, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_acc_on, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
										.addComponent(label_acc_min, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_acc_if_remeber, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(tf_num, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
										.addComponent(tf_acc_on)
										.addComponent(tf_acc_min)
										.addComponent(cb_acc_if_remeber, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(27, 27, 27)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(acc_sleep, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(label_acc_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(label_acc_max, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(tf_acc_max, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
										.addComponent(tf_sleep)
										.addComponent(tf_step, javax.swing.GroupLayout.Alignment.LEADING))
								.addContainerGap(244, Short.MAX_VALUE))
		);
		tab_accLayout.setVerticalGroup(
				tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_accLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_acc_min)
										.addComponent(label_acc_max, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(tf_acc_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(tf_acc_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(label_num)
												.addComponent(tf_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(label_acc_step)
												.addComponent(tf_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(27, 27, 27)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_acc_on)
										.addComponent(tf_acc_on, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(acc_sleep)
										.addComponent(tf_sleep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_accLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_acc_if_remeber)
										.addComponent(cb_acc_if_remeber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(511, Short.MAX_VALUE))
		);

		tabbedpanel.addTab("ACC参数设置", tab_acc);

		label_B_min.setText("B+ OFF最短时长(s)：");
		label_B_on.setText("B+ ON时长(s)：");
		label_B_num.setText("测试总测试：");
		label_B_max.setText("B+ OFF最长时长(s)：");
		label_B_step.setText("测试步进(ms)：");
		label_b_remenber.setText("是否有记忆ACC功能：");
		cb_b_remember.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{ "否", "是"}));
		Map<String, String> bMap = propertyUtil.readProperties(G.B_PROPERTY);
		if(bMap.size() == 0){
			tf_B_min.setText("10");
			tf_B_on.setText("10");
			tf_B_num.setText("5");
			tf_B_max.setText("10");
			tf_B_step.setText("50");
		}else{
			tf_B_min.setText(bMap.get("min"));
			tf_B_on.setText(bMap.get("on"));
			tf_B_num.setText(bMap.get("num"));
			tf_B_max.setText(bMap.get("max"));
			tf_B_step.setText(bMap.get("step"));
			cb_b_remember.setSelectedIndex(Integer.parseInt(bMap.get("remember")));
		}

		javax.swing.GroupLayout tab_BLayout = new javax.swing.GroupLayout(tab_B);
		tab_B.setLayout(tab_BLayout);
		tab_BLayout.setHorizontalGroup(
				tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_BLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(label_B_min, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_B_num, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_B_on, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(tf_B_min, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
										.addComponent(tf_B_num)
										.addComponent(tf_B_on, javax.swing.GroupLayout.Alignment.LEADING))
								.addGap(27, 27, 27)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(label_B_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_B_max, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
										.addComponent(label_b_remenber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(tf_B_max)
										.addComponent(tf_B_step, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
										.addComponent(cb_b_remember, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(251, Short.MAX_VALUE))
		);
		tab_BLayout.setVerticalGroup(
				tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_BLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_B_min)
										.addComponent(tf_B_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_B_max)
										.addComponent(tf_B_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_B_on)
										.addComponent(tf_B_on, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_B_step)
										.addComponent(tf_B_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_B_num)
										.addComponent(tf_B_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_b_remenber)
										.addComponent(cb_b_remember, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(549, Short.MAX_VALUE))
		);

		tabbedpanel.addTab("B+参数设置", tab_B);

		label_ccd_min.setText("CCD ON最小时长(s)：");
		label_ccd_off.setText("CCD OFF时长(s)：");
		label_ccd_num.setText("测试总次数：");
		label_ccd_max.setText("CCD ON最长时长(s)：");
		label_ccd_step.setText("测试步进(ms)：");
		label_ccd_id.setText("协议ID：");
		label_ccd_on_data.setText("CCD ON数据：");
		label_ccd_off_data.setText("CCD OFF数据：");
		label_ccd_baudrate.setText("协议波特率(K)：");
		label_ccd_type.setText("请选择倒车类型：");
		cb_ccd_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "硬件倒车", "协议倒车" }));
		label_ccd_attation.setText("备注：协议倒车的数据和ID请找项目开发人员。");
		label_ccd_cantype.setText("请选择can类型：");
		cb_ccd_cantype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "容错can", "高速can", "单线can"}));
		label_ccd_id.setVisible(false);
		tf_ccd_id.setVisible(false);
		label_ccd_on_data.setVisible(false);
		tf_ccd_on_data.setVisible(false);
		label_ccd_off_data.setVisible(false);
		tf_ccd_off_data.setVisible(false);
		label_ccd_baudrate.setVisible(false);
		tf_ccd_baudrate.setVisible(false);
		label_ccd_cantype.setVisible(false);
		cb_ccd_cantype.setVisible(false);
		tf_ccd_id.setText("133");
		tf_ccd_on_data.setText("7000000000000000");
		tf_ccd_off_data.setText("6000000000000000");
		tf_ccd_baudrate.setText("500");
		Map<String, String> cddMap = propertyUtil.readProperties(G.CCD_PROPERTY);
		if(cddMap.size() == 0){
			tf_ccd_min.setText("1");
			tf_ccd_off.setText("1");
			tf_ccd_num.setText("5");
			tf_ccd_max.setText("1");
			tf_ccd_step.setText("50");
		}else{
			tf_ccd_min.setText(cddMap.get("min"));
			tf_ccd_off.setText(cddMap.get("off"));
			tf_ccd_num.setText(cddMap.get("num"));
			tf_ccd_max.setText(cddMap.get("max"));
			tf_ccd_step.setText(cddMap.get("step"));
			int canType = Integer.parseInt(cddMap.get("type"));
			cb_ccd_type.setSelectedIndex(canType);
			if (canType != 0){
				tf_ccd_id.setText(cddMap.get("id"));
				tf_ccd_on_data.setText(cddMap.get("ccd_on_data"));
				tf_ccd_off_data.setText(cddMap.get("ccd_off_data"));
				tf_ccd_baudrate.setText(cddMap.get("baudrate"));
				cb_ccd_cantype.setSelectedIndex(Integer.parseInt(cddMap.get("index")));
				label_ccd_id.setVisible(true);
				tf_ccd_id.setVisible(true);
				label_ccd_on_data.setVisible(true);
				tf_ccd_on_data.setVisible(true);
				label_ccd_off_data.setVisible(true);
				tf_ccd_off_data.setVisible(true);
				label_ccd_baudrate.setVisible(true);
				tf_ccd_baudrate.setVisible(true);
				label_ccd_cantype.setVisible(true);
				cb_ccd_cantype.setVisible(true);
			}
		}



		javax.swing.GroupLayout tab_ccdLayout = new javax.swing.GroupLayout(tab_ccd);
		tab_ccd.setLayout(tab_ccdLayout);
		tab_ccdLayout.setHorizontalGroup(
				tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_ccdLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label_ccd_attation, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(tab_ccdLayout.createSequentialGroup()
												.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_ccdLayout.createSequentialGroup()
																.addComponent(label_ccd_off_data, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tf_ccd_off_data))
														.addGroup(tab_ccdLayout.createSequentialGroup()
																.addComponent(label_ccd_id, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tf_ccd_id))
														.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(tab_ccdLayout.createSequentialGroup()
																		.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																				.addComponent(label_ccd_off, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
																				.addComponent(label_ccd_min, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(label_ccd_num, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																				.addComponent(tf_ccd_off, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
																				.addComponent(tf_ccd_min, javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(tf_ccd_num)))
																.addGroup(tab_ccdLayout.createSequentialGroup()
																		.addComponent(label_ccd_cantype, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(cb_ccd_cantype, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
												.addGap(27, 27, 27)
												.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addGroup(tab_ccdLayout.createSequentialGroup()
																.addComponent(label_ccd_baudrate, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tf_ccd_baudrate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(tab_ccdLayout.createSequentialGroup()
																.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addComponent(label_ccd_max, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
																		.addComponent(label_ccd_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_ccd_type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addComponent(tf_ccd_max)
																		.addComponent(tf_ccd_step, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
																		.addComponent(cb_ccd_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
														.addGroup(tab_ccdLayout.createSequentialGroup()
																.addComponent(label_ccd_on_data, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tf_ccd_on_data)))))
								.addContainerGap(251, Short.MAX_VALUE))
		);
		tab_ccdLayout.setVerticalGroup(
				tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_ccdLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_ccd_min)
										.addComponent(tf_ccd_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_ccd_max)
										.addComponent(tf_ccd_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_ccd_off)
										.addComponent(tf_ccd_off, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_ccd_step)
										.addComponent(tf_ccd_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_ccd_num)
										.addComponent(tf_ccd_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_ccd_type)
										.addComponent(cb_ccd_type, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_ccd_cantype)
										.addComponent(cb_ccd_cantype, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_ccd_baudrate)
										.addComponent(tf_ccd_baudrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_ccd_id)
										.addComponent(tf_ccd_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_ccd_on_data)
										.addComponent(tf_ccd_on_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_ccd_off_data)
										.addComponent(tf_ccd_off_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE)
								.addComponent(label_ccd_attation)
								.addGap(67, 67, 67))
		);
		tabbedpanel.addTab("CCD参数设置", tab_ccd);

		label_bacc_acc_min.setText("ACC OFF最小时长(s)：");
		label_bacc_acc_max.setText("ACC OFF最长时长(s)：");
		label_bacc_b_min.setText("B+ OFF最小时长(s)：");
		label_bacc_b_max.setText("B+ OFF最长时长(s)：");
		label_bacc_b_on.setText("B+ ON时长(s)：");
		label_bacc_acc_on.setText("ACC ON时长(s)：");
		label_bacc_acc_step.setText("ACC OFF步进(ms)：");
		label_bacc_b_step.setText("B+ OFF步进(ms)：");
		label_bacc_num.setText("测试总次数：");
		label_bacc_remember.setText("是否有记忆ACC功能：");
		cb_bacc_remember.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "否", "是"}));
		Map<String, String> baccMap = propertyUtil.readProperties(G.B_ACC_PROPERTY);
		if(baccMap.size() == 0){
			tf_bacc_acc_min.setText("10");
			tf_bacc_acc_max.setText("10");
			tf_bacc_b_max.setText("10");
			tf_bacc_acc_on.setText("10");
			tf_bacc_b_min.setText("10");
			tf_bacc_b_on.setText("10");
			tf_bacc_acc_step.setText("50");
			tf_bacc_b_step.setText("50");
			tf_bacc_num.setText("1000");
		}else{
			tf_bacc_acc_min.setText(baccMap.get("acc_min"));
			tf_bacc_acc_max.setText(baccMap.get("acc_min"));
			tf_bacc_b_max.setText(baccMap.get("acc_min"));
			tf_bacc_acc_on.setText(baccMap.get("acc_on"));
			tf_bacc_b_min.setText(baccMap.get("b_min"));
			tf_bacc_b_on.setText(baccMap.get("b_on"));
			tf_bacc_acc_step.setText(baccMap.get("acc_step"));
			tf_bacc_b_step.setText(baccMap.get("b_step"));
			tf_bacc_num.setText(baccMap.get("num"));
			cb_bacc_remember.setSelectedIndex(Integer.parseInt(baccMap.get("remember")));
		}

		javax.swing.GroupLayout tab_acc_BLayout = new javax.swing.GroupLayout(tab_acc_B);
		tab_acc_B.setLayout(tab_acc_BLayout);
		tab_acc_BLayout.setHorizontalGroup(
				tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_acc_BLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label_bacc_b_min, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(tab_acc_BLayout.createSequentialGroup()
												.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(tab_acc_BLayout.createSequentialGroup()
																.addComponent(label_bacc_num, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tf_bacc_num, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(tab_acc_BLayout.createSequentialGroup()
																.addComponent(label_bacc_acc_min, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(tf_bacc_b_min, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(tf_bacc_acc_min, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(tf_bacc_b_on, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(tf_bacc_acc_step, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
												.addGap(0, 0, Short.MAX_VALUE))
										.addGroup(tab_acc_BLayout.createSequentialGroup()
												.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(label_bacc_b_on, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_bacc_acc_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGap(128, 128, 128)))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(tab_acc_BLayout.createSequentialGroup()
												.addComponent(label_bacc_remember, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(cb_bacc_remember, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(tab_acc_BLayout.createSequentialGroup()
												.addComponent(label_bacc_b_step, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(tf_bacc_b_step, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
										.addGroup(tab_acc_BLayout.createSequentialGroup()
												.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_acc_BLayout.createSequentialGroup()
																.addComponent(label_bacc_acc_on, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(tf_bacc_acc_on))
														.addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_acc_BLayout.createSequentialGroup()
																.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
																		.addComponent(label_bacc_acc_max, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_bacc_b_max, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(tf_bacc_acc_max, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(tf_bacc_b_max, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
												.addGap(0, 0, Short.MAX_VALUE)))
								.addGap(251, 251, 251))
		);
		tab_acc_BLayout.setVerticalGroup(
				tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_acc_BLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_bacc_acc_min)
										.addComponent(tf_bacc_acc_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_bacc_acc_max)
										.addComponent(tf_bacc_acc_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(33, 33, 33)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_bacc_b_min)
										.addComponent(label_bacc_b_max)
										.addComponent(tf_bacc_b_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(tf_bacc_b_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_bacc_b_on)
										.addComponent(label_bacc_acc_on)
										.addComponent(tf_bacc_acc_on, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(tf_bacc_b_on, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_bacc_acc_step)
										.addComponent(tf_bacc_acc_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_bacc_b_step)
										.addComponent(tf_bacc_b_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_bacc_num)
										.addComponent(tf_bacc_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_bacc_remember)
										.addComponent(cb_bacc_remember, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(447, Short.MAX_VALUE))
		);

		tabbedpanel.addTab("B+ACC参数设置", tab_acc_B);

		label_test_type.setText("请选择测试类型：");
		cb_test_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACC启动中进入CCD", "ACC启动后进入CCD"}));
		label_random.setText("第一次是否随机：");
		cb_random.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "否", "是"}));
		label_cacc_acc_min.setText("ACC OFF最小时长(s)：");
		label_cacc_acc_max.setText("ACC OFF最长时长(s)：");
		tf_cacc_cdd_min.setText("CCD ON最小时长(s)：");
		label_cacc_ccd_max.setText("CCD ON最大时长(s)：");
		label_cacc_ccd_off.setText("CCD OFF时长(s)：");
		label_cacc_acc_on.setText("ACC ON时长(s)：");
		label_cacc_acc_step.setText("ACC OFF步进(ms)：");
		label_cacc_ccd_step.setText("CCD ON步进(ms)：");
		label_cacc_ccd_num.setText("CCD测试次数：");
		label_cacc_num.setText("测试总次数：");
		label_attation.setText("备注：1.CCD测试次数代表ACC ON起来后进入CCD的次数。   2.协议倒车的数据和ID请找项目开发人员。");
		label_cacc_ccd_type.setText("请选择倒车类型：");
		cb_cacc_ccd_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "硬件倒车", "协议倒车"}));
		label_cacc_baudrate.setText("协议波特率(K)：");
		label_cacc_id.setText("协议ID：");
		label_cacc_on_data.setText("CCD ON数据：");
		label_cacc_off_data.setText("CCD OFF数据：");
		label_cacc_cantype.setText("请选择can类型：");
		cb_cacc_cantype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "容错can", "高速can", "单线can"}));
		label_cacc_baudrate.setVisible(false);
		label_cacc_id.setVisible(false);
		label_cacc_on_data.setVisible(false);
		tf_cacc_id.setVisible(false);
		tf_cacc_on_data.setVisible(false);
		label_cacc_off_data.setVisible(false);
		tf_cacc_baudrate.setVisible(false);
		tf_cacc_off_data.setVisible(false);
		label_cacc_cantype.setVisible(false);
		cb_cacc_cantype.setVisible(false);
		tf_cacc_baudrate.setText("500");
		tf_cacc_id.setText("133");
		tf_cacc_on_data.setText("7000000000000000");
		tf_cacc_off_data.setText("6000000000000000");
		Map<String, String> accCcdMap = propertyUtil.readProperties(G.ACC_CCD_PROPERTY);
		if(accCcdMap.size() == 0){
			tf_cacc_acc_min.setText("10");
			tf_cacc_acc_max.setText("10");
			tf_cacc_ccd_min.setText("1");
			tf_cacc_ccd_max.setText("1");
			tf_cacc_ccd_off.setText("1");
			tf_cacc_acc_on.setText("20");
			tf_cacc_acc_step.setText("50");
			tf_cacc_ccd_step.setText("50");
			tf_cacc_ccd_num.setText("5");
			tf_cacc_num.setText("1000");
		}else {
			cb_test_type.setSelectedIndex(Integer.parseInt(accCcdMap.get("test_type")));
			cb_random.setSelectedIndex(Integer.parseInt(accCcdMap.get("random")));
			tf_cacc_acc_min.setText(accCcdMap.get("acc_min"));
			tf_cacc_acc_max.setText(accCcdMap.get("acc_max"));
			tf_cacc_ccd_min.setText(accCcdMap.get("ccd_min"));
			tf_cacc_ccd_max.setText(accCcdMap.get("ccd_max"));
			tf_cacc_ccd_off.setText(accCcdMap.get("ccd_off"));
			tf_cacc_acc_on.setText(accCcdMap.get("acc_on"));
			tf_cacc_acc_step.setText(accCcdMap.get("acc_step"));
			tf_cacc_ccd_step.setText(accCcdMap.get("ccd_step"));
			tf_cacc_ccd_num.setText(accCcdMap.get("ccd_num"));
			tf_cacc_num.setText(accCcdMap.get("num"));
			cb_cacc_cantype.setSelectedIndex(Integer.parseInt(accCcdMap.get("can_type")));
			int ccdType = Integer.parseInt(accCcdMap.get("ccd_type"));
			cb_cacc_ccd_type.setSelectedIndex(ccdType);
			if (ccdType != 0) {
				tf_cacc_baudrate.setText(accCcdMap.get("baudrate"));
				tf_cacc_id.setText(accCcdMap.get("id"));
				tf_cacc_on_data.setText(accCcdMap.get("ccd_on_data"));
				tf_cacc_off_data.setText(accCcdMap.get("ccd_off_data"));
				label_cacc_baudrate.setVisible(true);
				label_cacc_id.setVisible(true);
				label_cacc_on_data.setVisible(true);
				tf_cacc_id.setVisible(true);
				tf_cacc_on_data.setVisible(true);
				label_cacc_off_data.setVisible(true);
				tf_cacc_baudrate.setVisible(true);
				tf_cacc_off_data.setVisible(true);
				label_cacc_cantype.setVisible(true);
				cb_cacc_cantype.setVisible(true);
			}
		}

		javax.swing.GroupLayout tab_acc_ccdLayout = new javax.swing.GroupLayout(tab_acc_ccd);
		tab_acc_ccd.setLayout(tab_acc_ccdLayout);
		tab_acc_ccdLayout.setHorizontalGroup(
				tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_acc_ccdLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label_attation)
										.addGroup(tab_acc_ccdLayout.createSequentialGroup()
												.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(label_cacc_ccd_num, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_test_type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_cacc_ccd_off, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_cacc_acc_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_cacc_acc_min, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
														.addComponent(tf_cacc_cdd_min, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_cacc_ccd_type, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_cacc_baudrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(label_cacc_on_data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(cb_test_type, 0, 132, Short.MAX_VALUE)
														.addComponent(tf_cacc_acc_min)
														.addComponent(tf_cacc_ccd_min)
														.addComponent(tf_cacc_ccd_off)
														.addComponent(tf_cacc_acc_step)
														.addComponent(tf_cacc_ccd_num)
														.addComponent(cb_cacc_ccd_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(tf_cacc_baudrate)
														.addComponent(tf_cacc_on_data))
												.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(tab_acc_ccdLayout.createSequentialGroup()
																.addGap(27, 27, 27)
																.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addComponent(label_random, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_cacc_ccd_step, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_cacc_num, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_cacc_acc_max, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
																		.addComponent(label_cacc_ccd_max, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_cacc_acc_on, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_cacc_cantype, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(label_cacc_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
														.addGroup(tab_acc_ccdLayout.createSequentialGroup()
																.addGap(30, 30, 30)
																.addComponent(label_cacc_off_data, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(cb_random, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_off_data, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_acc_max, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_ccd_max, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_acc_on, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_ccd_step, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_num, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(cb_cacc_cantype, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_cacc_id, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(248, Short.MAX_VALUE))
		);
		tab_acc_ccdLayout.setVerticalGroup(
				tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_acc_ccdLayout.createSequentialGroup()
								.addGap(34, 34, 34)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_test_type)
										.addComponent(cb_test_type, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_random)
										.addComponent(cb_random, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_acc_min)
										.addComponent(tf_cacc_acc_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_acc_max)
										.addComponent(tf_cacc_acc_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(tf_cacc_cdd_min)
										.addComponent(tf_cacc_ccd_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_ccd_max)
										.addComponent(tf_cacc_ccd_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_ccd_off)
										.addComponent(tf_cacc_ccd_off, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_acc_on)
										.addComponent(tf_cacc_acc_on, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_acc_step)
										.addComponent(tf_cacc_acc_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_ccd_step)
										.addComponent(tf_cacc_ccd_step, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_ccd_num)
										.addComponent(tf_cacc_ccd_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_num)
										.addComponent(tf_cacc_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_ccd_type)
										.addComponent(cb_cacc_ccd_type, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_cantype)
										.addComponent(cb_cacc_cantype, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_baudrate)
										.addComponent(tf_cacc_baudrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_id)
										.addComponent(tf_cacc_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(tab_acc_ccdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_cacc_on_data)
										.addComponent(tf_cacc_on_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(label_cacc_off_data)
										.addComponent(tf_cacc_off_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
								.addComponent(label_attation)
								.addGap(67, 67, 67))
		);
		tabbedpanel.addTab("ACC+CCD参数设置", tab_acc_ccd);

		btn_bat_path.setText("浏览");
		btn_bat_path.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		btn_bat_path.setForeground(Color.white);
		label_bat_path.setText("请选择执行的bat脚本：");
		btn_logcat_path.setText("浏览");
		btn_logcat_path.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		btn_logcat_path.setForeground(Color.white);
		btn_logcat_path.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilterTest fileFilter=new FileFilterTest(".bat");
				fileChooser.setFileFilter(fileFilter);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					String filePath= fileChooser.getSelectedFile().getAbsolutePath();
					tf_logcat_path.setText(filePath);
				}
			}
		});

		btn_bat_path.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileFilterTest fileFilter=new FileFilterTest(".bat");
				fileChooser.setFileFilter(fileFilter);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					String filePath= fileChooser.getSelectedFile().getAbsolutePath();
					tf_bat_path.setText(filePath);
				}
			}
		});

		label_isneed_script.setText("ACC ON状态下是否需要跑脚本：");
		cb_isneed_script.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "否", "是"}));
		label_isneed_logcat.setText("是否需要保存logcat：");
		cb_isneed_logcat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "否", "是"}));
		label_logcat_path.setText("请选择执行的logcat.bat脚本：");
		btn_bat_path.setVisible(false);
		label_bat_path.setVisible(false);
		tf_bat_path.setVisible(false);
		label_logcat_path.setVisible(false);
		tf_logcat_path.setVisible(false);
		btn_logcat_path.setVisible(false);
		Map<String, String> logMap = propertyUtil.readProperties(G.LOG_PROPERTY);
		if(logMap.size() != 0){
			cb_isneed_script.setSelectedIndex(Integer.parseInt(logMap.get("need_script")));
			cb_isneed_logcat.setSelectedIndex(Integer.parseInt(logMap.get("need_logcat")));
			if(logMap.containsKey("batName")){
				btn_bat_path.setVisible(true);
				label_bat_path.setVisible(true);
				tf_bat_path.setVisible(true);
				tf_bat_path.setText(logMap.get("batName"));
			}
			if (logMap.containsKey("logcatpath")){
				tf_logcat_path.setText(logMap.get("logcatpath"));
				label_logcat_path.setVisible(true);
				tf_logcat_path.setVisible(true);
				btn_logcat_path.setVisible(true);
			}
		}

		javax.swing.GroupLayout tab_scriptLayout = new javax.swing.GroupLayout(tab_script);
		tab_script.setLayout(tab_scriptLayout);
		tab_scriptLayout.setHorizontalGroup(
				tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_scriptLayout.createSequentialGroup()
								.addGap(23, 23, 23)
								.addGroup(tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(label_bat_path)
										.addComponent(label_isneed_logcat)
										.addComponent(label_logcat_path)
										.addComponent(label_isneed_script))
								.addGap(30, 30, 30)
								.addGroup(tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(tab_scriptLayout.createSequentialGroup()
												.addComponent(tf_bat_path, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btn_bat_path, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(tab_scriptLayout.createSequentialGroup()
												.addComponent(tf_logcat_path, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(btn_logcat_path, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(cb_isneed_script, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(cb_isneed_logcat, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(384, Short.MAX_VALUE))
		);
		tab_scriptLayout.setVerticalGroup(
				tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(tab_scriptLayout.createSequentialGroup()
								.addGap(29, 29, 29)
								.addGroup(tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_isneed_script)
										.addComponent(cb_isneed_script, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_bat_path)
										.addComponent(tf_bat_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_bat_path, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(tab_scriptLayout.createSequentialGroup()
												.addGap(43, 43, 43)
												.addComponent(label_isneed_logcat))
										.addGroup(tab_scriptLayout.createSequentialGroup()
												.addGap(40, 40, 40)
												.addComponent(cb_isneed_logcat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(18, 18, 18)
								.addGroup(tab_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(label_logcat_path)
										.addComponent(tf_logcat_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_logcat_path, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(435, Short.MAX_VALUE))
		);

		tabbedpanel.addTab("脚本设置", tab_script);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
		panel.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tabbedpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(tabbedpanel)
		);
		return panel;
	}// </editor-fold>
}
