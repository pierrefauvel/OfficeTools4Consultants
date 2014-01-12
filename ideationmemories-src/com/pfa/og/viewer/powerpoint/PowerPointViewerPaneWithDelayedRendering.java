package com.pfa.og.viewer.powerpoint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class PowerPointViewerPaneWithDelayedRendering extends JSplitPane{

	XMLSlideShow pptx ;
	Dimension pgsize ;
	JList lst;
	//JPanel pnl_image;
	JLabel lbl;
	
	public PowerPointViewerPaneWithDelayedRendering(InputStream is) throws IOException
	{
		super();
//		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		pptx = new XMLSlideShow(is);
		pgsize = pptx.getPageSize(); 
		int k=0;
//		System.out.println("Found "+pptx.getSlides().length+" slides");
		
		List<String> lst_slides = new ArrayList<String>();
		
		for(XSLFSlide slide : pptx.getSlides())
		{
//			System.out.println(k+":"+slide.getTitle());
			k++;
			lst_slides.add(k+":"+slide.getTitle());
		}
		
		lst = new JList(lst_slides.toArray(new String[]{}));
		lst.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
//		JPanel pnl_image = new JPanel();
//		pnl_image.setLayout(new BoxLayout(pnl_image,BoxLayout.X_AXIS));

		{
		BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB); 
		ImageIcon img_empty = new ImageIcon(img);
		lbl = new JLabel(img_empty);
//		pnl_image.add(lbl);
		}
		
		lst.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(arg0.getValueIsAdjusting())
					return;
				
				int idx = lst.getSelectedIndex();
//				System.out.println("Showing slide "+idx);

				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB); 
				Graphics2D graphics = img.createGraphics(); 
				graphics.setPaint(Color.white); 
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height)); 
				pptx.getSlides()[idx].draw(graphics); 
				ImageIcon icon = new ImageIcon(img); 
				lbl.setIcon(icon);
//				JLabel lbl = new JLabel(icon);
				
//				pnl_image.removeAll();
//				pnl_image.add(lbl);
//				pnl_image.repaint();
//				pnl_image.invalidate();
			}
			
		});
	
		this.setLeftComponent(new JScrollPane(lst));
		this.setRightComponent(new JScrollPane(lbl));
	}

	
	public void setVisibleSlide(int idx)
	{
		lst.setSelectedIndex(idx);
		lst.ensureIndexIsVisible(idx);
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		PowerPointViewerPaneWithDelayedRendering ppv = new PowerPointViewerPaneWithDelayedRendering(new FileInputStream("D:/Dropbox/En cours - Pro SQLI Conseil Lyon Suisse/Le Haut/Emmanuel/Beyond Agile.02.pptx"));
		ppv.setVisibleSlide(2);
		
		JFrame f = new JFrame("PowerPointViewer");
		f.getContentPane().add(new JScrollPane(ppv));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
