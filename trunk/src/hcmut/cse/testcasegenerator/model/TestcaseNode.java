package hcmut.cse.testcasegenerator.model;
import java.util.*;

import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;

public class TestcaseNode {
	private GraphNode node ;
	private ArrayList<TestcaseNode> before = new ArrayList<TestcaseNode>();
	
	
	public TestcaseNode(GraphNode node, ArrayList<TestcaseNode> before){
		this.node = node;
		ArrayList<TestcaseNode> result = new ArrayList<TestcaseNode>();
		if(before != null){
			for(TestcaseNode i : before){
				result.add(i);
			}
		}
		this.before = result;
	}
	
	public TestcaseNode(TestcaseNode n){
		this.node = n.getNode();
		ArrayList<TestcaseNode> result = new ArrayList<TestcaseNode>();
		if(n.getBefore() != null){
			for(TestcaseNode i : n.getBefore()){
				result.add(i);
			}
		}
		this.before = result;
	}
	
	public void setNode(GraphNode node){
		this.node = node;
	}
	
	public GraphNode getNode(){
		return this.node;
	}
	
	public void setBefore(ArrayList<TestcaseNode> before){
		ArrayList<TestcaseNode> result = new ArrayList<TestcaseNode>();
		if(before != null){
			for(TestcaseNode i : before){
				result.add(i);
			}
		}
		this.before = result;
	}
	
	public ArrayList<TestcaseNode> getBefore(){
		return this.before;
	}
	
	@Override 
	public boolean equals(Object o){
		TestcaseNode n = (TestcaseNode) o;
		return this.getNode().equals(n.getNode());
	}
	
	@Override
	public String toString(){
		return this.node.getText();
	}

	

}
