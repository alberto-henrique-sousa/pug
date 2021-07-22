package com.pugsource.util;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

/**
 * Class PDFTextParser
 * 
 * Classe responsável pela conversão de PDF em texto.
 * 
 * @author Alberto Henrique Sousa
 *
 */
public class PDFTextParser {
	
    private PDFParser parser;
    private String parsedText;
    private PDFTextStripper pdfStripper;
    private PDDocument pdDoc;
    private COSDocument cosDoc;
       
    /**
     * Retorna a conversão de um arquivo PDF em string.
     * 
     * @param fileName Arquivo a ser convertido
     * @return Retorna uma string com o conteúdo de um PDF
     */
    public String pdftoText(String fileName) {
        
        File f = new File(fileName);
        
        if (!f.isFile()) {
            return null;
        }
        
        try {
            parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            return null;
        }
        
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc); 
        } catch (Exception e) {
            e.printStackTrace();
            try {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
               } catch (Exception e1) {
               e.printStackTrace();
            }
            return null;
        }      
        return parsedText;
    }
    
    /**
     * Grava texto em arquivo
     * 
     * @param pdfText String convertida
     * @param fileName Arquivo a ser gravado
     */
    public void writeTexttoFile(String pdfText, String fileName) {
    	
    	try {
    		PrintWriter pw = new PrintWriter(fileName);
    		pw.print(pdfText);
    		pw.close();    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
