package io.scvis.geometry;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * The Layout2D class represents a 2D layout with position and rotation.
 * 
 * @author karlz
 */
public class Layout2D implements Border2D, Positionable2D, Cloneable {

	private static final long serialVersionUID = 7896521163278027263L;

	private final @Nonnull Border2D local;

	@CheckForNull
	private Border2D parent;

	private @Nonnull Vector2D position;
	private double rotation = 0;

	/**
	 * Constructs a Layout2D object with the specified local border, position, and
	 * rotation.
	 * 
	 * @param local    The local border of the layout.
	 * @param position The position of the layout.
	 * @param rotation The rotation angle of the layout.
	 */
	public Layout2D(@Nonnull Border2D local, @Nonnull Vector2D position, double rotation) {
		this.local = local;
		this.position = position;
		this.rotation = rotation;
	}

	@Override
	public Layout2D clone() {
		return new Layout2D(local);
	}

	/**
	 * Constructs a Layout2D object with the specified local border.
	 * 
	 * @param local The local border of the layout.
	 */
	public Layout2D(@Nonnull Border2D local) {
		this.local = local;
		this.position = Vector2D.ZERO;
	}

	/**
	 * 
	 * Applies the transformation to the layout. Recalculates the parent border if
	 * there are changes.
	 */
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

	/**
	 * Returns the local border of the layout.
	 * 
	 * @return The local border of the layout.
	 */
	public Border2D getBorderInLocal() {
		return local;
	}

	/**
	 * Returns the parent border of the layout. Applies the transformation before
	 * returning the parent border.
	 * 
	 * @return The parent border of the layout.
	 */
	public Border2D getBorderInParent() {
		applyTransformation();
		return parent;
	}

	@Override
	@Nonnull
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void setPosition(@Nonnull Vector2D position) {
		this.changed = true;
		this.position = position;
	}

	/**
	 * Returns the rotation angle of the layout.
	 * 
	 * @return The rotation angle of the layout.
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation angle of the layout. Sets the changed flag to true.
	 * 
	 * @param rotation The rotation angle to set.
	 */
	public void setRotation(double rotation) {
		this.changed = true;
		this.rotation = rotation;
	}
}
