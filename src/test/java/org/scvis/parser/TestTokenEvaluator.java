package org.scvis.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestTokenEvaluator {

    @Test
    void evaluate() {
        Assertions.assertEquals(List.of(23.0), new TokenEvaluator(new ArrayList<>(List.of(BaseOperator.ADD,
                BaseOperator.MULTIPLY)), new ArrayList<>(List.of(new NumberValue(3), new NumberValue(5),
                new NumberValue(4)))).evaluate());
    }
}
