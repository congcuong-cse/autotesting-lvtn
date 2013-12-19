package hcmut.cse.testcasegenerator;

import hcmut.cse.testcasegenerator.model.TestcaseGraphNode;

import de.htwg.flowchartgenerator.ast.model.INode;

/**
 * A Container for nodes and graphnodes.
 * 
 * @author a
 *
 */
public class TestcaseNodeContainer {
	private INode iNode;
	private TestcaseGraphNode gNode;
	private boolean collapsed = false;

	public TestcaseNodeContainer(INode node, TestcaseGraphNode node2) {
		super();
		iNode = node;
		gNode = node2;
	}

	public TestcaseNodeContainer(INode node, TestcaseGraphNode node2, boolean collapsed) {
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

	public TestcaseGraphNode getGNode() {
		return gNode;
	}

	public void setGNode(TestcaseGraphNode node) {
		gNode = node;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
