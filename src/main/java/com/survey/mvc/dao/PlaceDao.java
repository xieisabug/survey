package com.survey.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.survey.mvc.dto.PlaceDto;

@Repository("placeDao")
public class PlaceDao {

	@Autowired
    public JdbcTemplate jdbcTemplate;
	
	public List<PlaceDto> getAllPlace(){
        List<PlaceDto> placeDto;
        try {
        	placeDto = jdbcTemplate.query("select * from sur_place",new PlaceMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<PlaceDto>();
        }
        return placeDto;
    }
	
	public List<PlaceDto> getPlaceById(int father){
        List<PlaceDto> placeDto;
        try {
        	placeDto = jdbcTemplate.query("select * from sur_place where father="+father,new PlaceMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<PlaceDto>();
        }
        return placeDto;
    }
	
	private class PlaceMapper implements RowMapper<PlaceDto>{

        @Override
        public PlaceDto mapRow(ResultSet resultSet, int i) throws SQLException {
        	PlaceDto placeDto = new PlaceDto();
        	placeDto.setPid(resultSet.getInt("pid"));
        	placeDto.setPname(resultSet.getString("pname"));
        	placeDto.setFather(resultSet.getInt("father"));
            return placeDto;
        }
    }
}
