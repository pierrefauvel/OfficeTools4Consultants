package com.pfa.og.from.xmind;

import java.io.File;
import java.io.FileFilter;

public interface IXMindInterface {
	public static final FileFilter XMIND_FILES=new FileFilter(){
		public boolean accept(File arg0) {
			return arg0.getName().endsWith(".xmind");
		}
	};

}
