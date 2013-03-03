package com.eastedge.smarthome.util;

public class DataCon {
	String stringreturn;

	public DataCon(String string) {
		stringreturn = string;
	}

	/**
	 * 从机地址
	 */
	String address;
	/**
	 * 校验的数据
	 */
	String datatocrc;

	public String getDatatocrc() {
		return stringreturn.substring(0, stringreturn.length() - 4);
	}

	public void setDatatocrc(String datatocrc) {
		this.datatocrc = datatocrc;
	}

	public String getAddress() {
		return stringreturn.substring(0, 2);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFunno() {
		return stringreturn.substring(2, 4);
	}

	public void setFunno(String funno) {
		this.funno = funno;
	}

	public String getCrc() {
		return stringreturn.substring(stringreturn.length() - 4,
				stringreturn.length());
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	/**
	 * 功能码
	 */
	String funno;
	/**
	 * 校验码
	 */
	String crc;

}
