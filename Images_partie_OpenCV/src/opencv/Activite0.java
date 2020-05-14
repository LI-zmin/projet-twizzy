package opencv;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

//import utilitaireAgreg.*;

public class Activite0 {
	private static JFrame frame;
	private static JLabel imageLabel;

	public static void main(String[] args) {
		//MaBibliothequeTraitementImage.ObjectifARealiser("video1.mp4");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 

		frame = new JFrame("Video Playback Example");  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		frame.setSize(400,400);  
		imageLabel = new JLabel();
		frame.add(imageLabel);
		frame.setVisible(true);
		ImageProcessor imageProcessor = new ImageProcessor();
		Mat webcamMatImage = new Mat();  
		Image tempImage;  
		String filename = "C:\\Users\\Administrator\\Desktop\\projet-twizzy\\Images_partie_OpenCV\\src\\opencv\\videoplayback.mp4";
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