package io.scvis.observable;

import java.util.EventObject;

public class EventType<E extends EventObject> {
	private final String name;

	public EventType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
