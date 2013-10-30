package de.htwg.flowchartgenerator.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.dom4j.Document;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.htwg.flowchartgenerator.ast.ASTMethodChecker;
import de.htwg.flowchartgenerator.ast.ASTNodeMainVisitor;
import de.htwg.flowchartgenerator.ast.ASTNodeVisitor4Cover;
import de.htwg.flowchartgenerator.ast.model.FNode;
import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.exceptions.NoCoverPathFoundException;
import de.htwg.flowchartgenerator.exceptions.NoMethodFoundException;
import de.htwg.flowchartgenerator.utils.BundleChecker;
import de.htwg.flowchartgenerator.utils.Check4Cover;
import de.htwg.flowchartgenerator.utils.NodeSerializer;
import de.htwg.flowchartgenerator.utils.SessionSelector;
import de.htwg.flowchartgenerator.utils.Statics;
import de.htwg.flowchartgenerator.xml.XML2Cover;

/**
 * Controller
 * 
 * @author Aldi Alimucaj
 * 
 */
public class Controller implements IController {
	private static IController know;
	private IMethod method;
	private ICompilationUnit compUnit;
	private IWorkbenchPart targetPart;
	private Integer integer = new Integer(0);

	private Controller(ICompilationUnit compUnit, IMethod method, IWorkbenchPart targetPart) {
		BundleChecker.hasCodeCover();
		this.compUnit = compUnit;
		this.method = method;
		this.targetPart = targetPart;
	}

	public static IController newInstance(ICompilationUnit compUnit, IMethod method, IWorkbenchPart targetPart) {
		know = new Controller(compUnit, method, targetPart);
		return know;
	}

	public static IController getInstance() {
		if (null != know) {
			return know;
		} else {
			throw new NullPointerException();
		}

	}

	public Shell getShell() {
		return targetPart.getSite().getShell();
	}

