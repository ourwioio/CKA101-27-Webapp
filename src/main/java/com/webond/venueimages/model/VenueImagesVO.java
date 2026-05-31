package com.webond.venueimages.model;

public class VenueImagesVO{
	private Integer imagesId;
	private Integer venueId;
	private byte[] images;

	public VenueImagesVO() {
		super();
	}

	public VenueImagesVO(Integer imagesId, Integer venueId, byte[] images) {
		super();
		this.imagesId = imagesId;
		this.venueId = venueId;
		this.images = images;
	}

	public Integer getImagesId() {
		return imagesId;
	}

	public void setImagesId(Integer imagesId) {
		this.imagesId = imagesId;
	}

	public Integer getVenueId() {
		return venueId;
	}

	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}

	public byte[] getImages() {
		return images;
	}

	public void setImages(byte[] images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "VenueImagesVO [imagesId=" + imagesId + ", venueId=" + venueId + ", images=" + images + "]";
	}

}
