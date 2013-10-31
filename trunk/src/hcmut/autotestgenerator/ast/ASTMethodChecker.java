package hcmut.autotestgenerator.ast;

import hcmut.autotestgenerator.controller.IController;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;


/**
 * Helps identifying the node corresponding to the
 * offests.
 * 
 * @author Aldi Alimucaj
 *
 */
public class ASTMethodChecker extends ASTVisitor {
	IController know = null;
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

	public ASTMethodChecker(IController know, String str) {
		this.know = know;
		this.str = str;
	}
}
