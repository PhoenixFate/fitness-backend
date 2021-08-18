package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: 灵枢
 * Date: 2018/12/05
 * Time: 17:21
 * Description:读取Excel数据
 */
public class ExcelData {
    private XSSFSheet sheet;

    /**
     * 构造函数，初始化excel数据
     *
     * @param filePath  excel路径
     * @param sheetName sheet表名
     */
    public ExcelData(String filePath, String sheetName) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据行和列的索引获取单元格的数据
     *
     * @param row
     * @param column
     * @return
     */
    public String getExcelDateByIndex(int row, int column) {
        XSSFRow row1 = sheet.getRow(row);
        String cell = row1.getCell(column).toString();
        return cell;
    }

    /**
     * 根据某一列值为“******”的这一行，来获取该行第x列的值
     *
     * @param caseName
     * @param currentColumn 当前单元格列的索引
     * @param targetColumn  目标单元格列的索引
     * @return
     */
    public String getCellByCaseName(String caseName, int currentColumn, int targetColumn) {
        String operateSteps = "";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            String cell = row.getCell(currentColumn).toString();
            if (cell.equals(caseName)) {
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    //打印excel数据
    public void readExcelData() {
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columns; j++) {
                XSSFCell cell1 = row.getCell(j);
                if (cell1 != null) {
                    String cell = row.getCell(j).toString();
                    System.out.print(cell + "\t");
                }
            }
            System.out.println("");
        }
    }

    //打印excel数据
    public List<CustomerEntity> importCustomer() {
        //获取行数
        List<CustomerEntity> customerEntityList = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                continue;
            }
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            CustomerEntity customerEntity = new CustomerEntity();
            for (int j = 0; j < columns; j++) {
                XSSFCell cell1 = row.getCell(j);
                if (j == 1) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerEntity.setName(cell);
                    }
                } else if (j == 2) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        if (cell.equals("男")) {
                            customerEntity.setGender(1);
                        } else {
                            customerEntity.setGender(2);
                        }
                    }
                } else if (j == 3) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerEntity.setMobile(cell.substring(1));
                    }
                } else if (j == 9) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerEntity.setCoachName(cell);
                    }
                }
            }
            System.out.println("");
            customerEntityList.add(customerEntity);

        }
        System.out.println(customerEntityList);
        return customerEntityList;
    }

    //打印excel数据
    public List<CustomerContractEntity> importContract() throws ParseException {
        //获取行数
        List<CustomerContractEntity> customerContractList = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                continue;
            }
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            System.out.println("columns: " + columns);
            CustomerContractEntity customerContractEntity = new CustomerContractEntity();
            for (int j = 0; j < columns; j++) {
                XSSFCell cell1 = row.getCell(j);
                if (j == 2) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setCustomerName(cell);
                    }
                } else if (j == 4) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setMobile(cell.substring(1));
                    }
                } else if (j == 7) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setContractType(cell);
                    }
                } else if (j == 8) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setContractName("老会员卡" + "-" + customerContractEntity.getCustomerName() + "-" + cell);
                    }
                } else if (j == 11) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setTotalMoney(cell);
                        customerContractEntity.setPayMoney(cell);
                    }
                } else if (j == 13) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setContractStatus(cell);
                    }
                } else if (j == 14) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        String replace = cell.replace('天', ' ');
                        String replace1 = replace.replace('次', ' ');
                        System.out.print(cell + "\t");
                        customerContractEntity.setTotalRights(replace1.trim());
                    }
                } else if (j == 16) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        String replace = cell.replace('天', ' ');
                        String replace1 = replace.replace('次', ' ');
                        System.out.print(cell + "\t");
                        customerContractEntity.setFreeDays(Integer.parseInt(replace1.trim()));
                    }
                }else if (j == 20) {
                    System.out.println("激活时间: ");
                    System.out.println(row.getCell(j));
                    if (cell1 != null) {
                        Date date = row.getCell(j).getDateCellValue();
                        customerContractEntity.setActiveTime(date);
                    }
                } else if (j == 23) {
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setSalesmanName(cell);
                    }
                } else if (j == 25) {
                    if (cell1 != null) {
                        Date date = row.getCell(j).getDateCellValue();
                        System.out.print(date + "\t");
                        customerContractEntity.setOperationTime(date);
                    }
                }
            }
            customerContractList.add(customerContractEntity);
        }
        return customerContractList;
    }

    //打印excel数据
    public List<CustomerContractEntity> importContract2() throws ParseException {
        //获取行数
        List<CustomerContractEntity> customerContractList = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                continue;
            }
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            System.out.println("columns: " + columns);
            CustomerContractEntity customerContractEntity = new CustomerContractEntity();
            for (int j = 0; j < columns; j++) {
                XSSFCell cell1 = row.getCell(j);
                if (j == 2) {
                    //姓名
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setCustomerName(cell);
                    }
                } else if (j == 4) {
                    //手机
                    if (cell1 != null) {
                        String cell = row.getCell(j).toString();
                        System.out.print(cell + "\t");
                        customerContractEntity.setMobile(cell.substring(1));
                    }
                }
            }
            customerContractList.add(customerContractEntity);
        }
        return customerContractList;
    }

    //测试方法
    public static void main(String[] args) {
        ExcelData sheet1 = new ExcelData("customer.xlsx", "sheet1");

        sheet1.importCustomer();
        //获取第二行第4列
        String cell2 = sheet1.getExcelDateByIndex(1, 3);
        //根据第3列值为“customer23”的这一行，来获取该行第2列的值
        // String cell3 = sheet1.getCellByCaseName("customer23", 2, 1);
        // System.out.println(cell2);
        // System.out.println(cell3);
    }
}


