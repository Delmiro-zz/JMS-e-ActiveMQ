package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorFilaLog {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination log  = (Destination) context.lookup("log");
		MessageProducer producer = session.createProducer(log);
		
		Message message = session.createTextMessage("INF");
		producer.send(message , DeliveryMode.NON_PERSISTENT, 3 , 5000);
		
		session.close();
		connection.close();
		context.close();
	}
}
