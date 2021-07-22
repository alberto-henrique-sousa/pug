package com.pugsource.util;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Class ImageThumbnail
 * 
 * Classe responsável pela criação de miniaturas de imagens.
 * 
 * @author Alberto Henrique Sousa
 *
 */
public class ImageThumbnail {

	/**
	 * Cria miniatura de imagem em disco
	 * 
	 * @param orig Arquivo de origem
	 * @param thumb Arquivo de destino
	 * @param maxDim Tamanho máximo
	 */
	public static void createThumbnail(String orig, String thumb, int maxDim) {
		try {

			Image inImage = new ImageIcon(orig).getImage();
			double scale = (double)maxDim/(double)inImage.getWidth(null);

			int scaledW = (int)(scale*inImage.getWidth(null));
			int scaledH = (int)(scale*inImage.getHeight(null));
			if ((scaledW > 0 && scaledH > 0) && (inImage.getWidth(null) >= maxDim || inImage.getHeight(null) >= maxDim)) {
				
				Thumbnails.of(orig)
		        .size(scaledW, scaledH)
		        .toFile(thumb);

			}
			inImage.flush();
			inImage = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cria miniatura de imagem em disco
	 * 
	 * @param orig Arquivo de origem
	 * @param thumb Arquivo de destino
	 * @param maxDim Tamanho máximo
	 */
	public static void createThumbnailLimit(String orig, String thumb, int maxDim) {
		try {

			Image inImage = new ImageIcon(orig).getImage();
			double scale = (double)maxDim/(double)inImage.getWidth(null);

			int scaledW = (int)(scale*inImage.getWidth(null));
			int scaledH = (int)(scale*inImage.getHeight(null));
					
			if (scaledH > maxDim) {
				scale = (double)maxDim/(double)inImage.getHeight(null);
				
				scaledW = (int)(scale*inImage.getWidth(null));
				scaledH = (int)(scale*inImage.getHeight(null));				
			}
					
			if ((scaledW > 0 && scaledH > 0) && (inImage.getWidth(null) >= maxDim || inImage.getHeight(null) >= maxDim)) {

				AffineTransform tx = new AffineTransform();

				if (scale < 1.0d) {
					tx.scale(scale, scale);
				}

				Thumbnails.of(orig)
		        .size(scaledW, scaledH)
		        .toFile(thumb);
			}
			inImage.flush();
			inImage = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
