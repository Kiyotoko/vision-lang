package io.scvis.geometry;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.errorprone.annotations.DoNotCall;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public class Layout2D implements Border2D, Cloneable {

	@JsonProperty(value = "local", index = 5)
	@Nonnull
	private final Border2D local;

	@JsonIgnore
	@CheckForNull
	private Border2D parent;

	@JsonProperty(value = "position", index = 6)
	@Nonnull
	private Vector2D position = Vector2D.ZERO;
	@JsonProperty(value = "rotation", index = 7)
	private double rotation = 0;

	@JsonCreator
	public Layout2D(@JsonProperty("local") @Nonnull Border2D local,
			@JsonProperty("position") @Nonnull Vector2D position, @JsonProperty("rotation") double rotation) {
		this.local = local;
		this.position = position;
		this.rotation = rotation;
	}

	@Override
	public Layout2D clone() {
		return new Layout2D(local);
	}

	public Layout2D(@Nonnull Border2D local) {
		this.local = local;
	}

	public void applyTransformation() {
		if (changed || parent == null) {
			this.parent = local.translate(position).rotate(rotation);
			this.changed = false;
		}
	}

	@Override
	public boolean contains(@Nonnull Vector2D vector2D) {
		applyTransformation();
		return parent.contains(vector2D);
	}

	@Override
	public boolean intersects(@Nonnull Border2D border2D) {
		applyTransformation();
		return parent.intersects(border2D);
	}

	@JsonIgnore
	protected boolean changed;

	@Override
	@Nonnull
	public Border2D translate(double x, double y) {
		this.position = position.add(x, y);
		this.changed = true;
		return this;
	}

	@Override
	@Nonnull
	public Border2D rotate(double a) {
		this.rotation += a;
		this.changed = true;
		return this;
	}

	@Override
	@DoNotCall
	@Nonnull
	public Border2D rotate(@Nonnull Vector2D center, double a) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Nonnull
	public Vector2D centroid() {
		applyTransformation();
		return parent.centroid();
	}

	@JsonIgnore
	public Border2D getBorderInLocal() {
		return local;
	}

	@JsonIgnore
	public Border2D getBorderInParent() {
		applyTransformation();
		return parent;
	}

	@Nonnull
	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(@Nonnull Vector2D position) {
		this.changed = true;
		this.position = position;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.changed = true;
		this.rotation = rotation;
	}
}
