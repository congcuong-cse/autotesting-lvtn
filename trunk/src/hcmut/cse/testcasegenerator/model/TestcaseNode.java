package hcmut.cse.testcasegenerator.model;
import java.util.*;


public class TestcaseNode {
	private TestcaseGraphNode node ;
	private ArrayList<TestcaseNode> before = new ArrayList<TestcaseNode>();
	
	
	public TestcaseNode(TestcaseGraphNode node, ArrayList<TestcaseNode> before){
		this.node = new TestcaseGraphNode(node);
		this.before = before;
	}
	
	
	public TestcaseNode(TestcaseNode n){
		this.node = new TestcaseGraphNode( n.getNode());
		ArrayList<TestcaseNode> result = new ArrayList<TestcaseNode>();
		if(n.getBefore() != null){
			for(TestcaseNode i : n.getBefore()){
				result.add(new TestcaseNode(i));
			}
		}
		this.before = result;
	}
	
	public void setNode(TestcaseGraphNode node){
		this.node = node;
	}
	
	public TestcaseGraphNode getNode(){
		return this.node;
	}
	
	public void setBefore(ArrayList<TestcaseNode> before){
		ArrayList<TestcaseNode> result = new ArrayList<TestcaseNode>();
		if(before != null){
			for(TestcaseNode i : before){
				result.add(new TestcaseNode(i));
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
		 return  "<" + this.node.getType() + "," + this.node.getDeep() + "> " + this.node.getText() ;
	}

	

}
