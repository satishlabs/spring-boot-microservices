package com.satishlabs.booksearch.info;

public class BookPriceInfo {
	private Integer bookId;
	private Integer price;
	private double offer;
	private String serverPort;

	public BookPriceInfo() {
	}

	public BookPriceInfo(Integer bookId, Integer price, double offer, String serverPort) {
		super();
		this.bookId = bookId;
		this.price = price;
		this.offer = offer;
		this.serverPort = serverPort;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public double getOffer() {
		return offer;
	}

	public void setOffer(double offer) {
		this.offer = offer;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

}