package io.scvis.geometry;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * In mathematics and geometry, a shape refers to the form or configuration of
 * an object or figure. It is a fundamental concept used to describe and
 * classify objects based on their physical appearance.
 * <p>
 * A shape in 2D space is a flat, two-dimensional figure that is defined by its
 * boundaries or outlines.
 * 
 * @author karlz
 */
public abstract class Shape implements Border2D {

	private static final long serialVersionUID = 1430329205714811390L;

	/**
	 * Returns the zero vector. This method is used to define functional interfaces
	 * such as Supplier.
	 * 
	 * <p>
	 * <code>Shape::zero</code>
	 *
	 * @return the zero vector
	 */
	private static final Vector2D zero() {
		return Vector2D.ZERO;
	}

	protected Vector2D center;

	/**
	 * Retrieves the center of this shape.
	 *
	 * @return the center vector
	 */
	@Nonnull
	public Vector2D getCenter() {
		return centroid();
	}

	private transient int hash = 0;

	@Override
	public int hashCode() {
		if (hash == 0)
			hash = super.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		return "UnknownShape [center = " + center + ", class = " + getClass() + ", hash = " + hashCode() + "]";
	}

	/**
	 * Represents a polygon shape.
	 */
	public static class Polygon extends Shape {

		private static final long serialVersionUID = -3362147056076306244L;

		private final @Nonnull List<Vector2D> points;
		private final double minX;
		private final double minY;
		private final double maxX;
		private final double maxY;

		/**
		 * Creates a polygon with the given points, minX, minY, .
		 *
		 * @param points the points of the rectangle
		 * @param width  the width of the rectangle
		 * @param height the height of the rectangle
		 */
		protected Polygon(@Nonnull List<Vector2D> points, double minX, double minY, double maxX, double maxY) {
			this.points = points;
			this.minX = minX;
			this.minY = minY;
			this.maxX = maxX;
			this.maxY = maxY;
		}

		/**
		 * Constructs a polygon with the specified list of points.
		 *
		 * @param points the points of the polygon
		 */
		public Polygon(@Nonnull List<Vector2D> points) {
			this.points = points;
			minX = points.stream().max((a, b) -> (int) (a.getX() - b.getX())).orElseGet(Shape::zero).getX();
			minY = points.stream().max((a, b) -> (int) (a.getY() - b.getY())).orElseGet(Shape::zero).getY();
			maxX = points.stream().max((a, b) -> (int) (b.getX() - a.getX())).orElseGet(Shape::zero).getX();
			maxY = points.stream().max((a, b) -> (int) (b.getY() - a.getY())).orElseGet(Shape::zero).getY();
		}

		/**
		 * Checks whether a given vector is inside or outside a simple polygon using the
		 * Jordan test.
		 * 
		 * This method counts the number of intersections for a ray passing from the
		 * exterior of the polygon to any point: If odd, it shows that the point lies
		 * inside the polygon; if even, the point lies outside the polygon.
		 * 
		 * @param polygon  the list of vectors defining the polygon.
		 * @param vector2D the vector or point to check.
		 * @return true if the vector is inside, false otherwise.
		 */
		public static boolean contains(List<Vector2D> polygon, Vector2D vector2D) {
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
		 * @param a        the point a of the line.
		 * @param b        the point b of the line.
		 * @param vector2D the tested point.
		 * @return true if the ray crosses the line AB
		 */
		public static boolean crosses(Vector2D a, Vector2D b, Vector2D vector2D) {
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
					if (point != null && (contains(point)))
						return true;
			}
			for (Vector2D point : getPoints())
				if (point != null && (contains(point)))
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
			Vector2D centroid = center;
			for (Vector2D point : this.points)
				rotated.add(point.subtract(centroid).rotate(a).add(centroid));
			return new Polygon(rotated);
		}

