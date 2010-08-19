package com.supervaca.amazonrest.domain;

public class Image {
	String url;
	int height;
	int width;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Image [url=").append(url).append(", height=").append(height).append(", width=").append(width).append("]");
		return builder.toString();
	}
}
