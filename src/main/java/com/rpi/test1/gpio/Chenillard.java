package com.rpi.test1.gpio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Chenillard {
	
	@Autowired
	GpioController gpioController;
	
	@Autowired
	BlinkLed blinkLed;

	public void chenillard(int duration) {
		String[] leds = {"Green", "Red", "Blue"};

		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 3; i++) {
				  blinkLed.switch_led(leds[i], duration);
				}
			}

		
	}


}
