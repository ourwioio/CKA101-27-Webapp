package com.webond.venueimages.model;

public class VenueImagesVO{
	private Integer imagesId;
	private Integer venueId;
	private byte[] images;
	private byte cover;

	public VenueImagesVO() {
		super();
	}

	public VenueImagesVO(Integer imagesId, Integer venueId, byte[] images, byte cover) {
		super();
		this.imagesId = imagesId;
		this.venueId = venueId;
		this.images = images;
		this.cover = cover;
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

	public byte getCover() {
		return cover;
	}

	public void setCover(byte cover) {
		this.cover = cover;
	}

	

}
