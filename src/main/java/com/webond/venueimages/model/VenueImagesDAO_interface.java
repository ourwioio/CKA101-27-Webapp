package com.webond.venueimages.model;

import java.util.List;

public interface VenueImagesDAO_interface {
	
	public void insert(VenueImagesVO venueImagesVO);
	public void update(VenueImagesVO venueImagesVO);
	public void delete(Integer imagesId);
	public VenueImagesVO findByPrimaryKey(Integer imagesId);
	public List<VenueImagesVO> getAll();
	public List<Integer> getAllVenueIds();
	public List<VenueImagesVO> getAllVenueImages(Integer venueId);
	

}
