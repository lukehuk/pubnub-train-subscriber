package com.github.pubnub.train.subcriber;

import com.github.pubnub.train.collector.InteractionData;
import com.github.pubnub.train.collector.TrainDataCollector;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of the abstract SubscribeCallback PubNub class
 */
public class TrainSubscriber extends SubscribeCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainSubscriber.class);
    private final TrainDataCollector trainDataCollector;

    /**
     * Constructor
     *
     * @param trainDataCollector - Collects and processes data about the train
     */
    public TrainSubscriber(final TrainDataCollector trainDataCollector) {
        this.trainDataCollector = trainDataCollector;
    }

    @Override
    public void status(
        final PubNub pubnub,
        final PNStatus status
    ) {
        if (status.isError()) {
            LOGGER.error(status.getErrorData().getInformation());
        }
    }

    @Override
    public void message(
        final PubNub pubnub,
        final PNMessageResult message
    ) {
        // When a message is received, try and parse it into the SubscriberMessage POJO
        // If the message is valid, record the event using the TrainDataCollector instance
        try {
            final SubscriberMessage subscriberMessage = new Gson().fromJson(message.getMessage(), SubscriberMessage.class);
            trainDataCollector.recordInteraction(new InteractionData(
                subscriberMessage.getDetectorId(),
                message.getTimetoken()
            ));
        } catch (final JsonSyntaxException e) {
            LOGGER.error("Unreadable message received", e);
        } finally {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Message publisher: {}", message.getPublisher());
                LOGGER.debug("Message payload: {}", message.getMessage());
                LOGGER.debug("Message subscription: {}", message.getSubscription());
                LOGGER.debug("Message channel: {}", message.getChannel());
                LOGGER.debug("Message timestamp: {}", message.getTimetoken());
            }
        }
    }

    @Override
    public void presence(
        final PubNub pubnub,
        final PNPresenceEventResult presence
    ) {}
}
