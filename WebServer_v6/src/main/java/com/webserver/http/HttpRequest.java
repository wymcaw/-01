package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例表示客户端发送过来的一个HTTP请求
 * @author Administrator
 *
 */
public class HttpRequest {
	//请求行相关信息
	//请求方式
	private String method;
	//抽象路径
	private String uri;
	//协议版本
	private String protocol;
	
	//消息头相关信息
	private Map<String,Integer> headers=new HashMap<>();
	
	//消息正文相关信息
	
	
	//和连接相关的信息
	private Socket socket;
	private InputStream in;//输入流
	/*
	 * 实例化HTTPRequest就是解析的过程
	 */
	public HttpRequest(Socket socket){
		try {
			this.socket=socket;
			this.in=socket.getInputStream();
			/*
			 * 解析请求分为三步：
			 * 1.解析请求行
			 * 2.解析消息头
			 * 3.解析消息正文
			 */
			parseRequestLine();
			parseHeaders();
			parseContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseRequestLine(){
		System.out.println("HttpRequest:开始解析请求行..");
		try {
			String line=readLine();
			System.out.println(line);
			/*
			 * 将请求行内容按照拆分为三部分，并分别为
			 * 赋值给对应的属性：method,uri,protocol
			 */
			String[] data=line.split("\\s");//"\\s"表达空格
			method=data[0];
			uri=data[1];
			protocol=data[2];
			System.out.println("method"+method);
			System.out.println("uri:"+uri);
			System.out.println("protocol:"+protocol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("HttpRequest:解析请求行完毕！");
	}
	
	
	public void parseHeaders(){
		System.out.println("HttpRequest:开始解析消息头..");
		try {
			while(true){
				String line=readLine();
				//若是空字符串应当是单独读取到了CRLF
				if("".equals(line)){//""是空字符串，不是空格
					break;
				}
				System.out.println(line);
				/*          是冒号空格拆
				 * 将每一个消息头按照": "进行拆分，将拆分
				 * 出的消息头名字作为key，消息头对
				 * 应的值作为value保存到headers中
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
		System.out.println("HttpRequest:解析消息头完毕！");
	}
	public void parseContent(){
		System.out.println("HttpRequest:开始解析消息正文..");
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("HttpRequest:解析消息正文完毕！");
	}
	/**
	 * 读取一行字符串(以CRL结尾的)
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException{// throws IOException哪个调用他就把异常给他
		/*
		 * 测试从请求中读取一行字符串
		 * 因为请求行和消息头的特点都是以CRLF结尾的一行字符串
		 */
		int c1=-1;//表示上次读取到的字符
		int c2=-1;//表示本次读取到的字符
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
	 * 根据消息头的名字获取对应的值
	 */
	public Object getHeader(String name){
		return headers.get(name);
	}

}
