package de.htwg.flowchartgenerator.ast.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;



public class LNode extends FNode {
	private List<INode> exprs = new ArrayList<INode>();
	private InfixExpression.Operator operator;
	
	public LNode(List<INode> exprs, InfixExpression.Operator operator){
		this.exprs = exprs;
		this.operator = operator;
	}
	
	public void addExpr(INode node){
		this.exprs.add(node);
	}
	
	public List<INode> getExprs(){
		return this.exprs;
	}
	
	public void setOperator(InfixExpression.Operator operator){
		this.operator = operator;
	}
	
	public InfixExpression.Operator getOperator(){
		return this.operator;
	}
}
