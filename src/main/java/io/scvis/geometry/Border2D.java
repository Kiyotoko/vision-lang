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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = Polygon.class, name = "Polygon"), @Type(value = Rectangle.class, name = "Rectangle"),
		@Type(value = Circle.class, name = "Circle"), @Type(value = Area.class, name = "Area"),
		@Type(value = Kinetic2D.class, name = "Kinetic") })
@JsonSerialize
@JsonDeserialize
public interface Border2D {
	@CheckReturnValue
	boolean contains(@Nonnull Vector2D vector2D);

	@CheckReturnValue
	boolean intersects(@Nonnull Border2D border2D);

	@CheckReturnValue
	@Nonnull
	Border2D translate(double x, double y);

	@CheckReturnValue
	@Nonnull
	default Border2D translate(@Nonnull Vector2D v) {
		return translate(v.getX(), v.getY());
	}

	@CheckReturnValue
	@Nonnull
	Border2D rotate(@Nonnull Vector2D center, double a);

	@CheckReturnValue
	@Nonnull
	default Border2D rotate(double a) {
		return rotate(centroid(), a);
	}

	@CheckReturnValue
	@Nonnull
	Vector2D centroid();

}
