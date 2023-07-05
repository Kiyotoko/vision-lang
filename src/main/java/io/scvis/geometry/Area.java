package io.scvis.geometry;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import io.scvis.geometry.Shape.Polygon;
import io.scvis.proto.Mapper;

/**
 * The Area class represents a 2D area defined by a collection of borders.
 * 
 * @author karlz
 * @see Border2D
 */
public final class Area implements Border2D {

	private static final long serialVersionUID = 3359561327999324550L;

	private final @Nonnull List<? extends Border2D> outsides;
	private final @Nonnull List<? extends Border2D> insides;

	/**
	 * Constructs an Area object with the given outside and inside borders.
	 * 
	 * @param outsides The list of outside borders.
	 * @param insides  The list of inside borders.
	 */
	public Area(@Nonnull List<? extends Border2D> outsides, @Nonnull List<? extends Border2D> insides) {
		this.outsides = outsides;
		this.insides = insides;
	}

	/**
	 * Constructs an Area object with a single border.
	 * 
	 * @param border2D The single border of the area.
	 */
	public Area(@Nonnull Border2D border2D) {
		outsides = new ArrayList<>(List.of(border2D));
		insides = new ArrayList<>();
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

	/**
	 * Returns the list of outside borders of the area.
	 * 
	 * @return The list of outside borders.
	 */
	@Nonnull
	public List<? extends Border2D> getOutsides() {
		return outsides;
	}

	/**
	 * Returns the list of inside borders of the area.
	 * 
	 * @return The list of inside borders.
	 */
	@Nonnull
	public List<? extends Border2D> getInsides() {
		return insides;
	}

	@Override
	public String toString() {
		return "Area [outside = " + outsides + ", insides = " + insides + "]";
	}
}