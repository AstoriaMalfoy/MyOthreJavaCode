package FileInput;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author litao34
 * @ClassName Job_Export
 * @Description TODO
 * @CreateDate 2022/4/12-5:56 PM
 **/
public class Job_Export {

    private static Map<String,List<String>> data = new HashMap<>();

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/astoria/Desktop/file.xls");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = Workbook.getWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        System.out.println(rows);
        System.out.println(cols);
        for (int row = 0; row < rows ; row++){
            String name = sheet.getCell(row, 0).getContents();
            String dis = sheet.getCell(row, 4).getContents();
            List<String> fieldsList = data.get(name);
            if (Objects.isNull(fieldsList)){
                fieldsList = new ArrayList<>();
            }
            fieldsList.add(dis);
            if (row % 1000 == 0 ){
                System.out.println("读取量 : row");
            }
            data.put(name,fieldsList);
        }


        String path = "/Users/astoria/Desktop/out.xls";
        System.out.println(path);
        try {

            //定义一个Excel表格
            XSSFWorkbook wb = new XSSFWorkbook();  //创建工作薄
            XSSFSheet sheet1 = wb.createSheet("sheet1"); //创建工作表
            int line = 0;

            for (String s : data.keySet()) {
                List<String> values = data.get(s);
                XSSFRow row = sheet1.createRow(line);
                int start = 1;
                row.createCell(0).setCellValue(s);
                for (String value : values) {
                    row.createCell(start).setCellValue(value);
                    start++;
                }
                line++;
                if (line % 100 == 0 ){
                    System.out.println("写入量 :" + line + "  总量 : " + data.keySet().size());
                }
            }

            //输出流,下载时候的位置
//            FileWriter outputStream1 = new FileWriter(path);
            FileOutputStream outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("写入成功");
        } catch (Exception e) {
            System.out.println("写入失败");
            e.printStackTrace();
        }

    }
}
