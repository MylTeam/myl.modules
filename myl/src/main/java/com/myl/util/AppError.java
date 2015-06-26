package com.myl.util;

import java.util.Date;

public class AppError {
	
	private String url;
	private String stackTrace;
	private Date fecha;
	private String exceptionName;
	
	/**
	 * @param url
	 * @param stackTrace
	 * @param fecha
	 */
	public AppError(String url, String stackTrace, Date fecha,String exceptionName) {
		
		this.url = url;
		this.stackTrace = stackTrace;
		this.fecha = fecha;
		this.setExceptionName(exceptionName);
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the stackTrace
	 */
	public String getStackTrace() {
		return stackTrace;
	}
	
	/**
	 * @param stackTrace the stackTrace to set
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
}
