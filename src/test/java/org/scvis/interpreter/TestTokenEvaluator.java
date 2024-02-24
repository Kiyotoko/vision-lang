package org.scvis.interpreter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scvis.lang.Statement;

import java.util.List;

class TestTokenEvaluator {

    @Test
    void evaluate() {
        Assertions.assertEquals(List.of(23.0), new TokenEvaluator(new BuildInLib(), new Statement(List.of(Operators.Sign.ADD,
                Operators.MUL), List.of(3, Operators.Sign.ADD, 5, Operators.MUL, 4))).evaluate());
    }
}
