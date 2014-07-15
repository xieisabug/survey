package com.survey.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.mvc.dao.PlaceDao;
import com.survey.mvc.dto.PlaceDto;

@Service
public class PlaceService {

	@Autowired
	PlaceDao placeDao;
	
	public List<PlaceDto> getPlaceById(int father){
		return placeDao.getPlaceById(father);
	}
	
	public List<PlaceDto> getAllPlace(){
		return placeDao.getAllPlace();
	}
	
	public Map<String, List<PlaceDto>> getMap(){
		Map<String, List<PlaceDto>> map = new HashMap<String, List<PlaceDto>>();
		List<PlaceDto> city = placeDao.getPlaceById(0);
		map.put("0", city);
		for (PlaceDto placeDto : city) {
			map.put(placeDto.getPid()+"", placeDao.getPlaceById(placeDto.getPid()));
		}
		return map;
	}
}
