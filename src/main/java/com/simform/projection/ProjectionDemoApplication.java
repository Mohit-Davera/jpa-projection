package com.simform.projection;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.simform.projection.dto.BookDTO;
import com.simform.projection.dto.BookView;
import com.simform.projection.entity.Book;
import com.simform.projection.repo.BookRepository;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectionDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(ProjectionDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProjectionDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository) {
		return (args) -> {
			// save a few books
			// fetch all books
//			bookRepository.save(new Book("Spring Microservices in Action", "John Carnell", 2525.00));
//			bookRepository
//					.save(new Book("Spring Microservices in Action, Second Edition", "John Carnell", 4700.00));
//			bookRepository.save(new Book("Learning Spring Boot 2.0", "Greg L. Turnquist", 2879.00));

			// fetch all books
			log.info("Books found with findAll():");
			log.info("-------------------------------");
			bookRepository.findAll().forEach(bookEntity -> log.info(bookEntity.toString()));
			log.info("");

			// fetch all books by view
			log.info("Books found with findAllProjectedBy():");
			log.info("-------------------------------");
			bookRepository.findAllProjectedBy().forEach(bookView -> log.info("Book View {}, {}, {} ",
					bookView.getAuthor(), bookView.getName(), bookView.getPrice()));
			log.info("");

			// fetch Books by author name Entity Based
			log.info("Entity Based: Book found with findByAuthor('John Carnell'):");
			log.info("------------------------------------------");
			bookRepository.findByAuthor("John Carnell").forEach(bookEntity -> {
				log.info(bookEntity.toString());
			});
			log.info("");
			// fetch Books by author name Class Based
			log.info("Class Based: Book found with findByAuthor('John Carnell'):");
			log.info("--------------------------------------------");
			bookRepository.findDTOByAuthor("John Carnell").forEach(book -> {
				log.info(book.toString());
			});
			log.info("");
			// fetch Books by author name Interface Based
			log.info("Interface Based: Book found with findByAuthor('John Carnell'):");
			log.info("--------------------------------------------");
			List<BookView> books = bookRepository.findViewByAuthor("John Carnell");
			books.forEach(book -> {
				log.info("BookView {}, {}, {}", book.getName(), book.getAuthor(), book.getPrice());
			});
			log.info("");
			// fetch an individual book by ID
			Optional<Book> bookEntity = bookRepository.findById(3L);
			if (bookEntity.isPresent()) {
				log.info("Entity Based: Book found with findById(3L):");
				log.info("----------------------------------");
				log.info(bookEntity.get().toString());
				log.info("");
			}

			// fetch an individual book by ID Class Based
			Optional<BookDTO> bookDTO2 = bookRepository.findById(3L, BookDTO.class);
			if (bookDTO2.isPresent()) {
				log.info("Dynamic Projections ClassBased: Book found with findById(3L) BookDTO.class:");
				log.info("--------------------------------");
				log.info(bookDTO2.get().toString());
				log.info("");
			}

			// fetch an individual book by ID Interface Based
			Optional<BookView> bookView = bookRepository.findById(3L, BookView.class);
			if (bookView.isPresent()) {
				log.info("Dynamic Projections Interface Based: Book found with findById(3L) BookView.class:");
				log.info("--------------------------------");
				log.info("BookView [name=" + bookView.get().getName() + ", author=" + bookView.get().getAuthor() + ", price=" + bookView.get().getPrice() + "]");
				log.info("");
			}

		};
	}
}