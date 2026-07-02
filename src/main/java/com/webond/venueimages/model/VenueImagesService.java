package com.webond.venueimages.model;

import java.util.List;

public class VenueImagesService {
	private VenueImagesDAO_interface dao;

	public VenueImagesService() {
		dao = new VenueImagesDAO();
	}

	public VenueImagesVO addImages(Integer venueId, byte[] images, Byte cover) {
		VenueImagesVO viVO = new VenueImagesVO();

//		viVO.setImagesId(imagesId);
		viVO.setVenueId(venueId);
		viVO.setImages(images);
		viVO.setCover(cover);
		dao.insert(viVO);

		return viVO;
	}
	
	public VenueImagesVO updateImages(Integer imagesId, Integer venueId, byte[] images) {
		VenueImagesVO viVO = new VenueImagesVO();

		viVO.setImagesId(imagesId);
		viVO.setVenueId(venueId);
		viVO.setImages(images);
		dao.update(viVO);

		return viVO;
	}
	
	public void deleteImages(Integer imagesId) {
		dao.delete(imagesId);
	}
	
	public VenueImagesVO getOneImages(Integer images) {
		return dao.findByPrimaryKey(images);
	}
	
	public List<VenueImagesVO> getAll(){
		return dao.getAll();
	}
	
	public List<Integer> getAllVenueIds() {
	    return dao.getAllVenueIds();
	}
	
	public List<VenueImagesVO> getAllVenueImages(Integer venueId){
		return dao.getAllVenueImages(venueId);
	}
	
	
}
