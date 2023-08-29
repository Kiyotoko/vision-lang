package io.scvis.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Represents a polygon shape.
 */
public class Polygon implements Border2D {

	private static final long serialVersionUID = -3362147056076306244L;

	private final @Nonnull List<Vector2D> points;
	private final double minX;
	private final double minY;
	private final double maxX;
	private final double maxY;

	/**
	 * Constructs a polygon with the specified list of points.
	 *
	 * @param points the points of the polygon
	 */
	public Polygon(@Nonnull List<Vector2D> points) {
		this.points = points;
		minX = Collections.min(points, (a, b) -> (int) (a.getX() - b.getX())).getX();
		minY = Collections.min(points, (a, b) -> (int) (a.getY() - b.getY())).getY();
		maxX = Collections.max(points, (a, b) -> (int) (a.getX() - b.getX())).getX();
		maxY = Collections.max(points, (a, b) -> (int) (a.getY() - b.getY())).getY();
	}

	/**
	 * Checks whether a given vector is inside or outside a simple polygon using the
	 * Jordan test.
	 * <p>
	 * This method counts the number of intersections for a ray passing from the
	 * exterior of the polygon to any point: If odd, it shows that the point lies
	 * inside the polygon; if even, the point lies outside the polygon.
	 *
	 * @param polygon  the list of vectors defining the polygon.
	 * @param vector2D the vector or point to check.
	 * @return true if the vector is inside, false otherwise.
	 */
	public static boolean contains(@Nonnull List<Vector2D> polygon, @Nonnull Vector2D vector2D) {
		boolean inside = false;
		for (int i = 0; i < polygon.size(); i++) {
			int j = (i + 1) % polygon.size();
			if (crosses(polygon.get(i), polygon.get(j), vector2D))
				inside = !inside;
		}
		return inside;
	}

	/**
	 * Checks if a ray passing from the exterior of the polygon crosses a line AB of
	 * the polygon.
	 *
	 * @param a        the first point of the line.
	 * @param b        the second point of the line.
	 * @param vector2D the tested point.
	 * @return true if the ray crosses the line AB
	 */
	public static boolean crosses(@Nonnull Vector2D a, @Nonnull Vector2D b, Vector2D vector2D) {
		if (vector2D.getY() == a.getY() && a.getY() == b.getY())
			return a.getX() <= vector2D.getX() && vector2D.getX() <= b.getX()
					|| b.getX() <= vector2D.getX() && vector2D.getX() <= a.getX();
		if (vector2D.getY() == a.getY() && vector2D.getX() == b.getX())
			return true;
		if (a.getY() > b.getY()) {
			var t = a;
			a = b;
			b = t;
		}
		if (vector2D.getY() <= a.getY() || vector2D.getY() > b.getY())
			return false;
		double x = (a.getX() - vector2D.getX()) * (b.getY() - vector2D.getY())
				- (a.getY() - vector2D.getY()) * (b.getX() - vector2D.getX());
		return (x >= 0);
	}

	@Override
	public boolean contains(@Nonnull Vector2D vector2D) {
		if (vector2D.getX() < minX || vector2D.getX() > maxX)
			return false;
		if (vector2D.getY() < minY || vector2D.getY() > maxY)
			return false;
		return contains(points, vector2D);
	}

	@Override
	public boolean intersects(@Nonnull Border2D border2D) {
		if (border2D instanceof Polygon) {
			Polygon poly = (Polygon) border2D;
			for (Vector2D point : poly.getPoints())
				if (contains(point))
					return true;
		}
		for (Vector2D point : getPoints())
			if (contains(point))
				return true;
		return false;
	}

	@Override
	@Nonnull
	public Polygon translate(@Nonnull Vector2D v) {
		return translate(v.getX(), v.getY());
	}

	@Override
	@Nonnull
	public Polygon translate(double x, double y) {
		List<Vector2D> translated = new ArrayList<>();
		for (Vector2D point : this.points)
			translated.add(point.add(x, y));
		return new Polygon(translated);
	}

	@Override
	@Nonnull
	public Polygon rotate(double a) {
		return rotate(centroid(), a);
	}

	@Override
	@Nonnull
	public Polygon rotate(@Nonnull Vector2D center, double a) {
		List<Vector2D> rotated = new ArrayList<>();
		for (Vector2D point : this.points)
			rotated.add(point.subtract(center).rotate(a).add(center));
		return new Polygon(rotated);
	}

	private Vector2D center;

	@Override
	@Nonnull
	public Vector2D centroid() {
		if (center == null) {
			double x = 0;
			double y = 0;
			for (Vector2D vector2D : points) {
				x += vector2D.getX();
				y += vector2D.getY();
			}
			center = new Vector2D(x / points.size(), y / points.size());
		}
		return center;
	}

	/**
	 * Retrieves the points of the polygon.
	 *
	 * @return the points of the polygon
	 */
	@Nonnull
	public List<Vector2D> getPoints() {
		return points;
	}

	@Override
	public String toString() {
		return "Polygon [points = " + points + ", maxX = " + maxX + ", maxY = " + maxY + ", minX = " + minX
				+ ", minY = " + minY + "]";
	}
}
