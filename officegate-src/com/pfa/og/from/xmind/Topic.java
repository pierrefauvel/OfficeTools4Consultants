package com.pfa.og.from.xmind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Topic {

	private String label;
	private Set<String> flags=new HashSet<String>();
	private String structure;
	private List<Topic> children = new ArrayList<Topic>();
	
	public Topic(String label)
	{
		this.label=label;
	}
	public String getLabel()
	{
		return label;
	}
	public void addFlag(String flag)
	{
		flags.add(flag);
	}
	public void setStructure(String structure)
	{
		this.structure=structure;
	}
	public Iterable<String> getFlags()
	{
		return flags;
	}
	public String getStructure()
	{
		return structure;
	}
	public void addChild(Topic t)
	{
		children.add(t);
	}
	public Topic[] getChildren()
	{
		return children.toArray(new Topic[]{});
	}
	public boolean hasFlag (String flag)
	{
		for(String _flag : this.getFlags())
		{
			if (_flag.compareTo(flag)==0)
				return true;
		};
		return false;
	}
	public boolean hasAtLeastOneFlag ()
	{
		for(String _flag : this.getFlags())
		{
			return true;
		};
		return false;
	}
}
