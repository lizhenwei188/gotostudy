package com.gotostudy.study.com.utils;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;


public class ExcelUtil {

    /**
     * 导出excel
     * @param response 相应response
     * @param title    保存文件的标题
     * @param head     保存excel对象的实体类
     * @param list     需要保存的数据列表
     * @throws IOException  异常捕获
     */
    public static void exportExcel(HttpServletResponse response, String title, Class head, List list) throws IOException {
        export(response, title, head, list, null);
    }
    
    public static void export(HttpServletResponse response, String title, Class head, List list, Set<String> set) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(title, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        if(set == null){
            EasyExcel.write(response.getOutputStream(), head).sheet("模板")
                    .doWrite(list);
        }else{
            EasyExcel.write(response.getOutputStream(), head).includeColumnFiledNames(set).sheet("模板")
                    .doWrite(list);
        }

    }
}

