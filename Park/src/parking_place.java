import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class parking_place {
	
	/*Class Attribute*/
	public int num_parkinglots;		//Number of parking lots
	public  boolean[] status;		//Current status of each parking lot, 0/false means empty, 1/true means occupied
	private int[][] sensor_location;	//Pixel location of each virtual sensor, n * 2 matrix
	private ArrayList<int[][]> background;	// The background of each parking lot,无车的像素点
	private ArrayList<int[][]> car_background; // The car-background of each parking lot，停车位的像素点
	
	/*Global Variables*/
	private int sensor_size = 20; //pixel size of the each virtual sensor
	private int min_change_thres = 8; //threshold to determine pixel level change
	private double partchange_thres = 0.1; //threshold to the part change
	private double entirechange_thres = 0.75; //threshold to the entire change
	private double var_thres = 100; //unknown variable
	private double var_sec_thres = 100; //unknown variable
	
	
	/*Construct function
	 * input: the number of parking lots in monitoring
	 */
	public parking_place(int num){
		this.num_parkinglots = num;
		this.status = new boolean[num];
		this.sensor_location = new int[num][2];
		this.background = new ArrayList<int[][]>();
		this.car_background = new ArrayList<int[][]>();
	}
	
	/*Initial the pixel location of each virtual sensor
	 *Input: the file path of configuration file 
	 */
	//这个函数我们不用去关心，只要按照例子生成相关的文件就ok了
	public void set_sensor(String txt_file){
		File file = new File(txt_file);
		if(file.exists()){
			try{
				
				BufferedReader input = new BufferedReader (new FileReader(file));
				String text;
				int n = 0;
				while((text = input.readLine()) != null){
					int index=0;
					for(int i = 0; i<text.length(); i++){
						if(text.charAt(i)==' '){
							index = i;
							break;
						}
					}
					sensor_location[n][0]=Integer.parseInt(text.substring(0,index));
					sensor_location[n][1]=Integer.parseInt(text.substring(index+1, text.length()));
					n++;
				}
				input.close();
			}
			catch(IOException ioException){
				System.err.println("File Error!");
			}
		}

	}
	
	/*Initial the background of parking lots
	 *Each parking lot should be empty
	 *Input: the file path of the background image
	 */
	
	public void set_background(String image_file){
		this.background = retrieve_info(image_file);
	}
	
	/*Initial the car_background
	 */
	public void set_car_background(){
		for(int i = 0; i < this.num_parkinglots; i++){
			int [][] current_sensor = new int[sensor_size][sensor_size];
			for(int j = 0; j < sensor_size; j++){
				for(int k = 0; k < sensor_size; k++){
					current_sensor[j][k] = 0;
				}
			}
			car_background.add(current_sensor);	
		}
	}
	/*Update the status of parking lots by two sequential images
	 *Input: two file path of the sequential images
	 */
	public void update(String image_file, String image_file_next){
		ArrayList<int[][]> fst = retrieve_info(image_file);
		ArrayList<int[][]> sec = retrieve_info(image_file_next);
		ArrayList<int[][]> current_background = new ArrayList<int[][]>();
		for(int i = 0; i < this.num_parkinglots; i++){
			if(status[i] == false){
				current_background.add(background.get(i));
			}
			else{
				current_background.add(car_background.get(i));
			}
		}
		ArrayList<Integer> change_type = change_detection(compare(current_background,fst),fst);
		ArrayList<Integer> change_type_2 = change_detection(compare(this.background,fst),fst);
		ArrayList<Integer> change_type_next = change_detection(compare(fst,sec),sec);
		for(int i = 0; i < this.num_parkinglots; i++){
			status_update(change_type.get(i),change_type_2.get(i),change_type_next.get(i),i,fst.get(i));
		}
	}
	
	
	private int[][] get_pixel(String image_file){
		File file  = new File(image_file);
        int[][] result = null;
        if (!file.exists()) {
             return result;
        }
        try {
             BufferedImage bufImg = ImageIO.read(file);
             int height = bufImg.getHeight();
             int width = bufImg.getWidth();
             int tmp = 0;
             result = new int[width][height];
             for (int i = 0; i < width; i++) {
                  for (int j = 0; j < height; j++) {
                        tmp = bufImg.getRGB(i, j) & 0xFFFFFF;
                        //result[i][j] = (((tmp& 0xff0000 ) >> 16) + ((tmp & 0xff00 ) >> 8) +(tmp & 0xff ))/3;
                        result[i][j] = (tmp& 0xff0000 ) >> 16;
                  }
             }
             
        } catch (IOException e) {
             
             e.printStackTrace();
        }
        
        return result;
	}
	
	private ArrayList<int[][]> retrieve_info(String image_file){
		ArrayList<int[][]> result = new ArrayList<int[][]>();
		int[][] pixel_value = get_pixel(image_file);
		for(int i = 0; i < this.num_parkinglots; i++){
			int[][] current_sensor = new int[sensor_size][sensor_size];
			for(int j = 0; j < sensor_size; j++){
				for( int k = 0; k < sensor_size; k++){
					current_sensor[j][k] = pixel_value[j+sensor_location[i][0]][k+sensor_location[i][1]];
				}
			}
			result.add(current_sensor);			
		}
		
		return result;
	}
	
	private ArrayList<int[][]> compare(ArrayList<int[][]> fst, ArrayList<int[][]> sec){
		ArrayList<int[][]> result= new ArrayList<int[][]>();
		
		for(int i = 0; i < this.num_parkinglots; i++){
			int[][] tmp1 = fst.get(i).clone();
			int[][] tmp2 = sec.get(i).clone();
			int[][] tmp3 = new int[tmp1.length][tmp1[0].length];
			for(int j = 0; j < tmp1.length; j++){
				for(int k = 0; k < tmp1[0].length; k++){
					tmp3[j][k] = tmp1[j][k] - tmp2[j][k];
				}
			}
			result.add(tmp3);
		}
		return result;
	}
	
	
	/* Detect the change type of each parking lot based on the difference of pixels
	 * 0 means no change
	 * 1 means part change with small variation
	 * 2 means part change with large variation
	 * 3 means entire change with small variation
	 * 4 means entire change with large variation
	 */
	private ArrayList<Integer> change_detection(ArrayList<int[][]> difference, ArrayList<int[][]> sec){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < difference.size(); i++){
			int[][] current = difference.get(i);
			int[][] current_sec = sec.get(i).clone();
			int total_size = current.length * current[0].length;
			int count = 0;
			int total_value = 0;
			int total_sec = 0;
			for(int j = 0; j < current.length; j++){
				for(int k = 0; k < current[0].length; k++){
					
					if(Math.abs(current[j][k]) > min_change_thres){
						total_sec = total_sec + current_sec[j][k];
						count++;
						total_value = total_value + current[j][k];
					}
					else{
						current[j][k] = 0;
					}
				}
			}
			if(count < total_size * partchange_thres){
				result.add(0);
				continue;
			}
			double mean_sec = total_sec/count;
			double mean_change = total_value/count;
			double var = 0;
			double var_sec = 0;
			for(int j = 0; j < current.length; j++){
				for(int k = 0; k < current[0].length; k++){
					if(current[j][k] != 0){
						var = var + Math.pow(current[j][k] - mean_change, 2);
						var_sec = var_sec + Math.pow(current_sec[j][k] - mean_sec, 2);
					}
				}
			}
			var = var/count;
			var_sec = var_sec/count;
			if(count <= total_size * entirechange_thres && count >= total_size * partchange_thres && var < var_thres && var_sec < var_sec_thres){
				result.add(1);
			}
			else if(count <= total_size * entirechange_thres && count >= total_size * partchange_thres){
				result.add(2);
			}
			else if(count > total_size * entirechange_thres && var < var_thres && var < var_sec_thres){
				result.add(3);
			}
			else{
				result.add(4);
			}
		}
		
		return result;
	}
	
	
	private void status_update(int c_b, int n_b, int c_n, int index, int[][] current_pixel){
		if(status[index] == false){
			if(c_b == 0){
				background.set(index, current_pixel);
				return;
			}
			if(c_b == 1 || c_b == 2){
				if(c_n == 0){
					background.set(index, current_pixel);
				}
				return;
				
			}
			if(c_b == 4){
				if(c_n == 0){
					status[index] = true;
					car_background.set(index, current_pixel);
				}
				return;
			}
			if(c_b == 3){
				if(c_n == 0){
					background.set(index, current_pixel);
				}
				return;
			}
		}
		else{
			if(c_b == 0){
				car_background.set(index, current_pixel);
				return;
			}
			if(c_b == 1 || c_b == 2){
				if(c_n == 0){
					car_background.set(index, current_pixel);
				}
				return;
			}
			if(c_b == 4){
				if(c_n == 0){
					if(n_b == 0 || n_b == 1 || n_b == 2 || n_b == 3){
						status[index] = false;
						background.set(index, current_pixel);
					}
					else{
						car_background.set(index, current_pixel);
					}
				}
				return;
			}
			if(c_b == 3){
				if(c_n == 0){
					car_background.set(index, current_pixel);
				}
				return;
			}
			
		}
	}
}
