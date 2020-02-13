package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * �������
 * �����ÿһ��ʵ����ʾ�ͻ��˷��͹�����һ��HTTP����
 * @author Administrator
 *
 */
public class HttpRequest {
	//�����������Ϣ
	//����ʽ
	private String method;
	//����·��
	private String uri;
	//Э��汾
	private String protocol;
	
	//��Ϣͷ�����Ϣ
	private Map<String,Integer> headers=new HashMap<>();
	
	//��Ϣ���������Ϣ
	
	
	//��������ص���Ϣ
	private Socket socket;
	private InputStream in;//������
	/*
	 * ʵ����HTTPRequest���ǽ����Ĺ���
	 */
	public HttpRequest(Socket socket){
		try {
			this.socket=socket;
			this.in=socket.getInputStream();
			/*
			 * ���������Ϊ������
			 * 1.����������
			 * 2.������Ϣͷ
			 * 3.������Ϣ����
			 */
			parseRequestLine();
			parseHeaders();
			parseContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseRequestLine(){
		System.out.println("HttpRequest:��ʼ����������..");
		try {
			String line=readLine();
			System.out.println(line);
			/*
			 * �����������ݰ��ղ��Ϊ�����֣����ֱ�Ϊ
			 * ��ֵ����Ӧ�����ԣ�method,uri,protocol
			 */
			String[] data=line.split("\\s");//"\\s"���ո�
			method=data[0];
			uri=data[1];
			protocol=data[2];
			System.out.println("method"+method);
			System.out.println("uri:"+uri);
			System.out.println("protocol:"+protocol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("HttpRequest:������������ϣ�");
	}
	
	
	public void parseHeaders(){
		System.out.println("HttpRequest:��ʼ������Ϣͷ..");
		try {
			while(true){
				String line=readLine();
				//���ǿ��ַ���Ӧ���ǵ�����ȡ����CRLF
				if("".equals(line)){//""�ǿ��ַ��������ǿո�
					break;
				}
				System.out.println(line);
				/*          ��ð�ſո��
				 * ��ÿһ����Ϣͷ����": "���в�֣������
				 * ������Ϣͷ������Ϊkey����Ϣͷ��
				 * Ӧ��ֵ��Ϊvalue���浽headers��
				 */
				int index=line.indexOf(": ");
				String sub1=line.substring(0,index);
				Integer sub2=Integer.parseInt(line.substring(index));
				headers.put(sub1, sub2);
				
			}
			System.out.println("headers:"+headers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("HttpRequest:������Ϣͷ��ϣ�");
	}
	public void parseContent(){
		System.out.println("HttpRequest:��ʼ������Ϣ����..");
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("HttpRequest:������Ϣ������ϣ�");
	}
	/**
	 * ��ȡһ���ַ���(��CRL��β��)
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException{// throws IOException�ĸ��������Ͱ��쳣����
		/*
		 * ���Դ������ж�ȡһ���ַ���
		 * ��Ϊ�����к���Ϣͷ���ص㶼����CRLF��β��һ���ַ���
		 */
		int c1=-1;//��ʾ�ϴζ�ȡ�����ַ�
		int c2=-1;//��ʾ���ζ�ȡ�����ַ�
		//http://locslhost:8088/index.html
		StringBuilder builder=new StringBuilder();//
		while((c2=in.read())!=-1){
			if(c1==13&c2==10){
				break;
			}
			builder.append((char)c2);
			c1=c2;
		}
		return builder.toString().trim();
	}
	
	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getProtocol() {
		return protocol;
	}
	/*
	 * ������Ϣͷ�����ֻ�ȡ��Ӧ��ֵ
	 */
	public Object getHeader(String name){
		return headers.get(name);
	}

}
