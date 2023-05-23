package io.scvis.geometry;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.scvis.geometry.Shape.Circle;
import io.scvis.geometry.Shape.Polygon;
import io.scvis.geometry.Shape.Rectangle;

/**
 * The Border2D interface represents a 2D border or shape in geometry. It
 * provides methods for containment, intersection, translation, rotation, and
 * obtaining the centroid.
 * 
 * @author karlz
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = Polygon.class, name = "Polygon"), @Type(value = Rectangle.class, name = "Rectangle"),
		@Type(value = Circle.class, name = "Circle"), @Type(value = Area.class, name = "Area"),
		@Type(value = Kinetic2D.class, name = "Kinetic") })
@JsonSerialize
@JsonDeserialize
public interface Border2D {
	/**
	 * Checks if the specified point is contained within the border.
	 *
	 * @param vector2D the 2D vector representing the point
	 * @return true if the point is contained within the border, false otherwise
	 */
	@CheckReturnValue
	boolean contains(@Nonnull Vector2D vector2D);

	/**
	 * Checks if the border intersects with the specified border.
	 *
	 * @param border2D the 2D border to check intersection with
	 * @return true if the borders intersect, false otherwise
	 */
	@CheckReturnValue
	boolean intersects(@Nonnull Border2D border2D);

	/**
	 * Translates the border by the specified translation values.
	 *
	 * @param x the translation along the x-axis
	 * @param y the translation along the y-axis
	 * @return a new Border2D object representing the translated border
	 */
	@CheckReturnValue
	@Nonnull
	Border2D translate(double x, double y);

	/**
	 * Translates the border by the specified 2D vector.
	 *
	 * @param v the translation vector
	 * @return a new Border2D object representing the translated border
	 */
	@CheckReturnValue
	@Nonnull
	default Border2D translate(@Nonnull Vector2D v) {
		return translate(v.getX(), v.getY());
	}

	/**
	 * Rotates the border around the specified center point by the specified angle
	 * in radians.
	 *
	 * @param center the center point of rotation
	 * @param a      the angle of rotation in radians
	 * @return a new Border2D object representing the rotated border
	 */
	@CheckReturnValue
	@Nonnull
	Border2D rotate(@Nonnull Vector2D center, double a);

	/**
	 * Rotates the border around its centroid by the specified angle in radians.
	 *
	 * @param a the angle of rotation in radians
	 * @return a new Border2D object representing the rotated border
	 */
	@CheckReturnValue
	@Nonnull
	default Border2D rotate(double a) {
		return rotate(centroid(), a);
	}

	/**
	 * Calculates and retrieves the centroid of the border.
	 *
	 * @return the centroid of the border as a Vector2D
	 */
	@CheckReturnValue
	@Nonnull
	Vector2D centroid();
}
