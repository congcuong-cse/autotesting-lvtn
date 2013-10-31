package hcmut.autotestgenerator.utils;

import hcmut.autotestgenerator.ast.model.FNode;
import hcmut.autotestgenerator.ast.model.INode;

import org.eclipse.jdt.core.dom.ASTNode;


/**
 * The node normalizer
 * 
 * @author Aldi Alimucaj
 * 
 */
public final class NodeNormalizer {
	/**
	 * The nodes may not end with and end node. witch means that the next one in
	 * the row is null. in the case a if statement is the last one. This method
	 * prevents it.
	 * 
	 * @param node
	 * @return a normalized node
	 */
	public static INode normalize(INode node) {
		INode tmp = node;
		int index = 0;
		System.out.print("\nNormalizing ...");
		while (null != tmp && tmp.getNodes().size() > 0) {
			if (tmp.getType() == ASTNode.EXPRESSION_STATEMENT) {
				tmp = tmp.getNodes().get(0);
			} else if (tmp.getType() == ASTNode.IF_STATEMENT && tmp.getNodes().size() > 1) {
				tmp = tmp.getNodes().get(1);
				if (tmp.getType() <= 0) {
					tmp.setValue("FINISH");
					tmp.setType(0);
					break;
				}
			} else if (tmp.getType() == ASTNode.FOR_STATEMENT && tmp.getNodes().size() <= 1) {
				INode finish = new FNode("FINISH", 0);
				tmp.addNode(finish);
				break;
			} else if (tmp.getType() == ASTNode.DO_STATEMENT && tmp.getNodes().size() <= 1) {
				INode finish = new FNode("FINISH", 0);
				tmp.addNode(finish);
				break;
			} else if (tmp.getType() == ASTNode.SWITCH_STATEMENT && tmp.getNodes().size() >= 2) {
				tmp = tmp.getNodes().get(1);
				if (tmp.getType() <= 0) {
					tmp.setValue("FINISH");
					tmp.setType(0);
					break;
				}
			} else if (tmp.getType() == ASTNode.TRY_STATEMENT && tmp.getNodes().size() >= 1) {
				tmp = tmp.getNodes().get(1);
				if (tmp.getType() <= 0) {
					tmp.setValue("FINISH");
					tmp.setType(0);
					break;
				}
			} else {
				if (tmp.getNodes().size() > 1) {
					tmp = tmp.getNodes().get(1);
				} else {
					tmp = tmp.getNodes().get(0);
				}
			}
		}
		System.out.println("Done!");
		return node;
	}

	private NodeNormalizer() {
	};
}
