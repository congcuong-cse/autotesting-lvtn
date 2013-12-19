package hcmut.cse.testcasegenerator;

import static de.htwg.flowchartgenerator.utils.Statics.NODE_DEFAULT_TEXT;
import static de.htwg.flowchartgenerator.utils.Statics.RED_COLOR;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import hcmut.cse.testcasegenerator.model.TestcaseGraph;
import hcmut.cse.testcasegenerator.model.TestcaseGraphNode;
import hcmut.cse.testcasegenerator.model.TestcaseGraphConnection;

import de.htwg.flowchartgenerator.ast.model.INode;

/**
 * Contains the algorithm that connects the nodes with the corresponding ones.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class TestcaseGraphBuilder{
	private TestcaseNodeAdmin nodeAdmin = TestcaseNodeAdminFactory.getInstance();
	private TestcaseGraphNode nodeEnd; 
	private TestcaseGraphNode nodeTemp;

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
	public TestcaseGraphNode createView(TestcaseGraph g, INode node, TestcaseGraphNode linkNode, TestcaseGraphNode loopNode, TestcaseGraphNode switchNode, TestcaseGraphNode parent) {
		TestcaseGraphNode newGraphNode = null;

		if (node.getType() ==  ASTNode.SWITCH_CASE){
			String value = node.getValue();
			newGraphNode = new TestcaseGraphNode(g, value);
			if (node.getNodes().size() > 0) {
				TestcaseGraphNode nn = createView(g, node.getNodes().get(0), nodeTemp, loopNode, switchNode, newGraphNode);
				
				if (null != nn)
					nodeTemp = nn;
					new TestcaseGraphConnection(g, newGraphNode, nn);
			}
//			if (node.getNodes().size() == 0 && linkNode != null) {
//				new GraphConnection(g, ZestStyles.CONNECTIONS_DIRECTED, newGraphNode, linkNode);
//			}
			if(node.getNodes().size()== 0){
				new TestcaseGraphConnection(g, newGraphNode, nodeTemp);
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
			newGraphNode = new TestcaseGraphNode(g, expression);

			if (expression.equals("START")) {
				nodeAdmin.setRoot(node);
				newGraphNode.setType(0);
				nodeEnd =  new TestcaseGraphNode(g, "END", 0);
			}
			if (node.getNodes().size() > 0) {
				TestcaseGraphNode nn = createView(g, node.getNodes().get(0), linkNode, loopNode, switchNode, newGraphNode);
				if (null != nn)
					new TestcaseGraphConnection(g, newGraphNode, nn);
			}
			if (node.getNodes().size() == 0 && linkNode != null) {
				new TestcaseGraphConnection(g, newGraphNode, linkNode);
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
				TestcaseGraphNode yesGraphNode = null;
				TestcaseGraphNode noGraphNode = null;
				// The second or third node is the main stream.
				TestcaseGraphNode linkGraphNode = null;
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
			TestcaseGraphNode newGraphNode_;
			TestcaseGraphNode tail = null;
			tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, null);
			if (tail == null) {
				tail = linkNode;
			}
			nodeTemp = tail;
			TestcaseGraphNode lastButOne = null;
			if (node.getSize() > 2) {
				INode tn_ = node.getNodes().get(node.getSize() - 1);
				//INode tn = (node.getNodes().get(i).getSize() > 0) ? node.getNodes().get(i).getNodes().get(0) : node.getNodes().get(i);
				newGraphNode_ = createView(g, tn_, (lastButOne == null) ? tail : lastButOne, tail, lastButOne, newGraphNode);
				if(newGraphNode_.getText().contains("default :")){
					newGraphNode_.setText("default :");
					newGraphNode_.setType(0);
				}
				else
					newGraphNode_.setType(2);
				for (int i = node.getSize() - 2; i >= 2; i--) {
					INode tn = node.getNodes().get(i);
					//INode tn = (node.getNodes().get(i).getSize() > 0) ? node.getNodes().get(i).getNodes().get(0) : node.getNodes().get(i);
					lastButOne = createView(g, tn, (lastButOne == null) ? tail : lastButOne, tail, lastButOne, newGraphNode);
					if(lastButOne.getText().contains("default :")){
						lastButOne.setType(0);
						lastButOne.setText("default :");
					}
					else
						lastButOne.setType(2);
					(new TestcaseGraphConnection(g,lastButOne , newGraphNode_ )).setLineColor(RED_COLOR);
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
			TestcaseGraphNode ForNode = new TestcaseGraphNode(g, node.getValue(), 0);
			List<INode> inits = node.getNodes().get(node.getNodes().size() -2).getNodes();
			newGraphNode = ForNode;
			for(int i = inits.size()-1; i>=0; i--){
				TestcaseGraphNode newGraphNode__ = new TestcaseGraphNode(g, inits.get(i).getValue());
				new TestcaseGraphConnection(g, newGraphNode__, newGraphNode);
				newGraphNode= newGraphNode__;
			}
			List<INode> updates = node.getNodes().get(node.getNodes().size() -1).getNodes();
			TestcaseGraphNode head_tail = ForNode;
			for(int i = updates.size()-1; i>=0; i--){
				TestcaseGraphNode newGraphNode__ = new TestcaseGraphNode(g, updates.get(i).getValue());
				new TestcaseGraphConnection(g, newGraphNode__, head_tail);
				head_tail = newGraphNode__;
			}
			if (node.getNodes().size() != 0) {
				TestcaseGraphNode head = null;
				TestcaseGraphNode tail = null;
				if (node.getNodes().size() - 3 == 2) {
					tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, null);
				} else if (node.getNodes().size() -3 == 1) {
					tail = linkNode;
				}
				
				head = createView(g, node.getNodes().get(0), head_tail, tail);
				

				new TestcaseGraphConnection(g, ForNode, createConditionView(g, node.getNodes().get(node.getNodes().size() -3), head, tail));
			}

		}
		/************************************
		 * Handles the DO-WHILE-Loops
		 ************************************/
		if (node.getType() == ASTNode.DO_STATEMENT) {
			newGraphNode = new TestcaseGraphNode(g, node.getValue(), 0);
			if (node.getNodes().size() == 3) {
				TestcaseGraphNode body = null;// the inner part
				TestcaseGraphNode tail = null;// the main stream
				
				if (node.getNodes().get(1).getType() != -1) {
					tail = createView(g, node.getNodes().get(1), linkNode, loopNode, switchNode, newGraphNode);
				} else{
					tail = linkNode;
				}
				TestcaseGraphNode whil = createConditionView(g, node.getNodes().get(2), newGraphNode, tail);
				body = createView(g, node.getNodes().get(0), whil, loopNode, switchNode, newGraphNode);
				new TestcaseGraphConnection(g, newGraphNode, body);
			}

		}

		/************************************
		 * Handles the CONTINUE STATEMENT
		 ************************************/
		if (node.getType() == ASTNode.CONTINUE_STATEMENT) {
			if (null != linkNode && parent != linkNode && linkNode != switchNode) {
				new TestcaseGraphConnection(g, parent, linkNode);
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
//			if (null != loopNode && linkNode != loopNode && linkNode != switchNode) {
//				new TestcaseGraphConnection(g, linkNode, loopNode);
//			}
		}
		
		/************************************
		 * Handles the RETURN STATEMENTS
		 ************************************/
		if(node.getType() == ASTNode.RETURN_STATEMENT){
			newGraphNode = new TestcaseGraphNode(g, node.getValue());
			if (null != newGraphNode) {
				new TestcaseGraphConnection(g, newGraphNode, nodeEnd);
			}
		}
		
		/************************************
		 * For the case that there is no last node
		 ************************************/

		if (node.getNodes().size() == 0 && linkNode == null) {
			//GraphNode endGraphNode = new GraphNode(g, SWT.NONE, "END");
			

			if (node.getType() != 0 && node.getType() != ASTNode.RETURN_STATEMENT && null != newGraphNode) {
				new TestcaseGraphConnection(g, newGraphNode, nodeEnd);
			}
			if (null == newGraphNode) {
				newGraphNode = nodeEnd;
			}
			//endGraphNode.setBackgroundColor(RED_COLOR);
		}
		if (null != newGraphNode && node.getType() != ASTNode.BREAK_STATEMENT) {
			nodeAdmin.add(new TestcaseNodeContainer(node, newGraphNode));
		}
		
		return newGraphNode;
	}

	public TestcaseGraphNode createConditionView(TestcaseGraph g, INode nodes, TestcaseGraphNode yes, TestcaseGraphNode no){
		if(nodes.getOperator() != null
				&& ( nodes.getOperator().contains( "&&") || nodes.getOperator().contains("&") )){
			TestcaseGraphNode rightGraphNode = createConditionView(g, nodes.getNodes().get(1), yes, no);
			TestcaseGraphNode leftGraphNode = createConditionView(g, nodes.getNodes().get(0), rightGraphNode, no);
			return leftGraphNode;
		}
		else if(nodes.getOperator() != null
				&& ( nodes.getOperator().contains("||") || nodes.getOperator().contains("|") )){
			TestcaseGraphNode rightGraphNode = createConditionView(g, nodes.getNodes().get(1), yes, no);
			TestcaseGraphNode leftGraphNode = createConditionView(g, nodes.getNodes().get(0), yes, rightGraphNode);
			return leftGraphNode;
		}
		else if(nodes.getOperator() != null
				&& nodes.getOperator().contains("!")){
			return createConditionView(g, nodes.getNodes().get(0), no, yes);
		}
		else{		
			TestcaseGraphNode newGraphNode = new TestcaseGraphNode(g, nodes.getValue(),2);
			//System.out.println(newGraphNode.toString());
			if( yes != null){
				new TestcaseGraphConnection(g, newGraphNode, yes);
			}
			if( no != null){
				(new TestcaseGraphConnection(g, newGraphNode, no)).setLineColor(RED_COLOR);
			}
			return newGraphNode;
		}
	}
	
	public TestcaseGraphNode createView(TestcaseGraph g, INode nodes, TestcaseGraphNode linkNode) {
		return createView(g, nodes, linkNode, null);
	}

	public TestcaseGraphNode createView(TestcaseGraph g, INode nodes) {
		return createView(g, nodes, null);
	}

	public TestcaseGraphNode createView(TestcaseGraph g, INode node, TestcaseGraphNode linkNode, TestcaseGraphNode loopNode) {
		return createView(g, node, linkNode, loopNode, null, null);
	}
	
}
