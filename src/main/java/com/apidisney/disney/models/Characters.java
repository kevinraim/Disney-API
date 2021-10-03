package com.apidisney.disney.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="characters", schema="public")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Characters {
	
	@Id
	@Column(name="character_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="image")
	private String image;
	
	@Column(name="name")
	private String name;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="weigh")
	private Float weigh;
	
	@Column(name="biography")
	private String bio;
	
	@ManyToMany(targetEntity = Movies.class, fetch = FetchType.LAZY)
	@JoinTable(name="characters_movies", schema="public",
			joinColumns=@javax.persistence.JoinColumn(name="characters_id"),
			inverseJoinColumns =@javax.persistence.JoinColumn(name="movies_id"))
	private List<Movies> movies;
	
	Characters(){}
	
	public static String getImagePath() {
		return "src//main//resources//static//images//characters";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Float getWeigh() {
		return weigh;
	}

	public void setWeigh(Float weigh) {
		this.weigh = weigh;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Movies> getMovies() {
		return movies;
	}

	public void setMovies(List<Movies> movies) {
		this.movies = movies;
	}
	
	
	
}
