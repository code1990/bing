package com;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel操作工具类
 *
 * @author admin
 */
public class ExcelUtils {
    static Logger logger = LoggerFactory.getLogger(BingApplication.class);
    /*public static void main(String[] args) throws Exception {
        String srcPath = "C:\\Users\\admin\\Desktop\\bing\\keyword.xls";
        List<String> list = ExcelUtils.readXls(srcPath);
        List<String> list2 = new ArrayList<>();
        String srcPath2 = "C:\\Users\\admin\\Desktop\\bing\\keyword3.xls";
        for (int i = 0; i < list.size(); i++) {
            list2.add(list.get(i)+"\t"+"q");
        }
        logger.info(list2.size());
        ExcelUtils.writeXls(srcPath2,list2);
    }*/

    /**
     * @param path 需要读取的文件路径
     * @return
     */
    public static List<String> readXls(String path) {
        File file = new File(path);
        List<String> list = new ArrayList<>();
        try {
            //1、获取文件输入流
            InputStream inputStream = new FileInputStream(file);
            //2、获取Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            //3、得到Excel工作表对象
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            //4、循环读取表格数据
            for (Row row : sheetAt) {
                String info = row.getCell(0).getStringCellValue();
                if(!"".equals(info.trim())){
                    list.add(info);
                }

            }
            //5、关闭流
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeXls(String path,List<String> list) {
        File file = new File(path);
        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();
       //3.创建标题行
        //4.遍历数据,创建数据行
        for (int i = 0; i < list.size() ; i++) {
            String[] array = list.get(i).split("\t");
            HSSFRow dataRow = sheet.createRow(i);
            dataRow.createCell(0).setCellValue(array[0]);
            dataRow.createCell(1).setCellValue(array[1]);
        }
        try {
            //1、获取文件输入流
            FileOutputStream outputStream = new FileOutputStream(file);
            //10.写出文件,关闭流
            hssfWorkbook.write(outputStream);
            hssfWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getName()+">>>excel写入ok!");
        logger.info(file.getName()+">>>excel写入ok!");
    }
}
