package com.webserver.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.webserver.http.HttpRequest;

/**
 * 该线程用来处理与指定客户端交互工作
 * 遵循HTTP协议的交互要求(一问一答)，一次这里的处理
 * 过程分为三步
 * 1.解析请求
 * 2.处理请求
 * 3.响应客户端
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
				//解析请求
				HttpRequest request=new HttpRequest(socket);
				//处理请求
				/*
				 * 根据用户请求的抽象路径找到webapps目录下对应资源
				 * http://localhost:8088/myweb/index.html
				 */
				String path=request.getUri();
				File file=new File("./webapps"+path);
				if(file.exists()){
					System.out.println("资源已找到！");
					//将资源响应给客户端
					OutputStream out=socket.getOutputStream();
					
					//1发送转态行
					String line="HTTP/1.1 200 OK";
					out.write(line.getBytes("ISO8859-1"));
					out.write(13);//CR
					out.write(10);//LF
					
					//2发送响应头
					line="Content-Type: text/html";
					out.write(line.getBytes("ISO8859-1"));
					out.write(13);//CR
					out.write(10);//LF
					
					line="Content-Length: "+file.length();
					out.write(13);//CR
					out.write(10);//LF
					
					//单独发送CRLF表示响应头发送完毕！
					out.write(13);//CR
					out.write(10);//LF
					
					//3发送响应正文(用户请求的实际资源的数据)
					FileInputStream fis=new FileInputStream(file);
					int len=-1;
					byte[] data=new byte[1024*10];//10k
					while((len=fis.read(data))!=-1){
						out.write(data,0,len);
					}
					
				}else{
						System.out.println("资源未找到！");
						//响应404界面
						File notFoundPage=new File("./webapps/root/404.html");
						
						//将资源响应给客户端
						OutputStream ops=socket.getOutputStream();
						
						//1发送转态行
						String line="HTTP/1.1 404 NOT FOUND";
						ops.write(line.getBytes("ISO8859-1"));
						ops.write(13);//CR
						ops.write(10);//LF
						
						//2发送响应头
						line="Content-Type: 404/html";
						ops.write(line.getBytes("ISO8859-1"));
						ops.write(13);//CR
						ops.write(10);//LF
						
						line="Content-Length: "+notFoundPage.length();
						ops.write(line.getBytes("ISO8859-1"));
						ops.write(13);//CR
						ops.write(10);//LF
						
						//单独发送CRLF表示响应头发送完毕！
						ops.write(13);//CR
						ops.write(10);//LF
						
						//3发送响应正文(用户请求的实际资源的数据)
						int x=-1;
						byte[] arr=new byte[1024*10];
						while((x=fis.read(arr))!=-1){
							ops.write(arr,0,x);
						}

				}
				//响应客户端
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//响应客户端完毕后断开连接
				try {
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

				

		}

	

}
