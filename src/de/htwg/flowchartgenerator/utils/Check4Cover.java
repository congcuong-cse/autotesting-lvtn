package de.htwg.flowchartgenerator.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageDeclaration;

import de.htwg.flowchartgenerator.controller.Controller;
import de.htwg.flowchartgenerator.exceptions.NoCoverPathFoundException;
import de.htwg.flowchartgenerator.xml.XML2Cover;

/**
 * Link between the controller and the parser.
 * @author Aldi Alimucaj
 *
 */
public class Check4Cover {
	private static String CODECOVER_DIR = "/codecover";
	Node node = null;
	/**
	 * Gets the document and parses it.
	 * @return parsed document
	 * @throws NoCoverPathFoundException
	 */
	public static Document getDocument() throws NoCoverPathFoundException{
		Document doc = null;
		try {
			File f = getFile();
			if (null != f) {
				doc = XML2Cover.parse(f);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
	public static Document getDocument(File f) throws NoCoverPathFoundException{
		Document doc = null;
		try {
			if (null != f) {
				doc = XML2Cover.parse(f);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
	public static List<File> getFiles() throws MalformedURLException, NoCoverPathFoundException {
		List<File> list = new ArrayList<File>();
		IResource res = Controller.getInstance().getCompUnit().getJavaProject().getResource();
		File file = res.getLocation().toFile();
		if (file.isDirectory()) {
			File covDir = new File(file.getAbsolutePath() + CODECOVER_DIR);
			if (covDir.exists()) {
				File[] dirlist = covDir.listFiles();
				List<File> sessions = new ArrayList<File>();
				
				for (int i = 0; i < dirlist.length; i++) {
					if (!dirlist[i].isDirectory()) {
						sessions.add(dirlist[i]);
					}
				}
				Collections.sort(sessions);
				list = sessions;
			} else {
				System.err.println("No CodeCover Dir. No CodeCover XML File found to import!!!");
				throw new NoCoverPathFoundException();
			}
		}
		return list;
	}
	
	/**
	 * It it wanted to build the flow chart from the last session.
	 * This method checks for the last session and returns it.
	 * @return last session.
	 * @throws MalformedURLException
	 * @throws NoCoverPathFoundException
	 */
	public static File getFile() throws MalformedURLException, NoCoverPathFoundException {
		IResource res = Controller.getInstance().getCompUnit().getJavaProject().getResource();
		File file = res.getLocation().toFile();
		File returnFile = null;
		if (file.isDirectory()) {
			File covDir = new File(file.getAbsolutePath() + CODECOVER_DIR);
			if (covDir.exists()) {
				File[] dirlist = covDir.listFiles();
				List<File> sessions = new ArrayList<File>();
				
				for (int i = 0; i < dirlist.length; i++) {
					if (!dirlist[i].isDirectory()) {
						sessions.add(dirlist[i]);
					}
				}
				if(!sessions.isEmpty()){
					returnFile = sessions.get(0);
				}
				for (Iterator iterator = sessions.iterator(); iterator.hasNext();) {
					File file2 = (File) iterator.next();
					if(file2.lastModified() > returnFile.lastModified()){
						returnFile = file2;
					}
				}
			} else {
				System.err.println("No CodeCover Dir. No CodeCover XML File found to import!!!");
				throw new NoCoverPathFoundException();
			}
		}
		if (returnFile.isDirectory()) {
			returnFile = null;
		}
		System.out.println(returnFile);
		return returnFile;
	}
	
	public static List<String> getPackeges(IPackageDeclaration[] pd){
		List<String> packs = new ArrayList<String>();
		for (int i = 0; i < pd.length; i++) {
			packs.add(pd[i].getElementName());
		}
		return packs;
	}

}
