
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.spi.XmlWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

//���Ƿ���ˣ�����������󣬷�����Ϣ�������ķ������ÿ���ֻ��dopost����Ĵ���
public class TestParkServlet extends HttpServlet {
	//private XmlResolve xresolve;
	
	public TestParkServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		
		PrintWriter out = response.getWriter();
		//OutputStream outp = response.getOutputStream();//�������ֻ��������ݵ���
		//InputStream is = request.getInputStream();//�õ��ֻ���������������Ϳ��Եõ�xml
	
		File file = new File("e:/Troy/test2.xml");
		XmlResolve xmlResolve = new XmlResolve();
		
		xmlResolve.estimate(file,out);
		
		
		
	
	
		System.out.println("calculate ok!");
	
	}
	
	
	public void init() throws ServletException {
		// Put your code here
	}

}
