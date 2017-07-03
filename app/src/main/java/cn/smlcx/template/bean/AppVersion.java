package cn.smlcx.template.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lcx on 2017/7/3.
 */

public class AppVersion implements Serializable{
	private int avId;
	private String avCode;
	private int avForceUpdate;//0否1是
	private Date avPushDate;

	public int getAvId() {
		return avId;
	}

	public void setAvId(int avId) {
		this.avId = avId;
	}

	public String getAvCode() {
		return avCode;
	}

	public void setAvCode(String avCode) {
		this.avCode = avCode;
	}

	public int getAvForceUpdate() {
		return avForceUpdate;
	}

	public void setAvForceUpdate(int avForceUpdate) {
		this.avForceUpdate = avForceUpdate;
	}

	public Date getAvPushDate() {
		return avPushDate;
	}

	public void setAvPushDate(Date avPushDate) {
		this.avPushDate = avPushDate;
	}

	public AppVersion(int avId, String avCode, int avForceUpdate, Date avPushDate) {
		this.avId = avId;
		this.avCode = avCode;
		this.avForceUpdate = avForceUpdate;
		this.avPushDate = avPushDate;
	}

	public AppVersion() {
	}
}
