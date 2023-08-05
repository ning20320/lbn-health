package lbn.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.BaseRowSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PoiUtils {

    private static final String xls = "xls";
    private static final String xlsx = "xlsx";
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    /**
     * 读取excel文件，解析后返回
     */

    public static List<String[]> readExcel(MultipartFile file) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作簿对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        if (workbook != null){
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获取当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null){
                    continue;
                }
                //获取当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获取当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获取当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null){
                        continue;
                    }
                    //获取当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获取当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null){
            return cellValue;
        }
        //如果当前单元格内容为日期类型，需要特殊处理
        String dataFormatString = cell.getCellStyle().getDataFormatString();
        if (dataFormatString.equals("m/d/yy")){
            cellValue = new SimpleDateFormat(DATE_FORMAT).format(cell.getDateCellValue());
            return cellValue;
        }
        //判断数据类型
        switch (cell.getCellType()){
            case NUMERIC://数字
                BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                //数值 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
                cellValue = bd.toPlainString();
                break;
            case STRING://字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN://Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA://公式
                cellValue = cell.getCellFormula();
                break;
            case BLANK://控制
                cellValue = "";
                break;
            case ERROR://故障
                cellValue = "ERROR VALUE";
                break;
            default:
                cellValue = "UNKNOW VALUE";
                break;
        }
        return cellValue;
    }

    private static Workbook getWorkBook(MultipartFile file) {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作簿对象，表示整个excel
        Workbook workbook = null;
        try{
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    //检验文件是否合法
    private static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否为excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
}
