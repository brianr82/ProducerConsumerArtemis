package org.example;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.*;

import java.util.Random;

public class Producer {


    public static void main(String[] args) {
        ServerLocator locator = null;
        ClientSession session = null;
        ClientProducer producer = null;

        String brokerIP = "tcp://10.70.1.36:61616";
        String address = "merlin_default";

        byte[] payload;
        // sets the payload to the desired size
        payload = new byte[1024];
        Random rd = new Random();
        rd.nextBytes(payload);


        try {
            locator = ActiveMQClient.createServerLocator(brokerIP);
            ClientSessionFactory factory = locator.createSessionFactory();
            session = factory.createSession();
            System.out.println("[Producer]: Connected to Artemis Broker");

            session.start();
            producer = session.createProducer(address);

        } catch (Exception e) {
            e.printStackTrace();
        }




        ClientMessage messageWithBytes = session.createMessage(false);
        messageWithBytes.setDurable(false);
        messageWithBytes.putBytesProperty("payload", payload);
        messageWithBytes.putLongProperty("timestamp", System.currentTimeMillis());
        messageWithBytes.putStringProperty("message", "Hello");


        try {
            producer.send(messageWithBytes);
            System.out.println("[Producer]: Message sent to queue");
        } catch (ActiveMQException e) {
            throw new RuntimeException(e);
        }

    }

}
