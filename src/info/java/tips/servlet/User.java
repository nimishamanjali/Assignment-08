package info.java.tips.servlet;

public class User {

	private String name;
	private String address;
	private User bestfriend;

	public User(String name, String address, User bestfriend) {
		this.name = name;
		this.address = address;
		this.bestfriend = bestfriend;
	}

	public User(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getBestfriend() {
		return bestfriend;
	}

	public void setBestfriend(User bestfriend) {
		this.bestfriend = bestfriend;
	}

}
