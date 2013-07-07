package net.trulycanadian.recipleapplication.model;

import java.io.Serializable;

public class SimpleIngredients implements Serializable {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMeasurement() {
		return measurement;
	}
	public void setMeasurement(double measurement) {
		this.measurement = measurement;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	private String name;
	private double measurement;
	private String uuid;
	private String unit;
}
