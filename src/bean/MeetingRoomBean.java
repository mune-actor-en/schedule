package bean;

/**
*
* 会議室情報データ（モデル）
*
*/
public class MeetingRoomBean {

	private String roomId;
	private String roomName;

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}

}
