package hcmut.cse.testcasegenerator.ast;

import hcmut.cse.testcasegenerator.controller.ITestcaseController;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.controller.IController;

/**
 * Helps identifying the node corresponding to the
 * offests.
 * 
 * @author Aldi Alimucaj
 *
 */
public class ASTMethodChecker extends ASTVisitor {
	ITestcaseController know = null;
	String str = null;
	/**
	 * Method called automatically from the visitor.
	 */
	public boolean visit(MethodDeclaration node) {
		if(node.getName().toString().equals(str)){
			int pos = new Integer(node.getStartPosition());
			know.setPosition(pos);
		}
		return true;
	}

	public ASTMethodChecker(ITestcaseController know, String str) {
		this.know = know;
		this.str = str;
	}

}
