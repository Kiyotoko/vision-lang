package io.scvis.geometry;

import java.io.Serializable;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * The Layout3D class represents a 3D layout with position and rotations around
 * two axes.
 * 
 * @author karlz
 */
public class Layout3D implements Border3D, Cloneable, Serializable {

	private static final long serialVersionUID = -4505235043373481208L;

	@Nonnull
	private final Border3D local;
	@CheckForNull
	private transient Border3D parent;

	@Nonnull
	private Vector3D position;
	private double rotationA;
	private double rotationB;

	/**
	 * Constructs a Layout3D object with the specified local border, position, and
	 * rotations.
	 * 
	 * @param local     The local border of the layout.
	 * @param position  The position of the layout.
	 * @param rotationA The rotation around the first axis.
	 * @param rotationB The rotation around the second axis.
	 */
	public Layout3D(@Nonnull Border3D local, @Nonnull Vector3D position, double rotationA, double rotationB) {
		this.local = local;
		this.position = position;
		this.rotationA = rotationA;
		this.rotationB = rotationB;
	}

	@Override
	public Layout3D clone() {
		return new Layout3D(local);
	}

	/**
	 * 
	 * Constructs a Layout3D object with the specified local border.
	 * 
	 * @param local The local border of the layout.
	 */
	public Layout3D(@Nonnull Border3D local) {
		this.local = local;
		this.position = Vector3D.ZERO;
	}

	/**
	 * Applies the transformation to the layout. Recalculates the parent border if
	 * there are changes.
	 */
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

	/**
	 * Returns the local border of the layout.
	 * 
	 * @return The local border of the layout.
	 */
	@Nonnull
	public Border3D getBorderInLocal() {
		return local;
	}

	/**
	 * Returns the parent border of the layout. Applies the transformation before
	 * returning the parent border.
	 * 
	 * @return The parent border of the layout.
	 */
	@Nonnull
	public Border3D getBorderInParent() {
		applyTransformation();
		return parent;
	}

	/**
	 * Returns the position of the layout.
	 * 
	 * @return The position of the layout.
	 */
	@Nonnull
	public Vector3D getPosition() {
		return position;
	}

	/**
	 * Sets the position of the layout. Sets the changed flag to true.
	 * 
	 * @param position The position to set.
	 */
	public void setPosition(@Nonnull Vector3D position) {
		this.changed = true;
		this.position = position;
	}

	/**
	 * Returns the rotation angle around the first axis of the layout.
	 * 
	 * @return The rotation angle around the first axis of the layout.
	 */
	public double getRotationA() {
		return rotationA;
	}

	/**
	 * Sets the rotation angle around the first axis of the layout. Sets the changed
	 * flag to true.
	 * 
	 * @param rotationA The rotation angle to set.
	 */
	public void setRotationA(double rotationA) {
		this.changed = true;
		this.rotationA = rotationA;
	}

	/**
	 * Returns the rotation angle around the second axis of the layout.
	 * 
	 * @return The rotation angle around the second axis of the layout.
	 */
	public double getRotationB() {
		return rotationB;
	}

	/**
	 * Sets the rotation angle around the second axis of the layout. Sets the
	 * changed flag to true.
	 * 
	 * @param rotationB The rotation angle to set.
	 */
	public void setRotationB(double rotationB) {
		this.changed = true;
		this.rotationB = rotationB;
	}
}
