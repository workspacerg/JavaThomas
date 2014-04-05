package org.esgi.web;

import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esgi.web.action.IContext;

public class Context implements IContext {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public Properties properties;
	Map<String, Object> attribute = new HashMap<>();

	String regTel = "^[0-9]{10}$";
	String regLog = "^[a-zA-Z0-9]{3,8}$"; 
	String regMail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$"; // mail !
	String regPassword = "^[a-zA-Z]\\w{3,14}$"; 

	String title 		= "";
	String descr 		= "";
	String pathCss 		= "";
	String JSPath 		= "";
	String inlineCSS 	= "";
	String headerRow	= "";
	ArrayList<String> keyWords  = new ArrayList<String>();
	String keyWordsString = "";
	ArrayList<String> arrayJS  = new ArrayList<String>();
	String jsString = "";
	List<String> jsArray = new ArrayList<String>();

	Context(HttpServletRequest _request, HttpServletResponse _response, Properties properties){
		request = _request;
		response = _response;
		this.properties = properties;
	}
	@Override
	public Object getAttribute(String key) {
		return attribute.get(key);
	}

	@Override
	public void setAttribute(String key, Object o) {
		attribute.put(key, o);
	}

	Map<String, Object> mapParameters = new HashMap<>();

	@Override
	public Object getParameter(String key) {

		return mapParameters.get(key);
	}

	@Override
	public void setParameter(String key, Object o) {
		mapParameters.put(key, o);
	}

	@Override
	public HttpServletRequest getRequest() {
		return this.request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return this.response;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}


	public Map<String, StringWriter> getFragment() {
		return this.fragment;
	}

	@Override
	public StringWriter getFragment(String fragment) {
		return this.fragment.get(fragment);
	}
	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public void setTitle(String _title) {
		title = _title;
	}
	
	@Override
	public void setFragment(String fragment, StringWriter o) 
	{
		this.fragment.put(fragment,o);
	}
	
	@Override
	public String getDescription() {

		return descr;
	}
	
	@Override
	public void setDescription(String _descr) {

		descr = _descr;
	}
	
	@Override
	public void addKeyWord(String key) {
		keyWords.add(key);
	}
	
	@Override
	public void addJSDependancy(String scriptPath) {		
		arrayJS.add(scriptPath);	
	}
	@Override
	public void addCSSDependancy(String cssPath) {
		pathCss	= cssPath;

	}
	@Override
	public void inlineCSS(String cssInline) {
		inlineCSS = cssInline;	
	}
	@Override
	public void addRowHeader(String rowHeader) {
		headerRow = rowHeader;

	}
	@Override
	public String[] getJSDependancy() {
		if(arrayJS.size() == 0)
			return new String[0];
		String[] strs = new String[arrayJS.size()];
		
		int i = 0;
		
		for(String s : arrayJS)
			strs[i++] = "<script type='text/javascript' src='"+s+"'> </script>";
		
		return strs;
	}
	@Override
	public String getCssDependancy() {

		return pathCss;
	}
	@Override
	public String getInlineCss() {

		return inlineCSS;
	}
	@Override
	public String getRowHeader() {

		return headerRow;
	}
	@Override
	public String getKeyWords() {
		if(keyWords.size() == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		for(String s:keyWords)
			sb.append(s+",");
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
		
	}
	@Override
	public void addOnJsReady(String path) {
		jsArray.add(path);
	}
	@Override
	public List<String> getOnJsReady() {
		return jsArray;
	}
	
	public boolean valid(String value, EnumField enumType){

		String regex = regLog; // On prend login par défaut si ce n'est aucun des autres
		
		if(enumType.equals(EnumField.LOG))
			regex = regLog;
		if(enumType.equals(EnumField.MAIL))
			regex = regMail;
		if(enumType.equals(EnumField.PASSWORD))
			regex = regPassword;
		if(enumType.equals(EnumField.TELEPHONE))
			regex = regTel;
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value.toUpperCase());
		return m.matches(); 

		}
}
