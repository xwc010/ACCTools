package com.lckj.autotest.tools;

import java.io.*;
import java.util.*;

public class PropertyUtil {

    public static void main(String[] args){
        Map<String, String> map = new HashMap<>();
        map.put("min","10");
        map.put("max","20");
        new PropertyUtil().saveProperties("acc.properties", map, false);

        Map<String, String> read = new PropertyUtil().readProperties("acc.propertites");
        for(String key:read.keySet()){
            System.out.println(key+":"+read.get(key));
        }

    }

    Properties prop = new Properties();
    private String configDir = "config/";

    public void saveProperties(String filename, Map<String, String> map, boolean isAppend){
        try {
            File file = new File(configDir);
            if(!file.exists()){
                file.mkdir();
            }
            FileOutputStream oFile = new FileOutputStream(configDir+filename, isAppend);
            for(String key:map.keySet()){
                String value = map.get(key);
                prop.setProperty(key,value);
            }
            prop.store(oFile, "The New properties file");
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Map<String, String> readProperties(String filename){
        Map<String, String> map = new HashMap<>();
        File file = new File(configDir + filename);
        if(file.exists()){
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(configDir + filename));
                prop.load(in);
                Iterator<String> it = prop.stringPropertyNames().iterator();
                while (it.hasNext()){
                    String key = it.next();
                    String value = prop.getProperty(key);
                    map.put(key, value);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
