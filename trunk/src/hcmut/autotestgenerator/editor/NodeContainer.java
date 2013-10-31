package hcmut.autotestgenerator.editor;

import hcmut.autotestgenerator.ast.model.INode;

import org.eclipse.zest.core.widgets.GraphNode;


/**
 * A Container for nodes and graphnodes.
 * 
 * @author a
 *
 */
public class NodeContainer {
	private INode iNode;
	private GraphNode gNode;
	private boolean collapsed = false;

	public NodeContainer(INode node, GraphNode node2) {
		super();
		iNode = node;
		gNode = node2;
	}

	public NodeContainer(INode node, GraphNode node2, boolean collapsed) {
		super();
		iNode = node;
		gNode = node2;
		this.collapsed = collapsed;
	}

	public INode getINode() {
		return iNode;
	}

	public void setINode(INode node) {
		iNode = node;
	}

	public GraphNode getGNode() {
		return gNode;
	}

	public void setGNode(GraphNode node) {
		gNode = node;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
