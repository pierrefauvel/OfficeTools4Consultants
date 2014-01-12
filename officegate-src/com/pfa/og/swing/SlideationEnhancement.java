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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.pfa.og.stats.directory.SlidesStats;
import com.pfa.og.stats.xmind.XmindStats;
import com.pfa.og.swing.viewer.csv.CSVModel;
import com.pfa.og.transform.xmind2excel.Xmind2Excel2007;
import com.pfa.og.transform.xmind2impress.Xmind2Impress;
import com.pfa.og.transform.xmind2powerpoint.Xmind2Powerpoint;
import com.pfa.og.transform.xmind2word.Xmind2Word;

public class SlideationEnhancement {

	private static final String PROPERTIES_FILE__OFFICE_GATE4_SWING_PROPERTIES = ".OfficeGate4Swing.properties";
	private static final String PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE = "OfficeGate4Swing.selectXmin.selectedFile";
	private static final String PROPERTY__OFFICE_GATE4_SWING_SELECT_DIRECTORY_SELECTED_FILE = "OfficeGate4Swing.selectXmin.selectedDirectory";
	private static final String PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE_PARENT = "OfficeGate4Swing.selectXmin.selectedFileParent";

	private static File selectXmind(JFrame f) throws FileNotFoundException, IOException
	{
		JFileChooser fc = new JFileChooser();

		File fp=new File(System.getProperty("user.home")+File.separator+PROPERTIES_FILE__OFFICE_GATE4_SWING_PROPERTIES);
		if(fp.exists())
		{
			Properties p = new Properties();
			p.load(new FileInputStream(fp));
			String fparent = p.getProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE_PARENT);
			File ffp=new File(fparent);
			String selectedf = p.getProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE);
			
			File ffs=selectedf !=null ? new File(selectedf) : ffp;
		
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
			p.setProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE_PARENT, sf.getParentFile().getCanonicalPath());
			p.setProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE,sf.getCanonicalPath());
			FileOutputStream fos = new FileOutputStream(fp);
			p.store(new PrintStream(fos), "MAJ le "+new java.util.Date());
			fos.close();
			return sf;
		}
		else
			return null;
	}
	
	private static File selectDirectory(JFrame f) throws FileNotFoundException, IOException
	{
		JFileChooser fc = new JFileChooser();

		File fp=new File(System.getProperty("user.home")+File.separator+PROPERTIES_FILE__OFFICE_GATE4_SWING_PROPERTIES);
		if(fp.exists())
		{
			Properties p = new Properties();
			p.load(new FileInputStream(fp));
			String fparent = p.getProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE_PARENT);
			File ffp=new File(fparent);
			String selectedf = p.getProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_DIRECTORY_SELECTED_FILE);
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
				return arg0.isDirectory() ;
			}
	
			public String getDescription() {
				return "Directory contain Files";
			}
		});
		fc.setFileHidingEnabled(false);
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retour=fc.showOpenDialog(f);
		
		if(retour==JFileChooser.APPROVE_OPTION){
			File sf = fc.getSelectedFile();
			Properties p=new Properties();
			p.setProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_XMIND_SELECTED_FILE_PARENT, sf.getParentFile().getCanonicalPath());
			p.setProperty(PROPERTY__OFFICE_GATE4_SWING_SELECT_DIRECTORY_SELECTED_FILE,sf.getCanonicalPath());
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
		InputStream is_version = SlideationEnhancement.class.getResourceAsStream("/version.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is_version));
		String version = br.readLine();
		br.close();
		
		final JFrame f=new JFrame("Slideation Enhancement v"+version);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setIconImage(ImageIO.read(SlideationEnhancement.class.getResourceAsStream("/icons/Xmind.png")));
		
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS));
		
		final JTextArea txa=new JTextArea(3,80);
		txa.setEditable(false);
		
		JButton btn_csv2007 = new JButton(new ImageIcon(ImageIO.read(SlideationEnhancement.class.getResourceAsStream("/icons/Xmind.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_csv2007.setToolTipText("Office:Xmind => CSV 2007 (stats by flag)");
		JButton btn_ppt2007 = new JButton(new ImageIcon(ImageIO.read(SlideationEnhancement.class.getResourceAsStream("/icons/Dossier.png")).getScaledInstance(128,-1,Image.SCALE_SMOOTH)));
		btn_ppt2007.setToolTipText("Office:Directory of Powerpoint => CSV 2007 (stats by flag)");

		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.X_AXIS));
		
		pnl.add(btn_csv2007);
		pnl.add(btn_ppt2007);
		f.getContentPane().add(pnl);
		
		final JTabbedPane tbb = new JTabbedPane();
		f.getContentPane().add(tbb);
		
		f.getContentPane().add(new JScrollPane(txa));
		f.pack();
		f.setVisible(true);
		
		btn_csv2007.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
					File fxmind = selectXmind(f);
					if(fxmind!=null)
					{
						CSVModel[] fs = XmindStats.generateDocs(fxmind);
						StringBuffer sb=new StringBuffer();
						tbb.removeAll();
						for(CSVModel fi : fs)
						{
							sb.append("Generated "+fi.getFile().getCanonicalPath()+"\n");
							tbb.add(fi.getFile().getName(),fi.toSwingComponent());
						}
						txa.setText(sb.toString());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
					StackTraceFrame.showThrowable(e);
				};
			}
		});
		btn_ppt2007.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
					File fdir = selectDirectory(f);
					System.out.println("File "+fdir.getCanonicalPath());
					if(fdir!=null)
					{
						System.out.println("File "+fdir.getCanonicalPath());
						CSVModel fs = SlidesStats.countSlidesInDirector(fdir);
						StringBuffer sb=new StringBuffer();
						sb.append("Generated "+fs.getFile().getCanonicalPath()+"\n");
						txa.setText(sb.toString());
						tbb.removeAll();
						tbb.add(fs.getFile().getName(),fs.toSwingComponent());
					}
					else
					{
						txa.setText("Cancelled by user");
					}
				} catch (Throwable e) {
					StackTraceFrame.showThrowable(e);
				};
			}
		});
	}
		
}
