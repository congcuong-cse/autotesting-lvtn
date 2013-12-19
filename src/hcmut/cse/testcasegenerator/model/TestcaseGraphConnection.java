package hcmut.cse.testcasegenerator.model;

import org.eclipse.swt.graphics.Color;
public class TestcaseGraphConnection {
	private int id;
	private Color color = new Color(null, 204, 204, 204);
	private TestcaseGraphNode sourceNode;
	private TestcaseGraphNode destinationNode;
	
	public TestcaseGraphConnection(TestcaseGraph g, TestcaseGraphNode s, TestcaseGraphNode d){
		this.id = g.increateConnectionCount();
		this.sourceNode = s;
		s.getSourceConnections().add(this);
		this.destinationNode = d;
		s.getTargetConnections().add(this);
		g.addConnection(this);
	}
	
	public void setLineColor(Color c){
		this.color = c;
	}
	
	public TestcaseGraphNode getSource(){
		return this.sourceNode;
	}
	
	public TestcaseGraphNode getDestination(){
		return this.destinationNode;
	}
	
	public Color getLineColor(){
		return this.color;
	}
	
	@Override
	public String toString(){
		return this.sourceNode.toString() + " --> " + this.destinationNode.toString();
	}
}
