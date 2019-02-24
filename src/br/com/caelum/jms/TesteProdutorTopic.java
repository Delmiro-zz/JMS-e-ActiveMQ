package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopic {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topic  = (Destination) context.lookup("topicExemplo");
		MessageProducer producer = session.createProducer(topic);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		Message message = session.createObjectMessage(pedido);
		
		message.setBooleanProperty("atributo", false);
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
	}
}
