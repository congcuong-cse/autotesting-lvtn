package de.htwg.flowchartgenerator.utils;

import de.htwg.flowchartgenerator.ast.model.INode;
/**
 * Utilities for node operations.
 * @author Aldi Alimucaj
 *
 */
public class NodeUtilities {
	public static void copy(INode paste, INode copy){
		if(copy.isCovered()){
			paste.setCovered();
		}
		paste.setInfo(copy.getInfo());
		paste.setNodes(copy.getNodes());
		paste.setType(copy.getType());
		paste.setValue(copy.getValue());
		
	}
}
