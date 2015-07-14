/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessCharts;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
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
    XSSFRow row;
    XSSFWorkbook workbook;
    private FileInputStream fis; 
    private int totalrow;
    
    public void initExcel(String fileName) throws Exception{
        fis = new FileInputStream(new File("C:\\Users\\ssingh2\\Documents\\NetBeansProjects\\project_manage_dashboard\\src\\java\\businessCharts\\" + fileName));
        workbook = new XSSFWorkbook(fis);
    }
    
    private static boolean isContain(Cell cell,String keyword){
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
    
    private static int getColNo(String colName,XSSFRow row){
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
        System.out.println("kee" + getcellNokey +  " "+ getcellNo);
        while(rowIterator.hasNext()){
            totalrow++;
            row = (XSSFRow)rowIterator.next();
            Cell cell = row.getCell(getcellNo); 
            Cell cellkey = row.getCell(getcellNokey);
            try{
                if(isContain(cell, keyword))
                    notCloned++;
                if(isContain(cell, keyword) && isContain(cell, "clone"))
                    countCells++;
            }
            //System.out.println();
            catch(NullPointerException ex){
                continue;
            }
        }
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(countCells, notCloned); 
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
    
    public static void main(String args[]){
        Pair<Integer,Integer> cols;
        try {
         //   InsertBugDb.putData();
            BugsListAssignee b = new BugsListAssignee();
            b.putAssigneeData();
        } catch (Exception ex) {
            Logger.getLogger(readExcell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}