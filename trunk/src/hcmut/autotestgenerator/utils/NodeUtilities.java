package hcmut.autotestgenerator.utils;

import hcmut.autotestgenerator.ast.model.INode;
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
