package org.esgi.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONExtractor {

	public static String[] getDepencies(String path)
	{	
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try { 
			root = mapper.readTree(new File(path,"default.js"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Iterator<Entry<String, JsonNode>> ite = root.fields();
		
		HashMap<String, JsonNode> map = new HashMap<String, JsonNode>();
		Map<String, NodeTemplate> nodes = new HashMap<String, NodeTemplate>();
		
		parse(root, map, nodes);
			
		String index = null;
		for(String s : map.keySet()){
			if(index == null)
				index = s;
			else{
				if(map.get(s) != null){
					if(map.get(s).size() > map.get(index).size())
						index = s;
				}
					
			}
		}
		
		nodes.get(index).repet--;

		List<NodeTemplate> list = new ArrayList<NodeTemplate>(nodes.values());
		Collections.sort(list);
		
		String[] ordonnateList = new String[list.size()];
		for(int i = 0; i < list.size(); i++)
			ordonnateList[i] = list.get(i).nom;
		
		return ordonnateList;
	}

	
	public static void parse(JsonNode NodeTemplate, HashMap<String, JsonNode> map, Map<String, NodeTemplate> nodes){
		
		Iterator<Entry<String, JsonNode>> ite = NodeTemplate.fields();
				
		if(!ite.hasNext() && !NodeTemplate.isArray() && !NodeTemplate.isObject()){
			if(map.get(NodeTemplate.toString()) == null){
				map.put(NodeTemplate.toString(), null);
				if(nodes.get(NodeTemplate.toString().replaceAll("\"", "")) == null)
					nodes.put(NodeTemplate.toString().replaceAll("\"", ""), new NodeTemplate(NodeTemplate.toString().replaceAll("\"", "")));
				else
					nodes.get(NodeTemplate.toString().replaceAll("\"", "")).repet++;
			}
		}
		else{
			while(ite.hasNext()){
				Map.Entry<String, JsonNode> entry = ite.next();
				String key = entry.getKey();
				JsonNode value = entry.getValue();
				map.put(entry.getKey(), entry.getValue());
				if(nodes.get(key.replaceAll("\"", "")) == null)
					nodes.put(key.replaceAll("\"", ""), new NodeTemplate(key.replaceAll("\"", "")));
				else
					nodes.get(key.replaceAll("\"", "")).repet++;

				if(entry.getValue().isArray()){
					for(int i = 0; i < entry.getValue().size(); i++)
						parse(entry.getValue().get(i), map, nodes);
				}

					parse(entry.getValue(), map, nodes);
			}
		}
	}
	
	
}

class NodeTemplate implements Comparable<NodeTemplate>{
	
	public String nom;
	public int repet;
	
	public NodeTemplate(String n){
		nom = n;
		repet = 1;
	}
	
	@Override
	public int compareTo(NodeTemplate o) {
		if(repet < o.repet)
			return 1;
		else if(repet > o.repet)
			return -1;
		return 0;
	}


}
