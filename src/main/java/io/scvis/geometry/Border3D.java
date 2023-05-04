package io.scvis.geometry;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public interface Border3D {
	@CheckReturnValue
	boolean contains(@Nonnull Vector3D vector3D);

	@CheckReturnValue
	boolean intersects(@Nonnull Border3D border3D);

	@CheckReturnValue
	@Nonnull
	Border3D translate(double x, double y, double z);

	@CheckReturnValue
	@Nonnull
	default Border3D translate(@Nonnull Vector3D v) {
		return translate(v.getX(), v.getY(), v.getZ());
	}

	@CheckReturnValue
	@Nonnull
	Border3D rotate(@Nonnull Vector3D center, double a, double b);

	@CheckReturnValue
	@Nonnull
	default Border3D rotate(double a, double b) {
		return rotate(centroid(), a, b);
	}

	@CheckReturnValue
	@Nonnull
	Vector3D centroid();
}
