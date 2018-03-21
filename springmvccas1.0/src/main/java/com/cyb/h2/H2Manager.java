package com.cyb.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

public class H2Manager {
	static Server server; 
	 public static synchronized void initServer(){
		 try {
			server = Server.createTcpServer();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	 }
	 public static void start(){
		 initServer();
		 if(server!=null){
			try {
				server.start();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		 }
	 }
	 public static void stop(){
		 if(server!=null){
			try {
				server.stop();
				server = null;
				System.out.println("h2 server stop!");
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				server = null;
			}
		 }
	 }
	 public static void shutdown(){
		 if(server!=null){
			try {
				server.shutdown();
				System.out.println("h2 server shutdown!");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				server = null;
			}
		 }
	 }
}
