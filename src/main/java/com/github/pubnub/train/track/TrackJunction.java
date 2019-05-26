package com.github.pubnub.train.track;

/**
 * A simple POJO for storing track junction information. A track junction is the site of a detector
 */
class TrackJunction {
    private final String nextSection;
    private final String previousSection;
    private final double distanceToNextSection;

    /**
     * Constructor
     *
     * @param nextSection            - The next section after the junction as defined in the track structure
     * @param previousSection        - The previous section before the junction as defined in the track structure
     * @param distanceToNextJunction - This distance to the next junction. Units defined in TrackManager
     */
    TrackJunction(
        final String nextSection,
        final String previousSection,
        final double distanceToNextJunction
    ) {
        this.nextSection = nextSection;
        this.previousSection = previousSection;
        this.distanceToNextSection = distanceToNextJunction;
    }

    /**
     * @return The next section after the junction as defined in the track structure
     */
    String getNextSection() {
        return nextSection;
    }

    /**
     * @return The previous section before the junction as defined in the track structure
     */
    String getPreviousSection() {
        return previousSection;
    }

    /**
     * @return This distance to the next junction. Units defined in TrackManager
     */
    double getDistanceToNextSection() {
        return distanceToNextSection;
    }
}
