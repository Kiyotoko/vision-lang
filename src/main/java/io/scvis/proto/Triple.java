package io.scvis.proto;

/**
 * A triple holds tree values, a left, a center and a right value.
 * 
 * @author karlz
 *
 * @param <L> the type of the left value
 * @param <C> the type of the center value
 * @param <R> the type of the right value
 * 
 * @see Pair
 */
public class Triple<L, C, R> {
	private L left;
	private C center;
	private R right;

	/**
	 * Constructs a new triple and sets the left, center and right value.
	 * 
	 * @param left   the left value
	 * @param center the right value
	 * @param right  the right value
	 */
	public Triple(L left, C center, R right) {
		this.left = left;
		this.center = center;
		this.right = right;
	}

	/**
	 * Constructs a new triple.
	 */
	public Triple() {

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
	 * Returns the current associated center value.
	 * 
	 * @return center the new value
	 */
	public C getCenter() {
		return center;
	}

	public void setCenter(C center) {
		this.center = center;
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
