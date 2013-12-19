package hcmut.cse.testcasegenerator;
import org.eclipse.swt.graphics.Color;

import hcmut.cse.testcasegenerator.ast.*;
import hcmut.cse.testcasegenerator.model.TestcaseGraph;
import hcmut.cse.testcasegenerator.model.TestcaseGraphConnection;
import hcmut.cse.testcasegenerator.model.TestcaseGraphNode;

public class TestcaseGraphTest {

	/**
	 * @param args
	 */
	public final static Color WHITE = new Color(null, 255, 255, 255);
	public final static Color GRAY = new Color(null, 204, 204, 204);
	public static final Color H_COLOR = new Color(null, 255, 140, 0);
	public static final Color RED_COLOR = new Color(null, 200, 20, 20);
	public static final Color GREEN_COLOR = new Color(null, 80, 250, 90);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestcaseGraph graph = new TestcaseGraph();
		TestcaseGraphNode root = new TestcaseGraphNode(graph, "root");
		TestcaseGraphNode left = new TestcaseGraphNode(graph, "left");
		TestcaseGraphNode right = new TestcaseGraphNode(graph, "right");
		new TestcaseGraphConnection(graph, root, left);
		(new TestcaseGraphConnection(graph, root, right)).setLineColor(RED_COLOR);
		System.out.println(graph);
	}

}
