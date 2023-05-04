package io.scvis.observable;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveManager {
	private final List<Objective> objectives = new ArrayList<>();

	private boolean completed = false;

	public void registerObjective(Objective objective) {
		objectives.add(objective);
	}

	public void update() {
		if (objectives.size() == 0 || completed)
			return;
		for (int i = 0; i < objectives.size(); i++) {
			if (objectives.get(i).isBlocking()) {
				return;
			}
		}
		completed = true;
	}

	public boolean isCompleted() {
		return completed;
	}
}
