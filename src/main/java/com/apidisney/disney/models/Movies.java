package com.apidisney.disney.models;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="movies", schema="public")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Movies {
	
	@Id
	@Column(name="movie_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="image")
	private String image;
	
	@Column(name="title")
	private String title;
	
	@Column(name="releasedate")
	@Temporal(TemporalType.DATE)
	private Calendar releasedate;
	
	@Column(name="rating")
	private Integer rating;
	
	@ManyToMany(targetEntity = Characters.class, fetch = FetchType.LAZY)
	@JoinTable(name="characters_movies", schema="public",
				joinColumns=@javax.persistence.JoinColumn(name="movies_id"),
				inverseJoinColumns =@javax.persistence.JoinColumn(name="characters_id"))
	private List<Characters> characters;
	
	@ManyToOne
	@JoinColumn(name="genre_id")
	private Genres genres;
	
	public static String getImagePath() {
		return "src//main//resources//static//images//movies";
	}
	
	Movies(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Calendar releasedate) {
		this.releasedate = releasedate;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public List<Characters> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Characters> characters) {
		this.characters = characters;
	}

	public Genres getGenres() {
		return genres;
	}

	public void setGenres(Genres genres) {
		this.genres = genres;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
