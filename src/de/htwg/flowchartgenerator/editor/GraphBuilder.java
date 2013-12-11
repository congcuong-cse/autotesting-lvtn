package de.htwg.flowchartgenerator.editor;

import static de.htwg.flowchartgenerator.utils.Statics.GREEN_COLOR;
import static de.htwg.flowchartgenerator.utils.Statics.H_COLOR;
import static de.htwg.flowchartgenerator.utils.Statics.NODE_DEFAULT_TEXT;
import static de.htwg.flowchartgenerator.utils.Statics.RED_COLOR;

import org.eclipse.draw2d.Label;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import de.htwg.flowchartgenerator.ast.model.FNode;
import de.htwg.flowchartgenerator.ast.model.INode;

import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

/**
 * Contains the algorithm that connects the nodes with the corresponding ones.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class GraphBuilder implements IGraphBuilder {
	private NodeAdmin nodeAdmin = NodeAdminFactory.getInstance();
	private GraphNode nodeEnd; 
	private GraphNode nodeTemp;

	/**
	 * This method generates a graph with the nodes from the model. Calls itself recursively and should never return null.
	 * 
	 * @param g
	 *            is the graph to be painted on.
	 * @param node
	 *            is the actual node
	 * @param linkNode
	 *            is the next node to link the actual node.
	 * @param loopNode
	 *            is the the node pointing the the loop if the node is inside a loop.
	 * @param switchNode
	 *            is the head of a switch if the switch is parent of the actual node.
	 * 
	 * @return GraphNode
	 */
	@Override
	public GraphNode createView(Graph g, INode node, GraphNode linkNode, GraphNode loopNode, GraphNode switchNode, GraphNode parent) {
		GraphNode newGraphNode = null;

		if (node.getType() ==  ASTNode.SWITCH_CASE){
			String value = node.getValue();
			newGraphNode = new GraphNode(g, SWT.NONE, value);
			if (node.getNodes().size() > 0) {
				GraphNode nn = createView(g, node.getNodes().get(0), linkNode, loopNode, switchNode, newGraphNode);
				
				if (null != nn)
					nodeTemp = nn;
					new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, nn);
			}
//			if (node.getNodes().size() == 0 && linkNode != null) {
//				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, linkNode);
//			}
			if(node.getNodes().size()== 0){
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, nodeTemp);
			}
		}
		
		/************************************
		 * Handles the EXPRESSION STATEMENTS
		 ************************************/
		if (node.getType() == ASTNode.EXPRESSION_STATEMENT) {
			
			String expression = NODE_DEFAULT_TEXT;
			if (node.getValue().length() <= NODE_DEFAULT_TEXT.length()) {
				expression = node.getValue();
			}
			newGraphNode = new GraphNode(g, SWT.NONE, expression);

			if (expression.equals("START")) {
				newGraphNode.setBackgroundColor(GREEN_COLOR);
				nodeAdmin.setRoot(node);
				nodeEnd =  new GraphNode(g, SWT.NONE, "END");
				nodeEnd.setBackgroundColor(RED_COLOR);
			}
			if (node.getNodes().size() > 0) {
				GraphNode nn = createView(g, node.getNodes().get(0), linkNode, loopNode, switchNode, newGraphNode);
				if (null != nn)
					new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, nn);
			}
			if (node.getNodes().size() == 0 && linkNode != null) {
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, linkNode);
			}
		}
		/************************************
		 * Handles the IF-Statements
		 ************************************/
		if (node.getType() == ASTNode.IF_STATEMENT) {
			// If the if-Node has at least one statement than it has at least 2+1
			// Children!!! With Else-Part 3+1.
			INode conditionNode = node.getNodes().get(node.getSize() -1);
	
			if (node.getNodes().size() -1 > 0) {
				GraphNode yesGraphNode = null;
				GraphNode noGraphNode = null;
				// The second or third node is the main stream.
				GraphNode linkGraphNode = null;
				if (node.getNodes().size() -1 == 3) {
					// this is the last one the main stream
					INode tn = null;
					tn = (node.getNodes().get(2).getSize() > 0) ? node.getNodes().get(2).getNodes().get(0) : node.getNodes().get(2);						
					if (node.getNodes().get(1).getType() < 0) {
						noGraphNode = createView(g, tn, linkNode, loopNode, switchNode, newGraphNode);
					} else {
						linkGraphNode = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, newGraphNode);
						noGraphNode = createView(g, tn, linkGraphNode, loopNode, switchNode,newGraphNode);
					}
				} 
				else if (node.getNodes().size() -1 == 2) {
					// otherwise the second one has the internal. we connect the
					// if-st with his next child.
					if (node.getNodes().get(1).getType() < 0) {
						noGraphNode = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, newGraphNode);
					} else {
						linkGraphNode = createView(g, node.getNodes().get(1), linkGraphNode, loopNode, switchNode,newGraphNode);
						noGraphNode = linkGraphNode;
					}

				}
				else if (node.getNodes().size() -1 == 1 && node.getNodes().get(0).getType() != ASTNode.EMPTY_STATEMENT) {

					
				}
				else if (node.getNodes().size() -1 == 1 && node.getNodes().get(0).getType() == ASTNode.EMPTY_STATEMENT) {
					// otherwise the second one has the internal. we connect the
					// if-st with his next child.
					
					yesGraphNode = createView(g, node.getNodes().set(0, node.getNodes().get(0).getNodes().get(0)), linkNode, loopNode,
							switchNode, newGraphNode);

				}
				if (yesGraphNode == null){
					if(linkGraphNode != null){
						yesGraphNode = createView(g, node.getNodes().get(0), linkGraphNode, loopNode, switchNode, newGraphNode);
					}	
					else
						yesGraphNode = createView(g, node.getNodes().get(0), linkNode, loopNode, switchNode, newGraphNode);
				}
				if (noGraphNode == null){
					if (linkNode != null) {
						noGraphNode = linkNode;
					} else {
						noGraphNode = nodeEnd;
					}
				}
				newGraphNode = createConditionView(g, conditionNode, yesGraphNode, noGraphNode);
			}

				
		}

		/************************************
		 * Handles the Switch and SwitchCase statements.
		 ************************************/
		if (node.getType() == ASTNode.SWITCH_STATEMENT) {
			//TODO
			//newGraphNode = new GraphNode(g, SWT.NONE, node.getValue());
			GraphNode newGraphNode_;
			GraphNode tail = null;
			tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, null);
			if (tail == null) {
				tail = linkNode;
			}
			GraphNode lastButOne = null;
			if (node.getSize() > 2) {
				INode tn_ = node.getNodes().get(node.getSize() - 1);
				//INode tn = (node.getNodes().get(i).getSize() > 0) ? node.getNodes().get(i).getNodes().get(0) : node.getNodes().get(i);
				newGraphNode_ = createView(g, tn_, (lastButOne == null) ? tail : lastButOne, tail, lastButOne, newGraphNode);
				for (int i = node.getSize() - 2; i >= 2; i--) {
					INode tn = node.getNodes().get(i);
					//INode tn = (node.getNodes().get(i).getSize() > 0) ? node.getNodes().get(i).getNodes().get(0) : node.getNodes().get(i);
					(new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, lastButOne = createView(g, tn, (lastButOne == null) ? tail : lastButOne, tail, lastButOne, newGraphNode), newGraphNode_ 
						)).setLineColor(RED_COLOR);
					newGraphNode_ = lastButOne;
					if(i == 2){
						newGraphNode = newGraphNode_;
					}
				}
			}
		}
		/************************************
		 * Handles the FOR and WHILE-Loops No difference in path consuming between the to loops.
		 ************************************/
		if (node.getType() == ASTNode.FOR_STATEMENT) {
			newGraphNode = new GraphNode(g, SWT.NONE, node.getValue());
			
			if (node.getNodes().size() != 0) {
				GraphNode head = null;
				GraphNode tail = null;
				if (node.getNodes().size() - 1 == 2) {
					tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, newGraphNode);
				} else if (node.getNodes().size() -1 == 1) {
					tail = linkNode;
				}
				
				head = createView(g, node.getNodes().get(0), newGraphNode, tail);
				

				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, createConditionView(g, node.getNodes().get(node.getNodes().size() -1), head, tail));
			}

		}
		/************************************
		 * Handles the DO-WHILE-Loops
		 ************************************/
		if (node.getType() == ASTNode.DO_STATEMENT) {
			newGraphNode = new GraphNode(g, SWT.NONE, node.getValue());
			if (node.getNodes().size() == 3) {
				GraphNode body = null;// the inner part
				GraphNode tail = null;// the main stream
				
				if (node.getNodes().get(1).getType() != -1) {
					tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, newGraphNode);
				} else{
					tail = linkNode;
				}
				GraphNode whil = createConditionView(g, node.getNodes().get(2), newGraphNode, tail);
				body = createView(g, node.getNodes().get(0), whil, loopNode, switchNode, newGraphNode);
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, body);
			}

		}

		/************************************
		 * Handles the CONTINUE STATEMENT
		 ************************************/
		if (node.getType() == ASTNode.CONTINUE_STATEMENT) {
			if (null != linkNode && parent != linkNode && linkNode != switchNode) {
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, parent, linkNode);
			} else {
				return null;
			}
		}
		/************************************
		 * Handles the BREAK STATEMENT
		 ************************************/
		if (node.getType() == ASTNode.BREAK_STATEMENT) {
			// decides whether its a loop break or a switch break
			newGraphNode = (loopNode == null) ? linkNode : loopNode;
			if (null != loopNode && linkNode != loopNode && linkNode != switchNode) {
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, linkNode, loopNode);
			}
		}
		
		/************************************
		 * Handles the RETURN STATEMENTS
		 ************************************/
		if(node.getType() == ASTNode.RETURN_STATEMENT){
			newGraphNode = new GraphNode(g, SWT.NONE, node.getValue());
			if (null != newGraphNode) {
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, nodeEnd);
			}
		}
		
		/************************************
		 * For the case that there is no last node
		 ************************************/

		if (node.getNodes().size() == 0 && linkNode == null) {
			//GraphNode endGraphNode = new GraphNode(g, SWT.NONE, "END");
			

			if (node.getType() != 0 && node.getType() != ASTNode.RETURN_STATEMENT && null != newGraphNode) {
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, nodeEnd);
			}
			if (null == newGraphNode) {
				newGraphNode = nodeEnd;
			}
			//endGraphNode.setBackgroundColor(RED_COLOR);
		}
		if (node.isCovered()) {
			newGraphNode.setBackgroundColor(H_COLOR);
		}
		if (null != newGraphNode && node.getType() != ASTNode.BREAK_STATEMENT) {
			newGraphNode.setTooltip(new Label(node.getInfo()));
			nodeAdmin.add(new NodeContainer(node, newGraphNode));
		}
		
		return newGraphNode;
	}

	public GraphNode createConditionView(Graph g, INode nodes, GraphNode yes, GraphNode no){
		if(nodes.getOperator() != null
				&& ( nodes.getOperator().contains( "&&") || nodes.getOperator().contains("&") )){
			GraphNode rightGraphNode = createConditionView(g, nodes.getNodes().get(1), yes, no);
			GraphNode leftGraphNode = createConditionView(g, nodes.getNodes().get(0), rightGraphNode, no);
			return leftGraphNode;
		}
		else if(nodes.getOperator() != null
				&& ( nodes.getOperator().contains("||") || nodes.getOperator().contains("|") )){
			GraphNode rightGraphNode = createConditionView(g, nodes.getNodes().get(1), yes, no);
			GraphNode leftGraphNode = createConditionView(g, nodes.getNodes().get(0), yes, rightGraphNode);
			return leftGraphNode;
		}
		else if(nodes.getOperator() != null
				&& nodes.getOperator().contains("!")){
			return createConditionView(g, nodes.getNodes().get(0), no, yes);
		}
		else{		
			GraphNode newGraphNode = new GraphNode(g, SWT.NONE, nodes.getValue());
			//System.out.println(newGraphNode.toString());
			if( yes != null){
				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, yes);
			}
			if( no != null){
				(new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, no)).setLineColor(RED_COLOR);
			}
			return newGraphNode;
		}
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
		return createView(g, node, linkNode, loopNode, null, null);
	}
	
}
