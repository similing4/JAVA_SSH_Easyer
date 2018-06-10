package com.pingjia.hibernate;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {
	private static final long serialVersionUID = -4547812039635681749L;
	// Fields
	private Integer id;
	private String username;
	private String password;
	private Integer power;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String password, Integer power) {
		this.username = username;
		this.password = password;
		this.power = power;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPower() {
		return this.power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

}
