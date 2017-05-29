package model;

import org.json.JSONObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Message {
	private String type;
	private JSONObject json;

	public Message setJson(String string) {
		json = new JSONObject(string);
		type = json.getString(TYPE);
		return this;
	}

	public Message setJson(JSONObject json) {
		this.json = json;
		type = json.getString(TYPE);
		return this;
	}
	
	public Message setType(String type) {
		json = new JSONObject();
		this.type = type;
		json.put(TYPE, type);
		return this;
	}

	public Message(String type, String jsonString) {
		this.type = type;
		json = new JSONObject();
		json.put(TYPE, type);
		json.put(CONTENTS, jsonString);
	}

	public void add(String key, String value) {
		json.put(key, value);
	}

	public String get(String key) {
		return json.get(key).toString();
	}
	
	public String toString() {
		return json.toString();
	}
	
	public Object getObject(String key) {
		return json.get(key);
	}
	
	public long getLong(String key) {
		return json.getLong(key);
	}

	public final static String TYPE = "message type";

	public final static String REQUEST_CREATE_GROUP = "request/create group";
	public final static String REQUEST_JOIN_GROUP = "request/join group";
	public final static String REQUEST_CHANGE_NAME = "request/change name";
	public final static String REQUEST_EXIT_GROUP = "request/exit group";
	public final static String REQUEST_TEST = "request/test";

	public final static String RESPONSE_CREATE_GROUP = "response/create group";
	public final static String RESPONSE_JOIN_GROUP = "response/join group";
	public final static String RESPONSE_CHANGE_NAME = "response/change name";
	public final static String RESPONSE_EXIT_GROUP = "response/exit group";
	public final static String RESPONSE_UPLOAD_INFO = "response/upload info";  
	public final static String RESPONSE_DOWNLOAD_INFO = "response/download info";
	
	public final static String NOTI_ADD_PARTICIPANT = "noti/add participant";
	public final static String NOTI_CHANGE_NAME = "noti/change name";
	public final static String NOTI_EXIT_PARTICIPANT = "noti/exit participant";
	public final static String NOTI_UPLOAD_DATA = "noti/upload data";
	
	public final static String LOG_UPLOAD_INFO = "log/upload info";
	public final static String UPLOAD_START_TIME = "upload start time";
	public final static String MULTIPLE_CONTENTS_INFO = "multiple contents info";
	
	public final static String LOG_DOWNLOAD_INFO = "log/download info";
	public final static String DOWNLOAD_END_TIME_BEFORE_COMPRESS = "download end time before compress";
	public final static String DOWNLOAD_END_TIME_AFTER_COMPRESS = "download end time after compress";
	public final static String DOWNLOAD_DEVICETYPE = "download device type";
	public final static String DOWNLOAD_CONTENTS_LENGTH = "download contents length";
	public final static String DOWNLOAD_CONTENTS_TYPE = "download contents type";
	
	public final static String RESULT = "result";
	public final static String CONFIRM = "confirm";
	public final static String REJECT = "reject";

	public final static String NAME = "name";
	public final static String CHANGE_NAME = "change name";
	public final static String CONTENTS = "contents";
	public final static String LIST = "list";
	public final static String USER_INFO = "user information";
	public final static String GROUP_NAME = "group name";
	public final static String GROUP_PK = "group pk";
	public final static String GROUP_INFO = "group information";
	public final static String PARTICIPANT_NAME = "participant name";

	public final static String TEST_DEBUG_MODE = "debug";
}
