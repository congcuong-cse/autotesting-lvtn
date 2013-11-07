package de.htwg.flowchartgenerator.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConditionalExpression;

import de.htwg.flowchartgenerator.ast.model.FNode;
import de.htwg.flowchartgenerator.ast.model.INode;


/**
 * This class helps identifying whether a node is a
 * ternary operator or not.
 * 
 * @author Aldi Alimucaj
 *
 */
public class ASTExpressionChecker extends ASTVisitor {
	INode nodes = null;
	String str = null;

	/**
	 * Method called automatically from the visitor.
	 * Adds the ternary operators and subnodes if it the case.
	 * 
	 */
	public boolean visit(ConditionalExpression node) {
		INode then_ = new FNode(node.getThenExpression().toString(), ASTNode.EXPRESSION_STATEMENT);
		then_.setInfo(node.getThenExpression().toString());
		INode else_ = new FNode(node.getElseExpression().toString(), ASTNode.EXPRESSION_STATEMENT);
		else_.setInfo(node.getElseExpression().toString());
		//System.out.println(node.getThenExpression().toString());
		nodes.setValue(node.getExpression().toString());
		nodes.addNode(then_);
		nodes.getNodes().add(new FNode("", -1));
		nodes.addNode(else_);
		return true;
	}
	/**
	 * Default constructor
	 * @param nodes
	 */
	public ASTExpressionChecker(INode nodes) {
		this.nodes = nodes;
	}
	public ASTExpressionChecker(INode nodes, String str) {
		this.nodes = nodes;
		this.str = str;
	}
}
