package de.htwg.flowchartgenerator.ast;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

import de.htwg.flowchartgenerator.ast.model.INode;

public class ASTInfixExpressionChecker extends ASTVisitor {
	INode nodes = null;
	String str = null;
	
	public boolean visit(InfixExpression node){
		return visit_(node);
	}
	
	public boolean visit_(InfixExpression node){
		System.out.println(node.toString());
		if (node.getLeftOperand() instanceof InfixExpression){
			visit_((InfixExpression) node.getLeftOperand());
		}
		else
			System.out.println(node.getLeftOperand().toString());
		System.out.println(node.getOperator().toString());
		if (node.getRightOperand() instanceof InfixExpression){
			visit_((InfixExpression) node.getRightOperand());
		}
		else
			System.out.println(node.getRightOperand().toString());
		
		
		
		return true;
	}

}
