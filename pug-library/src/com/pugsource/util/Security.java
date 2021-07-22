package com.pugsource.util;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

/**
 * Class Security
 * 
 * Classe responsável pela criação de chaves de segurança
 * 
 * @author Alberto Henrique Sousa
 * 
 */
public class Security {
	
	/**
	 * Retorna uma string no formato de criptografia MD5
	 * 
	 * @param str Chave a ser convertida
	 * @return Retorna uma string com criptografia MD5
	 */
	static public String md5(String str) {
		try {
			java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");

			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];

			for (int i=0; i<charArray.length; i++)
				byteArray[i] = (byte) charArray[i];

			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();

			for (int i=0; i<md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i] ) & 0xff;

				if (val < 16)
					hexValue.append("0");

				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		} 
		catch (java.security.NoSuchAlgorithmException e) {
			// Ignore
		}

		return "";
	}
		
}
