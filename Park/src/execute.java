
public class execute {
	public static void main(String args[]){
		parking_place place1 = new parking_place(1);
		place1.set_sensor("E:\\park_file\\test.txt");//这个是配置文件，目前就按照这个文件进行，后续可能会修改
		place1.set_background("E:\\park_file\\background.jpg");//当前停车场的停车位，也就是厨师装填，用来和后面更新的图片进行对比
		place1.set_car_background();//设置停车场空的信息
		//两个图片信息需要一致，如果用户需要查看当前的信息，咋调用该接口，查看当前的停车位;调用此接口后需要把car_coming.jpg设置到set_background中，以作为基准
		place1.update("E:\\park_file\\car_coming.jpg", "E:\\park_file\\car_coming.jpg");
		
		System.out.println(place1.status.length);
		//car_coming.jpg 表示最新的图片；background.jpg表示最初的图片；每update后两者都将更新
	}

}
