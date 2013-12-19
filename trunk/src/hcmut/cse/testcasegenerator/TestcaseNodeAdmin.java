package hcmut.cse.testcasegenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hcmut.cse.testcasegenerator.model.TestcaseGraphNode;

import de.htwg.flowchartgenerator.ast.model.INode;


/**
 * Administrator for the nodes linked to the graphnodes. Contains references to
 * the corresponding models.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class TestcaseNodeAdmin {
	private INode root;
	private List<TestcaseNodeContainer> nodes = new ArrayList<TestcaseNodeContainer>();

	public void add(TestcaseNodeContainer nc) {
		nodes.add(nc);
	}

	public TestcaseGraphNode get(INode inode) {
		TestcaseGraphNode node = null;
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			TestcaseNodeContainer type = (TestcaseNodeContainer) iterator.next();
			if (type.getINode() == inode) {
				node = type.getGNode();
			}
		}
		return node;
	}

	public void set(INode inode, TestcaseGraphNode gnode) {
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			TestcaseNodeContainer type = (TestcaseNodeContainer) iterator.next();
			if (type.getINode() == inode) {
				type.setGNode(gnode);
			}
		}
	}

	public INode get(TestcaseGraphNode gnode) {
		INode node = null;
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			TestcaseNodeContainer type = (TestcaseNodeContainer) iterator.next();
			if (type.getGNode() == gnode) {
				node = type.getINode();
			}
		}
		return node;
	}

	public INode getRoot() {
		return root;
	}

	public void setRoot(INode root) {
		this.root = root;
	}

	public List<TestcaseGraphNode> getGNodes() {
		List<TestcaseGraphNode> l = new ArrayList<TestcaseGraphNode>();
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			TestcaseNodeContainer object = (TestcaseNodeContainer) iterator.next();
			l.add(object.getGNode());
		}
		return l;
	}

}
