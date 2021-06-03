package opencv;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.ml.CvANN_MLP;


public class main {
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		Mat tailleRef = new Mat(20,20, CvType.CV_32SC1);// c'est le taille on veut pour le panneau 
		// a identifier et le taille du data base

		//#############  build database  #################

		//		build automalicaly by a figure file

		//		String src1 = "C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\sourcDatabase\\redouble";
		//		ArrayList<String> refPaths1 = utils.getFiles(src1);		    
		//		ArrayList<Mat> refMats1 = new ArrayList<Mat>();
		//		for(int i = 0; i<refPaths1.size(); i++) {
		//			Mat m = utils.extractRoadSign(utils.LectureImage(refPaths1.get(i)));
		//			Mat mgray = utils.SigntoGray(m);
		//			refMats1.add(i,utils.Scaling(mgray,tailleRef));	
		//			Highgui.imwrite("C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\database\\"+"refredouble."+Integer.toString(i)+".jpg",refMats1.get(i));
		//		}

		// buuild manul (add some special figure)
		//		String srcmanul = "C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\sourcDatabase\\redouble\\1.jpg";
		//		Mat m = utils.LectureImage(srcmanul);
		//		Mat mgray = utils.SigntoGray(utils.Scaling(m, tailleRef));
		//		Highgui.imwrite("C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\database\\"+"refredoublemanul1"+".jpg",mgray);


//		//#####################  train neural network  ###############
//		CvANN_MLP bp = new CvANN_MLP();
//		// definir les layers 
//		int layerDetail[]={400,150,60,30,6};
//		Mat layerSize=new Mat(5,1,CvType.CV_32SC1);
//
//		layerSize.put(0,0,layerDetail[0]);
//		layerSize.put(1,0,layerDetail[1]);
//		layerSize.put(2,0,layerDetail[2]);
//		layerSize.put(3,0,layerDetail[3]);
//		layerSize.put(4,0,layerDetail[4]);
//		//		layerSize.put(5,0,layerDetail[5]);
//		bp.create(layerSize, CvANN_MLP.SIGMOID_SYM, 0, 0);
//		//bp.create(layerSize);
//		CvANN_MLP_TrainParams params = new CvANN_MLP_TrainParams(); 		
//		int train_method = 1; // 1 = prop, 0 = backprop
//		params.set_train_method(train_method);
//		params.set_bp_dw_scale(0.1);
//		params.set_bp_moment_scale(0.1);
//		params.set_rp_dw0(0.1);
//		params.set_rp_dw_plus(1.2);
//		params.set_rp_dw_minus(0.5);
//		params.set_rp_dw_max(50);
//
//		//	construire input data matrice
//		String src = "C:\\Users\\Administrator\\git\\projet-twizzy\\Images_partie_OpenCV\\database20x20";
//		ArrayList<String> refPaths = utils.getFiles(src);		    
//		ArrayList<Mat> refMats = new ArrayList<Mat>();
//		for(int i = 0; i<refPaths.size(); i++) {
//			refMats.add(i,utils.SigntoGray(utils.LectureImage(refPaths.get(i))));			
//		}
//
//		int nSamples = refMats.size();
//		int ndims = tailleRef.cols()*tailleRef.rows();
//		float[] inputsFloats = new float[0]; // creat a flaot[] to containe all the values of matrices
//
//		for(int i = 0; i<nSamples; i++) {				
//			Mat m = refMats.get(i);					
//			//utils.Imshow(Integer.toString(i), m);			
//			float[] dat = utils.mat2FloatArray(m);
//			inputsFloats = utils.concatFloat2(inputsFloats, dat);		
//		}
//
//		//convert inputsFloats into a input matrice
//		Mat inputs = new Mat(nSamples,ndims,CvType.CV_32FC1); //CV_32FC1 means float
//		inputs.put(0, 0, inputsFloats);	
//		//utils.Imshow("inputs", inputs);
//
//		// outputs matrices
//		int[] class1 = new int[] {1,0,0,0,0,0};
//		int[] class2 = new int[] {0,1,0,0,0,0};
//		int[] class3 = new int[] {0,0,1,0,0,0};
//		int[] class4 = new int[] {0,0,0,1,0,0};
//		int[] class5 = new int[] {0,0,0,0,1,0};
//		int[] class6 = new int[] {0,0,0,0,0,1};
//		ArrayList<int[]> out = new ArrayList<int[]>();
//		// construire une labels matrice coherent avec le inputs
//		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
//		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
//		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
//		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
//		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
//		out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);out.add(class1);
//
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//		out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);out.add(class2);
//
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//		out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);out.add(class3);
//
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//		out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);	out.add(class4);out.add(class4);out.add(class4);out.add(class4);out.add(class4);
//
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);		out.add(class5);out.add(class5);out.add(class5);out.add(class5);out.add(class5);
//
//
//		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);
//		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);
//		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);
//		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);
//		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);
//		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);		out.add(class6);out.add(class6);out.add(class6);out.add(class6);out.add(class6);
//
//		// outputs est le labels matrice 
//		Mat outputs = new Mat(nSamples,6,CvType.CV_32FC1); 
//		for (int i = 0;i<nSamples;i++) {
//			for(int j = 0; j<6; j++) {
//				outputs.put(i, j, out.get(i)[j]);
//			}
//		}
//		//System.out.println("output = "+outputs.dump());
//
//		//############### train the cnn  ###############
//		Mat sampleWeights=new Mat();
//		int r=bp.train(inputs,outputs,sampleWeights);
//		bp.save("bp20x20.xml");
		
		
		// add all the refs 
	
		ArrayList<String> refPaths = utils.getFiles("C:\\Users\\Megaport\\git\\projet-twizzy\\Images_partie_OpenCV\\ref");		    
		ArrayList<String> refNames = new ArrayList<String>();
		ArrayList<Mat> refMats = new ArrayList<Mat>();
		for(int i = 0; i<refPaths.size(); i++) {
			refNames.add(i, utils.getFileName(refPaths.get(i)));
			refMats.add(i,utils.LectureImage(refPaths.get(i)));
		}

		// VIDEO PLAY
		String filename = "C:\\Users\\Megaport\\git\\projet-twizzy\\Images_partie_OpenCV\\webcam.avi";

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
					if (!DetectedSign.empty()&&DetectedSign.size().height>30) {
						System.out.println(DetectedSign.size().height);
						DetectedSign=utils.SigntoGray(utils.Scaling(DetectedSign, tailleRef));
						float[] sampleFloat = utils.mat2FloatArray(DetectedSign);
						Mat testSample = new Mat(1,sampleFloat.length,CvType.CV_32FC1);
						testSample.put(0, 0, sampleFloat);
						Mat results=new Mat(); 
						
						CvANN_MLP ann=new CvANN_MLP();
						ann.load("bp20x20.xml");// load coeff alreay trained
						
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
					else {
						System.out.println("no sign detected");
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
