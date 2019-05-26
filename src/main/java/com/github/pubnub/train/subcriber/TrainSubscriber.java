package com.github.pubnub.train.subcriber;

import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainSubscriber extends SubscribeCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainSubscriber.class);

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
        final String messagePublisher = message.getPublisher();
        LOGGER.info("Message publisher: " + messagePublisher);
        LOGGER.info("Message Payload: " + message.getMessage());
        LOGGER.info("Message Subscription: " + message.getSubscription());
        LOGGER.info("Message Channel: " + message.getChannel());
        LOGGER.info("Message timetoken: " + message.getTimetoken());
    }

    @Override
    public void presence(
        final PubNub pubnub,
        final PNPresenceEventResult presence
    ) {}
}
