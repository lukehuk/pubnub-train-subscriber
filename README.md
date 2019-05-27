# The PubNub Train - Subscriber
This is the subscriber component of a simple PubNub demonstration project inspired by a locomotive tracking system.

To see the publisher component of this project visit [this project](https://github.com/lukehuk/pubnub-train-publisher).

To read more about the background and motivation of this project visit [this article](https://medium.com/@luke.heavens/the-pubnub-train-c90ea36fffce)

## Getting Started
### Prerequisites
This project requires:
* Java 8
* [Maven](https://maven.apache.org/)

### Installing
In order to use the application you will need publisher and subscriber PubNub keys. Free registration is possible at [https://dashboard.pubnub.com/signup](https://dashboard.pubnub.com/signup) 

The API keys should be added as the `PUBNUB-SUBSCRIBE-KEY` and `PUBNUB-PUBLISH-KEY`  environment variables. This should be trivial with IDE run configuration settings or from a command line this is possible by using `export`:

```
export PUBNUB-SUBSCRIBE-KEY={subscribeKey}
export PUBNUB-PUBLISH-KEY={publishKey}
```

The optional environment variable `PUBNUB-CHANNEL-NAME` can be provided in the same way, but if omitted this will default to `train-demo`

### Running
One started, the application will subscribe to the configured PubNub channel. It will attempt to interpret incoming messages as train location data. Using this information the trains position on the track, speed and other metrics will be calculated and logged.

#### Logging settings
Logging can be configured by adjusting the `simplelogger.properties` file

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [PubNub](https://www.pubnub.com/) - Realtime messaging platform 
* [SLF4J](https://www.slf4j.org/) - Simple logging

## License
This project is licensed under the Apache-2.0 License