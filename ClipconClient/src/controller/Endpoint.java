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

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import model.Contents;
import model.Message;
import model.MessageDecoder;
import model.MessageEncoder;
import model.MessageParser;
import model.User;
import userInterface.UserInterface;

@ClientEndpoint(decoders = { MessageDecoder.class }, encoders = { MessageEncoder.class })
public class Endpoint {

	private String uri = "ws://" + Main.SERVER_ADDR + ":8080/websocketServerModule/ServerEndpoint";

	private Session session = null;
	private static Endpoint uniqueEndpoint;
	private static UserInterface ui;

	public static User user;

	public static Endpoint getIntance() {
		try {
			if (uniqueEndpoint == null) {
				uniqueEndpoint = new Endpoint();
			}
		} catch (DeploymentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		return uniqueEndpoint;
	}

	public Endpoint() throws DeploymentException, IOException, URISyntaxException {
		URI uRI = new URI(uri);
		ContainerProvider.getWebSocketContainer().connectToServer(this, uRI);
		ui = UserInterface.getIntance();
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(Message message) {
		System.out.println("message type: " + message.get(Message.TYPE));
		switch (message.get(Message.TYPE)) {
		case Message.RESPONSE_CREATE_GROUP:

			switch (message.get(Message.RESULT)) {
			case Message.CONFIRM:
				System.out.println("create group confirm");

				ui.getStartingScene().showMainView(); // Show MainView
				user = MessageParser.getUserAndGroupByMessage(message); // create Group Object using primaryKey, name(get from server) and set to user

				while (true) {
					if (ui.getMainScene() != null && user != null) {
						break;
					}
					System.out.print(""); // [TODO] UI refresh
				}

				ui.getMainScene().initGroupParticipantList(); // UI list initialization
				
				break;
			case Message.REJECT:
				System.out.println("create group reject");
				break;
			}

			break;
			
		case Message.RESPONSE_CHANGE_NAME:
			
			switch (message.get(Message.RESULT)) {
			case Message.CONFIRM:
				System.out.println("nickname change confirm");
	
				String changeName = message.get(Message.CHANGE_NAME);
				
				user.setName(changeName);
				user.getGroup().getUserList().get(0).setName(changeName);
				user.getGroup().getUserList().get(0).setNameProperty(new SimpleStringProperty(changeName));
				
				ui.getMainScene().closeNicknameChangeStage();
				ui.getMainScene().initGroupParticipantList(); // UI list initialization

				break;
			case Message.REJECT:
				System.out.println("nickname change reject");
				break;
			}
			
			break;
			
		case Message.RESPONSE_JOIN_GROUP:

			switch (message.get(Message.RESULT)) {
			case Message.CONFIRM:
				System.out.println("join group confirm");

				ui.getGroupJoinScene().showMainView(); // close group join and show MainView
				user = MessageParser.getUserAndGroupByMessage(message); // create Group Object using primaryKey, name(get from server) and set to user

				while (true) {
					if (ui.getMainScene() != null && user != null) {
						break;
					}
					System.out.print(""); // [TODO] UI refresh
				}

				ui.getMainScene().initGroupParticipantList(); // UI list initialization

				break;
			case Message.REJECT:
				System.out.println("join group reject");
				ui.getGroupJoinScene().failGroupJoin(); // UI list initialization

				break;
			}

			break;

		case Message.RESPONSE_EXIT_GROUP:

			System.out.println("exit group");

			while (true) {
				if (ui.getMainScene() != null) {
					break;
				}
			}

			ui.getMainScene().showStartingView(); // show StartingView
			user = null;

			break;
			
		case Message.NOTI_ADD_PARTICIPANT: // receive a message when another user enters the group and updates the UI

			System.out.println("add participant noti");

			User newParticipant = new User(message.get(Message.PARTICIPANT_NAME));

			user.getGroup().getUserList().add(newParticipant);
			ui.getMainScene().getGroupParticipantList().add(newParticipant);
			ui.getMainScene().addGroupParticipantList(); // update UI list

			break;
			
		case Message.NOTI_CHANGE_NAME:
			
			System.out.println("change name noti");
			
			String name = message.get(Message.NAME);
			System.out.println("name = " + name);
			String changeName = message.get(Message.CHANGE_NAME);
			System.out.println("change name = " + changeName);
			
			for(int i=0; i<user.getGroup().getUserList().size(); i++) {
				if(user.getGroup().getUserList().get(i).getName().equals(name)) {
					user.getGroup().getUserList().remove(i);
					user.getGroup().getUserList().add(i, new User(changeName));
				}
			}
			
			ui.getMainScene().initGroupParticipantList(); // UI list initialization
			
			break;

		case Message.NOTI_EXIT_PARTICIPANT:

			System.out.println("exit participant noti");

			for (int i = 0; i < user.getGroup().getUserList().size(); i++) {
				if (message.get(Message.PARTICIPANT_NAME).equals(user.getGroup().getUserList().get(i).getName())) {
					int removeIndex = i;
					user.getGroup().getUserList().remove(removeIndex);
					break;
				}
			}

			ui.getMainScene().initGroupParticipantList(); // update UI list

			break;

		case Message.NOTI_UPLOAD_DATA:

			System.out.println("update date noti");

			Contents contents = MessageParser.getContentsbyMessage(message);

			user.getGroup().addContents(contents);
			System.out.println("-----<Endpoint> contentsValue Context-----\n" + contents.getContentsValue());

			ui.getMainScene().getHistoryList().add(0, contents);
			ui.getMainScene().addContentsInHistory(); // update UI list
			
			if (contents.getUploadUserName().equals(Endpoint.user.getName())) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ui.getMainScene().closeProgressBarStage();
			}
			
			break;
			
		case Message.RESPONSE_UPLOAD_INFO:
		case Message.RESPONSE_DOWNLOAD_INFO:
			break;

		default:
			System.out.println("Endpoint default");
			System.out.println("Endpoint default message type: " + message.get(Message.TYPE));
			break;
		}
	}

	public void sendMessage(Message message) throws IOException, EncodeException {
		if (session == null) {
			System.out.println("debuger_delf: session is null");
		}
		session.getBasicRemote().sendObject(message);
	}

	@OnClose
	public void onClose() {
		System.out.println("----------------------on Close----------------------");
		// TODO [delf] How to handle when a session is lost
	}

}