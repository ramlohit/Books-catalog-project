package booksCatalog.entities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Author;

public interface AuthorRepo extends JpaRepository<Author, String> {
	@Query("SELECT a FROM Author a JOIN a.titles t WHERE t.title_id = :titleId")
	List<Author> findByTitleId(@Param("titleId") String titleId);
	// List<Author> findByTitles_TitleId(String titleId);

}