package hcmut.cse.testcasegenerator.model;

import java.util.ArrayList;

public class TestcaseGraph {
	private int nodeCount = 0;
	private int connectionCount = 0;
	private ArrayList<TestcaseGraphNode> nodes = new ArrayList<TestcaseGraphNode>();
	private ArrayList<TestcaseGraphConnection> connections = new ArrayList<TestcaseGraphConnection>();

	public TestcaseGraph(){
		
	}
	
	public int getNodeCount(){
		return this.nodeCount;
	}
	
	public int getConnectionCount(){
		return this.connectionCount;
	}
	
	public int increateNodeCount(){
		this.nodeCount +=1;
		return this.nodeCount;
	}
	public int increateConnectionCount(){
		this.connectionCount +=1;
		return this.connectionCount;
	}
	
	public ArrayList<TestcaseGraphNode> getNodes(){
		return this.nodes;
	}
	
	public ArrayList<TestcaseGraphConnection> getConnections(){
		return this.connections;
	}
	
	public void addNode(TestcaseGraphNode node){
		this.nodes.add(node);
	}
	
	public void addConnection(TestcaseGraphConnection c){
		this.connections.add(c);
	}
}