		@Override
		@Nonnull
		public Vector2D centroid() {
			/* Checked */
			final Vector2D center = this.center;
			if (center == null) {
				double x = 0;
				double y = 0;
				for (Vector2D vector2D : points) {
					x += vector2D.getX();
					y += vector2D.getY();
				}
				return this.center = new Vector2D(x / points.size(), y / points.size());
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

	/**
	 * Represents a rectangle shape.
	 */
	public static class Rectangle extends Polygon {

		private static final long serialVersionUID = 4703005267556298683L;

		private final double width;
		private final double height;

		/**
		 * Creates a rectangle with the given points, width, and height.
		 *
		 * @param points the points of the rectangle
		 * @param width  the width of the rectangle
		 * @param height the height of the rectangle
		 */
		private Rectangle(@Nonnull List<Vector2D> points, double width, double height) {
			super(points);
			if (points.size() != 4)
				throw new IllegalArgumentException("A rectangle must always have 4 points, not " + points.size());
			this.width = width;
			this.height = height;
		}

		/**
		 * Creates a rectangle with the given width and height, with the bottom-left
		 * corner at (0,0).
		 *
		 * @param width  the width of the rectangle
		 * @param height the height of the rectangle
		 */
		public Rectangle(double width, double height) {
			super(new ArrayList<>(List.of(new Vector2D(0, 0), new Vector2D(width, 0), new Vector2D(width, height),
					new Vector2D(0, height))), 0, 0, width, height);
			this.width = width;
			this.height = height;
		}

		/**
		 * Creates a rectangle with the given starting and ending coordinates.
		 *
		 * @param startX the x-coordinate of the starting point
		 * @param startY the y-coordinate of the starting point
		 * @param endX   the x-coordinate of the ending point
		 * @param endY   the y-coordinate of the ending point
		 */
		public Rectangle(double startX, double startY, double endX, double endY) {
			super(new ArrayList<>(List.of(new Vector2D(startX, startY), new Vector2D(endX, startY),
					new Vector2D(endX, endY), new Vector2D(startX, endY))), startX, startY, endX, endY);
			this.width = endX - startX;
			this.height = endY - startY;
		}

		@Override
		public Rectangle translate(Vector2D v) {
			return translate(v.getX(), v.getY());
		}

		@Override
		public Rectangle translate(double x, double y) {
			List<Vector2D> points = new ArrayList<>();
			for (Vector2D point : getPoints())
				points.add(point.add(x, y));
			return new Rectangle(points, width, height);
		}

		@Override
		@Nonnull
		public Rectangle rotate(double a) {
			return rotate(centroid(), a);
		}

		@Override
		@Nonnull
		public Rectangle rotate(@Nonnull Vector2D center, double a) {
			List<Vector2D> points = new ArrayList<>();
			Vector2D centroid = center;
			for (Vector2D point : getPoints())
				points.add(point.subtract(centroid).rotate(a).add(centroid));
			return new Rectangle(points, width, height);
		}

		/**
		 * Retrieves the width of the rectangle.
		 *
		 * @return the width of the rectangle
		 */
		public double getWidth() {
			return width;
		}

		/**
		 * Retrieves the height of the rectangle.
		 *
		 * @return the height of the rectangle
		 */
		public double getHeight() {
			return height;
		}

		@Override
		public String toString() {
			return "Rectangle [points = " + getPoints() + ", width = " + width + ", height = " + height + "]";
		}
	}

	/**
	 * Represents a circle shape.
	 */
	public static class Circle extends Shape {

		private static final long serialVersionUID = 172519286642315942L;

		private final double radius;

		/**
		 * Creates a circle with the given center and radius.
		 *
		 * @param center
		 * @param radius
		 */
		public Circle(@Nonnull Vector2D center, double radius) {
			this.center = center;
			this.radius = radius;
		}

		@Override
		public boolean contains(@Nonnull Vector2D v) {
			v = v.subtract(center);
			return radius >= v.magnitude();
		}

		@Override
		public boolean intersects(@Nonnull Border2D border2D) {
			if (border2D instanceof Circle) {
				Circle circle = (Circle) border2D;
				return (radius + circle.radius) >= center.subtract(circle.center).magnitude();
			}
			return border2D.contains(center);
		}

		@Override
		@Nonnull
		public Circle translate(double x, double y) {
			return new Circle(center.add(x, y), radius);
		}

		@Override
		@Nonnull
		public Circle rotate(double a) {
			return new Circle(center, radius);
		}

		@Override
		@Nonnull
		public Circle rotate(@Nonnull Vector2D center, double r) {
			return new Circle(center.subtract(center).rotate(r).add(center), radius);
		}

		@Nonnull
		public Vector2D centroid() {
			return center;
		}

		/**
		 * Retrieves the radius of the circle.
		 *
		 * @return the radius of the circle.
		 */
		public double getRadius() {
			return radius;
		}

		@Override
		public String toString() {
			return "Circle [center = " + center + ", radius = " + radius + "]";
		}
	}
}
