package com.pfa.og.swing;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StackTraceFrame extends JFrame {

	public static void showThrowable(Throwable t){
		JFrame f = new JFrame("Local Stack Trace");
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS));
		StringBuffer sb=new StringBuffer();
		sb.append(t.getClass().getName());
		sb.append(": ");
		sb.append(t.getMessage());
		sb.append("\n");
		for(StackTraceElement ste:t.getStackTrace())
		{
			sb.append("\tat ");
			sb.append(ste.getClassName());
			sb.append(".");
			sb.append(ste.getMethodName());
			sb.append("(");
			sb.append(ste.getFileName());
			sb.append(":");
			sb.append(ste.getLineNumber());
			sb.append(")\n");
		}
		Throwable ti = t.getCause();
		while(ti!=null)
		{
			sb.append("Caused by");
			sb.append(ti.getClass().getName());
			sb.append(": ");
			sb.append(ti.getMessage());
			sb.append("\n");
			for(StackTraceElement ste:ti.getStackTrace())
			{
				sb.append("\tat ");
				sb.append(ste.getClassName());
				sb.append(".");
				sb.append(ste.getMethodName());
				sb.append("(");
				sb.append(ste.getFileName());
				sb.append(":");
				sb.append(ste.getLineNumber());
				sb.append(")\n");
			}
			ti=ti.getCause();
		};
		JTextArea txa = new JTextArea(sb.toString(),20,80);
		f.getContentPane().add(new JScrollPane(txa));
		f.pack();
		f.setVisible(true);
	}
	/*
	public static void showThrowable(JSONObject obj) {
		System.out.println("SHOWING THROWABLE");
		JFrame f = new JFrame("Remote Stack Trace");
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS));
		JSONArray a = obj.getJSONArray("stackTrace");
		StringBuffer sb=new StringBuffer();
		sb.append(obj.getString("classname"));
		sb.append(": ");
		if(obj.has("message")) sb.append(obj.getString("message"));
		sb.append("\n");
		for(int i=0;i<a.size();i++)
		{
			JSONObject obji = a.getJSONObject(i);
			sb.append("\tat ");
			sb.append(obji.getString("classname"));
			sb.append(".");
			sb.append(obji.getString("method"));
			sb.append("(");
			if(obji.has("file"))sb.append(obji.getString("file"));
			sb.append(":");
			sb.append(obji.getString("line"));
			sb.append(")\n");
		}
		while(obj.has("cause"))
		{
			sb.append("Caused by ");
			JSONObject obji=obj.getJSONObject("cause");
			sb.append(obji.getString("classname"));
			sb.append(": ");
			if(obji.has("message")) sb.append(obji.getString("message"));
			sb.append("\n");
			JSONArray ai = obji.getJSONArray("stackTrace");
			for(int i=0;i<ai.size();i++)
			{
				JSONObject objii = ai.getJSONObject(i);
				sb.append("\tat ");
				sb.append(objii.getString("classname"));
				sb.append(".");
				sb.append(objii.getString("method"));
				sb.append("(");
				if(objii.has("file"))sb.append(objii.getString("file"));
				sb.append(":");
				sb.append(objii.getString("line"));
				sb.append(")\n");
			}
			obj=obji;
		}
		JTextArea txa = new JTextArea(sb.toString(),20,80);
		f.getContentPane().add(new JScrollPane(txa));
		f.pack();
		f.setVisible(true);
	}
*/
	
}
