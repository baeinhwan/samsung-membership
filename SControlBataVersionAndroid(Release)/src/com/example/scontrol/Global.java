 // : 'All code (c) Samsung Techwin Co,Ltd. all rights reserved.'

package com.example.scontrol;

import android.app.Application;

public class Global extends Application{
	
	private int batteryAngle = -120;
	private int fuelAngle = 120;
	private int coolantAngle = -145;
	private int batteryX = 258;
	private int batteryY = 256;

	public int getbatteryAngle() {
		return batteryAngle;
	}

	public void setbatteryAngle(int batteryAngle) {
		this.batteryAngle = batteryAngle;
	}
	
	public int getfuelAngle() {
		return fuelAngle;
	}

	public void setfuelAngle(int fuelAngle) {
		this.fuelAngle = fuelAngle;
	}
	
	public int getcoolantAngle() {
		return coolantAngle;
	}

	public void setcoolantAngle(int coolantAngle) {
		this.coolantAngle = coolantAngle;
	}

	
	public int getBatteryX() {
		return batteryX;
	}
	
	public void setBatteryX(int batteryX){
		this.batteryX = batteryX;
	}

	public int getBatteryY() {
		return batteryY;
	}
	
	public void setBatteryY(int batteryY){
		this.batteryY = batteryY;
	}

}
