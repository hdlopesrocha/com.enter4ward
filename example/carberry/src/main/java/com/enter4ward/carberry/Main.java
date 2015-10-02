package com.enter4ward.carberry;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// create gpio controller instance
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput myButton = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02); 
 
		while(true){
        	myButton.setState(PinState.HIGH);
			Thread.sleep(50);
			myButton.setState(PinState.LOW);
			Thread.sleep(50);

			myButton.setState(PinState.HIGH);
			Thread.sleep(50);
			myButton.setState(PinState.LOW);
			Thread.sleep(50);

			myButton.setState(PinState.HIGH);
			Thread.sleep(50);
			myButton.setState(PinState.LOW);
			Thread.sleep(3000);
        }
		
        
	}

}
