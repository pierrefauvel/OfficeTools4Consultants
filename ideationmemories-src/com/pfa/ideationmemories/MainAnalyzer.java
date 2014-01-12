package com.pfa.ideationmemories;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.BoxLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.pfa.ideationmemories.FileHierarchyAnalyser.OutlineAndThumbnail;
import com.pfa.og.pivot.Outline;
import com.pfa.og.viewer.pivot.OutlinePane;
import com.pfa.og.viewer.powerpoint.PowerPointViewerPaneWithDelayedRendering;

public class MainAnalyzer {
	public static void main(File[] roots, final AnalysisLog analysis, final int count) throws NoSuchAlgorithmException, IOException, ParseException
	{
		final JFrame f=new JFrame("Analyser");

//		JDialog dlg = new JDialog(f,"Analysis...");
//		JList<File> lst = new JList<File>(roots);
//		dlg.getContentPane().setLayout(new BoxLayout(dlg.getContentPane(),BoxLayout.Y_AXIS));
//		dlg.getContentPane().add(lst);
//		dlg.pack();
//		dlg.setVisible(true);

		final StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
//v2		final Directory index = new RAMDirectory();
		final Directory index = FSDirectory.open(StartingFrame.getLuceneDirectoryPath());

		TableModel tm_log=null ;
		if(roots!=null)
		{
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			IndexWriter w = new IndexWriter(index, config);
			w.deleteAll();
			FileHierarchyAnalyser fha=new FileHierarchyAnalyser();
			for(File root: roots)
			{
				analysis.logPath("Analysing "+root.getCanonicalPath()+"...");
				fha.analyseFileHierarchy(root, w,analysis);
			}
	
			tm_log= analysis.flush();
			System.out.println("Analysis done, launching client.");
			w.close();
		}
		
//		dlg.setVisible(false);
		
		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(),BoxLayout.X_AXIS));
		final JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		f.getContentPane().add(sp);
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
		final JTextField txf = new JTextField();
		left.add(txf);
		txf.setMaximumSize(new Dimension(256,48));

		final JTable tbl = new JTable();
		final JTable tbl2 = new JTable();
		final JTable tbl3 = tm_log!=null ? new JTable(tm_log) : null;
		
		if(tbl3!=null) equipe(tbl3);

		StringBuffer sb=new StringBuffer();
		sb.append("<html>...*...:wildcard,<br/>");
		sb.append("..approx.~:approximate search,<br/>");
		sb.append("\"... ...\"~x:words with at most a distance of x,<br/>");
		sb.append("...^x ...: boost word by x<br/>");
		sb.append("+... ...:mandatory word,<br/>");
		sb.append("(... ...) AND ...<br/>");
		sb.append("(... ...) OR ...<br/>");
		sb.append("... NOT ...</html>");

		final JLabel _lbl = new JLabel(sb.toString());
		
		JButton btn = new JButton("OK");
		left.add(btn);
		{
			JTabbedPane tbb = new JTabbedPane();
//			left.add(new JScrollPane(tbl));
			tbb.add("Par contenu", new JScrollPane(tbl));
//			left.add(new JScrollPane(tbl2));
			tbb.add("Par chemin", new JScrollPane(tbl2));
			if(tbl3!=null) tbb.add("Chemins en erreur", new JScrollPane(tbl3));
			tbb.add("Syntaxe",_lbl);
			left.add(tbb);
		}
		
		sp.setLeftComponent(left);
		
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					Query q = new QueryParser(Version.LUCENE_35, "content", analyzer).parse(txf.getText());
					
					int hitsPerPage = count;
					IndexReader reader = IndexReader.open(index);
					IndexSearcher searcher = new IndexSearcher(reader);
					TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
					searcher.search(q, collector);
					ScoreDoc[] hits = collector.topDocs().scoreDocs;
					
					Object[][] o = new Object[hits.length][];
					String[] h = new String[]{
							"Title",
							"Root",
							"Extended Path",
//							"Extended Path-Tokenized",
							"Position in content",
					};
					
