package com.simform.projection.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDTO {
	
	private String name;
	private String author;
	private Double price;
	
	public BookDTO(String name, String author, Double price) {
		super();
		this.name = name;
		this.author = author;
		this.price = price;
	}

	@Override
	public String toString() {
		return "BookDTO [name=" + name + ", author=" + author + ", price=" + price + "]";
	}
	
	
}