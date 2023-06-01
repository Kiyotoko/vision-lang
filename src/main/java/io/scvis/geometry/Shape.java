package io.scvis.geometry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
@JsonDeserialize
public abstract class Shape implements Border2D {

	protected Vector2D center;

	@Nonnull
	public Vector2D getCenter() {
		return centroid();
	}

	@JsonIgnore
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

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	@JsonSerialize
	@JsonDeserialize
	public static class Polygon extends Shape {
		@JsonProperty("points")
		@Nonnull
		private final List<Vector2D> points;
		@JsonProperty("minX")
		private final double minX;
		@JsonProperty("minY")
		private final double minY;
		@JsonProperty("maxX")
		private final double maxX;
		@JsonProperty("maxY")
		private final double maxY;

		@JsonCreator
		protected Polygon(@JsonProperty("points") @Nonnull List<Vector2D> points, @JsonProperty("minX") double minX,
				@JsonProperty("minY") double minY, @JsonProperty("maxX") double maxX,
				@JsonProperty("maxY") double maxY) {
			this.points = points;
			this.minX = minX;
			this.minY = minY;
			this.maxX = maxX;
			this.maxY = maxY;
		}

		public Polygon(@Nonnull List<Vector2D> points) {
			this.points = points;
			minX = pick(points, (a, b) -> (int) (a.getX() - b.getX())).getX();
			minY = pick(points, (a, b) -> (int) (a.getY() - b.getY())).getY();
			maxX = pick(points, (a, b) -> (int) (b.getX() - a.getX())).getX();
			maxY = pick(points, (a, b) -> (int) (b.getY() - a.getY())).getY();
		}

		private static boolean contains(List<Vector2D> polygon, Vector2D vector2D) {
			boolean inside = false;
			for (int i = 0; i < polygon.size(); i++) {
				int j = (i + 1) % polygon.size();
				if (crosses(polygon.get(i), polygon.get(j), vector2D))
					inside = !inside;
			}
			return inside;
		}

		private static boolean crosses(Vector2D a, Vector2D b, Vector2D vector2D) {
			if (vector2D.getY() == a.getY() && a.getY() == b.getY())
				if (a.getX() <= vector2D.getX() && vector2D.getX() <= b.getX()
						|| b.getX() <= vector2D.getX() && vector2D.getX() <= a.getX())
					return true;
				else
					return false;
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
			if (x < 0)
				return false;
			else
				return true;
		}

		private static <T> T pick(List<T> list, Comparator<T> comparator) {
			if (list.isEmpty())
				return null;
			List<T> temp = new ArrayList<>(list);
			temp.sort(comparator);
			return temp.get(0);
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
					if (point != null)
						if (contains(point))
							return true;
			}
			for (Vector2D point : getPoints())
				if (point != null)
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
			List<Vector2D> points = new ArrayList<>();
			for (Vector2D point : this.points)
				points.add(point.add(x, y));
			return new Polygon(points);
		}

		@Override
		@Nonnull
		public Polygon rotate(double a) {
			return rotate(centroid(), a);
		}

		@Override
		@Nonnull
		public Polygon rotate(@Nonnull Vector2D center, double a) {
			List<Vector2D> points = new ArrayList<>();
			Vector2D centroid = center;
			for (Vector2D point : this.points)
				points.add(point.subtract(centroid).rotate(a).add(centroid));
			return new Polygon(points);
		}

		@Override
		@Nonnull
		public Vector2D centroid() {
			/* Checked */
			final Vector2D center = this.center;
			if (center == null) {
				double x = 0, y = 0;
				for (Vector2D vector2D : points) {
					x += vector2D.getX();
					y += vector2D.getY();
				}
				return this.center = new Vector2D(x / points.size(), y / points.size());
			}
			return center;
		}

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

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	@JsonSerialize
	@JsonDeserialize
	public static class Rectangle extends Polygon {
		@JsonProperty("width")
		private final double width;
		@JsonProperty("height")
		private final double height;

		@JsonCreator
		protected Rectangle(@JsonProperty("points") @Nonnull List<Vector2D> points, @JsonProperty("minX") double minX,
				@JsonProperty("minY") double minY, @JsonProperty("maxX") double maxX, @JsonProperty("maxY") double maxY,
				@JsonProperty("width") double width, @JsonProperty("height") double height) {
			super(points, minX, minY, maxX, maxY);
			this.width = width;
			this.height = height;
		}

		public Rectangle(@Nonnull List<Vector2D> points, double width, double height) {
			super(points);
			this.width = width;
			this.height = height;
		}

		public Rectangle(double width, double height) {
			super(new ArrayList<>(List.of(new Vector2D(0, 0), new Vector2D(width, 0), new Vector2D(width, height),
					new Vector2D(0, height))), 0, 0, width, height);
			this.width = width;
			this.height = height;
		}

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

		public double getWidth() {
			return width;
		}

		public double getHeight() {
			return height;
		}

		@Override
		public String toString() {
			return "Rectangle [points = " + getPoints() + ", width = " + width + ", height = " + height + "]";
		}
	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	@JsonSerialize
	@JsonDeserialize
	public static class Circle extends Shape {
		@JsonProperty("radius")
		private final double radius;

		@JsonCreator
		public Circle(@JsonProperty("center") @Nonnull Vector2D center, @JsonProperty("radius") double radius) {
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

		public double getRadius() {
			return radius;
		}

		@Override
		public String toString() {
			return "Circle [center = " + center + ", radius = " + radius + "]";
		}
	}
}
