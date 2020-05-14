package opencv;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.video.Video;
import org.opencv.imgproc.Imgproc;

public class utils {
	public static Mat LectureImage(String fichier) {
		File f = new File(fichier);
		Mat m = Highgui.imread(f.getAbsolutePath());
		return m;
	}
	public static void Imshow(String title, Mat img) {
		MatOfByte matOfByte = new MatOfByte();
		Highgui.imencode(".png", img, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try { 
			InputStream in = new ByteArrayInputStream(byteArray); 
			bufImage = ImageIO.read(in); 
			JFrame frame = new JFrame(); 
			frame.setTitle(title); 
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage))); 
			frame.pack(); 
			frame.setVisible(true); 
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
	}
	public static Mat DetecterObjetRouge(Mat hsv_image) {
		Mat threshold_img1 = new Mat(); 
		Mat threshold_img2 = new Mat(); 
		Mat threshold_img = new Mat(); 
		Core. inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), threshold_img1); 
		Core. inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2); 
		Core. bitwise_or(threshold_img1, threshold_img2, threshold_img); 
		Imgproc.GaussianBlur(threshold_img, threshold_img, new Size(9, 9), 2.0, 2.0); 
		return threshold_img; 
	}
	public static List<MatOfPoint> DetecterContours(Mat threshold_img) {

		int thresh = 100; 
		Mat canny_output = new Mat(); 
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
		MatOfInt4 hierarchy = new MatOfInt4(); 
		Imgproc.Canny( threshold_img, canny_output, thresh, thresh*2); 
		Imgproc.findContours( canny_output, contours, hierarchy,Imgproc.RETR_EXTERNAL, 
				Imgproc.CHAIN_APPROX_SIMPLE); 		
		return contours; 
	}

	public static Mat Scaling(Mat Object, Mat std) {// scale Object to size of sroadSign 
		Mat sObject = new Mat(); 
		Imgproc.resize(Object, sObject, std.size()); 
		return sObject;
	}

	public static Mat SigntoGray(Mat Object) {
		Mat grayObject = new Mat(Object.rows(), Object.cols(), Object.type()); 
		Imgproc.cvtColor(Object, grayObject, Imgproc.COLOR_BGRA2GRAY); 
		Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX); 
		return grayObject;
	}

	public static Mat extractRoadSign(Mat img) {// extract the rond red road sign from a image
		
		Mat m = img;
		Mat cuttedImg = new Mat();
		Mat hsv_image = Mat.zeros(m.size(),m.type()); 
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV); 

		Mat threshold_img = utils.DetecterObjetRouge(hsv_image); 
		List<MatOfPoint> contours = utils.DetecterContours(threshold_img); 
		MatOfPoint2f matOfPoint2f = new MatOfPoint2f(); 
		float[] radius = new float[1]; 
		Point center = new Point(); 
		for (int c=0; c < contours.size();c++) {
			MatOfPoint contour = contours.get(c); 
			double contourArea = Imgproc.contourArea(contour); 
			matOfPoint2f.fromList(contour.toList()); 
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius); 
			if ((contourArea/(Math.PI*radius[0]*radius[0])) >=0.7) { 
				Core.circle(m, center, (int)radius[0], new Scalar(0, 255, 0), 2); 
				Rect rect = Imgproc.boundingRect(contour); 
				Core.rectangle(m, new Point(rect.x,rect.y), 
						new Point(rect.x+rect.width,rect.y+rect.height), 
						new Scalar (0, 255, 0), 2); 
				Mat tmp = m.submat(rect.y,rect.y+rect.height,rect.x,rect.x+rect.width); 
				Mat ball = Mat.zeros(tmp.size(),tmp.type()); 
				tmp.copyTo(ball); 
				//utils.Imshow("Ball",ball); 
				cuttedImg = ball;
			}
		}	
		return cuttedImg;
	}
	
	public static void Matching(Mat sObject, Mat sroadSign) {// not functoning well
		//extraction des caracteristiques
		FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB); 
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB); 

		MatOfKeyPoint objectKeypoints = new MatOfKeyPoint(); 
		Mat grayObject = utils.SigntoGray(sObject);
		orbDetector.detect(grayObject, objectKeypoints); 
		
		MatOfKeyPoint signKeypoints = new MatOfKeyPoint(); 
		Mat graySign = utils.SigntoGray(sroadSign);
		orbDetector.detect(graySign, signKeypoints); 

		Mat objectDescriptor = new Mat(sObject.rows(), sObject.cols(), sObject.type()); 
		orbExtractor.compute(grayObject, objectKeypoints, objectDescriptor); 

		Mat signDescriptor = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type()); 
		orbExtractor.compute(graySign, signKeypoints, signDescriptor); 
		
		// le matching
		MatOfDMatch matchs = new MatOfDMatch(); 
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE); 
		matcher.match(objectDescriptor, signDescriptor, matchs); 
		
	    System.out.println(matchs.dump());
	    //System.out.println(matchs.);
		//return Integer.valueOf(matchs.dump().length());
		Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type()); 
		utils.Imshow("matched ing ", matchedImage);
		Features2d.drawMatches(sObject, objectKeypoints, sroadSign, signKeypoints, matchs, matchedImage); 

	}

	public static ArrayList<String> getFiles(String path) {// return names of ref
	    ArrayList<String> files = new ArrayList<String>();
	    File file = new File(path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	            files.add(tempList[i].toString());
	        }
	        if (tempList[i].isDirectory()) {
	        }
	    }
	    return files;
	}

	public static String getFileName(String fName) {
        File tempFile =new File( fName.trim());
        String fileName = tempFile.getName();

        String[] buff = fileName.split("\\.");
        return buff[0];
	}
	
	public static String splitDimension(String str) {    
        String[] buff = str.split("\\x");
        return buff[1];
	}
	
	public static void MatchingYujun(Mat object, Mat sroadSign) {
		
		FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB); 
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB); 

		MatOfKeyPoint objectKeypoints = new MatOfKeyPoint(); 
		Mat grayObject = utils.SigntoGray(object);
		orbDetector.detect(grayObject, objectKeypoints); 
		
		MatOfKeyPoint signKeypoints = new MatOfKeyPoint(); 
		Mat graySign = utils.SigntoGray(sroadSign);
		orbDetector.detect(graySign, signKeypoints); 

		Mat objectDescriptor = new Mat(object.rows(), object.cols(), object.type()); 
		orbExtractor.compute(grayObject, objectKeypoints, objectDescriptor); 

		Mat signDescriptor = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type()); 
		orbExtractor.compute(graySign, signKeypoints, signDescriptor); 
		
		// faire le matching
		MatOfDMatch matchs = new MatOfDMatch(); 
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE); 
		matcher.match(objectDescriptor, signDescriptor, matchs); 
		
	    //System.out.println(matchs.dump());
		//return Integer.valueOf(matchs.dump().length());
		Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type()); 
		Features2d.drawMatches(object, objectKeypoints, sroadSign, signKeypoints, matchs, matchedImage); 

	}
	
	// READ VIDEO 
	public static VideoCapture LectureVideo(String filename) {
		File f = new File(filename);		
		VideoCapture capture = new VideoCapture(f.getAbsolutePath());
		
		if(!capture.isOpened()) {
			System.out.println("error in lecture video");			
		}
		return capture;
	}

	public static BufferedImage Mat2BufferedImage(Mat m) {
		//Method converts a Mat to a Buffered Image
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( m.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);  
		return image;
	}

	public static void PlayVideo(String filename) {
		Mat frame = new Mat();

		//Create new VideoCapture object
		VideoCapture camera = new VideoCapture(filename);		
		//Create new JFrame object
		JFrame jframe = new JFrame("Video Title");
		//Inform jframe what to do in the event that you close the program
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Create a new JLabel object vidpanel
		JLabel vidPanel = new JLabel();
		//assign vidPanel to jframe
		jframe.setContentPane(vidPanel);
		//set frame size
		jframe.setSize(2000, 4000);
		//make jframe visible
		jframe.setVisible(true);
		while (true) {
			//If next video frame is available
			if (camera.read(frame)) {
				//Create new image icon object and convert Mat to Buffered Image
				ImageIcon image = new ImageIcon(utils.Mat2BufferedImage(frame));
				//Update the image in the vidPanel
				vidPanel.setIcon(image);
				//Update the vidPanel in the JFrame
				vidPanel.repaint();
			}
		}
		
	}
	public BufferedImage toBufferedImage(Mat matrix){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
		byte [] buffer = new byte[bufferSize];
		matrix.get(0,0,buffer); // get all the pixels
		BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);  
		return image;
	}
}

