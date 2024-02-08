package org.scvis.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scvis.lang.BuildInLib;

class TestTokenParser {

    @Test
    void size() {
        TokenParser parser = new TokenParser(new BuildInLib());
        Assertions.assertDoesNotThrow(() -> parser.tokenize("5*4+3"));
        System.out.print(parser.getStatement().tokens);
        Assertions.assertEquals(5, parser.getStatement().tokens.size());
        Assertions.assertEquals(2, parser.getStatement().operators.size());
    }

    @Test
    void parse() {
        TokenParser parser = new TokenParser(new BuildInLib());
        Assertions.assertDoesNotThrow(() -> parser.tokenize("a=2"));
        Assertions.assertDoesNotThrow(() -> parser.tokenize("print(2)"));
        Assertions.assertDoesNotThrow(() -> parser.tokenize("5*(4+3)"));
        Assertions.assertDoesNotThrow(() -> parser.tokenize("(5*4)+3"));
    }
}
