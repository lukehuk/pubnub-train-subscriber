package com.github.pubnub.train;

import com.github.pubnub.train.subcriber.TrainSubscriber;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.slf4j.LoggerFactory;
import java.util.Collections;

public final class PubNubTrain {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PubNubTrain.class);
    private static final String DEFAULT_CHANNEL_NAME = "train-demo";

    /**
     * Subscribes to a PubNub channel and listens for train location information
     *
     * @param args an array of command-line arguments for the application
     */
    public static void main(final String[] args) {
        LOGGER.info("Starting PubNub Train Demo ...");

        final Configuration config = new EnvironmentConfiguration();
        final String pubnubSubscribeKey = config.getString("PUBNUB-SUBSCRIBE-KEY");
        final String pubnubPublishKey = config.getString("PUBNUB-PUBLISH-KEY");
        final String pubnubChannel = config.getString("PUBNUB-CHANNEL-NAME", DEFAULT_CHANNEL_NAME);

        //Configure PubNub
        final PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(pubnubSubscribeKey);
        pnConfiguration.setPublishKey(pubnubPublishKey);
        pnConfiguration.setSecure(false);
        final PubNub pubnub = new PubNub(pnConfiguration);

        //Create PubNub listener and subscribe
        pubnub.addListener(new TrainSubscriber());
        pubnub.subscribe().channels(Collections.singletonList(pubnubChannel)).execute();

        LOGGER.info("Subscribed to {}", pubnubChannel);
    }
}
