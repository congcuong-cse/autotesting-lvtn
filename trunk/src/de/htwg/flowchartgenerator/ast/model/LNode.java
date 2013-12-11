package de.htwg.flowchartgenerator.ast.model;

import java.util.List;



public class LNode extends FNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841889678850365955L;
	private String operator = null;
	
	public LNode(List<INode> nodes, String value, int type, String operator) {
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
	public String getOperator(){
		return this.operator;
	}
	
	@Override
	public void setOperator(String operator ){
		this.operator = operator;
	}

}
