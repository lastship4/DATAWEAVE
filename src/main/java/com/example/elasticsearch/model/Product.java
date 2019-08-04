package com.example.elasticsearch.model;

public class Product {
	
	private String colorCode;
	private String title;
	private String thumbnail;
	private String description;
	private String brand;
	private String mrp;
	private String country;
	private String size;

	public Product(
	    String colorCode,
	    String title,
	    String thumbnail,
	    String description,
	    String brand,
	    String mrp,
	    String country,
	    String size) {
	  this.colorCode = colorCode;
	  this.title = title;
	  this.thumbnail = thumbnail;
	  this.description = description;
	  this.brand = brand;
	  this.mrp = mrp;
	  this.country = country;
	  this.size = size;
	}

	public String getColorCode() {
	  return colorCode;
	}

	public void setColorCode(String colorCode) {
	  this.colorCode = colorCode;
	}

	public String getTitle() {
	  return title;
	}

	public void setTitle(String title) {
	  this.title = title;
	}

	public String getThumbnail() {
	  return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
	  this.thumbnail = thumbnail;
	}

	public String getDescription() {
	  return description;
	}

	public void setDescription(String description) {
	  this.description = description;
	}

	public String getBrand() {
	  return brand;
	}

	public void setBrand(String brand) {
	  this.brand = brand;
	}

	public String getMrp() {
	  return mrp;
	}

	public void setMrp(String mrp) {
	  this.mrp = mrp;
	}

	public String getCountry() {
	  return country;
	}

	public void setCountry(String country) {
	  this.country = country;
	}

	public String getSize() {
	  return size;
	}

	public void setSize(String size) {
	  this.size = size;
	}

}
