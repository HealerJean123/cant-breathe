package com.hlj.eigth.message;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ProducerInfo;

public class NoPersistenceReceiver {
	public static void main(String[] args) throws Exception {
		
		
		String linuxIp = "myLinuxQj";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://"+linuxIp+":61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		final Session session = connection.createSession(Boolean.TRUE,
				Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createTopic("advisory_MyTopic");
		
		//获取生产者的信息
		//Destination destination = session.createTopic("ActiveMQ.Advisory.Producer.Topic.advisory_MyTopic");
	    /*
		Topic topic =session.createTopic("advisory_MyTopic");
		Destination destination = AdvisorySupport.getProducerAdvisoryTopic(topic);
		*/
		MessageConsumer consumer = session.createConsumer(destination);
		System.out.println("创建 advisory_MyTopic 消费者");
		Message message = consumer.receive();
		
	    while(message!=null) {

	          /*
	            if(message instanceof ActiveMQMessage){
	    		ActiveMQMessage amsg = (ActiveMQMessage)message;
	    		ProducerInfo prod = (ProducerInfo) amsg.getDataStructure();
	    		System.out.println(amsg.getProperty("producerCount"));
	    		System.out.println(prod.getProducerId()); 
	    	  }*/
	    	 TextMessage txtMsg = (TextMessage)message;  
		     System.out.println("收到消 息：" + txtMsg.getText());        
	    	 message = consumer.receive();
	    } 
	    session.commit();
		session.close();
		connection.close();
	}

}
