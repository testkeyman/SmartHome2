package com.eastedge.smarthome.util;

public class Const {
	// 情景含义
	// 照明全开（1-15级）
	/******************************
	 * 功能码
	 */
	/**
	 * 十进制3 　 数据域：　　　ADR-H,ADR-L,Num-H,Num-L(4个字节)　 说明： 读取从Num个16位寄存器的值。
	 * ADR-H&ADR-L 16位寄存器的起始地址，Num-H&Num-L 16位寄存器的数目。
	 * 注：该命令可以得到本转发器的RF设备地址。（该转发器一般一次只读一个16bit寄存器）。
	 */
	public static final int FUNNUM03 = 0x03;
	/**
	 * 十进制6 　 数据域：　　ADR-H,ADR-L,Data-H，Data-L 说明： 　　　装入一个16位寄存器的值
	 * 注：该命令可以改写本转发器的RF设备地址。
	 */
	public static final int FUNNUM06 = 0x06;
	/**
	 * 十进制65 　 数据域：　　　　　　Xx（一个字节）　 说明： 读取从Num个16位寄存器的值。 ADR-H&ADR-L
	 * 16位寄存器的起始地址，Num-H&Num-L 16位寄存器的数目。
	 * 注：该命令可以得到本转发器的RF设备地址。（该转发器一般一次只读一个16bit寄存器）。
	 */
	public static final int FUNNUM65 = 0x41;
	/**
	 * 十进制66 　 数据域：　　　ADR-H,ADR-L,Num-H,Num-L(4个字节)　 说明： 读取从Num个16位寄存器的值。
	 * ADR-H&ADR-L 16位寄存器的起始地址，Num-H&Num-L 16位寄存器的数目。
	 * 注：该命令可以得到本转发器的RF设备地址。（该转发器一般一次只读一个16bit寄存器）。
	 */
	public static final int FUNNUM66 = 0x42;
	/***
	 * 十进制67 　 数据域：　　　　　Xx，Yy （两个字节）　 说明： 　　　将Xx，Yy二个字节的内容发送出去
	 * 说明：Xx，Yy内容和该转发器的RF设备地址组合形成RF发射内容。
	 */
	public static final int FUNNUM67 = 0x43;
	/**
	 * 十进制68 　 数据域：　　　Xx　 说明： 得到传感器的状态寄存器值 （此品未列此项）
	 */
	public static final int FUNNUM68 = 0x44;
	/**
	 * 十进制70　 数据域：　　　Xx,Yy,Zz (三个字节)　 说明：　　　将Xx，Yy,Zz三个字节的内容发送出去
	 * Xx，Yy内容和该转发器的RF设备地址组合形成RF发射内容。
	 */
	public static final int FUNNUM70 = 0x46;
	/**
	 * 十进制71 　 数据域：　　　NC　 说明：无
	 */
	public static final int FUNNUM71 = 0x47;
	/**
	 * 十进制72　 数据域：　　　Xx　 说明： 　　　在广播方式，修改从设备的本机PID（地址）。 Xx即为新的从设备PID（地址）
	 */
	public static final int FUNNUM72 = 0x48;
	/**
	 * 十进制73 　 数据域：　　　Xx　 说明： 在广播方式，波特率设置: Xx=0 and Xx=1 for 9.6kbps;Xx=2 for
	 * 1.2kbps;Xx=3 for 300bps；Xx=4 for 19.2Kbps; Xx=5 for 38.4kbps ;Xx=6 for
	 * 115.2kpbs（注：某些不支持的波特率选项 自动改为9.6Kbps）。
	 */
	public static final int FUNNUM73 = 0x49;
	/**
	 * 十进制74　 数据域：　　　B0,B1,B2,B3（四个字节）　 说明：　　　将B0，B1,B2，B3，四个字节的内容发送出去。
	 */
	public static final int FUNNUM74 = 0x4a;
	/**
	 * 十进制75　 数据域：　　　B0,B1,B2,B3,B4（五个字节）　 说明： 　　　将B0，B1,B2，B3，B4五个字节的内容发送出去。
	 */
	public static final int FUNNUM75 = 0x4b;
	/**
	 * 十进制76 　 数据域：　　　B0,B1,B2,B3,B4，B5（六个字节）　 说明：
	 * 　　　　　　将B0，B1,B2，B3，B4，B5六个字节的内容发送出去。
	 */
	public static final int FUNNUM76 = 0x4c;
	/**
	 * 十进制77 　 数据域：　　　B0,B1,B2,B3,B4，B5（六个字节）　 说明：
	 * 　　　将B0，B1,B2，B3，B4，B5，B6七个字节的内容发送出去。
	 */
	public static final int FUNNUM77 = 0x4d;
	/**
	 * 十进制78 　 数据域：　　　　　　B0,B1,B2,B3,B4，B5，B6,B7 （八个字节）　 说明：
	 * 将B0，B1,B2，B3，B4，B5，B6，B7八个字节的内容发送出去。
	 */
	public static final int FUNNUM78 = 0x4e;

	/**************************
	 * 从机返回
	 */
	/**
	 * 十进制81 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */

	public static final int FUNNUM81 = 0x51;
	/**
	 * 十进制82 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM82 = 0x52;
	/**
	 * 十进制83 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM83 = 0x53;
	/**
	 * 十进制84 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM84 = 0x54;
	/**
	 * 十进制85 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM85 = 0x55;
	/**
	 * 十进制86 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM86 = 0x56;
	/**
	 * 十进制87 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM87 = 0x57;
	/**
	 * 十进制88 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM88 = 0x58;
	/**
	 * 十进制128 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM128 = (int) 0x80;
	/**
	 * 十进制129 　 数据域：　　　　　　从机返回B0一个字节数据　 说明： 　　　PID 0x51 B0 CRCH CRCL
	 */
	public static final int FUNNUM129 = (int) 0x81;
	/**06码前缀*/
	public static final String studySendPid="01060000";
	/**43码只关前缀*/
	public static final String controlOnlyClosePid="014314";
	/**43码只开前缀*/
	public static final String controlOnlyOpenPid="014318";
	/**43码光+前缀*/
	public static final String controlLightAddPid="01431a";
	/**43码光-前缀*/
	public static final String controlLightCutPid="014316";

}
