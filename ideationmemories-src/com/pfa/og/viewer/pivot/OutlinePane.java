package com.pfa.og.viewer.pivot;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.pfa.og.pivot.Outline;

public class OutlinePane extends JPanel {
	
	JTree t=new JTree();
	
	public OutlinePane(Outline o)
	{
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		add(new JScrollPane(t));

		DefaultMutableTreeNode root = map(o);
		t.setModel(new DefaultTreeModel(root));
	}
	
	private DefaultMutableTreeNode map (Outline o)
	{
//		System.out.println("Map "+o.getText());
		DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(o.getText());
		for(Outline child : o.getChildren())
		{
			dmtn.add(map(child));
		}
		return dmtn;
	}
}
