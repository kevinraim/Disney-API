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

import com.apidisney.disney.daoimp.CharactersDaoImp;
import com.apidisney.disney.models.Characters;


@RestController
public class CharactersRest {
	
	@Autowired
	private CharactersDaoImp charactersImp;
	
	@GetMapping(value="/characters/all")
	public List<Characters> getAll() {
		return charactersImp.getAll();
	}
	
	@GetMapping(value="/characters")
	public List<Object[]> get(){
		return charactersImp.getAllNameImage();
	}
	
	@GetMapping(value="/characters/{id}")
	public Characters getById(@PathVariable Long id) {
		return charactersImp.getById(id);
	}
	
	@GetMapping(value="/characters", params="age")
	public @ResponseBody List<Characters> getByAge(@RequestParam(name="age") int age){
		return charactersImp.filterByAge(age);
	}
	
	@GetMapping(value="/characters", params="weigh")
	public @ResponseBody List<Characters> getByWeigh(@RequestParam(name="weigh") float weigh){
		return charactersImp.filterByWeigh(weigh);
	}
	
	@GetMapping(value="/characters", params="movies")
	public @ResponseBody List<Object[]> getByMovieId(@RequestParam(name="movies") Long movie_id){
		return charactersImp.getByMovidId(movie_id);
	}
	
	
	@DeleteMapping(value="/characters/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		boolean delete = charactersImp.delete(id);
		
		if(delete == false) {
			return ResponseEntity.badRequest().body("Character id: "+id+" not found");
		}
		
		return ResponseEntity.ok().body("The character has been deleted");
	}
	
	@RequestMapping(path = "/characters", method=RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public @ResponseBody ResponseEntity<String> create(@RequestPart(value="character") Characters character, @RequestPart(value="image" ,required = false) MultipartFile characterImage) {
		
		if(character == null || character.getName() == null || character.getName().isEmpty()) {
			return ResponseEntity.badRequest().body("Field name cannot be null or empty");
		}
		
		String responseMessage = charactersImp.create(character, characterImage); 
		
		return responseMessage.equals("Successful character create") ? ResponseEntity.ok().body(responseMessage) :
																	   ResponseEntity.badRequest().body(responseMessage);
				
	}
	
	@RequestMapping(path = "/characters", method=RequestMethod.PUT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public @ResponseBody ResponseEntity<String> update(@RequestPart(value="character") Characters character, @RequestPart(value="image" ,required = false) MultipartFile characterImage) {
		
		if(character.getId() == null) {
			return ResponseEntity.badRequest().body("Field id cannot be null");
		}
		
		String responseMessage = charactersImp.update(character, characterImage);
		
		return responseMessage.equals("Successful character update") ? ResponseEntity.badRequest().body(responseMessage) :
																	   ResponseEntity.ok(responseMessage);
	}
}
