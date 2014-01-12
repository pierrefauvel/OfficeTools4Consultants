package com.pfa.og.swing;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;
import java.util.zip.ZipException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.from.xmind.Sheet;
import com.pfa.og.from.xmind.XmindReader;
import com.pfa.og.from.xmind.XmindToOutline;
import com.pfa.og.pivot.Outline;
import com.pfa.og.stats.xmind.XmindStats;
import com.pfa.og.swing.viewer.pivot.OutlinePane;
import com.pfa.og.transform.xmind2excel.Xmind2Excel2007;
import com.pfa.og.transform.xmind2impress.Xmind2Impress;
import com.pfa.og.transform.xmind2powerpoint.Xmind2Powerpoint;
import com.pfa.og.transform.xmind2word.Xmind2Word;

public class IdeationExport {

	private static final String PROPERTIES_FILE__OFFICE_GATE4_SWING_PROPERTIES = ".OfficeGate4Swing.properties";
	private static final String PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIN_SELECTED_FILE = "OfficeGate4Swing.selectXmin.selectedFile";
	private static final String PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIN_SELECTED_FILE_PARENT = "OfficeGate4Swing.selectXmin.selectedFileParent";

	private static File selectXmind(JFrame f) throws FileNotFoundException, IOException
	{
		JFileChooser fc = new JFileChooser();

		File fp=new File(System.getProperty("user.home")+File.separator+PROPERTIES_FILE__OFFICE_GATE4_SWING_PROPERTIES);
		if(fp.exists())
		{
			Properties p = new Properties();
			p.load(new FileInputStream(fp));
			String fparent = p.getProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIN_SELECTED_FILE_PARENT);
			File ffp=new File(fparent);
			String selectedf = p.getProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIN_SELECTED_FILE);
			File ffs;
			if(selectedf!=null)
				ffs=new File(selectedf);
			else
				ffs=ffp;
			fc.setCurrentDirectory(ffp);
			fc.setSelectedFile(ffs);
		}
		
		fc.setFileFilter(new javax.swing.filechooser.FileFilter(){
			public boolean accept(File arg0) {
				return arg0.isDirectory() || arg0.getName().endsWith(".xmind");
			}
	
			public String getDescription() {
				return "XMind Files";
			}
		});
		fc.setFileHidingEnabled(false);
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int retour=fc.showOpenDialog(f);
		
		if(retour==JFileChooser.APPROVE_OPTION){
			File sf = fc.getSelectedFile();
			Properties p=new Properties();
			p.setProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIN_SELECTED_FILE_PARENT, sf.getParentFile().getCanonicalPath());
			p.setProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIN_SELECTED_FILE,sf.getCanonicalPath());
			FileOutputStream fos = new FileOutputStream(fp);
			p.store(new PrintStream(fos), "MAJ le "+new java.util.Date());
			fos.close();
			return sf;
		}
		else
			return null;
	}
	
	private static String dump(Throwable t)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("An error occured\n");
		sb.append("Class:"+t.getClass()+"\n");
		sb.append("Message:"+t.getMessage()+"\n");
		for(StackTraceElement ste: t.getStackTrace())
		{
			sb.append(ste.getFileName()+":"+ste.getLineNumber()+" "+ste.getMethodName()+"\n");
		};
		if(t.getCause()!=null && t.getCause()!=t)
		{
			sb.append("Caused by\n");
			sb.append(dump(t.getCause()));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException
	{
		InputStream is_version = IdeationExport.class.getResourceAsStream("/version.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is_version));
		String version = br.readLine();
		br.close();
		
		final JFrame f=new JFrame("Ideation Export v"+version);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setIconImage(ImageIO.read(IdeationExport.class.getResourceAsStream("/icons/Xmind.png")));
		
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS));
		
		final JTextArea txa=new JTextArea(3,80);
		txa.setEditable(false);
		
		
		
		final JButton btn_word97 = new JButton(new ImageIcon(ImageIO.read(IdeationExport.class.getResourceAsStream("/icons/Word.gif")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_word97.setToolTipText("Office:Word 97, exportable to Office:PowerPoint 97");
		btn_word97.setEnabled(false);
		final JButton btn_impress32 = new JButton(new ImageIcon(ImageIO.read(IdeationExport.class.getResourceAsStream("/icons/Impress.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_impress32.setToolTipText("OpenOffice/Impress 3.2");
		btn_impress32.setEnabled(false);
		final JButton btn_powerpoint2007 = new JButton(new ImageIcon(ImageIO.read(IdeationExport.class.getResourceAsStream("/icons/Powerpoint.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_powerpoint2007.setToolTipText("Office:PowerPoint 2007");
		btn_powerpoint2007.setEnabled(false);
		final JButton btn_excel2007 = new JButton(new ImageIcon(ImageIO.read(IdeationExport.class.getResourceAsStream("/icons/Excel.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_excel2007.setToolTipText("Office:Excel 2007");
		btn_excel2007.setEnabled(false);
//		JButton btn_csv2007 = new JButton(new ImageIcon(ImageIO.read(OfficeGate4Swing.class.getResourceAsStream("/icons/Csv.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
//		btn_csv2007.setToolTipText("Office:CSV 2007 (stats by flag)");
		//		JButton btn_confluence = new JButton("<html>... to <b>Confluence Markup</b></html>");

		JPanel pnl_xmind = new JPanel();
		pnl_xmind.setLayout(new BoxLayout(pnl_xmind,BoxLayout.X_AXIS));
		
		final File[] fxmind = new File[1];
		
		final JLabel lbl_select_xmind = new JLabel("-");
		final JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
		JButton btn_select_xmind =new JButton(new ImageIcon(ImageIO.read(IdeationExport.class.getResourceAsStream("/icons/Xmind.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_select_xmind.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					container.removeAll();
					fxmind[0] = selectXmind(f);
					lbl_select_xmind.setText(fxmind[0].getName());
					JTabbedPane tbb = new JTabbedPane();
					XmindReader xr=new XmindReader();
					Sheet[] shs = xr.read(fxmind[0]);
					for(Sheet sh : shs)
					{
						Outline o = new XmindToOutline().convert(sh);
						OutlinePane op = new OutlinePane(o);
						tbb.add(sh.getLabel(),op);
					}
					container.add(tbb);
					btn_excel2007.setEnabled(true);
					btn_impress32.setEnabled(true);
					btn_powerpoint2007.setEnabled(true);
					btn_word97.setEnabled(true);
				}
				catch(Throwable t)
				{
					StackTraceFrame.showThrowable(t);
				}
			}
		});
		pnl_xmind.add(btn_select_xmind);
		pnl_xmind.add(lbl_select_xmind);
		pnl_xmind.add(container);
		
		f.getContentPane().add(pnl_xmind);
		
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.X_AXIS));
		
		pnl.add(btn_word97);
		pnl.add(btn_impress32);
		pnl.add(btn_powerpoint2007);
		pnl.add(btn_excel2007);
//		pnl.add(btn_csv2007);
//		pnl.add(btn_confluence);
		f.getContentPane().add(pnl);
		f.getContentPane().add(new JScrollPane(txa));
		f.pack();
		f.setVisible(true);
		
		btn_word97.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
//					File fxmind = selectXmind(f);
					if(fxmind[0]!=null)
					{
						String[] fs = Xmind2Word.generateDocs(fxmind[0]);
						StringBuffer sb=new StringBuffer();
						for(String fi : fs)
						{
							sb.append("Generated "+fi+"\n");
						}
						txa.setText(sb.toString());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
//					txa.setText(dump(e));
					StackTraceFrame.showThrowable(e);
				};
			}
		});

		btn_impress32.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
//					File fxmind = selectXmind(f);
					if(fxmind[0]!=null)
					{
						String[] fs = Xmind2Impress.generateDocs(fxmind[0]);
						StringBuffer sb=new StringBuffer();
						for(String fi : fs)
						{
							sb.append("Generated "+fi+"\n");
						}
						txa.setText(sb.toString());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
//					txa.setText(dump(e));
					StackTraceFrame.showThrowable(e);
				};
			}
		});

		btn_powerpoint2007.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
//					File fxmind = selectXmind(f);
					if(fxmind[0]!=null)
					{
						String[] fs = Xmind2Powerpoint.generateDocs(fxmind[0]);
						StringBuffer sb=new StringBuffer();
						for(String fi : fs)
						{
							sb.append("Generated "+fi+"\n");
						}
						txa.setText(sb.toString());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
//					txa.setText(dump(e));
					StackTraceFrame.showThrowable(e);
				};
			}
		});
		
		btn_excel2007.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
//					File fxmind = selectXmind(f);
					if(fxmind[0]!=null)
					{
						String fs = Xmind2Excel2007.toCSV(fxmind[0]);
						StringBuffer sb=new StringBuffer();
						sb.append("Generated "+fs+"\n");
						txa.setText(sb.toString());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
//					txa.setText(dump(e));
					StackTraceFrame.showThrowable(e);
				};
			}
		});
/*
		btn_csv2007.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
					File fxmind = selectXmind(f);
					if(fxmind!=null)
					{
						File[] fs = XmindStats.generateDocs(fxmind);
						StringBuffer sb=new StringBuffer();
						for(File fi : fs)
							sb.append("Generated "+fi.getCanonicalPath()+"\n");
						txa.setText(sb.toString());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
					txa.setText(dump(e));
				};
			}
		});
		*/
//		btn_word97.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent evt)
//			{
//				try {
//					File fxmind = selectXmind(f);
//					if(fxmind!=null)
//					{
//						String[] fs = Xmind2Word.generateDocs(fxmind);
//						StringBuffer sb=new StringBuffer();
//						for(String fi : fs)
//						{
//							sb.append("Generated "+fi+"\n");
//						}
//						txa.setText(sb.toString());
//					}
//					else
//					{
//						txa.setText("Cancelled by user");
//					}
//				} catch (Throwable e) {
//					txa.setText(dump(e));
//				};
//			}
//		});
	}
		
}
