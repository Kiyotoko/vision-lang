package io.scvis.observable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.scvis.observable.ChangeListener.ChangeEvent;
import io.scvis.observable.InvalidationListener.InvalidationEvent;

public class Property<T> implements ObservableValue<T> {

	private static final Logger logger = LoggerFactory.getLogger(Property.class);

	@Nullable
	private T value;

	public Property(@Nullable T value) {
		this.value = value;
	}

	@CheckForNull
	private Set<InvalidationListener<T>> invalidationListeners;

	protected void fireInvalidationEvent(@Nonnull InvalidationEvent<T> event) {
		logger.debug(event.toString());
		Iterator<InvalidationListener<T>> iterator = getInvalidationListeners().iterator();
		while (iterator.hasNext())
			iterator.next().invalidated(event);
	}

	@Override
	public void addInvalidationListener(@Nonnull InvalidationListener<T> listener) {
		getInvalidationListeners().add(listener);
	}

	@Override
	public void removeInvalidationListener(@Nonnull InvalidationListener<T> listener) {
		getInvalidationListeners().remove(listener);
	}

	@Nonnull
	private Set<InvalidationListener<T>> getInvalidationListeners() {
		final Set<InvalidationListener<T>> listeners = invalidationListeners;
		if (listeners == null) {
			return invalidationListeners = new HashSet<>();
		}
		return listeners;
	}

	@CheckForNull
	private Set<ChangeListener<T>> changeListeners;

	protected void fireChangeEvent(@Nonnull ChangeEvent<T> event) {
		logger.debug(event.toString());
		Iterator<ChangeListener<T>> iterator = getChangeListeners().iterator();
		while (iterator.hasNext())
			iterator.next().changed(event);
	}

	public void addChangeListener(@Nonnull ChangeListener<T> listener) {
		getChangeListeners().add(listener);
	}

	public void removeChangeListener(@Nonnull ChangeListener<T> listener) {
		getChangeListeners().remove(listener);
	}

	@Nonnull
	private Set<ChangeListener<T>> getChangeListeners() {
		final Set<ChangeListener<T>> listeners = changeListeners;
		if (listeners == null) {
			return changeListeners = new HashSet<>();
		}
		return listeners;
	}

	@Override
	@Nullable
	public T get() {
		return value;
	}

	@Override
	public void set(@Nullable T value) {
		if (get() != value) {
			fireChangeEvent(new ChangeEvent<>(this, this.value, value));
			this.value = value;
		}
		fireInvalidationEvent(new InvalidationEvent<>(this));
	}
}
