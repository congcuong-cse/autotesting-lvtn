

import java.io.Serializable;
/**
 * An extension of the class FNode. Adding informations about
 * the cover status of the node. 
 * @author Aldi Alimucaj
 *
 */
public class CoverNode extends FNode implements Serializable, Cloneable, INode {
	private static final long serialVersionUID = 4372661114947355355L;
	private int startOffset;
	private int endOffset;
	private boolean covered = false;


	public String toString() {
		return getValue();
	}

	// -------------------------- GETTER SETTER CONSTRUCTOR
	public boolean isCovered() {
		return covered;
	}

	public void setCovered() {
		this.covered = true;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}


	public CoverNode(String value, int type, int startOffset, int endOffset) {
		super(value,type);
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}


	public CoverNode(String value, int type) {
		super(value,type);
	}

	public CoverNode() {
	}
}
