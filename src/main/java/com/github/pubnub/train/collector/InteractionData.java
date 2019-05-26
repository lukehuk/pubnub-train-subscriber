package com.github.pubnub.train.collector;

/**
 * A simple POJO for representing an interaction event
 */
public class InteractionData {
    private final String detectorId;
    private final Long timestamp;

    /**
     * Constructor
     *
     * @param detectorId - The detector ID of the detector that recording an interaction
     * @param timestamp  - The timestamp in nanoseconds of the interaction event
     */
    public InteractionData(
        final String detectorId,
        final Long timestamp
    ) {
        this.detectorId = detectorId;
        this.timestamp = timestamp;
    }

    /**
     * @return The detector ID of the detector that recording an interaction
     */
    String getDetectorId() {
        return detectorId;
    }

    /**
     * @return The timestamp in nanoseconds of the interaction event
     */
    Long getTimestamp() {
        return timestamp;
    }
}
