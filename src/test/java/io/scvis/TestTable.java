package io.scvis;

import java.util.Set;

import org.junit.jupiter.api.Test;

import io.scvis.proto.ArrayTable;
import io.scvis.proto.HashTable;
import io.scvis.proto.Table;

class TestTable {

	@Test
	void array() {
		Table<String, String, String> table = new ArrayTable<>(Set.of("W", "U", "B", "R", "G"),
				Set.of("W", "U", "B", "R", "G"));
		table.set("W", "U", "Azorius");
		table.getCells().forEach(c -> System.out.println("(A) " + c));
	}

	@Test
	void hash() {
		Table<String, String, String> table = new HashTable<>();
		table.set("W", "U", "Azorius");
		table.set("[ ]", "[ ]", "Eldrazi");
		table.getCells().forEach(c -> System.out.println("(H) " + c));
		System.out.println(table.get("[ ]", "[ ]"));
	}
}
