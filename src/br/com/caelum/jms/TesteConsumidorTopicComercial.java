package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopicComercial {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		connection.start();
		
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic consumerTopic  = (Topic) context.lookup("topicExemplo");
		MessageConsumer consumer = session.createDurableSubscriber(consumerTopic, "assinatura");
		
		consumer.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.toString());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		connection.close();
		context.close();
	}
}
