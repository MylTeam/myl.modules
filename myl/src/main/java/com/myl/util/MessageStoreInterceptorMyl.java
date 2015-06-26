package com.myl.util;

import java.util.Collection;
import java.util.Map;

import org.apache.struts2.interceptor.MessageStoreInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myl.exception.MessageStoreInterceptorException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;

public class MessageStoreInterceptorMyl extends MessageStoreInterceptor {

	private static final long serialVersionUID = 1L;
	private static final String REDIRECT = "org.apache.struts2.dispatcher.ServletActionRedirectResult";

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageStoreInterceptorMyl.class);	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.MessageStoreInterceptor#after(com.opensymphony
	 * .xwork2.ActionInvocation, java.lang.String)
	 */
	@Override
	protected void after(ActionInvocation invocation, String arg1)
			throws MessageStoreInterceptorException {
		try {
			super.after(invocation, arg1);
		} catch (Exception e) {
			
			LOGGER.error("No se pudo generar reporte");
			throw new MessageStoreInterceptorException(e);
		}
		ActionConfig config = invocation.getProxy().getConfig();
		String resultCode = invocation.getResultCode();
		ResultConfig success = config.getResults().get(resultCode);
		if (success != null) {
			if (!success.getClassName().equals(REDIRECT)) {
				Map<String, Object> s = ActionContext.getContext().getSession();
				s.remove(actionErrorsSessionKey);
				s.remove(fieldErrorsSessionKey);
				s.remove(actionMessagesSessionKey);
			}
		} else {
			Map<String, Object> s = ActionContext.getContext().getSession();
			s.remove(actionErrorsSessionKey);
			s.remove(fieldErrorsSessionKey);
			s.remove(actionMessagesSessionKey);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.MessageStoreInterceptor#mergeCollection
	 * (java.util.Collection, java.util.Collection)
	 */
	@Override
	protected Collection mergeCollection(Collection col1, Collection col2) {
		return super.mergeCollection(col1, col2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.MessageStoreInterceptor#mergeMap(java.
	 * util.Map, java.util.Map)
	 */
	@Override
	protected Map mergeMap(Map map1, Map map2) {
		return super.mergeMap(map1, map2);
	}
}
