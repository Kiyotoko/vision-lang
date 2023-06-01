package io.scvis.geometry;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Layout3D implements Border3D, Cloneable {
	@JsonProperty(value = "local", index = 5)
	@Nonnull
	private final Border3D local;
	@JsonIgnore
	@CheckForNull
	private transient Border3D parent;

	@JsonProperty(value = "position", index = 6)
	@Nonnull
	private Vector3D position = Vector3D.ZERO;
	@JsonProperty(value = "rotationA", index = 7)
	private double rotationA = 0;
	@JsonProperty(value = "rotationB", index = 7)
	private double rotationB = 0;

	@JsonCreator
	public Layout3D(@JsonProperty("local") @Nonnull Border3D local,
			@JsonProperty("position") @Nonnull Vector3D position, @JsonProperty("rotationA") double rotationA,
			@JsonProperty("rotationB") double rotationB) {
		this.local = local;
		this.position = position;
		this.rotationA = rotationA;
		this.rotationB = rotationB;
	}

	@Override
	public Layout3D clone() {
		return new Layout3D(local);
	}

	public Layout3D(@Nonnull Border3D local) {
		this.local = local;
	}

	public void applyTransformation() {
		if (changed || parent == null) {
			this.parent = local.translate(position).rotate(rotationA, rotationB);
			this.changed = false;
		}
	}

	@Override
	public boolean contains(@Nonnull Vector3D vector3D) {
		applyTransformation();
		return parent.contains(vector3D);
	}

	@Override
	public boolean intersects(@Nonnull Border3D border3D) {
		applyTransformation();
		return parent.intersects(border3D);
	}

	@JsonIgnore
	protected transient boolean changed;

	@Override
	@Nonnull
	public Border3D translate(double x, double y, double z) {
		this.position = position.add(x, y, z);
		this.changed = true;
		return this;
	}

	@Override
	@Nonnull
	public Border3D rotate(double a, double b) {
		this.rotationA += a;
		this.rotationB += b;
		this.changed = true;
		return this;
	}

	@Override
	@Nonnull
	public Border3D rotate(@Nonnull Vector3D center, double a, double b) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Nonnull
	public Vector3D centroid() {
		applyTransformation();
		return parent.centroid();
	}

	@JsonIgnore
	public Border3D getBorderInLocal() {
		return local;
	}

	@JsonIgnore
	public Border3D getBorderInParent() {
		applyTransformation();
		return parent;
	}

	@Nonnull
	public Vector3D getPosition() {
		return position;
	}

	public void setPosition(@Nonnull Vector3D position) {
		this.changed = true;
		this.position = position;
	}

	public double getRotationA() {
		return rotationA;
	}

	public void setRotationA(double rotationA) {
		this.changed = true;
		this.rotationA = rotationA;
	}

	public double getRotationB() {
		return rotationB;
	}

	public void setRotationB(double rotationB) {
		this.changed = true;
		this.rotationB = rotationB;
	}
}
