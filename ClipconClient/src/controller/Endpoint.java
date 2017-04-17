package controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import model.Message;
import model.MessageDecoder;
import model.MessageEncoder;
import userInterface.UserInterface;

@ClientEndpoint(decoders = { MessageDecoder.class }, encoders = { MessageEncoder.class })
public class Endpoint {
	private String uri = "ws://182.172.16.118:8080/websocketServerModule/ServerEndpoint";
	private Session session = null;

	private static Endpoint uniqueEndpoint;
	private static UserInterface userInterface;

	public static Endpoint getIntance() {
		System.out.println("getIntance()");
		try {
			if (uniqueEndpoint == null) {
				uniqueEndpoint = new Endpoint();
				
				//userInterface = new UserInterface(); //????????????????????
			}
		} catch (DeploymentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return uniqueEndpoint;
	}

	public Endpoint() throws DeploymentException, IOException, URISyntaxException {
		System.out.println("Endpoint ������");
		URI uRI = new URI(uri);
		ContainerProvider.getWebSocketContainer().connectToServer(this, uRI);
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(Message message) {
		switch (Message.TYPE) {
		case Message.REQUEST_SIGN_IN: // �α��� ��û�� ���� ����
			
			switch (message.get("response")) {
			case "OK":
				userInterface.getStartingScene().showEntryView(); // EntryView ������
				// �������� �̸���, �г���, �ּҷ� �޾� User ��ü ����
				break;
			case "NOT OK":
				System.out.println("�̸���, ��й�ȣ ����ġ");
				break;
			}
			
			break;

		case Message.REQUEST_SIGN_UP:
			
			switch (message.get("response")) {
			case "OK":
				userInterface.getSignupScene().closeSignUpView(); // signUpView ����
				System.out.println("ȸ������ �Ϸ�");
				break;
			case "NOT OK":
				System.out.println("�̸���/�г��� �ߺ�");
				break;
			}
			
			break;

		case Message.REQUEST_CREATE_GROUP:
			
			switch (message.get("response")) {
			case "OK":
				userInterface.getEntryScene().showMainView(); // MainView ������
				// �������� primaryKey, (name) �޾� Group ��ü ���� �� User�� �Ҵ�
				// ���Ŀ� �ٸ� User ���� �� ���� respond �ް� UI ����
				break;
			case "NOT OK":
				break;
			}
			
			break;

		case Message.REQUEST_JOIN_GROUP:
			
			switch (message.get("response")) {
			case "OK":
				userInterface.getEntryScene().showMainView(); // MainView ������
				// �������� (primaryKey), name, ������ ����, �����丮 �޾� Group ��ü ���� �� User�� �Ҵ�
				// ���Ŀ� �ٸ� User ���� �� ���� respond �ް� UI ����
				break;
			case "NOT OK":
				break;
			}
			
			break;

		default:
			break;
		}
	}

	public void sendMessage(Message message) throws IOException, EncodeException {
		session.getBasicRemote().sendObject(message);
	}

	@OnClose
	public void onClose() {
		// ������ ������ �� ��� ���� ó��
	}
}