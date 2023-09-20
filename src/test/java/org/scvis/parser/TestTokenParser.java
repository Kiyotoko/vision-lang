package org.scvis.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTokenParser {

    @Test
    void parse() {
        TokenParser parser = new TokenParser();
        Assertions.assertDoesNotThrow(() -> parser.tokenize("5*4+3"));
        Assertions.assertEquals(5, parser.getTokens().size());
        Assertions.assertEquals(2, parser.getOperators().size());
    }
}
