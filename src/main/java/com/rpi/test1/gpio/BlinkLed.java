package com.rpi.test1.gpio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlinkLed {
	
	@Autowired
	GpioController gpioController;

	public void switch_led(String color, int duration) {
		// TODO Auto-generated method stubgpioController.color.high();
		try {
			gpioController.startLed(color);
			Thread.sleep(duration*1000);
			gpioController.stopLed(color);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
