package com.pugsource.util;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Pattern;

import org.hibernate.type.Type;

/**
 * Class Tools
 * 
 * Classe de utilitários
 * 
 * @author Alberto Henrique Sousa
 *
 */
public class Tools {

	/**
	 * Converte Object em String
	 * 
	 * @param value
	 * @return Retorna um objeto convertido em string
	 */
	public static String getParameterStr(Object value) 
	{
		if (value != null)
		{
			if(value instanceof String[] ) 
			{
				return ((String[])value)[0];
			} 
			else 
			{			
				return value.toString();
			}			
		}
		else
			return "";		
	}

	/**
	 * Converte Object em Integer
	 * 
	 * @param value
	 * @return Retorna um objeto convertido em inteiro
	 */
	public static Integer getParameterInt(Object value) 
	{
		if (value != null)
			if(value instanceof String[] ) 
			{				
				return new Integer(((String[])value)[0]);
			} 
			else 
			{			
				if (value != null && value.toString().trim().length() > 0)
					return new Integer(value.toString());
				else
					return 0;
			}			
		else
			return 0;		
	}			

	/**
	 * Converte Object em Date no formato DD/MM/YY HH:MM:SS
	 * 
	 * @param value
	 * @return Retorna um objeto convertido em data
	 */
	public static Date getParameterDate(Object value) 
	{
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			if (value != null) {
				if(value instanceof String[] ) 
					calendar.setTime(format.parse(((String[])value)[0]));
				else 
					calendar.setTime(format.parse(value.toString()));
				return calendar.getTime();
			}	
			else
				return null;
		}catch (java.text.ParseException e) { 
			//e.printStackTrace();
			return getParameterDateSmall(value);
		} 			
	}

	/**
	 * Converte Object em Date no formato DD/MM/YYYY
	 * 
	 * @param value
	 * @return Retorna um objeto convertido em data
	 */
	public static Date getParameterDateSmall(Object value) 
	{
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (value != null) {
				if(value instanceof String[] ) 
					calendar.setTime(format.parse(((String[])value)[0]));
				else 
					calendar.setTime(format.parse(value.toString()));
				return calendar.getTime();
			}	
			else
				return null;
		}catch (java.text.ParseException e) { 
			e.printStackTrace();
			return null;
		} 			
	}				

	/**
	 * Converte Object em Boolean
	 * 
	 * @param value
	 * @return Retorna um objeto convertido em boolean
	 */
	public static Boolean getParameterBoolean(Object value) 
	{
		if (value != null)
			if(value instanceof String[] ) 
			{				
				return new Boolean (((String[])value)[0]);
			} 
			else 
			{			
				return new Boolean (value.toString());
			}			
		else
			return null;		
	}

	/**
	 * Retorna uma string no formato de KB ou MB
	 * 
	 * @param obj
	 * @param mb
	 * @return Retorna uma string no formato de KB ou MB
	 */
	public static String formatSize(Object obj, boolean mb) {

		long bytes = -1L;

		if (obj instanceof Long) {
			bytes = ((Long) obj).longValue();
		} else if (obj instanceof Integer) {
			bytes = ((Integer) obj).intValue();
		}

		if (mb) {
			long mbytes = bytes / (1024 * 1024);
			long rest = 
				((bytes - (mbytes * (1024 * 1024))) * 100) / (1024 * 1024);
			return (mbytes + "." + ((rest < 10) ? "0" : "") + rest + " MB");
		} else {
			return ((bytes / 1024) + " KB");
		}

	}	

	/**
	 * Retorna uma chave CDATA para XML
	 * 
	 * @param s
	 * @return Retorna uma string no formato CDATA para XML
	 */
	public static String xmlCData(String s) {
		return "<![CDATA[" + s + "]]>";
	}

	/**
	 * Converte Boolean em Sim ou Não
	 * 
	 * @param value
	 * @return Retorna um boolean convertido em string, padrão brasileiro
	 */
	public static String boolToStrPT(Boolean value) {
		return value == null ? "" : (value ? "Sim" : "Não");
	}

	/**
	 * Converte uma String para um formato String UTF8
	 * 
	 * @param content
	 * @return Retorna uma string convertida no padrão UTF8
	 */
	public static String strToUTF8(String content) {
		String str = "";
		try {
			str = new String(content.getBytes("UTF-8"));    		
		}	catch (Exception e) {
			e.printStackTrace();
		}		  
		return str;
	}

	/**
	 * Retornar a extensão de um nome de arquivo
	 * 
	 * @param filename
	 * @return Retorna uma string com a extensão de um arquivo
	 */
	public static String stripFileExtension(String filename) {
		int x = -1;
		return (x = filename.lastIndexOf(".")) > 0 && x < filename.length() - 1 ? filename.substring(x+1) : filename;
	}

	/**
	 * Retorna se uma extensão de arquivo pertence a uma lista pré-definida
	 * 
	 * @param list
	 * @param extension
	 * @return Retorna True se uma extensão de arquivo pertence a uma lista
	 */
	public static int findFileExtension(String[] list, String extension) {
		int ret = -1;
		int index = 0;
		for (String string : list) {
			if (string.toLowerCase().equals(extension.toLowerCase())) {
				ret = index; 
			}
			index ++;
		}
		return ret;
	}

	/**
	 * Substitui caracteres de uma String utilizando expressões regulares
	 * 
	 * @param string
	 * @param regex
	 * @param replaceWith
	 * @return Retorna uma string com caracteres substituídos
	 */
	public static String replaceAll(String string, String regex, String replaceWith){
		Pattern myPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		string = myPattern.matcher(string).replaceAll(replaceWith);
		return string;
	} 	
	
	private static final char[] FIRST_CHAR =
		(" !'#$%&'()*+\\-./0123456789:;<->?@ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ E ,f'.++^%S<O Z  ''''.-"
				+ "-~Ts>o ZY !C#$Y|$'(a<--(_o+23'u .,1o>113?AAAAAAACEEEEIIIIDNOO"
				+ "OOOXOUUUUyTsaaaaaaaceeeeiiiidnooooo/ouuuuyty")
				.toCharArray();
	private static final char[] SECOND_CHAR =
		("  '         ,                                               "
				+ "\\                                   $  r'. + o  E      ''  "
				+ "  M  e     #  =  'C.<  R .-..     ..>424     E E            "
				+ "   E E     hs    e e         h     e e     h ")
				.toCharArray();
	/**
	 * Efetua as seguintes normalizaÃ§Ãµes:
	 * - Elimina acentos e cedilhas dos nomes;
	 * - Converte aspas duplas em simples;
	 * - Converte algumas letras estrangeiras para seus equivalentes ASCII
	 * (como ÃŸ = eszet, convertido para ss) 
	 * - PÃµe um "\" antes de vÃ­rgulas, permitindo nomes como
	 * "Verisign, Corp." e de "\", permitindo nomes como " a \ b ";
	 * - Converte os sinais de = para -
	 * - Alguns caracteres sÃ£o removidos:
	 * -> os superiores a 255,
	 * mesmo que possam ser representados por letras latinas normais
	 * (como S, = U+015E = Latin Capital Letter S With Cedilla);
	 * -> os caracteres de controle (exceto tab, que Ã© trocado por um espaÃ§o)
	 * @param str A string a normalizar.
	 * @return A string normalizada.
	 */
	public static String normalize(String str) {
		char[] chars = str.toCharArray();
		StringBuffer ret = new StringBuffer(chars.length * 2);
		for (int i = 0; i < chars.length; ++i) {
			char aChar = chars[i];
			if (aChar == ' ' || aChar == '\t') {
				ret.append(' '); // convertido para espaÃ§o
			} else if (aChar > ' ' && aChar < '\u0100') {
				if (FIRST_CHAR[aChar - ' '] != ' ') {
					ret.append(FIRST_CHAR[aChar - ' '] ) ; // 1 caracter
				}
				if (SECOND_CHAR[aChar - ' '] != ' ') {
					ret.append(SECOND_CHAR[aChar - ' '] ) ; // 2 caracteres
				}
			}
		}

		return ret.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static File[] dirListByAscendingName(File folder) {
		if (!folder.isDirectory()) {
			return null;
		}
		File files[] = folder.listFiles();
		Arrays.sort( files, new Comparator()
		{
			public int compare(final Object o1, final Object o2) {
				return ((File)o1).getName().compareTo
				(((File) o2).getName());
			}
		}); 
		return files;
	}  

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static File[] dirListByDescendingName(File folder) {
		if (!folder.isDirectory()) {
			return null;
		}
		File files[] = folder.listFiles();
		Arrays.sort( files, new Comparator()
		{
			public int compare(final Object o1, final Object o2) {
				return ((File)o2).getName().compareTo
				(((File) o1).getName());
			}
		}); 
		return files;
	}

	public static int diffDays(Date dateI, Date dateF){
		Calendar dateTmp1 = Calendar.getInstance();
		Calendar dateTmp2 = Calendar.getInstance();
		dateTmp1.setTime(dateI);
		dateTmp2.setTime(dateF);
		double milliSec1 = dateTmp1.getTimeInMillis();
		double milliSec2 = dateTmp2.getTimeInMillis();
		double numDays = ((milliSec2 - milliSec1)/86400000) + 1; // 86400000 = Milisegundos em um dia
		int ret = (int)(Math.floor(numDays));
		return ret;
	}		
	
	public static String dateToString(Date date, String mask) {
		if (mask == null || mask.length() == 0)
			mask = "dd/MM/yyyy";
		if (date != null)
			return new java.text.SimpleDateFormat(mask).format(date);
		else
			return "";
	}
	
	public static Date dateFormat(Date date, String mask) throws ParseException {
		if (mask == null || mask.length() == 0)
			mask = "dd/MM/yyyy";
		if (date == null)
			date = Calendar.getInstance().getTime();
		return new java.text.SimpleDateFormat(mask).parse(dateToString(date, mask));
	}
	
	public static void setParametersQuery(String name, Object value, Type type,
			ArrayList<ArrayList<Object>> parameters) {
		ArrayList<Object> parameter = new ArrayList<Object>();
		parameter.add(name); parameter.add(value); parameter.add(type);
		parameters.add(parameter);
	}	
	    	
}

