package booksCatalog.entities.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Publisher;

public interface PublisherRepo extends JpaRepository<Publisher, String> {
//	@Query("from booksCatalog.entities.Publisher as p  ")
//	List<Title> findByid();

//	@Query("select t from booksCatalog.entities.Title t order by t.publisher.city, t.price desc")
//	List<Publisher> sortByPublisherCity();
	@Query("SELECT p FROM Publisher p JOIN p.titlesList t WHERE t.title_id = :titleId")
	Publisher findByTitleId(@Param("titleId") String titleId);
//	
//	 Optional<Publisher> findBypub_name(String pub_name);

	Optional<Publisher> findByPubName(String publisherName);
}