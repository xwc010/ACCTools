package com.lckj.autotest.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageComparer {
	private String sourceImagePath;
	private String candidateImagePath;
//	private BufferedImage sourceImage;
//	private BufferedImage candidateImage;
//	
//	public ImageComparer(BufferedImage srcImage, BufferedImage canImage) {
//		this.sourceImage = srcImage;
//		this.candidateImage = canImage;
//	}

	public ImageComparer(String sourceImagePath, String candidateImagePath) {
		this.sourceImagePath = sourceImagePath;
		this.candidateImagePath = candidateImagePath;
	}

	/**
	 * Bhattacharyya Coefficient
	 * http://www.cse.yorku.ca/~kosta/CompVis_Notes/bhattacharyya.pdf
	 *
	 * @return
	 */
	public double modelMatch() {
		BufferedImage sourceImage = null;
		BufferedImage candidateImage = null;
		try {
			sourceImage = ImageIO.read(new File(this.sourceImagePath));
			candidateImage = ImageIO.read(new File(this.candidateImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HistogramFilter hfilter = new HistogramFilter();
		float[] sourceData = hfilter.filter(sourceImage, null);
		float[] candidateData = hfilter.filter(candidateImage, null);
		double[] mixedData = new double[sourceData.length];
		for(int i=0; i<sourceData.length; i++ ) {
			mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
		}

		// The values of Bhattacharyya Coefficient ranges from 0 to 1,
		double similarity = 0;
		for(int i=0; i<mixedData.length; i++ ) {
			similarity += mixedData[i];
		}

		// The degree of similarity
		return similarity;
	}

}
