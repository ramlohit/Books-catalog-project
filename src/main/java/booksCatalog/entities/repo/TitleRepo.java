package booksCatalog.entities.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Author;
import booksCatalog.entities.Publisher;
import booksCatalog.entities.Sale;
import booksCatalog.entities.Title;
import booksCatalog.entities.repo.dto.TitleAndAuthorDTO;
import booksCatalog.entities.repo.dto.TitleDetailsAuthorNamePublisherNameDTO;

public interface TitleRepo extends JpaRepository<Title, String> {

//9	  
	List<Title> findBytitle(String title);

//10
	List<Title> findBypriceBetween(String min, String max);

//11
	List<Title> findTop5ByOrderByYtdsalesDesc();

//7
	List<Title> findByPublisher(Optional<Publisher> publisher);

	// 8
	@Query("SELECT t.title as title, a.au_name as au_Name FROM Title t JOIN t.authorsList a where a.au_name= :au_name")
	List<TitleAndAuthorDTO> findTitlesByAuthor(@Param("au_name") String au_name);

	// 13
	@Query("SELECT t.title as title, t.price as price, t.ytdsales as ytdSales, a.au_name as au_Name, t.publisher.pubName as pub_Name FROM Title t JOIN t.authorsList a where t.title_id=:title_id")
	List<TitleDetailsAuthorNamePublisherNameDTO> findTitleDetailsAuthorNamePublisherName(
			@Param("title_id") String title_id);

}