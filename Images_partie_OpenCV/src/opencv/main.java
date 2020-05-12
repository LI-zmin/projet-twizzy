package opencv;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class main {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 

		//		1. mode niveau gris
		//		Mat m = utils.LectureImage("bgr.png"); 
		//		Vector<Mat> channels = new Vector<Mat>(); 
		//		Core.split(m, channels); // BGR order 
		//		for (int i=0; i < channels.size();i++) { 
		//		utils.Imshow(Integer.toString(i), channels.get(i)); } 

		//		2. mode RGB
		//		Mat m = utils.LectureImage("bgr.png"); 
		//		Vector<Mat> channels = new Vector<Mat>(); 
		//		Core.split(m, channels); // BGR order 
		//		Mat dst = Mat.zeros(m.size(), m.type()); 
		//		Vector<Mat> chans = new Vector<Mat>(); 
		//		Mat empty = Mat.zeros(m.size(), CvType.CV_8UC1); 
		//		for (int i=0; i < channels.size();i++) {
		////			utils.Imshow(Integer.toString(i), channels.get(i));
		//			chans.removeAllElements(); 
		//			for (int j=0; j<channels.size();j++) { 
		//				if (j != i) { 
		//					chans.add(empty); 
		//				}
		//				else{ 
		//					chans.add(channels.get(i)); 
		//				}
		//			}
		//			Core.merge(chans, dst);
		//			utils.Imshow(Integer.toString(i), dst);
		//		}

		//		3. HSV
		//		Mat m = utils.LectureImage("hsv.png"); 
		//		Mat output = Mat.zeros(m.size(),m.type()); //output = matrice zero
		//		Imgproc.cvtColor(m, output, Imgproc.COLOR_BGR2HSV); // use the class imgproc and fct cvtcolor to transforme img
		//		// out put is the hsv form of m
		//		utils.Imshow("   HSV Form of img hsv", output); 
		//		Vector<Mat> channels = new Vector<Mat>(); 
		//		Core.split(output, channels); 
		//		double hsv_values[][] = {{1, 255, 255}, {179, 1, 255}, {179, 0, 1}}; 
		//		for (int i=0; i<3; i++) { 
		//			utils.Imshow(Integer.toString(i)+"eme-HSV channel",channels.get(i)); 
		//			Mat chans[] = new Mat[3]; 
		//			for (int j=0; j < 3; j++) { 
		//				Mat empty = Mat.ones(m.size(), CvType.CV_8UC1); 
		//				Mat comp = Mat.ones(m.size(),CvType.CV_8UC1); 
		//				Scalar v = new Scalar(hsv_values[i][j]); 
		//				Core.multiply(empty,v,comp); 
		//				chans[j] = comp; 
		//			} 
		//			chans[i] = channels.get(i); 
		//			Mat dst = Mat.zeros(output.size(), output.type()); 
		//			Mat res = Mat.ones(dst.size(), dst.type()); 
		//			Core.merge(Arrays.asList(chans), dst); 
		//			Imgproc.cvtColor(dst, res, Imgproc.COLOR_HSV2BGR); 
		//			utils.Imshow(Integer.toString(i), res); 
		//		}


		//		4. plusieurs seuils (extrait les rouge)
		//		Mat m = utils.LectureImage("circles.jpg"); 
		//		Mat hsv_image = Mat.zeros(m.size(),m.type()); 
		//		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV); 
		//		Mat threshold_img1 = new Mat(); 
		//		Mat threshold_img2 = new Mat(); 
		//		Mat threshold_img = new Mat(); 
		//		Core. inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), threshold_img1); 
		//		Core. inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2); 
		//		Core. bitwise_or(threshold_img1, threshold_img2, threshold_img); 
		//		Imgproc.GaussianBlur(threshold_img, threshold_img, new Size(9, 9), 2.0, 2.0); 
		//		utils.Imshow("Cercles rouge", threshold_img); 


		//		6.extrait les contours des objests rouge
		//		Mat m = utils.LectureImage("circles_rectangles.jpg"); 
		//		utils.Imshow("Cercles origine", m); 
		//		Mat hsv_image = Mat.zeros(m.size(),m.type()); 
		//		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV); 
		//		utils.Imshow("HSV of cercles", hsv_image); 
		//		Mat threshold_img = utils.DetecterObjetRouge(hsv_image); 
		//		utils.Imshow("Seuillage", threshold_img); 
		//		int thresh = 100; 
		//		Mat canny_output = new Mat(); 
		//		List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); 
		//		MatOfInt4 hierarchy = new MatOfInt4(); 
		//		Imgproc.Canny( threshold_img, canny_output, thresh, thresh*2); 
		//		Imgproc.findContours( canny_output, contours, hierarchy,Imgproc.RETR_EXTERNAL, 
		//				Imgproc.CHAIN_APPROX_SIMPLE); 
		//		Mat drawing = Mat.zeros( canny_output.size(), CvType.CV_8UC3 ); 
		//		Random rand = new Random(); 
		//		for( int i = 0; i< contours.size(); i++ ) { 
		//			Scalar color = new Scalar( rand.nextInt(255 - 0 + 1) , rand.nextInt(255 - 0 + 1), 
		//					                   rand.nextInt(255 - 0 + 1) ); 
		//			Imgproc.drawContours( drawing, contours, i, color, 1, 8, hierarchy, 0, new Point() ); 
		//		} 
		//		utils.Imshow("Contours",drawing); 


		//		7. extrait les contour d'1 objet rouge circle
		//		Mat m = utils.LectureImage("circles_rectangles.jpg"); 
		//		utils.Imshow("Cercles et rectangles origine", m); 
		//		Mat hsv_image = Mat.zeros(m.size(),m.type()); 
		//		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV); 
		//		utils.Imshow("HSV", hsv_image); 
		//		Mat threshold_img = utils.DetecterObjetRouge(hsv_image); 
		//		utils.Imshow("Seuillage", threshold_img); 
		//		List<MatOfPoint> contours = utils.DetecterContours(threshold_img); 
		//		MatOfPoint2f matOfPoint2f = new MatOfPoint2f(); 
		//		float[] radius = new float[1]; 
		//		Point center = new Point(); 
		//		for (int c=0; c < contours.size();c++) { 
		//			MatOfPoint contour = contours.get(c); 
		//			double contourArea = Imgproc.contourArea(contour); 
		//			matOfPoint2f.fromList(contour.toList()); 
		//			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius); 
		//			if ((contourArea/(Math.PI*radius[0]*radius[0])) >=0.8) { // nb 0.8 depend which type of cirle we can accept
		//				Core.circle(m, center, (int)radius[0], new Scalar(0, 255, 0), 2); 
		//			} 
		//		} 
		//		utils.Imshow("Detection des cercles rouges", m);


		//  8. couper image cercle rouge et mise a echelle
		Mat m = utils.LectureImage("C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\s_p2.jpg"); 
		Mat extFromImg = utils.extractRoadSign(m); // couper image
		//utils.Imshow("ext", extFromImg);

		Mat roadSignTaille = utils.LectureImage("C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\ref\\ref70.jpg");
		Mat ImgEchelle = utils.Scaling(extFromImg, roadSignTaille); // the final img we try to match with diff ref
		utils.Imshow("ext_scal", ImgEchelle );

		//		9. faire le matching 
		//utils.Imshow("ext_scal", sroadSign );
		//utils.Matching(extFromImg, sroadSign);

		//		10. compare entre les ref et choisir le bonne

		// add all the refs 
		ArrayList<String> refPaths = utils.getFiles("C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\ref");		    
		ArrayList<String> refNames = new ArrayList<String>();
		ArrayList<Mat> refMats = new ArrayList<Mat>();
		for(int i = 0; i<refPaths.size(); i++) {
			refNames.add(i, utils.getFileName(refPaths.get(i)));
			refMats.add(i,utils.LectureImage(refPaths.get(i)));
			utils.Imshow(Integer.toString(i) , refMats.get(i));
		}

		// compare the new object with all the roadsign and regitre the length of the 
		// list with mathced points

		ArrayList<Integer> matchingReslut = new ArrayList<Integer>();
		for(int i = 0; i<refMats.size();i++) {
//			Integer element = utils.Matching(ImgEchelle, refMats.get(i));
//			matchingReslut.add(i, element);
//			System.out.println(matchingReslut.get(i));
			
			utils.Matching(ImgEchelle, refMats.get(i));
		}

	}
}
