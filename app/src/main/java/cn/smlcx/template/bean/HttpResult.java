package cn.smlcx.template.bean;

/**
 * Created by lcx on 2017/6/7.
 */

public class HttpResult<T> {
	private int code;
	private String msg;
	private T result;
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
