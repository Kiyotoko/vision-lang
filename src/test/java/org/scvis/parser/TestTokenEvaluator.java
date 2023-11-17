package org.scvis.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestTokenEvaluator {

    @Test
    void evaluate() throws EvaluationException, AccessException {
        Assertions.assertEquals(List.of(23.0), new TokenEvaluator(new ArrayList<>(List.of(BinaryOperator.OperatorAndSign.ADD,
                BinaryOperator.MUL)), new ArrayList<>(List.of(3, BinaryOperator.OperatorAndSign.ADD,5, BinaryOperator.MUL, 4))).evaluate());
    }
}
