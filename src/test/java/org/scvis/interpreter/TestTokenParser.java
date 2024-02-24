package org.scvis.interpreter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTokenParser {

    @Test
    void size() {
        TokenParser parser = new TokenParser(new BuildInLib());
        Assertions.assertDoesNotThrow(() -> parser.parse("5*4+3"));
        System.out.print(parser.getStatement().tokens);
        Assertions.assertEquals(5, parser.getStatement().tokens.size());
        Assertions.assertEquals(2, parser.getStatement().operators.size());
    }

    @Test
    void parse() {
        TokenParser parser = new TokenParser(new BuildInLib());
        Assertions.assertDoesNotThrow(() -> parser.parse("a=2"));
        Assertions.assertDoesNotThrow(() -> parser.parse("print(2)"));
        Assertions.assertDoesNotThrow(() -> parser.parse("5*(4+3)"));
        Assertions.assertDoesNotThrow(() -> parser.parse("(5*4)+3"));
    }
}
