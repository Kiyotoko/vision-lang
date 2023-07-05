package io.scvis.proto;

import java.util.Collection;

/**
 * The Corresponding interface represents an object that can provide an
 * associated value of type T.
 *
 * @param <T> the type of the associated value
 * @author karlz
 */
public interface Corresponding<T> {
	/**
	 * Retrieves the associated value.
	 *
	 * @return the associated value
	 */
	T associated();

	public static interface ExtendableCorresponding extends Corresponding<Object> {

	}

	/**
	 * Transforms a Corresponding object into its associated value.
	 *
	 * @param corresponding the Corresponding object
	 * @param <T>           the type of the associated value
	 * @return the associated value
	 */
	public static <T> T transform(Corresponding<T> corresponding) {
		return corresponding.associated();
	}

	/**
	 * Transforms a collection of Corresponding objects into a collection of their
	 * associated values.
	 *
	 * @param corresponding the collection of Corresponding objects
	 * @param <T>           the type of the associated value
	 * @return a collection of associated values
	 */
	public static <T> Collection<? extends T> transform(Collection<? extends Corresponding<T>> corresponding) {
		return Mapper.create((Corresponding<? extends T> c) -> c.associated()).map(corresponding);
	}
}
