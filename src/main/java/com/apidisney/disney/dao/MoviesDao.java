package com.apidisney.disney.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.apidisney.disney.models.Movies;

public interface MoviesDao {

	List<Movies> getAll();
	
	List<Object[]> getImageTitleRelasedate();
	
	Movies getById(Long id);
	
	String create(Movies movie, MultipartFile image);
	
	String update(Movies movie, MultipartFile image) throws IOException;
	
	boolean delete(Long id);
	
	Movies getByTitle(String name);
	
	List<Movies> orderByReleasedate(String ord);
	
	List<Movies> filterByGenre(Long genreId);
	
}
