package de.htwg.flowchartgenerator.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

import de.htwg.flowchartgenerator.ast.model.FNode;
import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.ast.model.LNode;

public class ASTInfixExpressionChecker extends ASTVisitor {
	INode nodes = null;
	String str = null;
	
	public boolean visit(InfixExpression node){
		
		visit_(node);
		return true;
	}
	
	public LNode visit_(InfixExpression node){
		List<INode> exprs = new ArrayList<INode>();
		InfixExpression.Operator operator = node.getOperator();
		
		if(node.getLeftOperand() instanceof InfixExpression){
			LNode left = visit_((InfixExpression) node.getLeftOperand());
			LNode right = visit_((InfixExpression) node.getRightOperand());
			exprs.add(left);
			exprs.add(right);
		}else{
			INode left = new FNode(node.getLeftOperand().toString(), ASTNode.EXPRESSION_STATEMENT);
			left.setInfo(node.getLeftOperand().toString());
			INode right = new FNode(node.getRightOperand().toString(), ASTNode.EXPRESSION_STATEMENT);
			left.setInfo(node.getRightOperand().toString());
			exprs.add(left);
			exprs.add(right);
		}
		
		LNode lnode = new LNode(exprs, operator);
		return lnode;
	}

}
