package com.flrjcx.xypt.common.utils;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.exception.WebServiceEnumException;

import java.io.File;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URL;
import java.util.*;

/**
 * @author : aftermath
 * @date : 2022-11-16 10:47:33
 */
public class FileUtils {
    public static List<File> getFile(String path){
        // get file list where the path has
        File menu = new File(path);
        // get the folder list
        File[] array = menu.listFiles();

        if (Objects.isNull(array)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_MENU_NULL_ERROR);
        }

        List<File> fileList = new ArrayList<>();

        for (File file : array) {
            if (file.isFile()) {
                //// only take file name
                //System.out.println("^^^^^" + file.getName());
                //// take file path and name
                //System.out.println("#####" + file);
                //// take file path and name
                //System.out.println("*****" + file.getPath());
                fileList.add(file);
            } else if (file.isDirectory()) {
                getFile(file.getPath());
            }
        }

        return fileList;
    }

    public static InputStream mergeInputStream(List<InputStream> list) {
        Vector<InputStream> v = new Vector<>(list);
        Enumeration<InputStream> en = v.elements();
        return new SequenceInputStream(en);
    }

    public static void main(String[] args) throws Exception {
        Enumeration<URL> resources = FileUtils.class.getClassLoader().getResources("dict/harmonious_dictionary");
        while(resources.hasMoreElements()){
            System.out.println(resources.nextElement());
        }
    }
}
