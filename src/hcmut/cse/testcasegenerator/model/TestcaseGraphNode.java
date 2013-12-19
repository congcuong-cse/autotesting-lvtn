package hcmut.cse.testcasegenerator.model;

import java.util.ArrayList;

public class TestcaseGraphNode {
	private int id;
	private String text;
	private String info;
	private int type = 1;
	private ArrayList<TestcaseGraphConnection> sourceConnections = new ArrayList<TestcaseGraphConnection>();
	private ArrayList<TestcaseGraphConnection> targetConnections = new ArrayList<TestcaseGraphConnection>();
	
	public TestcaseGraphNode(TestcaseGraph g, String t){
		this.id = g.increateNodeCount();
		this.text = t;
		g.addNode(this);	
	}
	
	public TestcaseGraphNode(TestcaseGraph g, String t, int type){
		this.id = g.increateNodeCount();
		this.text = t;
		this.type = type;
		g.addNode(this);	
	}
	
	public TestcaseGraphNode(TestcaseGraphNode n){
		this.id = n.getId();
		this.text = n.getText();
		this.info = n.getInfo();
		this.type = n.getType();
		this.sourceConnections = n.getSourceConnections();
		this.targetConnections = n.getTargetConnections();
	}
	
	
	public int getId(){
		return this.id;
	}
	
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getInfo(){
		return this.info;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
	
	public ArrayList<TestcaseGraphConnection> getSourceConnections(){
		return this.sourceConnections;
	}
	
	public ArrayList<TestcaseGraphConnection> getTargetConnections(){
		return this.targetConnections;
	}
	
	@Override 
	public boolean equals(Object o){
		TestcaseGraphNode n = (TestcaseGraphNode) o;
		return this.id == n.getId();
	}
	
	@Override
	public String toString(){
		return this.text;
	}
}