//					System.out.println("Found " + hits.length + " hits.");
					for(int i=0;i<hits.length;++i) {
					    int docId = hits[i].doc;
					    Document d = searcher.doc(docId);
					    o[i]=new Object[]{
					    		d.get("title"),
					    		d.get("root"),
					    		d.get("extended-path"),
//					    		d.get("extended-path-tokenized"),
					    		d.get("position")
					    };
					}
					
					tbl.setModel(new DefaultTableModel(o,h){
						public boolean isCellEditable(int r, int c)
						{
							return false;
						}
					});
					equipe(tbl);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
				try
				{
					Query q = new QueryParser(Version.LUCENE_35, "extended-path-tokenized", analyzer).parse(txf.getText());
					int hitsPerPage = count;
					IndexReader reader = IndexReader.open(index);
					IndexSearcher searcher = new IndexSearcher(reader);
					TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
					searcher.search(q, collector);
					ScoreDoc[] hits = collector.topDocs().scoreDocs;
					
					String[] h = new String[]{
							"Root",
							"Extended Path",
							"Extended Path Tokenized"
					};
					
//					System.out.println("Found " + hits.length + " hits.");

					Set<String> hs=new HashSet<String>();
					List<Object[]> lst = new ArrayList<Object[]>();
					
					for(int i=0;i<hits.length;++i) {
					    int docId = hits[i].doc;
					    Document d = searcher.doc(docId);
						String k = d.get("root")+" XXX "+d.get("extended-path");
						if(!hs.contains(k))
						{
							lst.add(new Object[]{
						    		d.get("root"),
						    		d.get("extended-path"),
						    		d.get("extended-path-tokenized")
						    });
							hs.add(k);
						}
					}
					
					tbl2.setModel(new DefaultTableModel(lst.toArray(new Object[][]{}),h){
						public boolean isCellEditable(int r, int c)
						{
							return false;
						}
					});
					equipe(tbl2);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}
			}
			);
		
		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				File root=null;
				if(!arg0.getValueIsAdjusting())
				{
					int idx = tbl.getSelectedRow();
					try
					{
						if(idx!=-1)
						{
							root = new File((String)tbl.getValueAt(idx,1));
							ExtendedPath ep = ExtendedPath.fromString((String)(tbl.getValueAt(idx,2)));
							Integer pos = Integer.parseInt((String)(tbl.getValueAt(idx,3)));

							if(ep.getExtension().compareTo("ppt")==0 || ep.getExtension().compareTo("pptx")==0)
							{
								InputStream is = FileHierarchyAnalyser.resolveInputStream(root, ep);
								PowerPointViewerPaneWithDelayedRendering ppvp = new PowerPointViewerPaneWithDelayedRendering(is);
								ppvp.setVisibleSlide(pos);
								sp.setRightComponent(ppvp);
							}
							else
							{
								OutlineAndThumbnail o = FileHierarchyAnalyser.resolveOutline(root, ep, pos);
								OutlinePane op = new OutlinePane(o.outline);
								if(o.thumbnail!=null)
								{
									JSplitPane spp = new JSplitPane();
									spp.setLeftComponent(op);
									spp.setRightComponent(new JScrollPane(new JLabel(o.thumbnail))); 
									sp.setRightComponent(spp);
								}
								else
								{
									sp.setRightComponent(op);
								}
							};
						}
					}
					catch(Throwable t)
					{
						t.printStackTrace();
						ExtendedPath ep = ExtendedPath.fromString((String)(tbl.getValueAt(idx,2)));
						analysis.log(root,ep, t);
					}
				}
			}
		});

		tbl2.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				File root=null;
				if(!arg0.getValueIsAdjusting())
				{
					int idx = tbl2.getSelectedRow();
					try
					{
						if(idx!=-1)
						{
							root = new File((String)tbl2.getValueAt(idx,0));
							ExtendedPath ep = ExtendedPath.fromString((String)(tbl2.getValueAt(idx,1)));

							if(ep.getExtension().compareTo("ppt")==0 || ep.getExtension().compareTo("pptx")==0)
							{
								InputStream is = FileHierarchyAnalyser.resolveInputStream(root, ep);
								PowerPointViewerPaneWithDelayedRendering ppvp = new PowerPointViewerPaneWithDelayedRendering(is);
//								ppvp.setVisibleSlide(pos);
								sp.setRightComponent(ppvp);
							}
							else
							{
								OutlineAndThumbnail o = FileHierarchyAnalyser.resolveOutline(root, ep, 0);
								OutlinePane op = new OutlinePane(o.outline);
								if(o.thumbnail!=null)
								{
									JSplitPane spp = new JSplitPane();
									spp.setLeftComponent(op);
									JLabel lblthb=new JLabel(o.thumbnail);
									lblthb.setMaximumSize(new Dimension(320,240));
									spp.setRightComponent(new JScrollPane(lblthb)); 
									sp.setRightComponent(spp);
								}
								else
								{
									sp.setRightComponent(op);
								}
							};
						}
					}
					catch(Throwable t)
					{
						t.printStackTrace();
						ExtendedPath ep = ExtendedPath.fromString((String)(tbl.getValueAt(idx,2)));
						analysis.log(root,ep, t);
					}
				}
			}
		});

		Dimension sizeOfScreen = Toolkit.getDefaultToolkit().getScreenSize();
		int offset = 64;
		
		f.setLocation(new Point(offset,offset));
		f.setSize(new Dimension(sizeOfScreen.width-2*offset,sizeOfScreen.height-2*offset));
		f.setVisible(true);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static void equipe(JTable tbl) {
		for(int i=0;i<tbl.getColumnCount();i++)
			tbl.getColumn(tbl.getColumnName(i)).setCellRenderer(new TooltipEnabledCellRenderer());
	}

	public static void main_no_index(int count) throws NoSuchAlgorithmException, IOException, ParseException {
		main(null,null,count);
	}
}
