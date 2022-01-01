package com.numankaraaslan.springHibernateJSPdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.numankaraaslan.springHibernateJSPdemo.model.Book;
import com.numankaraaslan.springHibernateJSPdemo.repo.BookRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService
{
	private BookRepo bookRepo;

	public List<Book> getBooks()
	{
		return bookRepo.getBooks();
	}

	public void save(Book newBook)
	{
		bookRepo.save(newBook);
	}
}
