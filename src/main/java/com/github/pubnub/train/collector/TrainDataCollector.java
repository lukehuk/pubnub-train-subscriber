package com.github.pubnub.train.collector;

import com.github.pubnub.train.track.TrackManager;
import com.github.pubnub.train.track.TrainLocation;
import com.github.pubnub.train.track.TrainLocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Collects and processes interaction event data
 */
public class TrainDataCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainDataCollector.class);
    private static final String TIME_UNIT = "s";

    //A thread safe queue that is expected to contain only one record in this implementation but this could be increased
    //to allow moving averages for speeds or changed to allow multiple trains
    private final ConcurrentLinkedQueue<InteractionData> interactionData;
    private final TrackManager trackManager;
    private final String speedUnit;

    /**
     * Constructor
     *
     * @param trackManager - An instance of TrackManager which contains information about the track structure
     */
    public TrainDataCollector(final TrackManager trackManager) {
        this.trackManager = trackManager;
        this.interactionData = new ConcurrentLinkedQueue<InteractionData>();
        this.speedUnit = TrackManager.DISTANCE_UNIT + "/" + TIME_UNIT; //Create speed unit from distance and time units
    }

    /**
     * Called when an interaction event occurs. Event is recorded and the latest train location information is logged
     *
     * @param interaction - Information about the interaction
     */
    public void recordInteraction(final InteractionData interaction) {
        //Remove the head of the queue
        final InteractionData previousInteraction = interactionData.poll();
        //Add to the tail of the queue
        interactionData.add(interaction);
        try {
            //If this is the first interaction
            if (null == previousInteraction) {
                logTrainLocationInformation(interaction, interaction);
            } else {
                logTrainLocationInformation(previousInteraction, interaction);
            }
        } catch (final TrainLocationException e) {
            LOGGER.error("Train detected at detector {} but the train location could not be determined", interaction.getDetectorId());
        }
    }

    /**
     * Using two interaction events determine and log train location information
     *
     * @param previousInteraction - The last recorded interaction event
     * @param currentInteraction  - The latest interaction event
     * @throws TrainLocationException Thrown if an error occurs whilst computing train location information
     */
    private void logTrainLocationInformation(
        final InteractionData previousInteraction,
        final InteractionData currentInteraction
    ) throws TrainLocationException {
        //If this is the first recorded interaction, we can't determine speed or direction
        if (previousInteraction.getDetectorId().equals(currentInteraction.getDetectorId())) {
            LOGGER.info(
                "Train recorded between sections {} and {}\n",
                trackManager.getNextSection(previousInteraction.getDetectorId()),
                trackManager.getPreviousSection(previousInteraction.getDetectorId())
            );
        } else {
            //Using the current and previous detection points, determine the trains location data
            final TrainLocation trainLocation = trackManager.determineLocation(previousInteraction.getDetectorId(), currentInteraction.getDetectorId());
            LOGGER.info("Train left section {} and entered section {}", trainLocation.getPreviousSection(), trainLocation.getCurrentSection());

            //Using the timestamp difference and distance between detectors calculate the trains speed
            final long timeDifference = currentInteraction.getTimestamp() - previousInteraction.getTimestamp();
            final double timeDifferenceSecs = timeDifference / 10000000.0;
            LOGGER.info("Time since last detection: {}{}", String.format("%.2f", timeDifferenceSecs), TIME_UNIT);
            final double speed = trainLocation.getDistanceBetweenSections() / timeDifferenceSecs;
            LOGGER.info("Train travelling at approximately {} {}\n", String.format("%.1f", speed), speedUnit);
        }
    }
}