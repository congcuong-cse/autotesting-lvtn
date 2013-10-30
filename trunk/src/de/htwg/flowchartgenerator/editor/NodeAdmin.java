package de.htwg.flowchartgenerator.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.zest.core.widgets.GraphNode;

import de.htwg.flowchartgenerator.ast.model.INode;


/**
 * Administrator for the nodes linked to the graphnodes. Contains references to
 * the corresponding models.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class NodeAdmin {
	private INode root;
	private List<NodeContainer> nodes = new ArrayList<NodeContainer>();

	public void add(NodeContainer nc) {
		nodes.add(nc);
	}

	public GraphNode get(INode inode) {
		GraphNode node = null;
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			NodeContainer type = (NodeContainer) iterator.next();
			if (type.getINode() == inode) {
				node = type.getGNode();
			}
		}
		return node;
	}

	public void set(INode inode, GraphNode gnode) {
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			NodeContainer type = (NodeContainer) iterator.next();
			if (type.getINode() == inode) {
				type.setGNode(gnode);
			}
		}
	}

	public INode get(GraphNode gnode) {
		INode node = null;
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			NodeContainer type = (NodeContainer) iterator.next();
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

	public List<GraphNode> getGNodes() {
		List<GraphNode> l = new ArrayList<GraphNode>();
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			NodeContainer object = (NodeContainer) iterator.next();
			l.add(object.getGNode());
		}
		return l;
	}

}
