package io.scvis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestTokenEvaluator {

    @Test
    void evaluate() {
        Assertions.assertEquals(23.0, new TokenEvaluator(new ArrayList<>( List.of(BaseOperator.ADD,
                BaseOperator.MULTIPLY)), new ArrayList<>(List.of(new Constant(3),
                BaseOperator.ADD, new Constant(5), BaseOperator.MULTIPLY, new Constant(4)))).evaluate());
        Assertions.assertEquals(239.0, new TokenEvaluator(new ArrayList<>( List.of(BaseOperator.SUBTRACT,
                BaseOperator.POW)), new ArrayList<>(List.of(new Constant(3),
                BaseOperator.POW, new Constant(5), BaseOperator.SUBTRACT, new Constant(4)))).evaluate());
    }
}
