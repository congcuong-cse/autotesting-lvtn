package de.htwg.flowchartgenerator.xml;

/**
 * A Covered elements with the information needed the recocgnize it from the
 * ASTVisiotr.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class CoveredElement {
	private String name;
	private String type;
	private int startOffset;
	private int endOffset;

	public String toString() {
		return name + " - " + type + " - " + startOffset + " - " + endOffset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public CoveredElement() {
	}

	public CoveredElement(String name, String type, int startOffset, int endOffset) {
		super();
		this.name = name;
		this.type = type;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endOffset;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + startOffset;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoveredElement other = (CoveredElement) obj;
		if (endOffset != other.endOffset)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startOffset != other.startOffset)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
