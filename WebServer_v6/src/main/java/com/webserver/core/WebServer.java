package com.webserver.core;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * core(核心)
 * URL(同一资源定位)
 * http://www.tedu.cn/index.html
 * HTTP默认端口是：8080
 * 协议名称//远端计算机地址信息/资源的抽象路径
 * DNS:域名解析服务
 * 访问日本的网站：通过美国的根服务器，发现没有后，发给其他的国家，日本那里有返回IP
 * 回到根服务器返回到电脑IP然后进入网站
 * 远端计算机地址信息访问DNS返回IP地址
 * 白名单机制：只能白名单上的有的才能用
 * 黑名单机制：黑名单上的不能用，
 * WebServer主类
 * WebServer是一个web容器，模拟Tomcat的功能
 * Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用服务器
 * 一个web容器用来管理部署在上面的若干webapp，并且可以与
 * 客户端(通常是浏览器)进行TCP连接，并基于HTTP协议完成交
 * 互，使得客户端可以访问当前容器上的各个webapp的相关资源以及功能
 * 每个webapp(网络应用)都包含：网页，静态资源，业务代码等
 * 我们上网时称的"网站"其实就是一个网络应用
 * @author Administrator
 *
 */
public class WebServer {
		private ServerSocket server;
		
		public WebServer(){
			try {
				System.out.println("正在启动服务端..");
				server=new ServerSocket(8088);
				System.out.println("服务端启动完毕！");
			} catch (Exception e) {
				
			}
		}
		public void start(){
			try {
				System.out.println("等待客户端连接..");
				Socket socket=server.accept();
				System.out.println("一个客户端连接了！");
				
				//启动一个线程处理
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
