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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.jdt.core.Signature;

import com.rits.cloning.Cloner;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.util.BshCanvas;
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
			graphBuilder.createView(g, nodes, 1);
			TestcaseGenerator tg = new TestcaseGenerator();
			tg.breadthFirstTraversal(g);
			for(ArrayList<TestcaseNode> i: tg.getTests()){
				System.out.println(i);
			}
			System.out.println("Generator testcase paths ...done!");
			
			String path = method.getResource().getParent().getLocation().toOSString();
			String classname = method.getParent().getElementName() + "_" + method.getElementName()
					+ method.getSignature().replace("(","_").replace(")","_").replace("[", "_") + "_Test";
			path += "\\" + classname +".java";
			System.out.println(path);
			
			ILocalVariable[] pVar = method.getParameters();
			
			ArrayList<TestcaseParameter> listPara = new ArrayList<TestcaseParameter>();
			boolean generatorInput = true;
			for (ILocalVariable i:pVar){
				if(i.getTypeSignature().equals("I")){
					//listPara.add(new TestcaseParameter(i,-10,-10,10));
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
				
				FileOutputStream fos = new FileOutputStream(path);
				PrintStream ps = new PrintStream(fos);
				
				IPackageDeclaration[] pd = compUnit.getPackageDeclarations();
				String pack="";
				for(IPackageDeclaration i: pd){
					pack += i.getElementName();
				}
				ps.println("/**");
				ps.println(" * This is an automatic Junit TestCase generated");
				ps.println(" * by ATG.");
				ps.println(" */");
				ps.println("package " + pack + ";");
				ps.println("import org.junit.Test;");
				ps.println("import static org.junit.Assert.*;");
				ps.println("import org.junit.Before;");
				ps.println("import org.junit.After;");
				ps.println("");
				ps.println("/*");
				ps.println(" * @author ATG");
				ps.println(" */");
				ps.println("public class " + classname + "{" );
				ps.println("");
				ps.println("\t" + method.getParent().getElementName() +" myClass;");
				ps.println("");
			    ps.println("\t@Before");
			    ps.println("\tpublic void initialize() {");
			    ps.println("\t\t myClass = new " + method.getParent().getElementName() + "();");
			    ps.println("\t}");
			    ps.println("");
			    ps.println("\t@After");
			    ps.println("\tpublic void clean() {");
			    ps.println("\t\t myClass = null;");
			    ps.println("\t}");
				int counttest = 1;
				if(generatorInput){
					for (ILocalVariable _p:pVar){
						if(_p.getTypeSignature().equals("I")){
							RangeLimitDialog dialog = new RangeLimitDialog(getShell(), _p.getElementName());
						    
						    // get the new values from the dialog
							//dialog.setMin(min)
						    if (dialog.open() == Window.OK) {
						    	int min = Integer.parseInt(dialog.getMin());
						    	int max = Integer.parseInt(dialog.getMax());
						    	listPara.add(new TestcaseParameter(_p,min,min,max));
						    }
						    else{
						    	listPara.add(new TestcaseParameter(_p,-10,-10,10));
						    }
						}
					}
				}
				// Get current time
				long start = System.currentTimeMillis();
				for(ArrayList<TestcaseNode> i: tg.getTests()){
					
					if(generatorInput){
						// Get current time
						long startTest = System.currentTimeMillis();

						// Do something ...

						// Get elapsed time in milliseconds
						long elapsedLoopTimeMillis;
						TestcaseParameter.beginLoop(listPara);	
						while(true){
							String outputExpected ="";
							int gonext = 0;
							try {
								
								Interpreter in = new Interpreter();
								for(TestcaseParameter t: listPara){
									//System.out.println("set("+ t.getVar().getElementName()+"," + t.getValue() + ")");
									in.set(t.getVar().getElementName(),t.getValue());
								}
								for(int m = 0; m< i.size(); m++){
									TestcaseNode e = i.get(m);
									if(e.getNode().getType() == 0 && e.getNode().getDeep()>1){
										//TODO
										ArrayList<TestcaseNode> newpaths = new ArrayList<TestcaseNode>();
										newpaths.add(e);
										for(int j = m+1; j < i.size(); j++){
											TestcaseNode newtest = i.get(j);
											if(newtest.getNode().getDeep() > 1 && newtest.getNode().getType() != 0){
												newpaths.add(newtest);
											}
											else{
												m = j;
												break;
											}
										}
										if(TestcaseGenerator.runLoop(in, newpaths, 2, outputExpected) == true){
											m --;
											continue;
										}
										else{
											gonext = 1;
											break;
										}
										
									}
									else if(e.getNode().getType()==1){
										//System.out.println("eval(" +e.getNode().getText() + ")");
										in.eval(e.getNode().getText());
									}
									else if(e.getNode().getType()==2){
										//System.out.println("eval(_con = " +e.getNode().getText() + ")");
										in.eval("_con = " + e.getNode().getText());
										if( (Boolean) in.get("_con") == false){
											gonext =1;
											break;
										}
									}
									
									else if(e.getNode().getType()==3){
										//System.out.println("eval("+  e.getNode().getText().replaceFirst("return","return _result =") + ")");
										in.eval(e.getNode().getText().replaceFirst("return","return _result ="));
										outputExpected = in.get("_result").toString();
									}

								}
							} catch (Throwable t) {
								// Auto-generated catch block
								t.printStackTrace();
								String err="";
								String errClass="";
								if( t instanceof bsh.TargetError){
									bsh.TargetError te = (bsh.TargetError) t;
									System.out.println(te.getTarget().toString());
									err = te.getTarget().toString();
									errClass = te.getTarget().getClass().getName();
									elapsedLoopTimeMillis = System.currentTimeMillis()-startTest;
									ps.println("");
									ps.println("\t/**");
									ps.println("\t * Test Number " + counttest);
									ps.println("\t * Path: "+i);
									ps.println("\t * Time: "+elapsedLoopTimeMillis + " ms");
									ps.println("\t * Result: error: "+err); 
									ps.println("\t * @throws Exception"); 
									ps.println("\t */");
									ps.println("\t@Test");
									ps.println("\tpublic void " + "test" + counttest + "() {");
									
									ps.println("\t\ttry{");
									
									
									
									String inputs = "";
									for(int m = 0; m<listPara.size();m++){
										TestcaseParameter it = listPara.get(m);
										if(m ==0){
											inputs += "input"+ (m+1);
										}
										else{
											inputs += ", input" + (m+1);
										}
										ps.println("\t\t\t"+ Signature.toString(it.getVar().getTypeSignature()) + " input" + (m+1) + " = " + it.getValue() + ";");
									}
									//not void
									if(!Signature.toString(method.getReturnType()).equals("void")){
										ps.println("\t\t\t"+ Signature.toString(method.getReturnType()) +" output = myClass."+ method.getElementName()+ "(" + inputs + ");");
									}
									else{
										ps.println("\t\t\t\tmyClass."+ method.getElementName()+ "(" + inputs + ");");
									}
									
									ps.println("\t\t}");
									ps.println("\t\tcatch(Exception e){");
									ps.println("\t\t\tassertEquals(\"exception\", \""+errClass+"\", e.getClass().getName());");
									ps.println("\t\t\treturn;");
									ps.println("\t\t}");
									ps.println("\t\tfail(\"Did not find expected exception\");");
									break;
								}
								else{
									System.out.println(t);
//									err = t.toString();
//									errClass = t.getClass().getName();
								}
								
								
							}
							if(gonext == 0){
								elapsedLoopTimeMillis = System.currentTimeMillis()-startTest;
								ps.println("");
								ps.println("\t/**");
								ps.println("\t * Test Number " + counttest);
								ps.println("\t * Path: "+i);
								ps.println("\t * Time: "+elapsedLoopTimeMillis+ " ms");
								ps.println("\t * Result: Ok"); 
								ps.println("\t * @throws Exception"); 
								ps.println("\t */");
								ps.println("\t@Test");
								ps.println("\tpublic void " + "test" + counttest + "() {");
								
								
								String inputs = "";
								for(int m = 0; m<listPara.size();m++){
									TestcaseParameter it = listPara.get(m);
									if(m ==0){
										inputs += "input"+ (m+1);
									}
									else{
										inputs += ", input" + (m+1);
									}
									ps.println("\t\t"+ Signature.toString(it.getVar().getTypeSignature()) + " input" + (m+1) + " = " + it.getValue() + ";");
								}
								//not void
								if(!Signature.toString(method.getReturnType()).equals("void")){
									ps.println("\t\t"+ Signature.toString(method.getReturnType()) +" output = myClass."+ method.getElementName()+ "(" + inputs + ");");
									ps.println("\t\t"+ Signature.toString(method.getReturnType()) +" outputExpected = " + outputExpected +";");
									ps.println("\t\t//compare results");
									ps.println("\t\tassertEquals(\"The result expected\",outputExpected,output);");
								}
								else{
									ps.println("\t\tmyClass."+ method.getElementName()+ "(" + inputs + ");");
								}
								break;
							}
							if(TestcaseParameter.endLoop(listPara)){
								elapsedLoopTimeMillis = System.currentTimeMillis()-startTest;
								ps.println("");
								ps.println("\t/**");
								ps.println("\t * Test Number " + counttest);
								ps.println("\t * Path: "+i);
								ps.println("\t * Time: "+elapsedLoopTimeMillis + " ms");
								ps.println("\t * Result: Cannot generate inputs!"); 
								ps.println("\t * @throws Exception"); 
								ps.println("\t */");
								ps.println("\t@Test");
								ps.println("\tpublic void " + "test" + counttest + "() {");;
								
								String inputs = "";
								for(int m = 0; m<listPara.size();m++){
									TestcaseParameter it = listPara.get(m);
									if(m ==0){
										inputs += "input"+ (m+1);
									}
									else{
										inputs += ", input" + (m+1);
									}
									ps.println("\t\t"+ Signature.toString(it.getVar().getTypeSignature()) + " input" + (m+1) + ";//TODO");
								}
								ps.println("\t\tfail(\"Cannot generate inputs!\");");
								ps.println("\t\t//myClass."+ method.getElementName()+ "(" + inputs + ");");
								break;
							}
							else{
								TestcaseParameter.nextValue(listPara);
							}
						}
					}
					ps.println("\t}");
					counttest++;
				}
				long elapsedTimeMillis = System.currentTimeMillis()-start;
				System.out.println("Time: " + elapsedTimeMillis + " ms");
				ps.print("}//" + "Time: " + elapsedTimeMillis + " ms");
				
				ps.close();
				System.out.println("Write ...Done!");
				
				// Get elapsed time in milliseconds
				
				Path fullpath = new Path(path);
				IDE.openEditorOnFileStore(targetPart.getSite().getPage(), EFS.getLocalFileSystem().getStore(fullpath));
				method.getJavaProject().getResource().refreshLocal(10, null);
				
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
