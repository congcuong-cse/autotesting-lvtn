package hcmut.autotestgenerator.controller;

import hcmut.autotestgenerator.editor.FlowChartOutlinePage;
import hcmut.autotestgenerator.editor.FlowChartOutlinePage.OutlineModel;
import hcmut.autotestgenerator.utils.Statics;

import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;


/**
 * Analyzer for the cyclomatic complexity.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class MacCabeAnalyzer {
	/**
	 * Analyze the nodes and connections from the graph. Build and expression
	 * and give a result about the approximately satisfaction of the complexity.
	 * 
	 * @param outlineModel
	 * @param g
	 */
	public static void analyzeMacCabe(OutlineModel outlineModel, Graph g) {
		int cons = outlineModel.getConnections();
		int nodes = outlineModel.getNodes();
		int result = (cons - nodes + 2);
		String generatorInfo = "MacCabe results" + "\nNodes: \t\t" + nodes + "\nConnections: \t" + cons + "\nLimit: \t\t" + Statics.MACCABE_MAX_RES
				+ "\n" + cons + " - " + nodes + " + 2 = " + result + "\nSatisfied :\t\t" + (result <= Statics.MACCABE_MAX_RES);
		GraphNode gn = new GraphNode(g, SWT.NONE, generatorInfo);
		gn.setLocation(500, 100);
		gn.setBackgroundColor(Statics.WHITE);
		gn.setHighlightColor(Statics.WHITE);
	}
}
