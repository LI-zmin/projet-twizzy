package opencv;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;

import java.awt.image.*;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
import org.opencv.highgui.VideoCapture;
/*import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;*/
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvANN_MLP;
import org.opencv.core.Core.MinMaxLocResult;

public class main {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		Mat tailleRef = new Mat(50,50, CvType.CV_32SC1);
		
		// couper image cercle rouge et mise a echelle
		Mat m = utils.LectureImage("C:\\Users\\Megaport\\git\\projet-twizzy\\Images_partie_OpenCV\\s_p1.jpg"); 
		Mat extFromImg = utils.extractRoadSign(m); // couper image
		//utils.Imshow("ext", extFromImg);

		Mat roadSignTaille  = utils.LectureImage("C:\\Users\\Megaport\\git\\projet-twizzy\\Images_partie_OpenCV\\ref\\ref30.jpg");
		Mat ImgEchelle = utils.Scaling(extFromImg, roadSignTaille); // the final img we try to match with diff ref

		//utils.Imshow("ext_scal", ImgEchelle );

		// add all the refs 

		ArrayList<String> refPaths = utils.getFiles("C:\\Users\\Megaport\\git\\projet-twizzy\\Images_partie_OpenCV\\ref");		    
		ArrayList<String> refNames = new ArrayList<String>();
		ArrayList<Mat> refMats = new ArrayList<Mat>();
		for(int i = 0; i<refPaths.size(); i++) {
			refNames.add(i, utils.getFileName(refPaths.get(i)));
			refMats.add(i,utils.LectureImage(refPaths.get(i)));
		}

		// VIDEO PLAY
		String filename = "C:\\Users\\Megaport\\git\\projet-twizzy\\Images_partie_OpenCV\\video1.avi";

		JFrame frame = new JFrame("Video Playback Example");  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		frame.setSize(400,400);  
		JLabel imageLabel = new JLabel();
		frame.add(imageLabel);
		frame.setVisible(true);
		ImageProcessor imageProcessor = new ImageProcessor();
		Mat webcamMatImage = new Mat();  
		BufferedImage tempImage;  
		VideoCapture capture = new VideoCapture(filename);
		
		
		if( capture.isOpened()){  
			while (true){  
				capture.read(webcamMatImage);  
				if( !webcamMatImage.empty() ){  
					tempImage= imageProcessor.toBufferedImage(utils.calloutRoadSign(webcamMatImage));
					Mat DetectedSign = utils.extractRoadSign(webcamMatImage);
					ImageIcon imageIcon = new ImageIcon(tempImage, "Video playback");
					imageLabel.setIcon(imageIcon);
					if (!DetectedSign.empty()) {
						DetectedSign=utils.SigntoGray(utils.Scaling(DetectedSign, tailleRef));
						float[] sampleFloat = utils.mat2FloatArray(DetectedSign);
						Mat testSample = new Mat(1,sampleFloat.length,CvType.CV_32FC1);
						testSample.put(0, 0, sampleFloat);
						Mat results=new Mat(); 
						CvANN_MLP ann=new CvANN_MLP();
						ann.load("bp.xml");// load coeff alreay trained
						ann.predict(testSample, results);    
						MinMaxLocResult minMaxLocResult0=Core.minMaxLoc(results);
						double prediction = minMaxLocResult0.maxLoc.x;
						if(prediction == 0.0) {
							System.out.println("ref30 ditected");
						}
						else if (prediction == 1.0) {
							System.out.println("ref50 ditected");
						}
						else if (prediction == 2.0) {
							System.out.println("ref70 ditected");
						}
						else if (prediction == 3.0) {
							System.out.println("ref90 ditected");
						}
						else if (prediction == 4.0) {
							System.out.println("ref110 ditected");
						}
						else {
							System.out.println("refdouble ditected");
						}
						/*//method Matching
						ArrayList<MatOfDMatch> matchingReslut1 = new ArrayList<MatOfDMatch>();
						double MaxNumberOfMatchedPoints1 =0.0;
						for(int i = 0; i<refMats.size();i++) {
							MatOfDMatch element = utils.Matching(DetectedSign, refMats.get(i));
							matchingReslut1.add(i, element);
							if (MaxNumberOfMatchedPoints1<element.size().height) {
								MaxNumberOfMatchedPoints1=element.size().height;
							}
						}
						for(int i = 0; i<matchingReslut1.size();i++) {
							if (MaxNumberOfMatchedPoints1==matchingReslut1.get(i).size().height) {
								 System.out.println("Panneau "+refNames.get(i)+" detected");
							}
						}*/
					}
					frame.pack();  //this will resize the window to fit the image
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}  
				else{  
					System.out.println(" Frame not captured or video has finished"); 
					break;  
				}
			}  
		}
		else{
			System.out.println("Couldn't open video file.");
		}
	}
}
