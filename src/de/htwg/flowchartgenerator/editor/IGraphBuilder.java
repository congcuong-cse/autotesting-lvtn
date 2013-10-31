package de.htwg.flowchartgenerator.editor;

import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import de.htwg.flowchartgenerator.ast.model.INode;

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
