package io.scvis.proto;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HashTree<T> implements Tree<T> {

	@Nonnull
	private Node<T> root = new TreeNode();

	@Nonnull
	@Override
	public Node<T> root() {
		return root;
	}

	@Nullable
	@Override
	public T get(Object... edges) {
		return at(edges).getValue();
	}

	@Nullable
	@Override
	public T set(@Nullable T value, Object... edges) {
		return at(edges).setValue(value);
	}

	@Nonnull
	@Override
	public Node<T> at(Object... edges) {
		Node<T> curr = root();
		for (Object edge : edges) {
			curr = curr.getRelations().computeIfAbsent(edge, e -> new TreeNode());
		}
		return curr;
	}

	@Override
	public void clear() {
		root = new TreeNode();
	}

	public class TreeNode implements Node<T> {
		@Nullable
		private T value;

		@Nullable
		@Override
		public T getValue() {
			return value;
		}

		@Nullable
		@Override
		public T setValue(@Nullable T value) {
			T previous = this.value;
			this.value = value;
			return previous;
		}

		@Nonnull
		private final HashMap<Object, Node<T>> relations = new HashMap<>();

		@Nonnull
		@Override
		public Map<Object, Node<T>> getRelations() {
			return relations;
		}

		@Override
		public String toString() {
			return "(" + value + ", relations=" + relations + ")";
		}
	}

	@Override
	public String toString() {
		return root.getRelations().toString();
	}
}
