package com.simform.projection.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simform.projection.dto.BookDTO;
import com.simform.projection.dto.BookView;
import com.simform.projection.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAll();
	
	List<Book> findByAuthor(String string);
	
	// Interface based Projection
	List<BookView> findViewByAuthor(String name);

	List<BookView> findAllProjectedBy();
	
	// Class based Projection
	List<BookDTO> findDTOByAuthor(String author);

	//Dynamic
	<T> Optional<T> findById(Long id, Class<T> type);

}