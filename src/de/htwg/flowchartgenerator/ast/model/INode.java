package de.htwg.flowchartgenerator.ast.model;

import java.util.List;

import org.eclipse.jdt.core.dom.InfixExpression;

/**
 * Model Interface. The listed methods are the ones needed for the 
 * computation of the algorithm. 
 * @author a
 * @see FNode, CoverNode
 */
public interface INode {
	public abstract void addNode(INode node);

	public abstract List<INode> getNodes();

	public abstract void setNodes(List<INode> nodes);

	public abstract String getValue();

	public abstract void setValue(String value);

	public abstract int getType();

	public abstract void setType(int type);

	public abstract int getSize();

	public abstract boolean isCovered();

	public abstract void setCovered();
	
	public abstract void setInfo(String str);
	
	public abstract String getInfo();
	
	public abstract boolean isFolded();
	public abstract void setFolded(boolean folded);
	
	public abstract void setOperator(String operator );
	public abstract String getOperator();

}