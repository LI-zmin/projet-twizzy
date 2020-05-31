package opencv;

import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;



public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		float data1[] = {1,1,1,};
		float data2[] = {2,2,2,3,3,3,4,};
		float data3[] = {5,6,6,6,7,7,7,8,8,8};

		float data[] = utils.concatFloat(data1, data2);
		 data = utils.concatFloat(data, data3);

		
		
		
		for(int i = 0; i<data.length; i++) {
			System.out.print(data[i]+"   ");
		}
		
//		Mat mat_gray=new Mat(2,24,CvType.CV_32FC1);
//		mat_gray.put(0,0,data);
//		
//		float dat[]=new float[mat_gray.rows()*mat_gray.cols()*mat_gray.channels()];
//		
//		mat_gray.get(0,0,dat);
//		
//		
//		for(int i =0; i < dat.length;i++) {
//			System.out.println(dat[i]);
//		}
//	
//	
	}

}
