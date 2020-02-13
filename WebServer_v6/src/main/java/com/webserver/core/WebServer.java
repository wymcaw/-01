package com.webserver.core;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * core(����)
 * URL(ͬһ��Դ��λ)
 * http://www.tedu.cn/index.html
 * HTTPĬ�϶˿��ǣ�8080
 * Э������//Զ�˼������ַ��Ϣ/��Դ�ĳ���·��
 * DNS:������������
 * �����ձ�����վ��ͨ�������ĸ�������������û�к󣬷��������Ĺ��ң��ձ������з���IP
 * �ص������������ص�����IPȻ�������վ
 * Զ�˼������ַ��Ϣ����DNS����IP��ַ
 * ���������ƣ�ֻ�ܰ������ϵ��еĲ�����
 * ���������ƣ��������ϵĲ����ã�
 * WebServer����
 * WebServer��һ��web������ģ��Tomcat�Ĺ���
 * Tomcat ��������һ����ѵĿ���Դ�����Web Ӧ�÷�����������������Ӧ�÷�����
 * һ��web�������������������������webapp�����ҿ�����
 * �ͻ���(ͨ���������)����TCP���ӣ�������HTTPЭ����ɽ�
 * ����ʹ�ÿͻ��˿��Է��ʵ�ǰ�����ϵĸ���webapp�������Դ�Լ�����
 * ÿ��webapp(����Ӧ��)����������ҳ����̬��Դ��ҵ������
 * ��������ʱ�Ƶ�"��վ"��ʵ����һ������Ӧ��
 * @author Administrator
 *
 */
public class WebServer {
		private ServerSocket server;
		
		public WebServer(){
			try {
				System.out.println("�������������..");
				server=new ServerSocket(8088);
				System.out.println("�����������ϣ�");
			} catch (Exception e) {
				
			}
		}
		public void start(){
			try {
				System.out.println("�ȴ��ͻ�������..");
				Socket socket=server.accept();
				System.out.println("һ���ͻ��������ˣ�");
				
				//����һ���̴߳���
				ClientHandler handler = new ClientHandler(socket);
				Thread t = new Thread(handler);
				t.start();
			    
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public static void main(String[] args) {
			WebServer server=new WebServer();
			server.start();
		}
	

}
