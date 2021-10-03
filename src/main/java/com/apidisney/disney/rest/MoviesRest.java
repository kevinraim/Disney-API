package com.apidisney.disney.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apidisney.disney.daoimp.MoviesDaoImp;
import com.apidisney.disney.models.Movies;

@RestController
public class MoviesRest {
	
	@Autowired
	private MoviesDaoImp moviesImp;
	
	
	@GetMapping(value="/movies/all")
	public List<Movies> getAll() {
		return moviesImp.getAll();
	}
	
	@GetMapping(value="/movies")
	public List<Object[]> get(){
		return moviesImp.getImageTitleRelasedate();
	}
	
	@GetMapping(value="/movies/{id}")
	public Movies getById(@PathVariable Long id) {
		return moviesImp.getById(id);
	}
	
	@GetMapping(value="/movies", params="name")
	public @ResponseBody Movies getByTitle(@RequestParam(name="name") String name){
		return moviesImp.getByTitle(name);
	}
	
	@GetMapping(value="/movies", params="genre")
	public @ResponseBody List<Movies> filterByGenre(@RequestParam(name="genre") Long genreId){
		return moviesImp.filterByGenre(genreId);
	}
	
	@GetMapping(value="/movies", params="order")
	public @ResponseBody List<Movies> orderByReleasedate(@RequestParam(name="order") String ord){
		ord.toUpperCase();
		return moviesImp.orderByReleasedate(ord);
	}
	
	@DeleteMapping(value="/movies/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		boolean delete = moviesImp.delete(id);
		
		if(delete == false) {
			return ResponseEntity.badRequest().body("Movie id: "+id+" not found");
		}
		
		return ResponseEntity.ok().body("The movie has been deleted");
	}
	
	
	@RequestMapping(path = "/movies", method=RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public @ResponseBody ResponseEntity<String> create(@RequestPart(value="movie") Movies movie, @RequestPart(value="image" ,required = false) MultipartFile movieImage) {
		
		if(movie == null || movie.getTitle() == null || movie.getTitle().isEmpty()) {
			return ResponseEntity.badRequest().body("Field title cannot be null or empty");
		}
		
		if(movie.getRating() != null && (movie.getRating() < 1 || movie.getRating() > 5)) {
			return ResponseEntity.badRequest().body("Field rating invalid value");
		}
		
		String responseMessage = moviesImp.create(movie, movieImage);
		
		return responseMessage.equals("Successful movie create") ? ResponseEntity.ok().body(responseMessage) :
																   ResponseEntity.badRequest().body(responseMessage);
	}
	
	@RequestMapping(path = "/movies", method=RequestMethod.PUT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> update(@RequestPart(value="movie") Movies movie, @RequestPart(value="image" ,required = false) MultipartFile movieImage) {
		
		if(movie.getId() == null) {
			return ResponseEntity.badRequest().body("Field id cannot be null");
		}
		
		if(movie.getRating() != null && (movie.getRating() < 1 || movie.getRating() > 5)) {
			return ResponseEntity.badRequest().body("Field rating invalid value");
		}
		
		String responseMessage = moviesImp.update(movie, movieImage);
		
		return responseMessage.equals("Successful movie update") ? ResponseEntity.badRequest().body(responseMessage) :
																   ResponseEntity.ok().body(responseMessage);
	}
}
