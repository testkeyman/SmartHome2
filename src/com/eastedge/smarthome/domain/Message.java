package com.eastedge.smarthome.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息提示的实体类
 * @author wsp
 *
 */
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1231478838990010982L;

	
	private int messageId;
	private String messageType;
	private String messageAddress;
	private String messageLevel;
	private Date messageTime;
}
