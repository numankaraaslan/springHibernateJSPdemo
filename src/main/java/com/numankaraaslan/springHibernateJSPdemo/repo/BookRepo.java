package com.numankaraaslan.springHibernateJSPdemo.repo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.numankaraaslan.springHibernateJSPdemo.model.Book;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class BookRepo
{
	private SessionFactory sessionFactory;

	@org.springframework.transaction.annotation.Transactional
	// NOT @javax.transaction.Transactional
	public void save(Book newBook)
	{
		Session session = sessionFactory.openSession();
		session.save(newBook);
		session.close();
	}

	@org.springframework.transaction.annotation.Transactional
	public List<Book> getBooks()
	{
		// let the spring handle the transaction
		Session session = sessionFactory.openSession();
		List<Book> books = session.createQuery("select b from Book b", Book.class).getResultList();
		session.close();
		return books;
	}
}
