/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author SUDHANSHU
 */

public class readExcell {
//    XSSFRow row;
    XSSFWorkbook workbook;
    private FileInputStream fis; 
    private int totalrow;
    
    public void initExcel(String fileName) throws Exception{
        fis = new FileInputStream(new File("C:\\Users\\Public\\Documents\\Dashboard_External\\" + fileName));
        workbook = new XSSFWorkbook(fis);
    }
    
    private boolean isContain(Cell cell,String keyword) throws Exception{
        try{
            try{
                if(cell.getStringCellValue().contains(keyword) || cell.getStringCellValue().startsWith(keyword) || cell.getStringCellValue().endsWith(keyword))
                    return true;
                return false;
            }
            catch(NullPointerException ex){
                return false;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    //returns string array if you just pass array of keywords to be excuted it will return 
    //string array as it is.
    public TreeMap<Double,String> getcontentList(String colName,String colName2) throws Exception{
        TreeMap< Double,String > treemap = new TreeMap< Double,String >();
        initExcel("excel.xlsx");
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = spreadsheet.iterator();
        XSSFRow row = (XSSFRow)rowIterator.next();
        int setColno = getColNo(colName,row);
        int setColno2 = getColNo(colName2,row);
        while(rowIterator.hasNext()){
            row = (XSSFRow) rowIterator.next();
            Cell cell = row.getCell(setColno);
            Cell cell2 = row.getCell(setColno2);
            try{
                treemap.put(cell.getNumericCellValue(), new SimpleDateFormat("yyyy-MM-dd").format(cell2.getDateCellValue()));
            }
            catch(Exception ex){
                treemap.put(cell.getNumericCellValue(),"nodate");
            }
        }
        return treemap;
    }
    
    public String[] queryByRowKey(String[] keyStringArray,String colName,String resultColName) throws Exception{
        String[] resultStringArray = new String[keyStringArray.length];
        initExcel("excel.xlsx");
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        
        Iterator<Row> rowIterator = spreadsheet.iterator();
        XSSFRow row = (XSSFRow)rowIterator.next();
        int getcellNo = getColNo(colName,row);
        int getReturncellKey = getColNo(resultColName,row);
        System.out.println("Inside queryByRowKey" + " CellNocolname" + getcellNo + "ReturnCellColName" + getReturncellKey);
        
        for(int i = 0;i < keyStringArray.length;i++){
            System.out.println("resultstring array elements" + keyStringArray[i]);
            rowIterator = spreadsheet.iterator();
            row = (XSSFRow)rowIterator.next();
            int flag = 0;
            while(rowIterator.hasNext()){
                row = (XSSFRow) rowIterator.next();
                Cell cell = row.getCell(getcellNo);
                Cell returnCell = row.getCell(getReturncellKey);
           //    System.out.println("Date is " + returnCell.getDateCellValue());
             //   try{
                    if(cell.getNumericCellValue() == Double.parseDouble(keyStringArray[i])){
                        flag = 1;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //    System.out.println("Date is " + sdf.format(returnCell.getDateCellValue()));
                        try{
                            if(returnCell.getDateCellValue() != null)
                                resultStringArray[i] = sdf.format(returnCell.getDateCellValue());
                        }
                        catch(Exception ex){
                            resultStringArray[i] = "No Deadline Available";
                        }
                    }
              //  }
               // catch(Exception ex){
              //      continue;
                //}
            }
            if(flag == 0){
                resultStringArray[i] = "Bug Not Available/Resolved";
                flag = 0;
            }
            else
                flag = 0;
        }
        
        return resultStringArray;
    }
        
    
    
    //gives col no of the column name which is passed through it just give first row
    //and the name of the column it will give you index of column to be done
    private int getColNo(String colName,XSSFRow row){
        Iterator<Cell> topCell = row.cellIterator();
        int cellNo = 0;
        colName = colName.toLowerCase();
        while(topCell.hasNext()){
            Cell cell = topCell.next();
            if(cell.getStringCellValue().toLowerCase().contentEquals(colName)){
                return cellNo;
            }
            cellNo++;
        }
        return -1;
    }
    
    
    public Pair<Integer,Integer> countColKeywordClone(String keyword,String colName) throws Exception{
        initExcel("excel.xlsx");
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        
        Iterator<Row> rowIterator = spreadsheet.iterator();
        XSSFRow row = (XSSFRow)rowIterator.next();
        int getcellNo = getColNo(colName,row);
        int getcellNokey = getColNo("keywords", row);
        int countCells = 0;
        int notCloned = 0;
        System.out.println("CellNokey" + getcellNokey +  "getqurycell"+ getcellNo);
        while(rowIterator.hasNext()){
            totalrow++;
            row = (XSSFRow)rowIterator.next();
            Cell cell = row.getCell(getcellNo); 
            Cell cellkey = row.getCell(getcellNokey);
            try{
                if(isContain(cell, keyword))
                    notCloned++;
                if(isContain(cell, keyword) && isContain(cellkey, "clone"))
                    countCells++;
            }
            //System.out.println();
            catch(NullPointerException ex){
                continue;
            }
        }
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(countCells, notCloned - countCells); 
        System.out.println(totalrow);
        return pair;
    }
    
    public int getTotalrows() throws Exception{
       initExcel("excel.xlsx");
       return totalrow = workbook.getSheetAt(0).getLastRowNum();
    }
    
    public TreeMap< String,TreeMap< String,Pair< Integer, Integer> > > groupBycount(String par1,String par2) throws Exception{
        //so that mapping could be done in O(n*log n*log n)
        initExcel("excel.xlsx");
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = spreadsheet.iterator();
        XSSFRow tempRow = (XSSFRow) rowIterator.next();
        int cell_branch = getColNo(par1, tempRow);
        int cell_assignee = getColNo(par2, tempRow);
        int cell_keyword = getColNo("Keywords", tempRow);
        System.out.println("columns no" + cell_assignee + " " + cell_branch + " " + cell_keyword);
        TreeMap< String,TreeMap< String,Pair< Integer, Integer> > >list = new TreeMap<  >();
        while(rowIterator.hasNext()){
            XSSFRow row = (XSSFRow) rowIterator.next();
            Cell Branchcell = row.getCell(cell_branch);
            Cell Assigneecell = row.getCell(cell_assignee);
            if(list.containsKey(Branchcell.getStringCellValue())){
                if(list.get(Branchcell.getStringCellValue()).containsKey(Assigneecell.getStringCellValue())){
                    try{
                        Cell keycell =  row.getCell(cell_keyword);
                        if(isContain(keycell, "clone")){
                            int key = list.get(Branchcell.getStringCellValue()).get(Assigneecell.getStringCellValue()).getKey();
                            int value = list.get(Branchcell.getStringCellValue()).get(Assigneecell.getStringCellValue()).getValue();
                            //System.out.println("key->" + key);
                            key++;
                            list.get(Branchcell.getStringCellValue()).put(Assigneecell.getStringCellValue(),new Pair<>(key,value));
                        }
                        else{
                            int key = list.get(Branchcell.getStringCellValue()).get(Assigneecell.getStringCellValue()).getKey();
                            int value = list.get(Branchcell.getStringCellValue()).get(Assigneecell.getStringCellValue()).getValue();
                            //System.out.println("value->" + value);
                            value++;
                            list.get(Branchcell.getStringCellValue()).put(Assigneecell.getStringCellValue(),new Pair<>(key,value));
                        }
                    }
                    catch(NoSuchElementException ex){
                        int key = list.get(Branchcell.getStringCellValue()).get(Assigneecell.getStringCellValue()).getKey();
                        int value = list.get(Branchcell.getStringCellValue()).get(Assigneecell.getStringCellValue()).getValue();
                        value++;
                        list.get(Branchcell.getStringCellValue()).put(Assigneecell.getStringCellValue(),new Pair<>(key,value));
                    }
                }
                else{
                    try{
                        Cell keycell =  row.getCell(cell_keyword);
                        if(isContain(keycell, "clone"))
                            list.get(Branchcell.getStringCellValue()).put(Assigneecell.getStringCellValue(),new Pair<>(1,0));
                        else
                            list.get(Branchcell.getStringCellValue()).put(Assigneecell.getStringCellValue(),new Pair<>(0,1));
                    }
                    catch(NoSuchElementException ex){
                        list.get(Branchcell.getStringCellValue()).put(Assigneecell.getStringCellValue(),new Pair<>(0,1));
                    }
                }
            }
            else{
                if(isContain(row.getCell(cell_keyword), "clone")){
                    TreeMap< String,Pair<Integer,Integer> > tp = new TreeMap<>();
                    tp.put(Assigneecell.getStringCellValue(),new Pair<>(1,0));
                    list.put(Branchcell.getStringCellValue(),tp);
                }
                else{
                    TreeMap< String,Pair<Integer,Integer> > tp = new TreeMap<>();
                    tp.put(Assigneecell.getStringCellValue(),new Pair<>(0,1));
                    list.put(Branchcell.getStringCellValue(),tp);
                }
            }
        }
        return list;
    }
    
//    public static void main(String args[]){
//        Pair<Integer,Integer> cols;
//        try {
//            jsontreegenerator jsg = new jsontreegenerator();
//            jsg.treeGenerate();
//        } catch (Exception ex) {
//            Logger.getLogger(readExcell.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}