package org.esgi.tools;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.esgi.web.action.IContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperAjax {

	private ObjectMapper mapper;
	private StringWriter sw;
	private Map<String,Object> map;
	
	public MapperAjax(){		
		mapper = new ObjectMapper();
		sw = new StringWriter();
		map = new HashMap<String, Object>();
	}
	
	public void Add(String key, Object value){
		map.put(key, value);
	}
	
	public void Write(IContext context){
		try{
			mapper.writeValue(sw, map);
			context.getResponse().getWriter().print(sw.toString());
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
	}
}
