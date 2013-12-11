package de.htwg.flowchartgenerator.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.ast.model.LNode;


public class ASTLogicalExpressionChecker{
	private INode node = null;
	private INode tmp = null;
	private ArrayList<INode> travel = null;
	
	
	
	final public static List<InfixExpression.Operator> op = Arrays.asList(InfixExpression.Operator.CONDITIONAL_OR, 
			InfixExpression.Operator.CONDITIONAL_AND, 
			InfixExpression.Operator.OR, 
			InfixExpression.Operator.AND, 
			InfixExpression.Operator.XOR);
	
	public INode getNode(){
		return this.node;
	}
	
	public ArrayList<INode> getTravel(){
		return this.travel;
	}
	
	public INode operate(Expression expr){
		INode tree = visit_(expr);
		lnr(tree, null, tree);
		return this.node;
	}
	
	public ArrayList<INode> travel_lrn(INode inode){
		this.travel = new ArrayList<INode>();
		lrn(inode, null, inode);
		return this.travel;
	}
	
	public ArrayList<INode> travel_lnr(INode inode){
		this.travel = new ArrayList<INode>();
		lnr(inode, null, inode);
		return this.travel;
	}
	
	public INode visit_(Expression expr){
		INode result = new LNode();
		
		//System.out.println(expr.toString());
		if(expr instanceof InfixExpression && op.contains(((InfixExpression) expr).getOperator()) ){
			INode left = visit_(((InfixExpression) expr).getLeftOperand());
			INode right = visit_(((InfixExpression) expr).getRightOperand());
			result.addNode(left);
			result.addNode(right);			
			result.setOperator(((InfixExpression) expr).getOperator().toString());
			result.setValue(result.getOperator());
			result.setInfo(expr.toString());
			result.setType(ASTNode.INFIX_EXPRESSION);
			
		}
		else if(expr instanceof PrefixExpression && ((PrefixExpression) expr).getOperator() == PrefixExpression.Operator.NOT )
		{
			INode not = visit_(((PrefixExpression) expr).getOperand());
			result.addNode(not);
			result.setOperator(((PrefixExpression) expr).getOperator().toString());
			result.setValue(result.getOperator());
			result.setInfo(expr.toString());
			result.setType(ASTNode.PREFIX_EXPRESSION);
			
		}
		else if(expr instanceof ParenthesizedExpression){
			return visit_(((ParenthesizedExpression) expr).getExpression());
		}
		else{
			result.setValue(expr.toString());
			result.setOperator(null);
			result.setInfo(expr.toString());
			result.setType(ASTNode.EXPRESSION_STATEMENT);
		}
		return result;
	}
	public void lnr(INode tree, INode parent, INode root){
		if(tree.getSize()>=1){
			lnr(tree.getNodes().get(0), tree, root);
		}
		
		if(tree.getSize()==0){
			System.out.println(tree.getValue());
			INode newNode = new LNode();
			newNode.setValue(tree.getValue());
			newNode.setOperator(null);
			newNode.setInfo(tree.getInfo());
			newNode.setType(ASTNode.EXPRESSION_STATEMENT);
			if(tmp==null){
				node = newNode;
			}
			else{
				tmp.addNode(newNode);
			}
			tmp = newNode;
		}
		else{
			System.out.println(tree.getOperator());
//			result.setOperator(tree.getOperator());
//			result.setValue(tree.getValue());
//			result.setInfo(tree.getInfo());
//			result.setType(ASTNode.PREFIX_EXPRESSION);
		}
		if(tree.getSize()==2){
			lnr(tree.getNodes().get(1), tree, root);
		}
	}
	
	public void lrn(INode tree, INode parent, INode root){
		if(tree.getSize()>=1){
			lrn(tree.getNodes().get(0), tree, root);
		}
		if(tree.getSize()==2){
			lrn(tree.getNodes().get(1), tree, root);
		}
		if(tree.getSize()==0){
			System.out.println(tree.getValue());
			INode newNode = new LNode();
			newNode.setValue(tree.getValue());
			newNode.setOperator(null);
			newNode.setInfo(tree.getInfo());
			newNode.setType(tree.getType());
			this.travel.add(newNode);
		}
		else{
			System.out.println(tree.getOperator());
			INode newNode = new LNode();
			newNode.setValue(tree.getOperator());
			newNode.setOperator(tree.getOperator());
			newNode.setInfo(tree.getInfo());
			newNode.setType(tree.getType());
			this.travel.add(newNode);
		}
	}

}
