package booksCatalog.entities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Sale;
import booksCatalog.entities.Store;
import booksCatalog.entities.Title;
import booksCatalog.entities.repo.dto.TitleDetailsAuthorNamePublisherNameDTO;

public interface StoreRepo extends JpaRepository<Store, String> {

	// 14
	@Query("SELECT t FROM Store s JOIN s.titles t WHERE s.store_id = :store_id")
	List<Title> findByTitlesByStoreId(@Param("store_id") String store_id);
}