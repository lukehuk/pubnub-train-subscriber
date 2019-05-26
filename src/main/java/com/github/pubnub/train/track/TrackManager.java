package com.github.pubnub.train.track;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that manages information about a train track, including section names, detectors and section distances
 */
public class TrackManager {
    public static final String DISTANCE_UNIT = "cm";
    //A static track representation which could be made dynamic in the future
    private static final Map<String, TrackJunction> trackStructure = new HashMap<String, TrackJunction>() {{
        put("7", new TrackJunction("A", "D", 26));
        put("8", new TrackJunction("B", "A", 27.5));
        put("10", new TrackJunction("C", "B", 28));
        put("12", new TrackJunction("D", "C", 26));
    }};

    /**
     * Returns the section identifier of the next section of track as defined in the track structure
     *
     * @param detectorId - A given track junction detector
     * @return A section identifier
     * @throws TrainLocationException Thrown if an unknown detector is provided
     */
    public String getNextSection(final String detectorId) throws TrainLocationException {
        if (trackStructure.containsKey(detectorId)) {
            return trackStructure.get(detectorId).getNextSection();
        } else {
            throw new TrainLocationException("Unknown detector " + detectorId);
        }
    }

    /**
     * Returns the section identifier of the previous section of track as defined in the track structure
     *
     * @param detectorId - A given track junction detector
     * @return A section identifier
     * @throws TrainLocationException Thrown if an unknown detector is provided
     */
    public String getPreviousSection(final String detectorId) throws TrainLocationException {
        if (trackStructure.containsKey(detectorId)) {
            return trackStructure.get(detectorId).getPreviousSection();
        } else {
            throw new TrainLocationException("Unknown detector " + detectorId);
        }
    }

    /**
     * Produces train location information using two detectors to determine direction of travel
     *
     * @param previousDetector - The last recorded detection
     * @param currentDetector  - The current detection
     * @return Information the trains position on the track
     * @throws TrainLocationException - Thrown if either of the detectors are unknown or if the two detectors are not adjacent
     */
    public TrainLocation determineLocation(
        final String previousDetector,
        final String currentDetector
    ) throws TrainLocationException {
        final TrackJunction currentJunction = trackStructure.get(currentDetector);
        final TrackJunction previousJunction = trackStructure.get(previousDetector);

        if (null == currentJunction) {
            throw new TrainLocationException("Unknown detector " + currentDetector);
        }

        if (null == previousJunction) {
            throw new TrainLocationException("Unknown detector " + previousDetector);
        }

        // Determine if the train is traveling forward through the track structure or backwards
        if (currentJunction.getPreviousSection().equals(previousJunction.getNextSection())) {
            return new TrainLocation(
                currentJunction.getNextSection(),
                currentJunction.getPreviousSection(),
                previousJunction.getDistanceToNextSection()
            );
        } else if (currentJunction.getNextSection().equals(previousJunction.getPreviousSection())) {
            return new TrainLocation(
                previousJunction.getPreviousSection(),
                previousJunction.getNextSection(),
                currentJunction.getDistanceToNextSection()
            );
        } else {
            throw new TrainLocationException("Non adjacent detectors provided");
        }
    }
}
