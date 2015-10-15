package com.hangum.tadpole.engine.query.dao.system;

import java.sql.Timestamp;

/**
 * UserLoginHistory table dao
 * 
 * @author hangum
 *
 */
public class UserLoginHistoryDAO {
	
	int seq;
	int user_seq;
	String login_ip;
	String connet_time;
	String disconnect_time; 
	
	public UserLoginHistoryDAO() {
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the user_seq
	 */
	public int getUser_seq() {
		return user_seq;
	}

	/**
	 * @param user_seq the user_seq to set
	 */
	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
	}

	/**
	 * @return the login_ip
	 */
	public String getLogin_ip() {
		return login_ip;
	}

	/**
	 * @param login_ip the login_ip to set
	 */
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	/**
	 * @return the connet_time
	 */
	public String getConnet_time() {
		return connet_time;
	}

	/**
	 * @param connet_time the connet_time to set
	 */
	public void setConnet_time(String connet_time) {
		this.connet_time = connet_time;
	}

	/**
	 * @return the disconnect_time
	 */
	public String getDisconnect_time() {
		return disconnect_time;
	}

	/**
	 * @param disconnect_time the disconnect_time to set
	 */
	public void setDisconnect_time(String disconnect_time) {
		this.disconnect_time = disconnect_time;
	}
	

}
