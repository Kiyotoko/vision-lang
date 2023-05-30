package io.scvis.geometry;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.scvis.geometry.Shape.Polygon;
import io.scvis.proto.Mapper;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public final class Area implements Border2D {
	@JsonProperty("outsides")
	@Nonnull
	private final List<? extends Border2D> outsides;
	@JsonProperty("insides")
	@Nonnull
	private final List<? extends Border2D> insides;

	@JsonCreator
	public Area(@JsonProperty("outsides") List<? extends Border2D> outsides,
			@JsonProperty("insides") List<? extends Border2D> insides) {
		this.outsides = outsides;
		this.insides = insides;
	}

	public Area(Border2D border2D) {
		outsides = List.of(border2D);
		insides = List.of();
	}

	@Override
	public boolean contains(@Nonnull Vector2D vector2D) {
		for (Border2D outside : getOutsides()) {
			if (outside.contains(vector2D)) {
				for (Border2D inside : getInsides())
					if (inside.contains(vector2D))
						return false;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean intersects(@Nonnull Border2D border2D) {
		for (Border2D outside : getOutsides()) {
			if (outside.intersects(border2D))
				return true;
		}
		return false;
	}

	@Override
	@Nonnull
	public Area translate(double x, double y) {
		Mapper<Border2D, ? extends Border2D> mapper = Mapper.create((Border2D e) -> e.translate(x, y));
		return new Area(mapper.map(outsides), mapper.map(insides));
	}

	@Override
	@Nonnull
	public Area rotate(@Nonnull Vector2D center, double a) {
		Mapper<Border2D, ? extends Border2D> mapper = Mapper.create((Border2D e) -> e.rotate(center, a));
		return new Area(mapper.map(outsides), mapper.map(insides));
	}

	@Override
	@Nonnull
	public Vector2D centroid() {
		List<Vector2D> centers = new ArrayList<>();
		for (Border2D outside : getOutsides()) {
			centers.add(outside.centroid());
		}
		return new Polygon(centers).centroid();
	}

	@Nonnull
	public List<? extends Border2D> getOutsides() {
		return outsides;
	}

	@Nonnull
	public List<? extends Border2D> getInsides() {
		return insides;
	}

	@Override
	public String toString() {
		return "Area [outside = " + outsides + ", insides = " + insides + "]";
	}
}