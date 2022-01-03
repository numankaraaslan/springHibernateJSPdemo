package com.numankaraaslan.springHibernateJSPdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.numankaraaslan.springHibernateJSPdemo.model.Book;
import com.numankaraaslan.springHibernateJSPdemo.service.BookService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
// EnableWebMvc not required because spring-webmvc is in the dependencies, not just spring-web
// @EnableWebMvc
public class JSPController
{
	private BookService bookService;

	@GetMapping("/wellcome")
	public ModelAndView helloWorld()
	{
		ModelAndView wellcomeJSP = new ModelAndView("wellcome");
		String message = "Welllcome to the JSP demo project";
		wellcomeJSP.addObject("message", message);
		return wellcomeJSP;
	}

	@GetMapping("/books")
	public ModelAndView getBooks()
	{
		ModelAndView booksJSP = new ModelAndView("books");
		booksJSP.addObject("books", bookService.getBooks());
		return booksJSP;
	}

	@GetMapping("/addbook")
	public ModelAndView addbook()
	{
		ModelAndView booksJSP = new ModelAndView("addbook");
		booksJSP.addObject("book", new Book());
		return booksJSP;
	}

	@PostMapping("/addbook")
	public ModelAndView addbookPost(@ModelAttribute Book newBook)
	{
		bookService.save(newBook);
		return new ModelAndView("redirect:/books");
	}

	@GetMapping(path =
	{ "", "/", "/index", "/index.jsp" })
	public ModelAndView index()
	{
		return new ModelAndView("index");
	}
}
