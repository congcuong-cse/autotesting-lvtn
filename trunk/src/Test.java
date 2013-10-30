 
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
 
public class Test {
	public static void main(String args[]){
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource("public class a { int b; public int a(int b) { int c = 3; return b; } }".toCharArray());
		//parser.setSource("/*abc*/".toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		//ASTNode node = parser.createAST(null);
 
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 
		cu.accept(new ASTVisitor() {
 
 
			public boolean visit(MethodDeclaration node) {
				SimpleName name = node.getName();

				System.out.println("Declaration of '"+name+"' return : "+node.getReturnType2().toString());
				return false; // do not continue to avoid usage info
			}
 
		});
	}
	
	int test(int a, int b){
		int c;
		c = 3;
		if(a==0){
			int e;
			a = b;
			return 1;
			}
		else if(a==1)
				return b;
		else return c;
		
	}
}