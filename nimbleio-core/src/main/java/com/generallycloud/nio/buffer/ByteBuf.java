package com.generallycloud.nio.buffer;

import java.io.IOException;

import com.generallycloud.nio.component.TCPEndPoint;

public interface ByteBuf {
	
	public static final int UNIT_CAPACITY = 12;

	public abstract void release();
	
	public abstract ByteBuf duplicate();

	public abstract int remaining();

	public abstract int position();

	public abstract ByteBuf position(int position);

	public abstract int limit();

	public abstract ByteBuf limit(int limit);

	public abstract int capacity();

	public abstract boolean hasRemaining();

	public abstract boolean hasArray();
	
	public abstract byte [] array();
	
	public abstract ByteBuf flip();

	public abstract ByteBuf clear();
	
	public abstract byte get(int index);
	
	public abstract int getInt();
	
	public abstract long getLong();
	
	public abstract int getInt(int offset);
	
	public abstract long getLong(int offset);
	
	public abstract void getBytes(byte [] dst);
	
	public abstract byte [] getBytes();
	
	public abstract void getBytes(byte [] dst,int offset,int length);

	public abstract void putBytes(byte [] src);
	
	public abstract void putBytes(byte [] src,int offset,int length);

	// 往buffer中write
	public abstract int read(TCPEndPoint endPoint) throws IOException;

	// 往buffer中read
	public abstract int write(TCPEndPoint endPoint) throws IOException;

}