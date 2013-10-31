package de.htwg.flowchartgenerator.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.exceptions.NoMethodFoundException;

/**
 * Class containing methods which process the information gathered from the xml file.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class XML2Cover {

	private final static String COV_ITEM_PATH = "//*[name()='Cov']";
	private final static String LOC_PATH = "/*[name()='LocList']/*[name()='Loc']";
	// Object Attributes
	Element root;
	private Document document = null;
	private List<String> cookies;
	private List<String> coveredItems = new ArrayList<String>();
	private List<CoveredElement> coveredElements = new ArrayList<CoveredElement>();

	/**
	 * Checks if the node is within the offsets.
	 * 
	 * @param node
	 * @param startOffset
	 * @param length
	 * @return true if the offsets correspond
	 */
	public boolean checkNode(INode node, int startOffset, int length) {
		for (Iterator i = coveredElements.iterator(); i.hasNext();) {
			CoveredElement son = (CoveredElement) i.next();
			if (startOffset == son.getStartOffset() && startOffset + length == son.getEndOffset()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds the covered elements
	 * 
	 * @param e
	 * @return The covered elements
	 */
	public List<String> getCoveredElements(Element e) {
		List<String> tempCov = new ArrayList<String>();
		List l = e.selectNodes(COV_ITEM_PATH);
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			Element object = (Element) iterator.next();
			tempCov.add(object.attribute("CovItemId").getText());
		}
		return tempCov;
	}

	/**
	 * Localizes the elements to be covered from the file.
	 * 
	 * @param coveredItems
	 * @return list of elements to be covered.
	 * @throws NoMethodFoundException
	 */
	public List<CoveredElement> localizeCE(List<String> coveredItems) throws NoMethodFoundException {
		List<CoveredElement> tempCE = new ArrayList<CoveredElement>();
		for (Iterator iterator = coveredItems.iterator(); iterator.hasNext();) {
			String ci = (String) iterator.next();
			Element element = (Element) root.selectSingleNode(getModifiedPath(ci));
			if (null != element) {
				CoveredElement ce = new CoveredElement();
				ce = setOffests(element);
				ce.setName(element.attribute("CovItemId").getText());
				ce.setType(element.getQualifiedName());
				tempCE.add(ce);
			}
			if (tempCE.size() == 0) {
				System.out.println("element == null");
				throw new NoMethodFoundException();
			}
		}
		return tempCE;
	}

	/**
	 * sets the offests for the element into the node.
	 * 
	 * @param element
	 * @return a covered element
	 */
	public CoveredElement setOffests(Element element) {
		CoveredElement ce = new CoveredElement();
		String fullPath = element.getUniquePath() + LOC_PATH;
		Element loc = (Element) element.selectSingleNode(fullPath);
		if (null != loc) {
			ce.setStartOffset(Integer.valueOf(loc.attribute("StartOffset").getText()));
			ce.setEndOffset(Integer.valueOf(loc.attribute("EndOffset").getText()));
		}

		return ce;
	}

	/**
	 * Finds the path corresponding to the location of the class and method.
	 * 
	 * @param s
	 * @return string containing the path.
	 */
	private String getModifiedPath(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("/*[name()='TestSessionContainer']/*[name()='MASTRoot']");
		for (Iterator iterator = cookies.iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			sb.append("//*[@Name='" + type + "']");
		}
		sb.append("//*[@CovItemId='" + s + "']");
		return sb.toString();
	}

	public boolean checkLoopNode(INode node, int startOffset, int length) {
		String id = null;
		for (Iterator i = coveredElements.iterator(); i.hasNext();) {
			CoveredElement son = (CoveredElement) i.next();
			if (startOffset == son.getStartOffset() && startOffset + length == son.getEndOffset()) {
				id = son.getName();
				break;
			}
		}
		Element element = (Element) root.selectSingleNode(getModifiedPath(id));
		Element rootLoopNode = (Element) root.selectSingleNode(element.getUniquePath() + "/*[name()='RootTerm']");
		String rlnID = rootLoopNode.attribute("CovItemId").getText();
		Element coveredLoop = (Element) root.selectSingleNode("//*[name()='TestSessionContainer']/*[name()='TestSession']//*[@RootTermCovItemId='"
				+ rlnID + "']");
		if (null != coveredLoop) {
			return true;
		}
		return false;
	}

	/**
	 * Document of pared file.
	 * 
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public static Document parse(File file) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		if (null == document) {
			throw new DocumentException();
		}
		return document;
	}

	public void init() throws NoMethodFoundException {
		root = document.getRootElement();
		coveredItems = getCoveredElements(root);
		coveredElements = localizeCE(coveredItems);
	}

	// -------------------------- GETTER SETTER CONSTRUCTOR
	public XML2Cover(List<String> packs, Document document) throws NoMethodFoundException {
		this.cookies = packs;
		this.document = document;
		init();
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
