package com.github.tky.utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Excels {
    
    private static final Logger logger = LoggerFactory.getLogger(Excels.class) ;
    private final static String CONTENTTYPE = "application/vnd.ms-excel";

    private final static String CONTENTTYPEXML = "text/xml";
    
    private final static String ENCODING = "utf-8";
    
    public final static int EXCELSIZE = 5000;
//    public final static int EXCELSIZE = 5;
    /**
     *  根据输入的条件导出excel文件,并写到response中
     * @param response
     * @param contentList
     * @param handler
     */
    public static <T> void exportExcel(HttpServletResponse response, List<T> contentList, RowHandler<T> handler) {
        String[] title = handler.getTitle() ;
        XSSFWorkbook wwb = null;
        response.reset();
        response.setContentType(CONTENTTYPE);
        try {
            String filename = URLEncoder.encode(handler.getFilename(), ENCODING);
            response.addHeader("Content-Disposition", "attachment; filename=\""    + filename + ".xls\"");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1) ;
        }
        try (OutputStream os = response.getOutputStream();){
            // 创建EXCEL
            wwb = new XSSFWorkbook();
            // 创建工作簿
            XSSFSheet sheet = wwb.createSheet();

            // 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
            sheet.setColumnWidth((short) 0, 2500);
            sheet.setColumnWidth((short) 1, 5000);

            // 创建一个单元格，从0开始
            XSSFRow rowTitle = sheet.createRow((short) 0);
            // 设置单元格样式
            XSSFCellStyle hs = wwb.createCellStyle();
            hs.setFillForegroundColor((short)22);
            hs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            hs.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
            // 设置字体样式
            XSSFFont hf = wwb.createFont();
            hf.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            hf.setFontHeightInPoints((short) 13);
            hs.setFont(hf);

            // 构造一个数组设置第一行之后的单元格
            XSSFCell cellTitle[] = new XSSFCell[title.length];
            for (int i = 0; i < title.length; i++) {
                cellTitle[i] = rowTitle.createCell(i);
                cellTitle[i].setCellStyle(hs);
                cellTitle[i].setCellType(HSSFCell.CELL_TYPE_STRING);
                cellTitle[i].setCellValue(title[i]);
            }
            for (int i = 0; i < contentList.size(); i++) {
                XSSFRow row = sheet.createRow((short) (i + 1));
                for (int j = 0; j < title.length; j++) {
                    XSSFCell cell = row.createCell(j);
                    Object value = handler.handler(contentList.get(i), j) ;
                    if(handler.textOnly()){
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(null == value ? "": value.toString());
                    }else{
                        // TODO 需要时进行完善
                    }
                    
                }
            }
            wwb.write(os);
            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }
    }
    
    /**
     *  海量数据导出，生产的文件的后缀名为.xml 双击就直接用excel 打开了，如果行数大于65536 就需要用07以上来打开了，如果用03则只能显示出65536条记录
     * @param response
     * @param contentList
     * @param handler
     */
    public static <T> void exportExcelBig(HttpServletResponse response, List<T> contentList, RowHandler<T> handler){
        response.reset();
        response.setContentType(CONTENTTYPEXML);
        try {
            String filename = URLEncoder.encode(handler.getFilename(), ENCODING);
            response.addHeader("Content-Disposition", "attachment; filename=\""    + filename + ".xml\"");
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage(),e1);
        }
        
         try {
            exportExcelBig(response.getOutputStream(), contentList,handler) ;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }
    
    public static <T> void exportExcelBig(OutputStream os, List<T> contentList, RowHandler<T> handler){
        String[] title = handler.getTitle() ; 
        StringBuffer sb = new StringBuffer("");
        
        setExcelHead(sb, title, contentList.size() + 1);
        int currentRecord = 0;
        try (DataOutputStream rafs = new DataOutputStream(new BufferedOutputStream(os));){
            while(currentRecord < contentList.size() ){
                T t = contentList.get(currentRecord) ;
                StringBuffer row = new StringBuffer("<Row>") ;
                for (int i = 0; i < title.length; i++) {
                    row.append(handler.handler(t, i)) ;
                }
                row.append("</Row>");
                sb.append(row) ;
                if (currentRecord % EXCELSIZE == 0) {
                    rafs.write(sb.toString().getBytes());
                    rafs.flush();
                    sb.setLength(0);
                }
                currentRecord++;
            }
            setExcelTail(sb) ;
            
            rafs.write(sb.toString().getBytes());
            rafs.flush();
            rafs.close();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e) ;
        }
    }
    
    private static void setExcelTail(StringBuffer sb) {
        sb.append("</Table>");
        sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
        
        sb.append("<ProtectObjects>False</ProtectObjects>");
        
        sb.append("<ProtectScenarios>False</ProtectScenarios>");
        
        sb.append("</WorksheetOptions>");
        
        sb.append("</Worksheet>");
        sb.append("</Workbook>");
    }

    /**
     * 大量数据导出时使用
     * @param cellValue
     * @return 单元格类型为文本类型的EXCEL单元格
     */
    public static String toStringCell(String cellValue){
        String value = cellValue == null ? "" : cellValue ;
        return "<Cell><Data ss:Type=\"String\">" + value + "</Data></Cell>" ;
    }
    
    /**
     * 大量数据导出时使用
     * @param cellValue
     * @return 单元格类型为数值类型的EXCEL单元格
     */
    public static String toNumberCell(String cellValue){
        String value = cellValue == null ? "" : cellValue ;
        return "<Cell><Data ss:Type=\"Number\">" + value + "</Data></Cell>" ;
    }
    
    private static void setExcelHead(StringBuffer sb, String[] title, int total) {
        sb.append("<?xml version=\"1.0\" encoding=\""+ENCODING+"\"?>");
        
        sb.append("<?mso-application progid=\"Excel.Sheet\"?>");
        
        sb.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"");
        
        sb.append("  xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
        
        sb.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
        
        sb.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"");
        
        sb.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
        
        sb.append(" <Styles>");
        sb.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">");
        sb.append("   <Alignment ss:Vertical=\"Center\"/>");
        sb.append("   <Borders/>");
        sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>");
        sb.append("   <Interior/>");
        sb.append("   <NumberFormat/>");
        sb.append("   <Protection/>");
        sb.append("  </Style>");
        sb.append("  <Style ss:ID=\"s62\">");
        sb.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
        sb.append("  </Style>");
        sb.append("  <Style ss:ID=\"s83\">");
        sb.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
        sb.append("   <Borders/>");
        sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"16\" ss:Bold=\"1\"/>");
        sb.append("   <Interior ss:Color=\"#A5A5A5\" ss:Pattern=\"Solid\"/>");
        sb.append("   <NumberFormat/>");
        sb.append("   <Protection/>");
        sb.append("  </Style>");
        sb.append(" </Styles>");
        sb.append("<Worksheet ss:Name=\"Sheet0\">");
        
        sb.append("<Table ss:ExpandedColumnCount=\"" + title.length
                + "\" ss:ExpandedRowCount=\"" + total
                + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:StyleID=\"s62\">");
        //设置表头
        sb.append("<Column ss:StyleID=\"s62\" ss:Width=\"160\" ss:Span=\"" + (title.length-1) + "\"/>");
        sb.append("<Row ss:AutoFitHeight=\"0\">");
        for (int i = 0; i < title.length; i++) {
            sb.append("<Cell ss:StyleID=\"s83\"><Data ss:Type=\"String\">" + title[i] + "</Data></Cell>");
            
        }
        sb.append("</Row>");
    }
    
}
