package de.htwg.flowchartgenerator.ast.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.InfixExpression;

/**
 * This class is a representation of an INode interface. Java elements are represented as nodes linked to each other Witch means that it
 * represents the model of the algorithm.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class FNode implements Serializable, Cloneable, INode {
	protected static final long serialVersionUID = 4372661154947355355L;
	protected List<INode> nodes = new ArrayList<INode>();
	protected String value = "";
	protected String fullInfo = "";
	protected String fullInfo_ = "";
	protected int type = -1;
	protected boolean folded = false;

	public String toString() {
		return getValue();
	}

	// -------------------------- GETTER SETTER CONSTRUCTOR

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void addNode(INode node) {
		nodes.add(node);
	}

	public List<INode> getNodes() {
		return nodes;
	}

	public void setNodes(List<INode> nodes) {
		this.nodes = nodes;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FNode(List<INode> nodes, String value, int type) {
		this.nodes = nodes;
		this.value = value;
		this.type = type;
	}

	public FNode(String value, int type) {
		this.value = value;
		this.type = type;
	}

	public FNode() {
	}

	@Override
	public int getSize() {
		return nodes.size();
	}

	@Override
	public boolean isCovered() {
		return false;
	}

	@Override
	public void setCovered() {
		// Nothing to do
	}

	@Override
	public String getInfo() {
		return fullInfo;
	}

	@Override
	public void setInfo(String str) {
		this.fullInfo = str;
	}
	
	@Override
	public String getInfo_() {
		return fullInfo_;
	}

	@Override
	public void setInfo_(String str) {
		this.fullInfo_ = str;
	}

	@Override
	public boolean isFolded() {
		return folded;
	}

	@Override
	public void setFolded(boolean folded) {
		this.folded = folded;
	}
	
	@Override
	public void setOperator(String operator ){

	}
	
	@Override
	public String getOperator(){
		return null;
	}

}
