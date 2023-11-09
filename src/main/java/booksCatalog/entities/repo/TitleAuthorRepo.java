package booksCatalog.entities.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import booksCatalog.entities.TitleAuthor;
import booksCatalog.entities.TitleAuthorCPK;

public interface TitleAuthorRepo extends JpaRepository<TitleAuthor, TitleAuthorCPK> {
//	@Query("SELECT ta FROM TitleAuthor ta WHERE ta.key.title_id = :title_id AND ta.key.au_id = :au_id")
//	List<TitleAuthor> findByTitle_idAndAuthor_id(@Param("title_id") String title_id, @Param("au_id") String au_id);
//	
}