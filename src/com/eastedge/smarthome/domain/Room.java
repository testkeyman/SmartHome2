package com.eastedge.smarthome.domain;

import java.io.Serializable;
/**
 * 房间控制实体
 */
public class Room implements Serializable {
	private static final long serialVersionUID = -5127855749255642606L;

	/**房间控制ID*/
	private int roomCtrlId;
	/**房间控制位置*/
	private int roomCtrlPosition;
	/**房间控制名称*/
	private String roomCtrlName;
	/**房间控制背景*/
	private String roomCtrlBg;
	/**房间内电器的排放顺序*/
	private int roomPosition;
	private int roomEleId;
	private int roomId;


	/** 房间控制 */
	public Room(int roomCtrlId, int roomCtrlPosition, String roomCtrlName,
			String roomCtrlBg) {
		super();
		this.roomCtrlId = roomCtrlId;
		this.roomCtrlPosition = roomCtrlPosition;
		this.roomCtrlName = roomCtrlName;
		this.roomCtrlBg = roomCtrlBg;
	}

	/** 房间 */
	public Room(int roomCtrlId, String roomCtrlName, int roomPosition,
			int roomEleId) {
		super();
		this.roomCtrlId = roomCtrlId;
		this.roomCtrlName = roomCtrlName;
		this.roomPosition = roomPosition;
		this.roomEleId = roomEleId;

	}

	public Room(int roomId, int roomPosition) {
		this.roomCtrlId = roomId;
		this.roomCtrlPosition = roomPosition;
	}

	public int getRoomId() {
		return roomCtrlId;
	}

	public int getRoomCtrlId() {
		return roomCtrlId;
	}

	public void setRoomCtrlId(int roomCtrlId) {
		this.roomCtrlId = roomCtrlId;
	}

	public int getRoomCtrlPosition() {
		return roomCtrlPosition;
	}

	public void setRoomCtrlPosition(int roomCtrlPosition) {
		this.roomCtrlPosition = roomCtrlPosition;
	}

	public String getRoomCtrlName() {
		return roomCtrlName;
	}

	public void setRoomCtrlName(String roomCtrlName) {
		this.roomCtrlName = roomCtrlName;
	}

	public String getRoomCtrlBg() {
		return roomCtrlBg;
	}

	public void setRoomCtrlBg(String roomCtrlBg) {
		this.roomCtrlBg = roomCtrlBg;
	}

	public int getRoomPosition() {
		return roomPosition;
	}

	public void setRoomPosition(int roomPosition) {
		this.roomPosition = roomPosition;
	}

	public int getRoomEleId() {
		return roomEleId;
	}

	public void setRoomEleId(int roomEleId) {
		this.roomEleId = roomEleId;
	}

	public String toStringRoom() {
		return "Room [roomCtrlId=" + roomCtrlId + ", roomCtrlName="
				+ roomCtrlName + ", roomPosition=" + roomPosition
				+ ", roomEleId=" + roomEleId + "]";
	}

	@Override
	public String toString() {
		return "Room [roomCtrlId=" + roomCtrlId + ", roomCtrlPosition="
				+ roomCtrlPosition + ", roomCtrlName=" + roomCtrlName
				+ ", roomCtrlBg=" + roomCtrlBg + "]";
	}

}
