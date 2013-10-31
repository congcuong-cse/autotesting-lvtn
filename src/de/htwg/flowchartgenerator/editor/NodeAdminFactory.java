package de.htwg.flowchartgenerator.editor;

/**
 * Factory for the node administrator
 * 
 * @author Aldi Alimucaj
 * 
 */
public class NodeAdminFactory {
	private static NodeAdmin nodeAdmin = new NodeAdmin();

	private NodeAdminFactory() {
	}

	public static NodeAdmin getInstance() {
		return nodeAdmin;
	}

}
