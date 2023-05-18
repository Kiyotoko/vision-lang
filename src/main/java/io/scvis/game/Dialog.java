package io.scvis.game;

import java.util.ArrayList;
import java.util.List;

import io.scvis.proto.Pair;

public class Dialog {
	private List<DialogOption> options = new ArrayList<>();

	public Dialog() {

	}

	public class DialogOption extends Pair<List<String>, List<Integer>> {
		public DialogOption(List<String> dialog, List<Integer> options) {
			super(dialog, options);
		}

		public List<String> peek() {
			List<String> peeks = new ArrayList<>();
			for (int i = 0; i < getRight().size(); i++) {
				peeks.add(getDialogOptions().get(i).getPreview());
			}
			return peeks;
		}

		public String getPreview() {
			return getDialog().get(0);
		}

		public List<String> getDialog() {
			return getLeft();
		}

		public List<DialogOption> getOptions() {
			List<DialogOption> next = new ArrayList<>();
			for (int i = 0; i < getRight().size(); i++) {
				next.add(getDialogOptions().get(i));
			}
			return next;
		}
	}

	private int last = -1;

	public DialogOption current() {
		return (last > 0 && last < options.size()) ? options.get(last) : getDefault();
	}

	public DialogOption next(int option) {
		return options.get(current().getRight().get(option));
	}

	public DialogOption getDefault() {
		return options.isEmpty() ? null : options.get(0);
	}

	public boolean hasDefault() {
		return getDefault() != null;
	}

	public List<DialogOption> getDialogOptions() {
		return options;
	}
}
