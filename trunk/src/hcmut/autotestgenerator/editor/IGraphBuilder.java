package hcmut.autotestgenerator.editor;

import hcmut.autotestgenerator.ast.model.INode;

import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;


/**
 * The Interface for the GraphBuilders
 * 
 * @author Aldi Alimucaj
 * 
 */
public interface IGraphBuilder {
	public GraphNode createView(Graph g, INode node);

	public GraphNode createView(Graph g, INode node, GraphNode linkNode);

	public GraphNode createView(Graph g, INode node, GraphNode linkNode, GraphNode loopNode);

	public GraphNode createView(Graph g, INode node, GraphNode linkNode, GraphNode loopNode, GraphNode switchNode, GraphNode parent);
}
