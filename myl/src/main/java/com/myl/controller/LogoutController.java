package com.myl.controller;


import javax.inject.Named;




import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.myl.util.NombreObjetosSesion;
import com.opensymphony.xwork2.ActionContext;

@Named
@Results({ @Result(name = "login", location = "/pages/login.jsp") })
public class LogoutController {	

	public String execute() {		
		ActionContext.getContext().getSession()
				.remove(NombreObjetosSesion.USUARIO);
		return "login";
	}

	public String index() {
		return execute();
	}

}