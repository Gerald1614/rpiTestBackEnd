package com.rpi.test1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pi4j.io.exception.IOException;
import com.rpi.test1.gpio.BlinkLed;
import com.rpi.test1.gpio.Chenillard;
import com.rpi.test1.gpio.GpioController;
import com.rpi.test1.stock.CfltStock;
import com.rpi.test1.stock.StockInfo;


@RestController
public class RpiController {
	
	@Autowired
	GpioController gpioController;
	@Autowired
	Chenillard chenillard;
	
	@Autowired
	CfltStock cfltStock;
	
	@Autowired
	BlinkLed blinkLed;
	
    Gson gson = new Gson();  
	
	@CrossOrigin
	@GetMapping("/")
	public String message() {
		return"---------Hello Spring Boot App------------";
	}
	@CrossOrigin
	@GetMapping("/stock")
	public String stock(@RequestParam(defaultValue = "CFLT") String stockName, @RequestParam(defaultValue = "2023-10-27") String stockDate) throws java.io.IOException, InterruptedException {
	StockInfo result = cfltStock.cfltStock(stockName, stockDate);
	String stockResult = gson.toJson(result);
	if (result.status.equals("NOT_FOUND")) {
		for (int i = 0; i < 3; i++) {
			  blinkLed.switch_led("Blue", 2);
			}
		return stockResult;
	} else if (result.status.equals("OK")){
		if(result.close > result.open) {
			try {
				gpioController.startLed("Green");
				Thread.sleep(5*1000);
				gpioController.stopLed("Green");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(result.close < result.open) {
			try {
				gpioController.startLed("Red");
				Thread.sleep(5*1000);
				gpioController.stopLed("Red");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(result.close == result.open) {
			try {
				gpioController.startLed("Blue");
				Thread.sleep(5*1000);
				gpioController.stopLed("Blue");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stockResult;
	} else if (result.status.equals("ERROR")){
		for (int i = 0; i < 3; i++) {
			  blinkLed.switch_led("Red", 2);
			}
		return stockResult;
	}
	return null;

	}
	@CrossOrigin
	@GetMapping("/green/stop")
	public String stopGreen() {
		try {
			gpioController.stopLed("Green");
			System.out.println(" green led off");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\": \"green light stopped\"}";
	}
	@CrossOrigin
	@GetMapping("/green/start")
	public String green() {
		try {
			gpioController.startLed("Green");
			System.out.println(" green led on");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\": \"green light started\"}";
	}
	
	@CrossOrigin
	@GetMapping("/blue/start")
	public String blue() {
		try {
			gpioController.startLed("Blue");
			System.out.println(" blue led on");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\": \"blue light started\"}";
	}
	
	@CrossOrigin
	@GetMapping("/blue/stop")
	public String stopBlue() {
		try {
			gpioController.stopLed("Blue");
			System.out.println(" blue led off");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\": \"blue light stopped\"}";
	}
	@CrossOrigin
	@GetMapping("/red/start")
	public String red() {
		try {
			gpioController.startLed("Red");
			System.out.println(" Red led on");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\": \"red light started\"}";
	}
	
	@CrossOrigin
	@GetMapping("/red/stop")
	public String stopRed() {
		try {
			gpioController.stopLed("Red");
			System.out.println(" Red led off");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\": \"red light stopped\"}";
	}
	
	@CrossOrigin
	@GetMapping("/chenille/{duration}")
	public String chenille( @PathVariable("duration") int duration) {
		chenillard.chenillard(duration);
		return "{\"status\": \"chenillard stopped\"}";
	}
}