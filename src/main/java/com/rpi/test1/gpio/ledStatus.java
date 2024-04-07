package com.rpi.test1.gpio;

public class ledStatus {

	public ledStatus(String led, String status) {
		super();
		this.led = led;
		this.status = status;
	}
	public String led;
    public String status;
    
	@Override
	public String toString() {
		return "ledStatus [led=" + led + ", status=" + status + "]";
	}
}

