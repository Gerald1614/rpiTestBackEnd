package com.rpi.test1.gpio;



import org.springframework.stereotype.Component;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.google.gson.Gson;


@Component
public class GpioController {
	
    private static final int PIN_LED_GREEN = 5;
    private static final int PIN_LED_BLUE = 6;
    private static final int PIN_LED_RED = 23;
	private Context pi4jGreen;
	private Context pi4jBlue;
	private Context pi4jRed;
	private Context pi4jGreenStatus;
	private Context pi4jBlueStatus;
	private Context pi4jRedStatus;
	private DigitalOutputConfigBuilder ledGreenConfig;
	public DigitalOutput led_Green;
	private DigitalOutputConfigBuilder ledBlueConfig;
	public DigitalOutput led_Blue;
	private DigitalOutputConfigBuilder ledRedConfig;
	public DigitalOutput led_Red;
	private DigitalInputConfigBuilder ledGreenStatusConfig;
	public DigitalInput ledGreenStatus;
	private DigitalInputConfigBuilder ledBlueStatusConfig;
	public DigitalInput ledBlueStatus;
	private DigitalInputConfigBuilder ledRedStatusConfig;
	public DigitalInput ledRedStatus;
    
	   Gson gson = new Gson();  
	
	
    public GpioController () {  	
    	pi4jGreen = Pi4J.newAutoContext();
    	pi4jBlue = Pi4J.newAutoContext();
    	pi4jRed = Pi4J.newAutoContext();
        pi4jGreenStatus = Pi4J.newAutoContext();
        pi4jBlueStatus = Pi4J.newAutoContext();
        pi4jRedStatus = Pi4J.newAutoContext();

        ledGreenStatusConfig = DigitalInput.newConfigBuilder(pi4jGreenStatus)
                //.id("my-digital-input")
                .address(PIN_LED_GREEN)
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input");
       ledGreenStatus = pi4jGreenStatus.create(ledGreenStatusConfig);
       
       ledBlueStatusConfig = DigitalInput.newConfigBuilder(pi4jBlueStatus)
               //.id("my-digital-input")
               .address(PIN_LED_BLUE)
               .pull(PullResistance.PULL_DOWN)
               .provider("pigpio-digital-input");
      ledBlueStatus = pi4jBlueStatus.create(ledBlueStatusConfig);
      
      ledRedStatusConfig = DigitalInput.newConfigBuilder(pi4jRedStatus)
              //.id("my-digital-input")
              .address(PIN_LED_RED)
              .pull(PullResistance.PULL_DOWN)
              .provider("pigpio-digital-input");
     ledRedStatus = pi4jRedStatus.create(ledRedStatusConfig);

        
      ledGreenConfig = DigitalOutput.newConfigBuilder(pi4jGreen)
               .id("led")
               .name("LED Flasher")
               .address(PIN_LED_GREEN)
               .shutdown(DigitalState.LOW)
               .initial(DigitalState.LOW)
               .provider("pigpio-digital-output");
      led_Green = pi4jGreen.create(ledGreenConfig);

      
      ledBlueConfig = DigitalOutput.newConfigBuilder(pi4jBlue)
              .id("led")
              .name("LED Flasher")
              .address(PIN_LED_BLUE)
              .shutdown(DigitalState.LOW)
              .initial(DigitalState.LOW)
              .provider("pigpio-digital-output");
     led_Blue = pi4jBlue.create(ledBlueConfig);
     
     
     ledRedConfig = DigitalOutput.newConfigBuilder(pi4jRed)
             .id("led")
             .name("LED Flasher")
             .address(PIN_LED_RED)
             .shutdown(DigitalState.LOW)
             .initial(DigitalState.LOW)
             .provider("pigpio-digital-output");
    led_Red = pi4jRed.create(ledRedConfig);
    
    
    ledGreenStatus.addListener(e -> {
    	ledStatus ledStatusMsg = new ledStatus("green", e.state().toString());
    	String ledMQQTMsg = gson.toJson(ledStatusMsg);
			System.out.println(ledMQQTMsg);
    });
    ledBlueStatus.addListener(e -> {
    	ledStatus ledStatusMsg = new ledStatus("blue", e.state().toString());
    	String ledMQQTMsg = gson.toJson(ledStatusMsg);
			System.out.println(ledMQQTMsg);
    });
    ledRedStatus.addListener(e -> {
    	ledStatus ledStatusMsg = new ledStatus("red", e.state().toString());
    	String ledMQQTMsg = gson.toJson(ledStatusMsg);
			System.out.println(ledMQQTMsg);
    });

      
    }

	public GpioController(DigitalOutput led_Green, DigitalOutput led_Blue, DigitalOutput led_Red, DigitalInput ledGreenStatus, DigitalInput ledBlueStatus, DigitalInput ledRedStatus) {
		super();
		this.led_Green = led_Green;
		this.led_Blue = led_Blue;
		this.led_Red = led_Red;
		this.ledGreenStatus = ledGreenStatus;
		this.ledBlueStatus = ledBlueStatus;
		this.ledRedStatus = ledRedStatus;
	}

	public void startLed(String color) {
		if (color == "Green") {
			led_Green.high();
		}
		else if (color == "Blue") {
			led_Blue.high();
		}
		else if (color == "Red") {
			led_Red.high();
		}

	}
	
	public void stopLed(String color) {
		if (color == "Green") {
			led_Green.low();
		}
		else if (color == "Blue") {
			led_Blue.low();
		}
		else if (color == "Red") {
			led_Red.low();
		}

	}

}
