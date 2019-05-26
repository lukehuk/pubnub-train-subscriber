# The PubNub Train - Subscriber
The subscriber component of a simple PubNub demonstration project inspired by a locomotive tracking system.

## Other components
To see the subscriber component of this project visit [this project](https://github.com/lukehuk/pubnub-train-publisher).

## Background
To read more about the background and motivation of this project visit [this article](https://medium.com/p/c90ea36fffce)

## Getting Started
### Prerequisites
This project requires:
* Java 8
* [Maven](https://maven.apache.org/)

### Installing
In order to use the application you will need publisher and subscriber PubNub keys. Free registration is possible at [https://dashboard.pubnub.com/signup](https://dashboard.pubnub.com/signup) 

The API keys should be added as the `PUBNUB_SUBSCRIBE_KEY` and `PUBNUB_PUBLISH_KEY`  environment variables. This should be trivial with IDE run configuration settings or from a command line this is possible by using `export`:

```
export PUBNUB_SUBSCRIBE_KEY={subscribeKey}
export PUBNUB_PUBLISH_KEY={publishKey}
```

### Running
One started, the application will subscribe to the configured PubNub channel. It will attempt to interpret incoming messages as train location information. Using this information the trains location, speed and other metrics will be calculated.

#### Logging settings
Logging can be configured by adjusting the `simplelogger.properties` file

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [Jersey](https://jersey.github.io/) - Open source framework for developing RESTful Web Services in Java 
* [SLF4J](https://www.slf4j.org/) - Simple logging

## License
This project is licensed under the Apache-2.0 License