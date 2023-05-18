package io.scvis.game;

public class Objective {
	private String title;
	private String description;
	private float delayVisible;
	private boolean optional;
	private boolean completed;

	public Objective(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public void start() {

	}

	public void update(String description, String counter, String notification) {

	}

	public void complete(String description, String counter, String notification) {
		completed = true;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public float getDelayVisible() {
		return delayVisible;
	}

	public boolean isOptional() {
		return optional;
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isBlocking() {
		return !(optional || completed);
	}

}
