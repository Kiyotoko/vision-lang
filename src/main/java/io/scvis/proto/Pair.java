package io.scvis.proto;

/**
 * A pair holds two values, a left and a right value.
 * 
 * @author karlz
 *
 * @param <L> the type of the left value
 * @param <R> the type of the right value
 * 
 * @see Triple
 */
public class Pair<L, R> {
	private L left;
	private R right;

	/**
	 * Constructs a new pair and sets the left and right value.
	 * 
	 * @param left  the new left value
	 * @param right the new right value
	 */
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Constructs a new pair.
	 */
	public Pair() {

	}

	/**
	 * Returns the current associated left value.
	 * 
	 * @return the left value
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * Sets the left value.
	 * 
	 * @param left the new value
	 */
	public void setLeft(L left) {
		this.left = left;
	}

	/**
	 * Returns the current associated right value.
	 * 
	 * @return the right value
	 */
	public R getRight() {
		return right;
	}

	/**
	 * Sets the right value.
	 * 
	 * @param right the new right value
	 */
	public void setRight(R right) {
		this.right = right;
	}
}
