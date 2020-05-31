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
import org.opencv.core.Core.MinMaxLocResult;
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
import org.opencv.core.TermCriteria;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
/*import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;*/
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvANN_MLP;
import org.opencv.ml.CvANN_MLP_TrainParams;
import org.opencv.ml.CvSVM;
import org.opencv.ml.CvSVMParams;
import org.opencv.objdetect.CascadeClassifier;

public class main {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		Mat tailleRef = new Mat(50,50, CvType.CV_32SC1);

		// build database

		//		String src1 = "C:\\Users\\Administrator\\Desktop\\database";
		//		ArrayList<String> refPaths1 = utils.getFiles(src1);		    
		//		ArrayList<Mat> refMats1 = new ArrayList<Mat>();
		//		for(int i = 0; i<refPaths1.size(); i++) {
		//			refMats1.add(i,utils.Scaling(utils.SigntoGray(utils.extractRoadSign(utils.LectureImage(refPaths1.get(i)))), tailleRef));	
		//			Highgui.imwrite("Images_partie_OpenCV\\database\\"+"6."+Integer.toString(i)+".jpg",refMats1.get(i));
		//		}
		//			


		//	neural network
		CvANN_MLP bp = new CvANN_MLP();
		// 3 layers 
		
		int layerDetail[]={2500,500,50,6};
		Mat layerSize=new Mat(4,1,CvType.CV_32SC1);

		layerSize.put(0,0,layerDetail[0]);
		layerSize.put(1,0,layerDetail[1]);
		layerSize.put(2,0,layerDetail[2]);
		layerSize.put(3,0,layerDetail[3]);
//		layerSize.put(4,0,layerDetail[4]);
//		layerSize.put(5,0,layerDetail[5]);
		bp.create(layerSize, CvANN_MLP.SIGMOID_SYM, 0, 0);
		//bp.create(layerSize);
		CvANN_MLP_TrainParams params = new CvANN_MLP_TrainParams(); 		
		int train_method = 1; // 1 = prop, 0 = backprop
		params.set_train_method(train_method);
		params.set_bp_dw_scale(0.1);
		params.set_bp_moment_scale(0.1);
		//		params.set_rp_dw0(0.1);
		//		params.set_rp_dw_plus(1.2);
		//		params.set_rp_dw_minus(0.5);
		//		params.set_rp_dw_max(50);



		//	 input datas matrice
		String src = "C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\database";
		ArrayList<String> refPaths = utils.getFiles(src);		    
		ArrayList<Mat> refMats = new ArrayList<Mat>();
		for(int i = 0; i<refPaths.size(); i++) {
			refMats.add(i,utils.SigntoGray(utils.LectureImage(refPaths.get(i))));			
		}

		int nSamples = refMats.size();
		int ndims = tailleRef.cols()*tailleRef.rows();
		float[] inputsFloats = new float[0]; // creat a flaot[] to containe all the values of matrices

		for(int i = 0; i<nSamples; i++) {				
			Mat m = refMats.get(i);					
			//utils.Imshow(Integer.toString(i), m);			
			float[] dat = utils.mat2FloatArray(m);
			inputsFloats = utils.concatFloat2(inputsFloats, dat);		
		}

		//		for(int i = 0; i<inputsFloats.length; i++) {
		//			System.out.print(i+"-"+inputsFloats[i]+"   ");
		//		}

		//convert inputsFloats into a input matrice
		Mat inputs = new Mat(nSamples,ndims,CvType.CV_32FC1); //CV_32FC1 means float
		inputs.put(0, 0, inputsFloats);	
		//utils.Imshow("inputs", inputs);

		//		System.out.println("input.16 = "+refMats.get(15).dump());
		//		System.out.println("input = "+inputs.dump());
		// outputs matrices
		int[] class1 = new int[] {1,0,0,0,0,0};
		int[] class2 = new int[] {0,1,0,0,0,0};
		int[] class3 = new int[] {0,0,1,0,0,0};
		int[] class4 = new int[] {0,0,0,1,0,0};
		int[] class5 = new int[] {0,0,0,0,1,0};
		int[] class6 = new int[] {0,0,0,0,0,1};
		ArrayList<int[]> out = new ArrayList<int[]>();
		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
		out.add(class4);out.add(class4);out.add(class4);out.add(class4);
		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);

		Mat outputs = new Mat(nSamples,6,CvType.CV_32FC1); 
		for (int i = 0;i<nSamples;i++) {
			for(int j = 0; j<6; j++) {
				outputs.put(i, j, out.get(i)[j]);
			}
		}
		//System.out.println("output = "+outputs.dump());

		// train
		Mat sampleWeights=new Mat();
		int r=bp.train(inputs,outputs,sampleWeights);
		bp.save("bp.xml");
	
		// predict

		String samplePaths = "C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\s_p3.jpg";		
		Mat Sample = utils.SigntoGray(utils.Scaling(utils.extractRoadSign(utils.LectureImage(samplePaths)), tailleRef));
		utils.Imshow("Sample", Sample);
		float[] sampleFloat = utils.mat2FloatArray(Sample);
		Mat testSample = new Mat(1,sampleFloat.length,CvType.CV_32FC1);
		testSample.put(0, 0, sampleFloat);
		//utils.Imshow("", testSample);

		Mat results=new Mat(); 
		CvANN_MLP ann=new CvANN_MLP();
		ann.load("bp.xml");
		ann.predict(testSample, results);    
		//MinMaxLocResult minMaxLocResult0=Core.minMaxLoc(results);
		System.out.println("results="+results.dump());

		//	//	10. compare entre les ref et choisir le bonne
		//
		//		// add all the refs 
		//		ArrayList<String> refPaths = utils.getFiles("Images_partie_OpenCV\\ref");		    
		//		ArrayList<String> refNames = new ArrayList<String>();
		//		ArrayList<Mat> refMats = new ArrayList<Mat>();
		//		for(int i = 0; i<refPaths.size(); i++) {
		//			refNames.add(i, utils.getFileName(refPaths.get(i)));
		//			refMats.add(i,utils.LectureImage(refPaths.get(i)));		
		//		}
		//
		//		// compare the new object with all the roadsign and regitre the length of the 
		//		// list with mathced points
		//		Mat img = utils.LectureImage("Images_partie_OpenCV\\s_p1.jpg"); 
		//		Mat object = utils.Scaling(utils.extractRoadSign(img), refMats.get(1));
		//		ArrayList<MatOfDMatch> matchingReslut = new ArrayList<MatOfDMatch>();
		//		for(int i = 0; i<refMats.size();i++) {
		//			MatOfDMatch element = utils.Matching(object, refMats.get(i));
		//			matchingReslut.add(i, element);
		//		}
		//		

		//		// VIDEO PLAY
		//		String filename = "C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\video1.avi";
		//
		//		VideoCapture cap = utils.LectureVideo(filename);
		//
		//        utils.PlayVideo(cap);

	}
}
