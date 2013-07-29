
import java.io.Writer;
import java.util.*;


import com.thoughtworks.xstream.XStream;


public class response {
	
	
	private String parkName ;
	private String command; 
	private String picNum;
	private List picVlaue;
	
	private String errCode;
	private String parkNum;
	private List<Park> park;
	
	public String getParkNum() {
		return parkNum;
	}

	public void setParkNum(String parkNum) {
		this.parkNum = parkNum;
	}
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public List<Park> getPark() {
		return park;
	}

	public void setPark(List<Park> park) {
		this.park = park;
	}
	public List getPicVlaue() {
		return picVlaue;
	}

	public void setPicVlaue(List picVlaue) {
		this.picVlaue = picVlaue;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public void responseTo(Writer writer,String command,String parkName,String picNum){

		response c = new response();
		parking_place place1 = new parking_place(Integer.parseInt(picNum));
		place1.set_sensor("E:\\park_file\\test.txt");
		place1.set_background("E:\\park_file\\background.jpg");
		place1.set_car_background();
		place1.update("E:\\park_file\\car_coming.jpg", "E:\\park_file\\car_coming.jpg");
		
	
		
		//List<Boolean> list_1 = new ArrayList<Boolean>();
		List list = new ArrayList();
		boolean[] status = place1.status;
		for(int i = 0;i<status.length;i++){
			boolean b = status[i];
			list.add(b);
		}
		
		c.setCommand(command);
		c.setParkName(parkName);
		c.setPicNum(picNum);
		c.setPicVlaue(list);
		
		
		XStream xstream = new XStream();
		xstream.alias("picValue", boolean.class);
		xstream.addImplicitCollection(response.class, "picVlaue");
		xstream.toXML(c,writer);
		}
	public void responseTo(Writer writer,String command,String errCode,String parkNum,String[][] park_Data){
		String[][] parkData = park_Data;;
		response c = new response();
		List<Park> list = new ArrayList<Park>();
		Park p = null;
		for(int i = 0;i<parkData.length;i++){
			for(int j = 0;j<parkData[i].length;j++){
				p = new Park();
				p.setParkName(parkData[i][0]);
				p.setParkAllocation(parkData[i][1]);
				
			}
			list.add(p);
		}
		
		c.setCommand(command);
		c.setErrCode(errCode);
		c.setParkNum(parkNum);
		c.setPark(list);
		
		XStream xstream = new XStream();
		xstream.addImplicitCollection(response.class, "park");
		xstream.toXML(c,writer);
	
	}
	public void respinseTO(Writer writer,String command,String errCode){
		response c = new response();
		c.setCommand(command);
		c.setErrCode(errCode);
		XStream xstream = new XStream();
		xstream.toXML(c, writer);
	}
	
}