	/**
	 * The idea of this method is to make the compilation unit source and the method source suitable for the up coming operations in the AST
	 * classes.
	 */
	@Override
	public void operate() {
		INode nodes = null;
		try {
			String javaCode = method.getSource();
			System.out.println(method.getSource());

			ASTParser astParser = ASTParser.newParser(AST.JLS3);

			nodes = new FNode("START", ASTNode.EXPRESSION_STATEMENT);

			// Method visitor
			astParser.setSource(javaCode.toCharArray());
			astParser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
			ASTVisitor visitor = new ASTNodeMainVisitor(targetPart, nodes);
			ASTNode node = astParser.createAST(null);
			node.accept(visitor);
			// Write the node to disc.
			new NodeSerializer(nodes, targetPart, getFullPath(method), method).doWrite();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	public void operateFromChosenTest() {
		INode nodes = null;
		try {
			String javaCode = method.getSource();
			System.out.println(method.getSource());

			ASTParser astParser = ASTParser.newParser(AST.JLS3);

			nodes = new FNode("START", ASTNode.EXPRESSION_STATEMENT);

			// CU visitor
			astParser.setSource(compUnit.getSource().toCharArray());
			astParser.setKind(ASTParser.K_COMPILATION_UNIT);
			ASTVisitor visitor = new ASTMethodChecker(this, method.getElementName());
			ASTNode node1 = astParser.createAST(null);
			node1.accept(visitor);
			// ================
			// Method visitor
			// ================

			// Parse the Abstract Syntax Tree
			astParser.setSource(javaCode.toCharArray());
			astParser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);

			// Get data from XML-Session
			System.out.println("wating,");
			SessionSelector ss = null;
			try {
				ss = new SessionSelector(getShell(), Check4Cover.getFiles());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoCoverPathFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File file = ss.getFile();
			System.out.println("stoped waiting");

			Document doc = null;
			XML2Cover x2c = null;
			try {
				doc = Check4Cover.getDocument(file);
				if (null != doc) {
					List<String> packs = Check4Cover.getPackeges(compUnit.getPackageDeclarations());
					String className = compUnit.getElementName().substring(0, compUnit.getElementName().indexOf(".java"));
					packs.add(className);
					packs.add(method.getElementName());
					x2c = new XML2Cover(packs, doc);
				} else {
					MessageDialog.openError(getShell(), "Flow Plug-in", "No Document Found!");
					System.err.println("No Document Found!");
				}
			} catch (NoCoverPathFoundException e) {
				MessageDialog.openError(Controller.getInstance().getShell(), Statics.EDITOR_NAME, "No ./codecover path found!");
			} catch (NoMethodFoundException e) {
				MessageDialog.openError(Controller.getInstance().getShell(), Statics.EDITOR_NAME, method.getElementName()
						+ " was not found in the session." + "\nCheck if you saved it after the execution.");
			}
			// Initialize object
			visitor = new ASTNodeVisitor4Cover(nodes, x2c, getPosition());
			ASTNode node = astParser.createAST(null);
			node.accept(visitor);
			// Write the node to disc.
			new NodeSerializer(nodes, targetPart, getFullPath(method), method).doWrite();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void operateFromTest() {
		INode nodes = null;
		try {
			String javaCode = method.getSource();
			System.out.println(method.getSource());

			ASTParser astParser = ASTParser.newParser(AST.JLS3);

			nodes = new FNode("START", ASTNode.EXPRESSION_STATEMENT);

			// CU visitor
			astParser.setSource(compUnit.getSource().toCharArray());
			astParser.setKind(ASTParser.K_COMPILATION_UNIT);
			ASTVisitor visitor = new ASTMethodChecker(this, method.getElementName());
			ASTNode node1 = astParser.createAST(null);
			node1.accept(visitor);
			// ================
			// Method visitor
			// ================

			// Parse the Abstract Syntax Tree
			astParser.setSource(javaCode.toCharArray());
			astParser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);

			// Get data from XML-Session
			Document doc = null;
			XML2Cover x2c = null;
			try {
				doc = Check4Cover.getDocument();
				if (null != doc) {
					List<String> packs = Check4Cover.getPackeges(compUnit.getPackageDeclarations());
					String className = compUnit.getElementName().substring(0, compUnit.getElementName().indexOf(".java"));
					packs.add(className);
					packs.add(method.getElementName());
					x2c = new XML2Cover(packs, doc);
				} else {
					MessageDialog.openError(getShell(), "Flow Plug-in", "No Document Found!");
					System.err.println("No Document Found!");
				}
			} catch (NoCoverPathFoundException e) {
				MessageDialog.openError(Controller.getInstance().getShell(), Statics.EDITOR_NAME, "No ./codecover path found!");
			} catch (NoMethodFoundException e) {
				MessageDialog.openError(Controller.getInstance().getShell(), Statics.EDITOR_NAME, method.getElementName()
						+ " was not found in the session." + "\nCheck if you saved it after the execution.");
			}
			// Initialize object
			visitor = new ASTNodeVisitor4Cover(nodes, x2c, getPosition());
			ASTNode node = astParser.createAST(null);
			node.accept(visitor);
			// Write the node to disc.
			new NodeSerializer(nodes, targetPart, getFullPath(method), method).doWrite();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ICompilationUnit getCompUnit() {
		return compUnit;
	}

	@Override
	public void setPosition(int pos) {
		this.integer = new Integer(pos);
	}

	@Override
	public int getPosition() {
		return integer.intValue();
	}

	private String getFullPath(IMethod cu) {
		IResource res = cu.getJavaProject().getResource();
		String path = System.getProperty("java.io.tmpdir")+Statics.SEPARATOR;
		if(null!=cu.getResource()){
				path+= cu.getResource().getRawLocation().toOSString().toString().substring(
						cu.getResource().getRawLocation().toOSString().toString().lastIndexOf(Statics.SEPARATOR),
						cu.getResource().getRawLocation().toOSString().toString().lastIndexOf(".")) + "_" ;
		
		IFolder fol = res.getProject().getFolder(Statics.CFG_DIR);
		if (!fol.exists()) {
			try {
				fol.create(IResource.NONE, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		}
		
		return path+ cu.getElementName()
		+ Statics.EXTENTION;
	}

}
