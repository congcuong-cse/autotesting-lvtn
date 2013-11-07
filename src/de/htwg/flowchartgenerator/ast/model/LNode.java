package de.htwg.flowchartgenerator.ast.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;



public class LNode extends FNode {
	private InfixExpression.Operator operator = null;
	
	public LNode(List<INode> nodes, String value, int type, InfixExpression.Operator operator) {
		this.nodes = nodes;
		this.value = value;
		this.type = type;
		this.operator = operator;
	}

	public LNode(String value, int type) {
		this.value = value;
		this.type = type;
	}
	
	public LNode(){

	}
	
	@Override
	public InfixExpression.Operator getOperator(){
		return this.operator;
	}
	
	@Override
	public void setOperator(InfixExpression.Operator operator ){
		this.operator = operator;
	}

}
