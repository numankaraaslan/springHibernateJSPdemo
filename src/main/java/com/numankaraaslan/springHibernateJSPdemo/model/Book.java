package com.numankaraaslan.springHibernateJSPdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "springhibernate", name = "Book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// Remember to create "springhibernate" schema in posgtresql database
// CREATE SCHEMA springhibernate AUTHORIZATION postgres; GRANT ALL ON SCHEMA springhibernate TO postgres;
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private int year;

	@Column
	private String author;

	public Book(String name, int year, String author)
	{
		this.name = name;
		this.year = year;
		this.author = author;
	}

}
