package com.apidisney.disney.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.apidisney.disney.models.Characters;


public interface CharactersDao {
	
	List<Characters> getAll();
	
	List<Object[]> getAllNameImage();
	
	Characters getById(Long id);
	
	String create(Characters character, MultipartFile image);
	
	String update(Characters character, MultipartFile image);
	
	boolean delete(Long id);

	List<Characters> filterByAge(int filter);
	
	List<Characters> filterByWeigh(float filter);
	
	List<Object[]> getByMovidId(Long movieId);
}
