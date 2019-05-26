package com.github.pubnub.train;

import com.github.pubnub.train.collector.TrainDataCollector;
import com.github.pubnub.train.subcriber.TrainSubscriber;
import com.github.pubnub.train.track.TrackManager;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.slf4j.LoggerFactory;
import java.util.Collections;

public final class PubNubTrain {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PubNubTrain.class);
    private static final String DEFAULT_CHANNEL_NAME = "train-demo";
    private static final String PUBNUB_SUBSCRIBE_KEY_ENV = "PUBNUB-SUBSCRIBE-KEY";
    private static final String PUBNUB_PUBLISH_KEY_ENV = "PUBNUB-PUBLISH-KEY";
    private static final String PUBNUB_CHANNEL_NAME_ENV = "PUBNUB-CHANNEL-NAME";

    /**
     * Subscribes to a PubNub channel and listens for train location information
     *
     * @param args an array of command-line arguments for the application
     */
    public static void main(final String[] args) {
        LOGGER.info("Starting PubNub Train Demo ...");

        final Configuration config = new EnvironmentConfiguration();
        final String pubnubSubscribeKey = config.getString(PUBNUB_SUBSCRIBE_KEY_ENV);
        final String pubnubPublishKey = config.getString(PUBNUB_PUBLISH_KEY_ENV);
        final String pubnubChannel = config.getString(PUBNUB_CHANNEL_NAME_ENV, DEFAULT_CHANNEL_NAME);

        if (null == pubnubPublishKey || null == pubnubSubscribeKey) {
            LOGGER.error("Both the {} and {} environment variables need to be set", PUBNUB_PUBLISH_KEY_ENV, PUBNUB_SUBSCRIBE_KEY_ENV);
        }

        //Configure PubNub
        final PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(pubnubSubscribeKey);
        pnConfiguration.setPublishKey(pubnubPublishKey);
        pnConfiguration.setSecure(false);
        final PubNub pubnub = new PubNub(pnConfiguration);

        //Create PubNub listener and subscribe
        final TrainDataCollector trainDataCollector = new TrainDataCollector(new TrackManager());
        pubnub.addListener(new TrainSubscriber(trainDataCollector));
        pubnub.subscribe().channels(Collections.singletonList(pubnubChannel)).execute();

        LOGGER.info("Subscribed to {}", pubnubChannel);
    }
}
