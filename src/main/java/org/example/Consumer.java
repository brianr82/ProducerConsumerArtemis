package org.example;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.*;

import java.util.Random;

public class Consumer {


    public static void main(String[] args) {
        ServerLocator locator;
        ClientSession session = null;
        ClientConsumer consumer = null;

        String brokerIP = "tcp://10.70.1.36:61616";
        String address = "merlin_default::merlin_default_to_be_forwarded_to_parent";



        try {
            locator = ActiveMQClient.createServerLocator(brokerIP);
            ClientSessionFactory factory = locator.createSessionFactory();
            session = factory.createSession();
            System.out.println("[Consumer]: Connected to Artemis Broker");

            session.start();
            consumer = session.createConsumer(address);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("[Consumer]: Waiting for first message to arrive...");



        while(true) {
            try {
                ClientMessage clientMessageRecv = consumer.receive();
                System.out.println("New message Received-----------");
                System.out.println("Message: " + clientMessageRecv.getStringProperty("message"));
                clientMessageRecv.acknowledge();
                session.commit();

            }catch (ActiveMQException e) {
                e.printStackTrace();
            }

        }
    }
}
