package com.enter4ward.mystream;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Payload {
	private byte [] data;
	private long sequenceNumber = 0l;
	private Lock lock;
	private Condition condition;
	private Map<String,String> params = new TreeMap<String,String>();
	
	public Map<String, String> getParams() {
		return params;
	}

	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void signal(){
		lock.lock();
		condition.signalAll();
		lock.unlock();
	}
	
	public Payload(){
		lock = new ReentrantLock();
		condition = lock.newCondition();
		data = new byte[0];
	}
	
	public byte[] getData() {
		synchronized (this) {			
			return data;
		}
	}

	public void setData(byte[] data) {
		synchronized (this) {			
			this.data = data;
			++sequenceNumber;
		}
	}

	public void await() {
		lock.lock();
		try {
			condition.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}		
	}

	public void addParam(String key,String value) {
		params.put(key, value);
	}
	
}
