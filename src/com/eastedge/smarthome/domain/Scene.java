package com.eastedge.smarthome.domain;

import java.io.Serializable;

public class Scene implements Serializable {
	private static final long serialVersionUID = -6132811896855410407L;

	/** 情景ID */
	private int sceneId;
	/** 情景位置 */
	private int scenePosition;
	/** 情景名称 */
	private String sceneName;
	/** 情景的背景 */
	private String sceneBg;
	public Scene(int sceneId,String sceneName,String sceneBg,int scenePos){
		this.sceneId=sceneId;
		this.sceneName=sceneName;
		this.sceneBg=sceneBg;
		this.scenePosition=scenePos;
	}
	public Scene(int sceneId,int scenePos){
		this.sceneId=sceneId;
		this.scenePosition=scenePos;
	}
	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public int getScenePosition() {
		return scenePosition;
	}

	public void setScenePosition(int scenePosition) {
		this.scenePosition = scenePosition;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSceneBg() {
		return sceneBg;
	}

	public void setSceneBg(String sceneBg) {
		this.sceneBg = sceneBg;
	}

}
