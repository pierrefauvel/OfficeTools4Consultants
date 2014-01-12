package com.pfa.ideationmemories;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.AbstractAction;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

public class StartingFrame extends JFrame {
	
	private Action action_go2;

	static String version;
	
	static
	{
		try
		{
			InputStream is_version = StartingFrame.class.getResourceAsStream("/version.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is_version));
			version = br.readLine();
			br.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}	
	
	public StartingFrame() throws IOException
	{
		super("Ideation Memories v"+version);
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

		
		/*
		List<String> lst_sources = new ArrayList<String>();
		String li=null;
		BufferedReader br = new BufferedReader(new InputStreamReader(StartingFrame.class.getResourceAsStream("sources_list.txt")));
		while( (li = br.readLine())!=null)
		{
			li = li.trim();
			if(li.length()>0)
			{
				lst_sources.add(li);
			}
		}
		*/
		
		final DefaultListModel dlm = new DefaultListModel();
		restoreFileList(dlm);
//		final JList<String> lst = new JList<String>(lst_sources.toArray(new String[]{}));
		final JList lst = new JList(dlm);
		
		Action action_add = new AbstractAction("Add...")
		{
			public void actionPerformed(ActionEvent e)
			{
				flagSourceListChanged();
				try
				{
					JFileChooser jfc = new JFileChooser()
					{
						public boolean isDirectorySelectionEnabled()
						{
							return true;
						}
					};
					
					jfc.setFileFilter(new FileFilter(){

						@Override
						public boolean accept(File arg0) {
							return arg0.isDirectory();
						}

						@Override
						public String getDescription() {
							return "Directories only";
						}
						
					});
//					jfc.setMultiSelectionEnabled(false);
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//					jfc.ensureFileIsVisible(new File("D:/Dropbox"));
					//jfc.showDialog(StartingFrame.this,"Select directories...");
//					jfc.showOpenDialog(StartingFrame.this);
					jfc.setAcceptAllFileFilterUsed(false);
//				    File[] f = jfc.getSelectedFiles();
//					if(f!=null)
//					{
//						for(File fi:f)
//						{
//							dlm.addElement(fi.getCanonicalPath());
//						};
//						storeFileList(dlm);
//					}
					if (jfc.showOpenDialog(StartingFrame.this) == JFileChooser.APPROVE_OPTION) {
						SourceItem si = new SourceItem(URLEncoder.encode(jfc.getSelectedFile().getCanonicalPath()));
						dlm.addElement(si);
						storeFileList(dlm);
					      }
					    else {
					     };
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}

		};
		
		Action action_remove = new AbstractAction("Remove...")
		{
			public void actionPerformed(ActionEvent e)
			{
				flagSourceListChanged();
				try
				{
					
					for (int i=0;i<lst.getModel().getSize();i++)
					{
						if(lst.isSelectedIndex(i))
						{
							SourceItem item = (SourceItem)lst.getModel().getElementAt(i);
							dlm.removeElement(item);
						};
						storeFileList(dlm);
					};
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		};

//v2		lst.setEnabled(false);
		
		Action action_go = new AbstractAction("Index and Search !")
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					List<File> lst_files = new ArrayList<File>();
					for (int i=0;i<lst.getModel().getSize();i++)
					{
//v2						if(lst.isSelectedIndex(i))
						{
							SourceItem item = (SourceItem)lst.getModel().getElementAt(i);
							String decoded = URLDecoder.decode(item.getEncodedURL());
							lst_files.add(new File(decoded));
						};
					};
					MainAnalyzer.main(lst_files.toArray(new File[]{}), new AnalysisLog(),100);
					flagIndexUpdated();
					StartingFrame.this.setVisible(false);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}
		};

//v2
		action_go2 = new AbstractAction("Search Only !")
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					MainAnalyzer.main_no_index(100);
					StartingFrame.this.setVisible(false);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}
		};
		
		restoreChangeState();
		
		JToolBar btb = new JToolBar(JToolBar.HORIZONTAL);
		btb.setFloatable(false);
		btb.add(action_add);
		btb.add(action_remove);
		btb.add(action_go);
		btb.add(action_go2);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		getContentPane().add(btb);
		getContentPane().add(lst);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void restoreChangeState() {
		try
		{
			if(!new File(getStateFilePath()).exists())
			{
				action_go2.setEnabled(false);
				return;
			}
			Properties p = new Properties();
			p.load(new FileInputStream(getStateFilePath()));
			String s = p.getProperty("source-list-changed-after-index-load");
			action_go2.setEnabled(! Boolean.parseBoolean(s));
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	protected void flagIndexUpdated() {
		try
		{
		Properties p = new Properties();
		p.put("source-list-changed-after-index-load",Boolean.toString(false));
		p.store(new FileOutputStream(getStateFilePath()),"");
		action_go2.setEnabled(! false);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	protected void flagSourceListChanged() {
		try
		{
		Properties p = new Properties();
		p.put("source-list-changed-after-index-load",Boolean.toString(true));
		p.store(new FileOutputStream(getStateFilePath()),"");
		action_go2.setEnabled(! true);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	private void storeFileList(DefaultListModel dlm) throws IOException {
		String home = System.getProperty("user.home");
//		System.out.println("Home="+home);
		File f = new File(home+File.separator+".ideation_memories.txt");
		PrintWriter pw = new PrintWriter(new FileWriter(f));
		for(Object s : dlm.toArray())
		{
			SourceItem si = (SourceItem)s;
			pw.println(si.getEncodedURL());
		}
		pw.close();
	}
	private void restoreFileList(DefaultListModel dlm) throws IOException {
		String home = System.getProperty("user.home");
//		System.out.println("Home="+home);
		File f = new File(home+File.separator+".ideation_memories.txt");
		if(f.exists())
		{
			dlm.removeAllElements();
			BufferedReader br = new BufferedReader(new FileReader(f));
			String l = null;
			while( (l=br.readLine())!=null)
			{
				SourceItem si = new SourceItem(l);
				dlm.addElement(si);
			};
			br.close();
		}
	}

	private String getStateFilePath() throws IOException {
		String home = System.getProperty("user.home");
		File f = new File(home+File.separator+".ideation_memories"+File.separator+"StartingFrame.state.properties");
		f.getParentFile().mkdirs();
		return f.getCanonicalPath();
	}


	public static File getLuceneDirectoryPath()
	{
		String home = System.getProperty("user.home");
		File f = new File(home+File.separator+".ideation_memories"+File.separator+"lucene");
		f.mkdirs();
		return f;
	}
	
	public static void main(String[] args) throws IOException
	{
		new StartingFrame().setVisible(true);
	}
	
	public static class SourceItem
	{
		private String _encodedUrl;
		public SourceItem(String encodedUrl)
		{
			_encodedUrl = encodedUrl;
		}
		public String getEncodedURL()
		{
			return _encodedUrl;
		}
		public String toString()
		{
			return URLDecoder.decode(_encodedUrl);
		}
	}
}
