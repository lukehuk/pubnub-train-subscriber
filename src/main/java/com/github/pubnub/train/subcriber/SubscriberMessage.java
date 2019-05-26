package com.github.pubnub.train.subcriber;

/**
 * A simple POJO that represents the message expected from the subscriber
 */
public class SubscriberMessage {
    private final String detectorId;

    /**
     * Constructor
     *
     * @param detectorId - The detector ID of a triggered detector
     */
    public SubscriberMessage(final String detectorId) {
        this.detectorId = detectorId;
    }

    /**
     * @return The detector ID of a triggered detector
     */
    String getDetectorId() {
        return detectorId;
    }
}
