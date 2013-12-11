package hcmut.cse.testcasegenerator;
import java.util.*;
import hcmut.cse.testcasegenerator.model.TestcaseNode;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;

import static de.htwg.flowchartgenerator.utils.Statics.RED_COLOR;

public class TestcaseGenerator {
	private ArrayList<ArrayList<TestcaseNode>> tests = new ArrayList<ArrayList<TestcaseNode>>();
	
	public ArrayList<ArrayList<TestcaseNode>> getTests(){
		return this.tests;
	}

	public ArrayList<TestcaseNode> getTruePath(Graph g, TestcaseNode startNode, TestcaseNode endNode){
		ArrayList<TestcaseNode> result =  new ArrayList<TestcaseNode>();
		result.add(startNode);
		
		Stack<TestcaseNode> stack = new Stack<TestcaseNode>();
		stack.push(startNode);
		
		while(!stack.empty()){
			TestcaseNode n = stack.pop();
			if(endNode.equals(n)){
				return result;
			}
			else{
				List<GraphConnection> gn = n.getNode().getSourceConnections();
				ArrayList<TestcaseNode> newBefore =  new ArrayList<TestcaseNode>();
				if( n.getBefore() != null){
					for (TestcaseNode i : n.getBefore()) {
					newBefore.add(i);
					}
				}
				newBefore.add(n);
				TestcaseNode newTestcaseNode = null; 
				if(gn.size() == 1){
					newTestcaseNode = new TestcaseNode(gn.get(0).getDestination(), newBefore);
					stack.push(newTestcaseNode);
					result.add(newTestcaseNode);
				}
				else if(gn.size() == 2){
					if(gn.get(0).getLineColor() == RED_COLOR){
						newTestcaseNode = new TestcaseNode(gn.get(1).getDestination(), newBefore);
						if(result.contains(newTestcaseNode)){
							newTestcaseNode = new TestcaseNode(gn.get(0).getDestination(), newBefore);
						}
						stack.push(newTestcaseNode);
						result.add(newTestcaseNode);
					}
					else{
						newTestcaseNode = new TestcaseNode(gn.get(0).getDestination(), newBefore);
						if(result.contains(newTestcaseNode)){
							newTestcaseNode = new TestcaseNode(gn.get(1).getDestination(), newBefore);
						}
						stack.push(newTestcaseNode);
						result.add(newTestcaseNode);
					}
				}
			}
		}
		
		return null;
	}
	
	public void travel(Graph g){
		if(g.getNodes().size()>2){
			TestcaseNode startNode = new TestcaseNode((GraphNode) g.getNodes().get(0),null);
			TestcaseNode endNode = new TestcaseNode((GraphNode) g.getNodes().get(1),null);
			this.tests.add(getTruePath(g, startNode, endNode));
		}
	}
	
	public void breadthFirstTraversal(Graph g){
		
		if(g.getNodes().size()>2){
			TestcaseNode startNode = new TestcaseNode((GraphNode) g.getNodes().get(0),null);
			TestcaseNode endNode = new TestcaseNode((GraphNode) g.getNodes().get(1),null);
			
			//addTruePath
			this.tests.add(getTruePath(g, startNode, endNode));
			
			ArrayList<TestcaseNode> notVisited = new ArrayList<TestcaseNode>();
			for(int i=0; i< g.getNodes().size(); i++){
				TestcaseNode tmpNode = new TestcaseNode( (GraphNode) g.getNodes().get(i), null);
				notVisited.add(tmpNode);
			}
			Queue<TestcaseNode> q = new LinkedList<TestcaseNode>();
			q.add(startNode);
			while(!q.isEmpty()){
				//System.out.println(q.toString());
				TestcaseNode n = q.poll();
				System.out.println("<<<" + n.toString());
				List<GraphConnection> gn = n.getNode().getSourceConnections();
				TestcaseNode newTestcaseNode1 = null;
				TestcaseNode newTestcaseNode2 = null;
				ArrayList<TestcaseNode> newBefore =  new ArrayList<TestcaseNode>();
				if( n.getBefore() != null){
					for (TestcaseNode i : n.getBefore()) {
					newBefore.add(i);
					}
				}
				newBefore.add(n);
				if(gn.size()>0){
					newTestcaseNode1 = new TestcaseNode(gn.get(0).getDestination(),newBefore);
				}
				if(gn.size()>1){
					newTestcaseNode2 = new TestcaseNode(gn.get(1).getDestination(),newBefore);
				}
				
				if(gn.size() == 1 && notVisited.contains(newTestcaseNode1) ){
					q.add(newTestcaseNode1);
					notVisited.remove(newTestcaseNode1);
				}
				else if (gn.size ()== 2){
					if(gn.get(0).getLineColor() == RED_COLOR){
						//true
						if(notVisited.contains(newTestcaseNode2)){
							q.add(newTestcaseNode2);
							notVisited.remove(newTestcaseNode2);
						}
						//newtest
						ArrayList<TestcaseNode> newtest = new ArrayList<TestcaseNode>();
						for( TestcaseNode i : newTestcaseNode1.getBefore()){
							newtest.add(i);
						}
						for( TestcaseNode j : getTruePath(g, newTestcaseNode1, endNode)){
							newtest.add(j);
						}
						this.tests.add(newtest);
						//flase
						if(notVisited.contains(newTestcaseNode1)){
							q.add(newTestcaseNode1);
							notVisited.remove(newTestcaseNode1);
						}
						
					}
					else{
						//true
						if(notVisited.contains(newTestcaseNode1)){
							q.add(newTestcaseNode1);
							notVisited.remove(newTestcaseNode1);
						}
						//newtest
						ArrayList<TestcaseNode> newtest = new ArrayList<TestcaseNode>();
						for( TestcaseNode i : newTestcaseNode1.getBefore()){
							newtest.add(i);
						}
						for( TestcaseNode j : getTruePath(g, newTestcaseNode2, endNode)){
							newtest.add(j);
						}
						this.tests.add(newtest);
						//false
						if(notVisited.contains(newTestcaseNode2)){
							q.add(newTestcaseNode2);
							notVisited.remove(newTestcaseNode2);

						}
						
					}
				}
				
			}
			
		}
		

	}
}
