package main.java.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageHandler {
	private long timestamp;
	private String playerid;
	private int command;
	private int xcord;
	private int ycord;
	
	public MessageHandler(long timestamp, String playerid, int command, int xcord, int ycord) {
		this.timestamp = timestamp;
		this.playerid = playerid;
		this.command = command;
		this.xcord = xcord;
		this.ycord = ycord;
	}
	
	public void send(DataOutputStream os) {
		byte[] stringBytes = playerid.getBytes();
		try {
			os.writeLong(timestamp);
			os.write(stringBytes.length);
			os.writeInt(command);
			os.writeInt(xcord);
			os.write(ycord);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static synchronized String readString(DataInputStream is) {
		StringBuilder sb = null;
		try {
			int length = is.readInt();
			byte[] string = new byte[length];
			is.readFully(string);
			sb = new StringBuilder();
			for (byte b : string) {
				sb.append((char) b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
		}
	
}
