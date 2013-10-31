 
import hcmut.autotestgenerator.ast.ASTMethodChecker;
import hcmut.autotestgenerator.ast.ASTNodeMainVisitor;
import hcmut.autotestgenerator.ast.ASTNodeVisitor4Cover;
import hcmut.autotestgenerator.ast.model.FNode;
import hcmut.autotestgenerator.ast.model.INode;
import hcmut.autotestgenerator.controller.Controller;
import hcmut.autotestgenerator.controller.IController;
import hcmut.autotestgenerator.exceptions.NoCoverPathFoundException;
import hcmut.autotestgenerator.exceptions.NoMethodFoundException;
import hcmut.autotestgenerator.utils.Check4Cover;
import hcmut.autotestgenerator.utils.NodeSerializer;
import hcmut.autotestgenerator.utils.Statics;
import hcmut.autotestgenerator.xml.XML2Cover;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.dialogs.MessageDialog;


class ASTMethodChecker___ extends ASTVisitor{
	Integer pos = new Integer(0);
	String str = null;
	
	public boolean visit(MethodDeclaration node) {
		if(node.getName().toString().equals(str)){
			setPosition(node.getStartPosition());
		}
		return true;
	}
	
	public ASTMethodChecker___ (String srt){
		this.str = srt;
	}
	
	public void setPosition(int pos) {
		this.pos = new Integer(pos);
	}

	public int getPosition() {
		return this.pos.intValue();
	}
	
}
 
public class Test {
	public static void main(String args[]){
//		ASTParser parser = ASTParser.newParser(AST.JLS4);
//		parser.setSource("public class a { int b; public int a(int b) { int c = 3; return b; } }".toCharArray());
//		//parser.setSource("/*abc*/".toCharArray());
//		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
//		//ASTNode node = parser.createAST(null);
//
//
//		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
// 
//		cu.accept(new ASTVisitor() {
// 
// 
//			public boolean visit(MethodDeclaration node) {
//				SimpleName name = node.getName();
//
//				System.out.println("Declaration of '"+name+"' return : "+node.getReturnType2().toString());
//				return false; // do not continue to avoid usage info
//			}
// 
//		});
		
		INode nodes = null;
		String javaCode = "int a(int b) { int c = 3; return b; }";
		System.out.println(javaCode);

		ASTParser astParser = ASTParser.newParser(AST.JLS4);

		nodes = new FNode("START", ASTNode.EXPRESSION_STATEMENT);

		// CU visitor
		astParser.setSource("public class a { int b; public int a(int b) { int c = 3; return b; } }".toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit tmp = (CompilationUnit) astParser.createAST(null);
		final ICompilationUnit cu = (ICompilationUnit) tmp.getJavaElement();
		// ================
		// Method visitor
		// ================
		astParser.setSource(javaCode.toCharArray());
		astParser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		//final IMethod method = (IMethod) astParser.createAST(null); 
		
		ASTMethodChecker___ visitor___ = new ASTMethodChecker___("a");
		ASTNode node1 = astParser.createAST(null);
		node1.accept(visitor___);

		// Initialize object
		ASTVisitor visitor = new ASTNodeVisitor4Cover(nodes, null, visitor___.getPosition() );
		ASTNode node = astParser.createAST(null);
		node.accept(visitor);
		// Write the node to disc.
		//new NodeSerializer(nodes, targetPart, getFullPath(method), method).doWrite();

	}
	
	int test(int a, int b){
		int c;
		c = 3;
		if(a==0 && b == 0){
			int e;
			a = b;
			return 1;
			}
		else if(a==1)
				return b;
		else return c;
		
	}
	
	public int a( int b, int c){
		if (b == 0)
			return 1;
		else{
			int d = c;
			c = b+1;
			
		}
		return c;
	}
}