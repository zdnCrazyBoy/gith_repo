
public class execute {
	public static void main(String args[]){
		parking_place place1 = new parking_place(1);
		place1.set_sensor("E:\\park_file\\test.txt");//����������ļ���Ŀǰ�Ͱ�������ļ����У��������ܻ��޸�
		place1.set_background("E:\\park_file\\background.jpg");//��ǰͣ������ͣ��λ��Ҳ���ǳ�ʦװ������ͺ�����µ�ͼƬ���жԱ�
		place1.set_car_background();//����ͣ�����յ���Ϣ
		//����ͼƬ��Ϣ��Ҫһ�£�����û���Ҫ�鿴��ǰ����Ϣ��զ���øýӿڣ��鿴��ǰ��ͣ��λ;���ô˽ӿں���Ҫ��car_coming.jpg���õ�set_background�У�����Ϊ��׼
		place1.update("E:\\park_file\\car_coming.jpg", "E:\\park_file\\car_coming.jpg");
		
		System.out.println(place1.status.length);
		//car_coming.jpg ��ʾ���µ�ͼƬ��background.jpg��ʾ�����ͼƬ��ÿupdate�����߶�������
	}

}
