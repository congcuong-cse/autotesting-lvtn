package de.htwg.flowchartgenerator.editor;

import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Container for the GraphViewer
 */
public class GraphViewContainer {
	private static GraphViewer gf;

	public static GraphViewer getGf() {
		return gf;
	}

	public static void setGf(GraphViewer gf) {
		GraphViewContainer.gf = gf;
	}

}
