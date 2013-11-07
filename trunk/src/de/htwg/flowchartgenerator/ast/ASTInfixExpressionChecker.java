package de.htwg.flowchartgenerator.ast;

import java.util.*;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.ast.model.LNode;


public class ASTInfixExpressionChecker extends ASTVisitor {
	INode nodes = null;
	//String str = null;
	
	final public static List<InfixExpression.Operator> op = Arrays.asList(Operator.CONDITIONAL_OR, Operator.CONDITIONAL_AND, Operator.OR, Operator.AND, Operator.XOR);
	
	public ASTInfixExpressionChecker(INode node) {
		this.nodes = node;
	}
	
	public boolean visit(InfixExpression expr){
		this.nodes.addNode(visit_(expr));
		return false;
	}
	
	public INode visit_(Expression expr){
		INode result = new LNode();
		
		if(expr instanceof InfixExpression && op.contains(((InfixExpression) expr).getOperator()) ){
			INode left = visit_(((InfixExpression) expr).getLeftOperand());
			INode right = visit_(((InfixExpression) expr).getRightOperand());
			result.addNode(left);
			result.addNode(right);			
			result.setOperator(((InfixExpression) expr).getOperator());
			result.setValue(expr.toString());
			result.setInfo(expr.toString());
			result.setType(ASTNode.INFIX_EXPRESSION);
			
		}else{
			result.setValue(expr.toString());
			result.setOperator(null);
			result.setInfo(expr.toString());
			result.setType(ASTNode.EXPRESSION_STATEMENT);
		}
		return result;
	}

}
