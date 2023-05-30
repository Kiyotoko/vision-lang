package io.scvis.proto;

import javax.annotation.Nullable;

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
	@Nullable
	private L left;
	@Nullable
	private R right;

	/**
	 * Constructs a new pair and sets the left and right value.
	 * 
	 * @param left  the new left value
	 * @param right the new right value
	 */
	public Pair(@Nullable L left, @Nullable R right) {
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
	@Nullable
	public L getLeft() {
		return left;
	}

	/**
	 * Sets the left value.
	 * 
	 * @param left the new value
	 */
	public void setLeft(@Nullable L left) {
		this.left = left;
	}

	/**
	 * Returns the current associated right value.
	 * 
	 * @return the right value
	 */
	@Nullable
	public R getRight() {
		return right;
	}

	/**
	 * Sets the right value.
	 * 
	 * @param right the new right value
	 */
	public void setRight(@Nullable R right) {
		this.right = right;
	}
}
