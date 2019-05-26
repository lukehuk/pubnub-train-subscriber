package com.github.pubnub.train.track;

/**
 * A simple POJO for storing train location information
 */
public class TrainLocation {
    private final String currentSection;
    private final String previousSection;
    private final double distanceBetweenSections;

    /**
     * Constructor
     *
     * @param currentSection          - The track section identifier of the section the train is entering
     * @param previousSection         - The track section identifier of the section the train has left
     * @param distanceBetweenSections - The distance between the current junction and the next i.e. the current section length
     */
    TrainLocation(
        final String currentSection,
        final String previousSection,
        final double distanceBetweenSections
    ) {
        this.currentSection = currentSection;
        this.previousSection = previousSection;
        this.distanceBetweenSections = distanceBetweenSections;
    }

    /**
     * @return The track section identifier of the section the train is entering
     */
    public String getCurrentSection() {
        return currentSection;
    }

    /**
     * @return The track section identifier of the section the train has left
     */
    public String getPreviousSection() {
        return previousSection;
    }

    /**
     * @return The distance between the current junction and the next i.e. the current section length
     */
    public double getDistanceBetweenSections() {
        return distanceBetweenSections;
    }
}
