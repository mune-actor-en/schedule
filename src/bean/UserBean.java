package bean;

/**
 *
 * ユーザー情報データ（モデル）
 *
 */
public class UserBean {

	private String userId;
	private String userName;
	private String sex;

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

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

}
