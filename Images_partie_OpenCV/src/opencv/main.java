package opencv;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvANN_MLP;
import org.opencv.ml.CvANN_MLP_TrainParams;
import org.opencv.ml.Ml;

import com.sun.jmx.snmp.SnmpV3Message;
import com.sun.prism.Image;

public class main {
	private static JFrame frame;
	private static JLabel imageLabel;
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 


		//  8. couper image cercle rouge et mise a echelle
		Mat m = utils.LectureImage("C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\s_p1.jpg"); 
		Mat Object = utils.extractRoadSign(m); // couper image
		//utils.Imshow("object extracted from the img", Object);

		Mat sroadSign = utils.LectureImage("C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\ref\\ref70.jpg");
		Mat sObject = utils.Scaling(Object, sroadSign); // the final img we try to match with diff ref
		//utils.Imshow("the final object we want to match wth diff refs", sObject );

		//		9. faire le matching 
		//	utils.Imshow("stdSize", stdSize );
		utils.MatchingYujun(sObject, sroadSign);
		//
		////		//		10. compare entre les ref et choisir le bonne
		////
		////		// add all the refs 
		//		ArrayList<String> refPaths = utils.getFiles("C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\ref");		    
		//		ArrayList<String> refNames = new ArrayList<String>();
		//		ArrayList<Mat> refMats = new ArrayList<Mat>();
		//		for(int i = 0; i<refPaths.size(); i++) {
		//			refNames.add(i, utils.getFileName(refPaths.get(i)));
		//			refMats.add(i,utils.LectureImage(refPaths.get(i)));
		//			utils.Imshow(Integer.toString(i) , refMats.get(i));
		//		}
		//
		//		// compare the new object with all the roadsign and regitre the length of the 
		//		// list with mathced points
		//
		//		ArrayList<Integer> matchingReslut = new ArrayList<Integer>();
		//		for(int i = 0; i<refMats.size();i++) {
		////			Integer element = utils.Matching(ImgEchelle, refMats.get(i));
		////			matchingReslut.add(i, element);
		////			System.out.println(matchingReslut.get(i));
		//			
		//			utils.Matching(sObject, refMats.get(i));
		//		}



		// VIDEO PLAY
		String filename = "C:\\Users\\Administrator\\Desktop\\videoplayback.mp4";

		//VideoCapture cap = utils.LectureVideo(filename);

		//internet example
		//utils.PlayVideo(filename);


		// ex prof
		frame = new JFrame("Video Playback Example");  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		frame.setSize(400,400);  
		imageLabel = new JLabel();
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
					tempImage= imageProcessor.toBufferedImage(webcamMatImage);
					ImageIcon imageIcon = new ImageIcon(tempImage, "Video playback");
					imageLabel.setIcon(imageIcon);
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
