package org.scvis.parser;

import org.scvis.ScVisException;

public class EvaluationException extends ScVisException {

    public EvaluationException(String message, int errorCode) {
        super(message, errorCode);
        checkForRange(200, 299);
    }
}
