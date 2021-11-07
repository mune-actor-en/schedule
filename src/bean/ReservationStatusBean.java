package bean;

public class ReservationStatusBean {

	private String roomId;
	private String roomName;
	private String userId;
	private String userName;
	
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
	
	public void setId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

}
