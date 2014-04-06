package org.esgi.web.action;

import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IContext {
	Object getAttribute(String key);
	void setAttribute(String key, Object o);
	Object getParameter(String key);
	void setParameter(String key, Object o);
	Properties getProperties();
	
	// Object Instance must have a toString implementation
	StringWriter getFragment(String fragment);
	void setFragment(String fragment,StringWriter o);
	
	public Map<String, StringWriter> fragment = new HashMap<>();
	public Map<String, StringWriter> getFragment();	
	
	String getTitle();
	void setTitle(String title);
	
	String getDescription();
	void setDescription(String _descr);
	
	String getKeyWords();
	void addKeyWord(String key);
	
	String[] getJSDependancy();
	void addJSDependancy(String scriptPath);
	
	String getCssDependancy();
	void addCSSDependancy(String cssPath);
	
	String getInlineCss();
	void inlineCSS(String cssInline);
	
	String getRowHeader();
	void addRowHeader(String rowHeader);
	
	void addOnJsReady(String path);
	List<String> getOnJsReady();
	
	HttpServletRequest getRequest();
	HttpServletResponse getResponse();
}