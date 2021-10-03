package com.apidisney.disney.daoimp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.apidisney.disney.dao.CharactersDao;
import com.apidisney.disney.filecontroller.ImageController;
import com.apidisney.disney.models.Characters;
import com.apidisney.disney.models.Movies;


@Repository
@Transactional
public class CharactersDaoImp implements CharactersDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Characters> getAll() {
		return entityManager.createQuery("FROM Characters", Characters.class).getResultList();
	}

	@Override
	public List<Object[]> getAllNameImage() {
		return entityManager.createQuery("SELECT name, image FROM Characters", Object[].class).getResultList();
	}

	@Override
	public String create(Characters character, MultipartFile image) {
		
		if(character.getMovies() != null) {
			List<Movies> moviesList = character.getMovies();
			
			for(Movies movie : moviesList) {
				if(entityManager.find(Movies.class, movie.getId()) == null) {
					return "Invalid movie id";
				}
			}
		}
		
		if(image != null && !image.isEmpty()) {
			String imageName = ImageController.imageUpload(Characters.getImagePath(), image);
			character.setImage(imageName);
		}
		
		entityManager.merge(character);
		return "Successful character create";
	}

	@Override
	public String update(Characters character, MultipartFile image) {
		
		if(character.getMovies() != null) {
			List<Movies> moviesList = character.getMovies();
			
			for(Movies movie : moviesList) {
				if(entityManager.find(Movies.class, movie.getId()) == null) {
					return "Invalid movie id";
				}
			}
		}
		
		Characters characterToUpdate = entityManager.find(Characters.class, character.getId());
		
		if(characterToUpdate == null) {
			return "Character not exist";
		}
		
		if(character.getName() != null && !character.getName().isEmpty()) {
			characterToUpdate.setName(character.getName());
		}
		
		if(image != null && !image.isEmpty()) {
			ImageController.imageDelete(Characters.getImagePath() + "//" + characterToUpdate.getImage());
			
			String imageName = ImageController.imageUpload(Characters.getImagePath(), image);
			characterToUpdate.setImage(imageName);
		}
		
		if(character.getAge() != null) {
			characterToUpdate.setAge(character.getAge());
		}
		
		if(character.getWeigh() != null) {
			characterToUpdate.setWeigh(character.getWeigh());
		}
		
		if(character.getBio() != null && !character.getBio().isEmpty()) {
			characterToUpdate.setBio(character.getBio());
		}
		
		entityManager.merge(characterToUpdate);
		return "Successful character update";
	}

	@Override
	public boolean delete(Long id) {
		Characters character = entityManager.find(Characters.class, id);
		
		if(character != null) {
			
			if(character.getImage() != null && !character.getImage().isEmpty()) {
				ImageController.imageDelete(Characters.getImagePath() + "//" + character.getImage());
			}
			entityManager.remove(character);
			return true;
		}

		return false;
	}

	@Override
	public List<Characters> filterByAge(int filter) {
		return entityManager.createQuery("FROM Characters WHERE age="+filter, Characters.class).getResultList();
	}
	
	@Override
	public List<Characters> filterByWeigh(float filter) {
		return entityManager.createQuery("FROM Characters WHERE weigh="+filter, Characters.class).getResultList();
	}

	@Override
	public Characters getById(Long id) {
		return entityManager.find(Characters.class, id);
	}


	@Override
	public List<Object[]> getByMovidId(Long movieId) {
		return entityManager.createQuery("FROM Characters c JOIN c.movies cm WHERE cm.id="+movieId, Object[].class).getResultList();
	}


}
