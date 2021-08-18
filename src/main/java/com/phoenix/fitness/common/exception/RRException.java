

package com.phoenix.fitness.common.exception;

import com.phoenix.fitness.common.constant.ExceptionEnum;

/**
 * 自定义异常
 *
 * @author Mark sm516116978@outlook.com
 */
public class RRException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;

    public RRException(ExceptionEnum exceptionEnum){
    	this.msg=exceptionEnum.getMsg();
    	this.code=exceptionEnum.getCode();
	}
	public RRException(ExceptionEnum exceptionEnum,String message){
		this.msg=message;
		this.code=exceptionEnum.getCode();
	}
    
    public RRException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public RRException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public RRException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public RRException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
