package com.pfa.og.to.excel2007;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.pivot.Outline;

public class Outline2Excel2007 {

	private XSSFWorkbook wb;
	private File fout;
	
	public Outline2Excel2007(File fout) {
		wb = new XSSFWorkbook();
		this.fout = fout;
	}

	public void add(Outline i) {
		XSSFSheet sh = wb.createSheet(i.getText());
		int k=0;
		for (Outline j : i.getChildren())
		{
			Row r = sh.createRow(k);
			r.createCell(0).setCellValue(j.getText());
			add(sh,k+1,1,j);
			k+=count(j);
		}
	}

	private void add(XSSFSheet sh,int row, int col, Outline j) {
		sh.createRow(row).createCell(col).setCellValue(j.getText());
		int l=row+1;
		for(Outline k : j.getChildren())
		{
			Row r = sh.createRow(l);
			add(sh,l,col+1,k);
			l+=count(k);
		}
	}

	private static int count(Outline j) {
		int s = 1;
		for( Outline i : j.getChildren())
		{
			s+= count(i);
		};
		return s;
	}

	public void flush() throws IOException {
		OutputStream os=new FileOutputStream(fout);
		wb.write(os);
		os.flush();
	}

}
