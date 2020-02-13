package com.webserver.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.webserver.http.HttpRequest;

/**
 * ���߳�����������ָ���ͻ��˽�������
 * ��ѭHTTPЭ��Ľ���Ҫ��(һ��һ��)��һ������Ĵ���
 * ���̷�Ϊ����
 * 1.��������
 * 2.��������
 * 3.��Ӧ�ͻ���
 * 
 * @author Administrator
 *
 */
public class ClientHandler implements Runnable {
		private Socket socket;
		public ClientHandler(Socket scoket){
			this.socket=scoket;
		}
		public void run(){
			try {
				//��������
				HttpRequest request=new HttpRequest(socket);
				//��������
				/*
				 * �����û�����ĳ���·���ҵ�webappsĿ¼�¶�Ӧ��Դ
				 * http://localhost:8088/myweb/index.html
				 */
				String path=request.getUri();
				File file=new File("./webapps"+path);
				if(file.exists()){
					System.out.println("��Դ���ҵ���");
					//����Դ��Ӧ���ͻ���
					OutputStream out=socket.getOutputStream();
					
					//1����ת̬��
					String line="HTTP/1.1 200 OK";
					out.write(line.getBytes("ISO8859-1"));
					out.write(13);//CR
					out.write(10);//LF
					
					//2������Ӧͷ
					line="Content-Type: text/html";
					out.write(line.getBytes("ISO8859-1"));
					out.write(13);//CR
					out.write(10);//LF
					
					line="Content-Length: "+file.length();
					out.write(13);//CR
					out.write(10);//LF
					
					//��������CRLF��ʾ��Ӧͷ������ϣ�
					out.write(13);//CR
					out.write(10);//LF
					
					//3������Ӧ����(�û������ʵ����Դ������)
					FileInputStream fis=new FileInputStream(file);
					int len=-1;
					byte[] data=new byte[1024*10];//10k
					while((len=fis.read(data))!=-1){
						out.write(data,0,len);
					}
					
				}else{
						System.out.println("��Դδ�ҵ���");
						//��Ӧ404����
						File notFoundPage=new File("./webapps/root/404.html");
						
						//����Դ��Ӧ���ͻ���
						OutputStream ops=socket.getOutputStream();
						
						//1����ת̬��
						String line="HTTP/1.1 404 NOT FOUND";
						ops.write(line.getBytes("ISO8859-1"));
						ops.write(13);//CR
						ops.write(10);//LF
						
						//2������Ӧͷ
						line="Content-Type: 404/html";
						ops.write(line.getBytes("ISO8859-1"));
						ops.write(13);//CR
						ops.write(10);//LF
						
						line="Content-Length: "+notFoundPage.length();
						ops.write(line.getBytes("ISO8859-1"));
						ops.write(13);//CR
						ops.write(10);//LF
						
						//��������CRLF��ʾ��Ӧͷ������ϣ�
						ops.write(13);//CR
						ops.write(10);//LF
						
						//3������Ӧ����(�û������ʵ����Դ������)
						int x=-1;
						byte[] arr=new byte[1024*10];
						while((x=fis.read(arr))!=-1){
							ops.write(arr,0,x);
						}

				}
				//��Ӧ�ͻ���
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//��Ӧ�ͻ�����Ϻ�Ͽ�����
				try {
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

				

		}

	

}
