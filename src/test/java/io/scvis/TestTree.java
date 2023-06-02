package io.scvis;

import org.junit.jupiter.api.Test;

import io.scvis.proto.HashTree;
import io.scvis.proto.Tree;

class TestTree {

	@Test
	void print() {
		Tree<Long> residents = new HashTree<>();
		residents.set(8_000_000_000l);
		residents.set(500_000_000l, "Europe");
		residents.set(80_000_000l, "Europe", "Germany");
		residents.set(3_000_000l, "Europe", "Germany", "Berlin");
		residents.set(4_000_000l, "Europe", "Germany", "Saxony");
		residents.set(70_000_000l, "Europe", "France");

		System.out.println(residents);
	}
}
