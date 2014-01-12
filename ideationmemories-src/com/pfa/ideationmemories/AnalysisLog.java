package com.pfa.ideationmemories;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class AnalysisLog {

	private PrintWriter _pw;
	
	static final String[] HEADERS = new String[]{
		"Root",
		"Extended Path",
		"Class",
		"Message"
	};
	
	private List<Object[]> _lst;
	
	public AnalysisLog()
	{
		try {
			_pw=new PrintWriter(new FileWriter("IdeationMemorites Analysis.log"));
			_lst = new ArrayList<Object[]>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logPath(String string) {
		System.out.println("["+string+"]");
		_pw.println("["+string+"]");
	}

	public void log(File root,ExtendedPath path, Throwable problem) {
		String summary = "Root="+root+";Path="+path+"; Class="+problem.getClass()+"; Message="+problem.getMessage();
		_lst.add(new Object[]{root,path, problem.getClass(), problem.getMessage()});
		System.out.println(summary);
		_pw.println(summary);
		dump(problem);
	}

	private void dump(Throwable problem)
	{
		for(StackTraceElement e : problem.getStackTrace())
		{
			_pw.println("\t"+e.getClassName()+":"+e.getLineNumber()+" "+e.getMethodName());
		}
		if(problem.getCause()!=null && problem.getCause()!=problem)
		{
			_pw.println("Caused by");
			dump(problem.getCause());
		}
	}
	
	public TableModel flush() {
		_pw.flush();
		return new DefaultTableModel(_lst.toArray(new Object[][]{}), HEADERS){
			public boolean isCellEditable(int c, int r)
			{
				return false;
			}
		};
	}

}
