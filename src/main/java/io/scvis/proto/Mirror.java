package io.scvis.proto;

public abstract class Mirror<S, R> implements Reference<S> {

	private R reflection;

	public Mirror(S source, R reflection) {
		this.reflection = reflection;
	}

	public Mirror() {

	}

	public R getReflection() {
		return reflection;
	}

	public void setReflection(R reflection) {
		this.reflection = reflection;
	}

	private S source;

	public void setSource(S source) {
		this.source = source;
	}

	public S getSource() {
		return source;
	}

}
