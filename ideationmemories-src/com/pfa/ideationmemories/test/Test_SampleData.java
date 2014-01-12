package com.pfa.ideationmemories.test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.lucene.queryParser.ParseException;

import com.pfa.ideationmemories.AnalysisLog;
import com.pfa.ideationmemories.MainAnalyzer;

public class Test_SampleData {
	public static void main(String[]args) throws NoSuchAlgorithmException, IOException, ParseException
	{
		File[] roots = new File[]{
				new File("sample")
		};
		MainAnalyzer.main(roots, new AnalysisLog(),20);
	}
}
