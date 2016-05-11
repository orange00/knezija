package knezija.models;

public interface Record<T> {
	/**
	 * Returns whether two records are logically the same. 
	 * Two records are logically the same if all their fields are 
	 * the same. Does not compare the id field(since the id field comparison 
	 * belongs to the physical and not logical comparison).
	 * @param other
	 * @return
	 */
	boolean logicallyEquals(T other);
}
