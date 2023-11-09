package booksCatalog.entities.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import booksCatalog.entities.Sale;
import booksCatalog.entities.SaleCPK;
import booksCatalog.entities.repo.dto.SalesByTitleAcrossStoresDTO;
import booksCatalog.entities.repo.dto.StoreSumOfQtyDTO;

public interface SaleRepo extends JpaRepository<Sale, SaleCPK> {

	//12
	@Query("select  s.store.store_id as storeId, sum(s.qty_sold) as sumOfQtySold from Sale s Group By storeId Order By sumOfQtySold Desc")
	List<StoreSumOfQtyDTO> findByTop5StoresBySales(PageRequest pageRequest);

	// 15
	@Query("select s.store.store_id as storeId,s.qty_sold as qtySold from Sale s join s.title t where t.title_id=:title_id")
	List<SalesByTitleAcrossStoresDTO> findBySalesByTitleAcrossStore(@Param("title_id") String title_id);

}