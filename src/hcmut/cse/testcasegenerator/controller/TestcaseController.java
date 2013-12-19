package hcmut.cse.testcasegenerator.controller;

import hcmut.cse.testcasegenerator.TestcaseGenerator;
import hcmut.cse.testcasegenerator.TestcaseGraphBuilder;
import hcmut.cse.testcasegenerator.ast.ASTNodeMainVisitor;
import hcmut.cse.testcasegenerator.model.TestcaseGraph;
import hcmut.cse.testcasegenerator.model.TestcaseNode;
import hcmut.cse.testcasegenerator.model.TestcaseParameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import bsh.EvalError;
import bsh.Interpreter;
import de.htwg.flowchartgenerator.ast.model.FNode;
import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.utils.BundleChecker;

/**
 * Controller
 * 
 * @author Pham Cong Cuong
 * 
 */
public class TestcaseController implements ITestcaseController {
	private static ITestcaseController know;
	private IMethod method;
	private ICompilationUnit compUnit;
	private IWorkbenchPart targetPart;
	private Integer integer = new Integer(0);

	private TestcaseController(ICompilationUnit compUnit, IMethod method, IWorkbenchPart targetPart) {
		BundleChecker.hasCodeCover();
		this.compUnit = compUnit;
		this.method = method;
		this.targetPart = targetPart;
	}

	public static ITestcaseController newInstance(ICompilationUnit compUnit, IMethod method, IWorkbenchPart targetPart) {
		know = new TestcaseController(compUnit, method, targetPart);
		return know;
	}

	public static ITestcaseController getInstance() {
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
			
			//Generator paths:
			System.out.println("Generator testcases:");
			System.out.println("Generator testcase paths:");
			TestcaseGraphBuilder graphBuilder = new TestcaseGraphBuilder();
			TestcaseGraph g = new TestcaseGraph();
			graphBuilder.createView(g, nodes);
			TestcaseGenerator tg = new TestcaseGenerator();
			tg.breadthFirstTraversal(g);
			for(ArrayList<TestcaseNode> i: tg.getTests()){
				System.out.println(i);
			}
			System.out.println("Generator testcase paths ...done!");
			
			String path = method.getResource().getParent().getLocation().toOSString();
			String classname = method.getParent().getElementName() + "_" + method.getElementName()
					+ method.getSignature().replace("(","_").replace(")","_") + "_Test";
			path += "\\" + classname +".java";
			System.out.println(path);
			
			ILocalVariable[] pVar = method.getParameters();
			String[] pName = method.getParameterNames();
			System.out.println(pVar.toString());
			System.out.println(pName.toString());
			
			ArrayList<TestcaseParameter> listPara = new ArrayList<TestcaseParameter>();
			boolean generatorInput = true;
			for (ILocalVariable i:pVar){
				if(i.getTypeSignature().equals("I")){
					listPara.add(new TestcaseParameter(i,-10,-10,10));
				}
//				else if(i.getTypeSignature().equals("Z")){
//					listPara.add(new TestcaseParameter(i,0,0,1));
//				}
				else{
					generatorInput = false;
					break;
				}
			}
	        
	        File file = new File(path);
			if (file.exists()) {
				boolean ans = MessageDialog
						.openQuestion(targetPart.getSite().getShell(), "Testcase Generator", "File already exists. Do you want to overwrite it?");
				if (ans) {
					file.delete();
				}

			}
			if (!file.exists()) {
//				PasswordDialog dialog = new PasswordDialog(getShell());
			    
			    // get the new values from the dialog
//			    if (dialog.open() == Window.OK) {
//			      String user = dialog.getUser();
//			      String pw = dialog.getPassword();
//			      System.out.println(user);
//			      System.out.println(pw);
//			    }
				//InputDialog dlg = new InputDialog( getShell(), "Title", "Enter text", "Initial value", null); 
				//dlg.open();
				//if (dlg.open() == Window.OK) // User clicked OK; run perl String input = dlg.getValue(); // TODO:do something with value } return null; }
				FileOutputStream fos = new FileOutputStream(path);
				PrintStream ps = new PrintStream(fos);
				
				IPackageDeclaration[] pd = compUnit.getPackageDeclarations();
				String pack="";
				for(IPackageDeclaration i: pd){
					pack += i.getElementName();
				}
				ps.println("package " + pack + ";");
				ps.println("import org.junit.Test;");
				ps.println("import org.junit.Before;");
				ps.println("import org.junit.After;");
				ps.println("public class " + classname + "{" );
				ps.println("\t" + method.getParent().getElementName() +" myClass;");

			    ps.println("\t@Before");
			    ps.println("\tpublic void initialize() {");
			    ps.println("\t\t myClass = new " + method.getParent().getElementName() + "();");
			    ps.println("\t}");
			    ps.println("\t@After");
			    ps.println("\tpublic void clean() {");
			    ps.println("\t\t myClass = null;");
			    ps.println("\t}");
				int counttest = 1;
				for(ArrayList<TestcaseNode> i: tg.getTests()){
					ps.println("\t@Test");
					ps.println("\tpublic void " + "test" + counttest + "() {");
					ps.println("\t\t//"+i);
					if(generatorInput){
						TestcaseParameter.beginLoop(listPara);
						while(true){
							int gonext = 0;
							try {
								Interpreter in = new Interpreter();
								
								for(TestcaseParameter t: listPara){
									System.out.println("set("+ t.getVar().getElementName()+"," + t.getValue() + ")");
									in.set(t.getVar().getElementName(),t.getValue());
								}
								for(TestcaseNode e: i){
									
									if(e.getNode().getType()==1){
										System.out.println("eval(" +e.getNode().getText() + ")");
										in.eval(e.getNode().getText());
									}
									else if(e.getNode().getType()==2){
										System.out.println("eval(_con = " +e.getNode().getText() + ")");
										in.eval("_con = " + e.getNode().getText());
										if( (Boolean) in.get("_con") == false){
											gonext =1;
											break;
										}
									}

								}
							} catch (EvalError e) {
								// TODO Auto-generated catch block
							}
							if(gonext == 0){
								String inputs = "" + listPara.get(0).getValue();
								for(int k = 1; k<listPara.size();k++){
									inputs += "," +listPara.get(k).getValue();
								}
								
								ps.println("\t\t//myClass."+ method.getElementName()+ "(" + inputs + ");");
							}
							if(TestcaseParameter.endLoop(listPara)){
								ps.println("\t\t//myClass."+ method.getElementName()+ "(" /*TODO*/ + ");");
								break;
							}
							else{
								TestcaseParameter.nextValue(listPara);
							}
						}
					}
					else{
						ps.println("\t\t//myClass."+ method.getElementName()+ "(" /*TODO*/ + ");");
					}
					ps.println("\t}");
					counttest++;
				}
				ps.print("}");
				ps.close();
				Path fullpath = new Path(path);
				IDE.openEditorOnFileStore(targetPart.getSite().getPage(), EFS.getLocalFileSystem().getStore(fullpath));
				method.getJavaProject().getResource().refreshLocal(10, null);
				System.out.println("Write ...Done!");
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
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
}
