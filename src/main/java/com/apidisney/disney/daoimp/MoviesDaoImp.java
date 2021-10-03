package com.apidisney.disney.daoimp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.apidisney.disney.dao.MoviesDao;
import com.apidisney.disney.filecontroller.ImageController;
import com.apidisney.disney.models.Characters;
import com.apidisney.disney.models.Genres;
import com.apidisney.disney.models.Movies;

@Repository
@Transactional
public class MoviesDaoImp implements MoviesDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Movies> getAll() {
		return entityManager.createQuery("FROM Movies", Movies.class).getResultList();
	}

	@Override
	public List<Object[]> getImageTitleRelasedate() {
		String query = "SELECT image, title, releasedate FROM Movies";
		return entityManager.createQuery(query, Object[].class).getResultList();
	}

	@Override
	public Movies getById(Long id) {
		return entityManager.find(Movies.class, id);
	}
	

	@Override
	public String create(Movies movie, MultipartFile image) {
		
		if(movie.getCharacters() != null) {
			List<Characters> charactersList = movie.getCharacters();
			
			for(Characters character : charactersList) {
				if(entityManager.find(Characters.class, character.getId()) == null){
					return "Invalid character id";
				}
			}
		}
		
		if(entityManager.find(Genres.class, movie.getGenres().getId()) == null) {
			return "Invalid genre id";
		}
		
		if(image != null && !image.isEmpty()) {
			
			String imageName = ImageController.imageUpload(Movies.getImagePath(), image);	
			movie.setImage(imageName);
		}
		
		entityManager.merge(movie);
		return "Successful movie create";
	}
	

	@Override
	public String update(Movies movie, MultipartFile image) {
		
		if(movie.getCharacters() != null) {
			List<Characters> charactersList = movie.getCharacters();
			
			for(Characters character : charactersList) {
				if(entityManager.find(Characters.class, character.getId()) == null){
					return "Invalid character id";
				}
			}
		}
		
		if(movie.getGenres() != null && entityManager.find(Genres.class, movie.getGenres().getId()) == null) {
			return "Invalid genre id";
		}
		
		Movies movieToUpdate = entityManager.find(Movies.class, movie.getId());
		
		if(movieToUpdate == null) {
			return "Movie not exist";
		}
		
		if(movie.getTitle() != null && !movie.getTitle().isEmpty() ) {
			movieToUpdate.setTitle(movie.getTitle());
		}
		
		if(image != null && !image.isEmpty()) {
			ImageController.imageDelete(Movies.getImagePath() +  "//" + movieToUpdate.getImage());
			
			String imageName = ImageController.imageUpload(Movies.getImagePath(), image);	
			movieToUpdate.setImage(imageName);
		}
		
		if(movie.getRating() != null) {
			movieToUpdate.setRating(movie.getRating());
		}
		
		if(movie.getReleasedate() != null) {
			movieToUpdate.setReleasedate(movie.getReleasedate());
		}
		
		if(movie.getGenres() != null) {
			movieToUpdate.setGenres(movie.getGenres());
		}
		
		entityManager.merge(movieToUpdate);
		return "Successful movie update";
	}

	@Override
	public boolean delete(Long id) {
		Movies movie = entityManager.find(Movies.class, id);
		
		if(movie != null) {
			
			if(movie.getImage() != null && !movie.getImage().isEmpty()) {
				ImageController.imageDelete(Movies.getImagePath() + "//" + movie.getImage());
			}
			entityManager.remove(movie);
			return true;
		}
		
		return false;
	}

	@Override
	public Movies getByTitle(String name) {
		return entityManager.createQuery("FROM Movies WHERE title LIKE '" + name +"'", Movies.class).getSingleResult();
	}

	@Override
	public List<Movies> orderByReleasedate(String ord) {
		return entityManager.createQuery("FROM Movies ORDER BY releasedate "+ ord, Movies.class).getResultList();
	}

	@Override
	public List<Movies> filterByGenre(Long genreId) {
		return entityManager.createQuery("FROM Movies WHERE genre_id="+genreId, Movies.class).getResultList();
	}

}
