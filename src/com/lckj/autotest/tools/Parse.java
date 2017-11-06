package com.lckj.autotest.tools;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        createExcel();
        Parse.updateExcel(Parse.parse());
    }

    static int countFiles = 0;// 声明统计文件个数的变量
    static int countFolders = 0;// 声明统计文件夹的变量

    public  static File[] searchFile(File folder, final String keyWord) {// 递归查找包含关键字的文件

        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isFile())// 如果是文件
                    countFiles++;
                else
                    // 如果是目录
                    countFolders++;
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().toLowerCase().contains(keyWord.toLowerCase())))// 目录或文件包含关键字
                    return true;
                return false;
            }
        });

        List<File> result = new ArrayList<File>();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
            if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
                result.add(subFolders[i]);
            } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                File[] foldResult = searchFile(subFolders[i], keyWord);
                for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
                    result.add(foldResult[j]);// 文件保存到集合中
                }
            }
        }

        File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
        result.toArray(files);// 集合数组化
        return files;
    }

    public static Map<String,String> parseCaseLog(File file){
        Map<String,String> map = new HashMap<String,String>();
        String path = file.getAbsolutePath();
        String[] list = path.split("\\\\");
        map.put("casename",list[list.length-3]);
        map.put("loop", list[list.length-2]);

        String caseStep = "";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"utf8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String lineTxt = null;
            while((lineTxt = br.readLine()) != null) {
                if (lineTxt.contains("OK (1 test)")) {
                    map.put("ispass", "success");
                }
                if (lineTxt.contains("INSTRUMENTATION_STATUS: caseStep=")) {
                    caseStep += lineTxt.split("INSTRUMENTATION_STATUS: caseStep=")[1].trim() + "\n";
                }
            }
            inputStreamReader.close();

            if(!map.containsKey("ispass")){
                map.put("ispass","fail");
            }
            map.put("caseStep", caseStep);

        }catch (Exception e){
            e.printStackTrace();
        }

        return map;

    }

    public static List<Map<String,String>> parse(){
        List<Map<String,String>> list = new ArrayList<>();
        String path = "result";
        File files[] = searchFile(new File(path),"case.log");
        for (int i=0;i<files.length;i++){
            Map<String,String> map =  parseCaseLog(files[i]);
            list.add(map);
        }
        return list;
    }

    public static void createExcel(){
        try {
            // 打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File("result.xls"));
            WritableSheet sheet1 = book.createSheet("result1", 0);
            sheet1.addCell(new Label(0, 0, "测试次数"));
            sheet1.addCell(new Label(1, 0, "测试结果"));
            sheet1.addCell(new Label(2, 0, "时间"));
            sheet1.addCell(new Label(3, 0, "原因"));
            sheet1.setColumnView(0,20);
            sheet1.setColumnView(1,20);
            sheet1.setColumnView(2,30);
            sheet1.setColumnView(3,80);

            // 生成名为“result2”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("result2", 1);
            // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)

            Label label00 = new Label(0, 0, "Loop");
            Label label01 = new Label(1, 0, "caseName");
            Label label02 = new Label(2, 0, "isPass");
            Label label03 = new Label(3, 0, "caseStep");
            sheet.addCell(label00);
            sheet.addCell(label01);
            sheet.addCell(label02);
            sheet.addCell(label03);
            sheet.setColumnView(0,20);
            sheet.setColumnView(1,20);
            sheet.setColumnView(2,20);
            sheet.setColumnView(3,50);
            // 写入数据并关闭文件
            book.write();
            book.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateExcel(List<Map<String,String>> list){
        try {
            // Excel获得文件
            Workbook wb = Workbook.getWorkbook(new File("result.xls"));
            // 打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(new File("result.xls"), wb);
            // update
            WritableSheet sheet = book.getSheet(1);
            for (int i=0;i<list.size();i++){
                Map<String ,String> map = list.get(i);
                Label label0 = new Label(0, i+1, map.get("loop"));
                Label label1 = new Label(1, i+1, map.get("casename"));
                Label label2 = new Label(2, i+1, map.get("ispass"));
                Label label3 = new Label(3, i+1, map.get("caseStep"));
                sheet.addCell(label0);
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
            }
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateExcel(String num, String isPass, String time, String reason){
        try {
            // Excel获得文件
            Workbook wb = Workbook.getWorkbook(new File("result.xls"));
            int rownum =  wb.getSheet(0).getRows();
            // 打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(new File("result.xls"), wb);
            // update
            WritableSheet sheet = book.getSheet(0);
            sheet.addCell(new Label(0, rownum, num));
            sheet.addCell(new Label(1, rownum, isPass));
            sheet.addCell(new Label(2, rownum, time));
            sheet.addCell(new Label(3, rownum, reason));
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
