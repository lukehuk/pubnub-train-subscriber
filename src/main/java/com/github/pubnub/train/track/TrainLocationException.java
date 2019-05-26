package com.github.pubnub.train.track;

/**
 * A custom exception class used for errors relating to train location logic
 */
public class TrainLocationException extends Exception {
    TrainLocationException(final String message) {
        super(message);
    }
}
