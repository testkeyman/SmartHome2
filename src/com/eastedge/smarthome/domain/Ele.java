package com.eastedge.smarthome.domain;

public class Ele {
	private int eleClassifyId;
	private int eleClassifyPosition;
	private String eleClassifyName;
	private String eleClassifyBg;
	private int eleId;
	private String eleName;
	private int elePosition;
	private int eleRoomId;

	/**
	 * 电器分类
	 * 
	 * @param eleClassifyId
	 *            分类ID
	 * @param eleClassifyPosition
	 *            分类图标位置
	 * @param eleClassifyName
	 *            分类名称
	 * @param eleClassifyBg
	 *            分类的背景色
	 */
	public Ele(int eleClassifyId, 
			String eleClassifyName, String eleClassifyBg,int eleClassifyPosition) {
		super();
		this.eleClassifyId = eleClassifyId;
		this.eleClassifyPosition = eleClassifyPosition;
		this.eleClassifyName = eleClassifyName;
		this.eleClassifyBg = eleClassifyBg;
	}
	public Ele(int eleClassifyId,int eleClassifyPosition){
		this.eleClassifyId=eleClassifyId;
		this.eleClassifyPosition=eleClassifyPosition;
	}

	/**
	 * 电器
	 * 
	 * @param eleId
	 *            电器ID
	 * @param eleName
	 *            电器名称
	 * @param elePosition
	 *            电器图标位置
	 */
	public Ele(int eleId, String eleName, int elePosition) {
		super();
		this.eleId = eleId;
		this.eleName = eleName;
		this.elePosition = elePosition;
	}

	/**
	 * 电器所在房间
	 * 
	 * @param eleId
	 *            电器ID
	 * @param eleName
	 *            电器名称
	 * @param elePosition
	 *            电器图标位置
	 * @param eleRoomId
	 *            电器所在房间ID
	 */
	public Ele(int eleId, String eleName, int elePosition, int eleRoomId) {
		super();
		this.eleId = eleId;
		this.eleName = eleName;
		this.elePosition = elePosition;
		this.eleRoomId = eleRoomId;
	}

	public int getEleClassifyId() {
		return eleClassifyId;
	}

	public void setEleClassifyId(int eleClassifyId) {
		this.eleClassifyId = eleClassifyId;
	}

	public int getEleClassifyPosition() {
		return eleClassifyPosition;
	}

	public void setEleClassifyPosition(int eleClassifyPosition) {
		this.eleClassifyPosition = eleClassifyPosition;
	}

	public String getEleClassifyName() {
		return eleClassifyName;
	}

	public void setEleClassifyName(String eleClassifyName) {
		this.eleClassifyName = eleClassifyName;
	}

	public String getEleClassifyBg() {
		return eleClassifyBg;
	}

	public void setEleClassifyBg(String eleClassifyBg) {
		this.eleClassifyBg = eleClassifyBg;
	}

	public int getEleId() {
		return eleId;
	}

	public void setEleId(int eleId) {
		this.eleId = eleId;
	}

	public String getEleName() {
		return eleName;
	}

	public void setEleName(String eleName) {
		this.eleName = eleName;
	}

	public int getElePosition() {
		return elePosition;
	}

	public void setElePosition(int elePosition) {
		this.elePosition = elePosition;
	}

	public int getEleRoomId() {
		return eleRoomId;
	}

	public void setEleRoomId(int eleRoomId) {
		this.eleRoomId = eleRoomId;
	}

	public String toStringEleClassify() {
		return "Ele [eleClassifyId=" + eleClassifyId + ", eleClassifyPosition="
				+ eleClassifyPosition + ", eleClassifyName=" + eleClassifyName
				+ ", eleClassifyBg=" + eleClassifyBg + "]";
	}

	@Override
	public String toString() {
		return "Ele [eleClassifyId=" + eleClassifyId + ", eleId=" + eleId
				+ ", eleName=" + eleName + ", elePosition=" + elePosition
				+ ", eleRoomId=" + eleRoomId + "]";
	}

}
