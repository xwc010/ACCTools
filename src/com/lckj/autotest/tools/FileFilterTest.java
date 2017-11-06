package com.lckj.autotest.tools;

import javax.swing.filechooser.FileFilter;

public class FileFilterTest extends FileFilter {
	private String str;

	public FileFilterTest(String str) {
		this.str = str;
	}

	public boolean accept(java.io.File f) {
		if (f.isDirectory())
			return true;
		return f.getName().endsWith(str); // 设置为选择以.txt为后缀的文件
	}

	public String getDescription() {
		return str;
	}
}