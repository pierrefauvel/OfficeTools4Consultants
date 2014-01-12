package com.pfa.ideationmemories.test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.lucene.queryParser.ParseException;

import com.pfa.ideationmemories.AnalysisLog;
import com.pfa.ideationmemories.MainAnalyzer;

public class Test_RealData {
	public static void main(String[]args) throws NoSuchAlgorithmException, IOException, ParseException
	{
		File[] roots = new File[]{
//				new File("D:/SQLI"),
//				new File("D:/Fichiers plus anciens/SQLI Plus Anciens"),
				new File("D:/Fichiers plus anciens"),
				new File("D:/Dropbox/En cours - Pro SQLI Conseil Lyon Suisse"),
				new File("D:/Dropbox/En cours - Pro Perso"),
				new File("D:/Dropbox/En cours - Ecriture"),
				new File("D:/Dropbox/Pro plus anciens"),
				new File("D:/Nouvelles archives"),
				new File("D:/Nouvelles nouvelles archives"),
				
		};
		MainAnalyzer.main(roots, new AnalysisLog(),100);
	}
}
