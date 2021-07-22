package com.pugsource.util;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class XmlSerializer
 * 
 * Classe responsável pela formatação de dados no formato XML
 * 
 * @author Alberto Henrique Sousa
 *
 */
public class XmlSerializer
{	
	/**
	 * Retorna uma mensagem de erro no formato XML
	 * 
	 * @param err
	 * @return Retorna uma string no formato XML
	 */
	public String error(String err)
	{
		StringBuffer sb = new StringBuffer();
		appendHead(sb);
		sb.append("<data><error>");
		cdata(sb, err);
		sb.append("</error></data></response>");
		
		return sb.toString();
	}

	/**
	 * Converte uma lista de objetos em uma String no formato XML
	 * 
	 * @param list
	 * @return Retorna uma string no formato XML
	 */
	public String serialize(List<Object> list)
	{
		StringBuffer sb = new StringBuffer();
		appendHead(sb);
		sb.append("<data>");
		for (int i = 0; i < list.size(); i++)
		{
			sb.append("<row>");
			@SuppressWarnings("unchecked")
			Map<String, Object> vo = (Map<String, Object>) list.get(i);
			appendMap(sb, vo);
			sb.append("</row>");
		}
		sb.append("</data><metadata></metadata>");
		appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * Converte uma lista de objetos em uma String no formato XML
	 * 
	 * @param list
	 * @param toReturn
	 * @return Retorna uma string no formato XML
	 */
	public String serialize(List<Object> list, Map<String, Object> toReturn)
	{
		StringBuffer sb = new StringBuffer();
		appendHead(sb);
		sb.append("<data>");
		for (int i = 0; i < list.size(); i++)
		{
			sb.append("<row>");
			@SuppressWarnings("unchecked")
			Map<String, Object> vo = (Map<String, Object>) list.get(i);
			appendMap(sb, vo);
			sb.append("</row>");
		}
		sb.append("</data>");
		appendMetadata(sb, toReturn);
		appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * Converte uma String no formato XML
	 * 
	 * @param str
	 * @return Retorna uma string no formato XML
	 */
	public String serialize(String str)
	{
		StringBuffer sb = new StringBuffer();
		appendHead(sb);
		sb.append("<data>").append(str);
		sb.append("</data><metadata></metadata>");
		appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * Definição do cabeçalho XML
	 * 
	 * @param sb
	 */
	private void appendHead(StringBuffer sb)
	{
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>");
	}

	/**
	 * Inclui tag Response
	 * 
	 * @param sb
	 */
	private void appendTail(StringBuffer sb)
	{
		sb.append("</response>");
	}

	/**
	 * Inclui metadata
	 * 
	 * @param sb
	 * @param meta
	 */
	private void appendMetadata(StringBuffer sb, Map<String, Object> meta) {
		sb.append("<metadata>");
		appendMap(sb, meta);
		sb.append("</metadata>");
	}
	
	/**
	 * Inclui Map
	 * 
	 * @param sb
	 * @param hm
	 */
	private void appendMap(StringBuffer sb, Map<String, Object> hm) {
		hm.keySet().iterator();
		for (Iterator<String> iter = hm.keySet().iterator(); iter.hasNext();)
		{
			String key = (String) iter.next();
			sb.append('<').append(key).append('>');
			if (hm.get(key) != null)
			{
				sb.append(hm.get(key));
			}
			sb.append("</").append(key).append('>');
		}
	}

	/**
	 * Inclui tag CDATA
	 * 
	 * @param sb
	 * @param str
	 */
	private void cdata(StringBuffer sb,  String str) {
		sb.append("<![CDATA[").append(str).append("]]>");
	}
	
}