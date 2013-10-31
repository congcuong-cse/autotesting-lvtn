package de.htwg.flowchartgenerator.controller;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.editor.FlowChartOutlinePage;
import de.htwg.flowchartgenerator.editor.GraphBuilder4redraw;
import de.htwg.flowchartgenerator.editor.GraphViewContainer;
import de.htwg.flowchartgenerator.editor.IGraphBuilder;
import de.htwg.flowchartgenerator.editor.NodeAdmin;
import de.htwg.flowchartgenerator.editor.NodeAdminFactory;
import de.htwg.flowchartgenerator.editor.FlowChartOutlinePage.OutlineModel;

/**
 * The node folding action.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class NodeFolder {
	private GraphNode node2collapse;
	private Graph graph;
	private NodeAdmin nodeAdmin = NodeAdminFactory.getInstance();
	private GraphViewer gv = GraphViewContainer.getGf();
	boolean folded;

	/**
	 * Prepares the graph and the graphviewer to be redrawn. it cleans up the
	 * old nodes and connection, fills it with the new ones and applies the
	 * layout algorithm again to ensure that the new positions correspond to the
	 * new graph.
	 */
	private void prepare2collapse() {
		INode model;
		if ((model = nodeAdmin.get(node2collapse)) != null) {
			int type = model.getType();
			if (type == ASTNode.IF_STATEMENT || type == ASTNode.TRY_STATEMENT || type == ASTNode.SWITCH_STATEMENT || type == ASTNode.FOR_STATEMENT
					|| type == ASTNode.DO_STATEMENT) {
				model.setFolded(folded);
			} else {
				if (type == ASTNode.BREAK_STATEMENT) {
					MessageDialog
							.openWarning(gv.getControl().getShell(), "Flow Plug-in", "Due to indirect connections this node cannot be collapsed");
				}
				return;
			}

		}
		INode root = nodeAdmin.getRoot();
		List<GraphNode> hide = graph.getNodes();
		for (int i = 0; i < hide.size(); i++) {
			GraphNode object = (GraphNode) hide.get(i);
			if (!object.isDisposed() && object.isVisible()) {
				object.setVisible(false);
			}

		}
		graph.getNodes().clear();
		graph.getConnections().clear();
		IGraphBuilder graphBuilder = new GraphBuilder4redraw();
		graphBuilder.createView(graph, root);
		OutlineModel outlineModel = new OutlineModel(graph.getNodes().size(), graph.getConnections().size());
//		MacCabeAnalyzer.analyzeMacCabe(outlineModel, graph);

		gv.applyLayout();

	}

	public GraphNode getNode2collapse() {
		return node2collapse;
	}

	public void setNode2collapse(GraphNode node2collapse) {
		this.node2collapse = node2collapse;
	}

	public NodeFolder(GraphNode node2collapse, Graph graph, boolean visible) {
		this.node2collapse = node2collapse;
		this.graph = graph;
		this.folded = visible;
		prepare2collapse();
	}

	public NodeFolder(GraphNode node2collapse) {
		super();
		this.node2collapse = node2collapse;
	}

}
