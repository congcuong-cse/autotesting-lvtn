package de.htwg.flowchartgenerator.editor;

import static de.htwg.flowchartgenerator.utils.Statics.H_COLOR;
import static de.htwg.flowchartgenerator.utils.Statics.NODE_DEFAULT_TEXT;
import static de.htwg.flowchartgenerator.utils.Statics.NODE_MAX_TEXT_LENGTH;

import org.eclipse.draw2d.Label;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.utils.ResourcesUtils;
import de.htwg.flowchartgenerator.utils.Statics;

/**
 * Contains the algorithm that connects the nodes with the corresponding ones
 * and check whether the node is folded or not. If it is folded than the
 * branches of that node are ignored and is treated as an expression.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class GraphBuilder4redraw extends GraphBuilder implements IGraphBuilder {
	private NodeAdmin nodeAdmin = NodeAdminFactory.getInstance();

	/**
	 * This method generates a graph with the nodes from the model. Calls itself
	 * recursively and should never return null. It checks before if the actual
	 * node is folded and if it is the case the branches of that node are
	 * ignored and is treated as an expression.
	 * 
	 * @param g
	 *            is the graph to be painted on.
	 * @param node
	 *            is the actual node
	 * @param linkNode
	 *            is the next node to link the actual node.
	 * @param loopNode
	 *            is the the node pointing the the loop if the node is inside a
	 *            loop.
	 * @param switchNode
	 *            is the head of a switch if the switch is parent of the actual
	 *            node.
	 * 
	 * @return GraphNode
	 */
	@Override
	public GraphNode createView(Graph g, INode node, GraphNode linkNode, GraphNode loopNode, GraphNode switchNode,GraphNode parent) {
		GraphNode newGraphNode = null;

		if (node.isFolded()) {
			String expression = NODE_DEFAULT_TEXT;
			if (node.getValue().length() <= NODE_MAX_TEXT_LENGTH) {
				expression = node.getValue();
			}
			newGraphNode = new GraphNode(g, SWT.NONE, expression);
			newGraphNode.setBackgroundColor(Statics.GRAY);

			newGraphNode.setImage(new Image(g.getDisplay(), ResourcesUtils.getLocation().toString() + "/icons/expand.png"));
			if (node.getType() == ASTNode.IF_STATEMENT || node.getType() == ASTNode.TRY_STATEMENT) {
				GraphNode temp = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode,newGraphNode);
				if (null != temp) {
					new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, temp);
				}

			}
			if (node.getType() == ASTNode.SWITCH_STATEMENT) {
				GraphNode temp = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode,newGraphNode);
				if (null != temp) {
					new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, temp);
				}
			}
			if (node.getType() == ASTNode.FOR_STATEMENT || node.getType() == ASTNode.DO_STATEMENT) {
				GraphNode tail = null;
				if (node.getNodes().size() == 2) {
					tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode,newGraphNode);
				} else if (node.getNodes().size() == 1) {
					tail = linkNode;
				}
				if (null != tail) {
					new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, tail);
				}
			}
			if (node.isCovered()) {
				newGraphNode.setBackgroundColor(H_COLOR);
			}
			if (null != newGraphNode) {
				newGraphNode.setTooltip(new Label(node.getInfo()));
				nodeAdmin.set(node, newGraphNode);
			}
			return newGraphNode;
		}

		return super.createView(g, node, linkNode, loopNode, switchNode,newGraphNode);

	}

	@Override
	public GraphNode createView(Graph g, INode nodes, GraphNode linkNode) {
		return createView(g, nodes, linkNode, null);
	}

	@Override
	public GraphNode createView(Graph g, INode nodes) {
		return createView(g, nodes, null);
	}

	@Override
	public GraphNode createView(Graph g, INode node, GraphNode linkNode, GraphNode loopNode) {
		return createView(g, node, linkNode, loopNode, null,null);
	}
}
