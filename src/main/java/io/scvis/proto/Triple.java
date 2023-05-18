package io.scvis.proto;

public class Triple<L, C, R> {
	private L left;
	private C center;
	private R right;

	public Triple(L left, C center, R right) {
		this.left = left;
		this.center = center;
		this.right = right;
	}

	public Triple() {

	}

	public L getLeft() {
		return left;
	}

	public void setLeft(L left) {
		this.left = left;
	}

	public C getCenter() {
		return center;
	}

	public void setCenter(C center) {
		this.center = center;
	}

	public R getRight() {
		return right;
	}

	public void setRight(R right) {
		this.right = right;
	}
}
