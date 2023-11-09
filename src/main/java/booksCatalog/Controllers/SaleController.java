package booksCatalog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Sale;
import booksCatalog.entities.repo.SaleRepo;
import booksCatalog.entities.repo.dto.SalesByTitleAcrossStoresDTO;
import booksCatalog.entities.repo.dto.StoreSumOfQtyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class SaleController {
	@Autowired
	SaleRepo saleRepo;

	@GetMapping("/sales")
	@Operation(summary = "list of all sales")
	public List<Sale> getAllSale() {
		return saleRepo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addsale")
	@Operation(summary = "Add a new Sale", description = "Adding a new sale details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Added new Sale Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request ") })
	public Sale addNewSale(@Valid @RequestBody Sale sale) {
		try {
			var optSale = saleRepo.findById(sale.getKey());
			if (optSale.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sale already present");
			saleRepo.save(sale);
			return sale;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletesales")
	@Operation(summary = "Delete a Sale By Key", description = "Given a Key it deletes the Sales by the given Key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted Sales Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Given store_id not found"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void deleteOneSale(@RequestBody Sale deleteSale) {
		var optsale = saleRepo.findById(deleteSale.getKey());
		if (optsale.isPresent())
			saleRepo.deleteById(deleteSale.getKey());
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale Not Found!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updatesales")
	@Operation(summary = "Update a Sale By key", description = "Given a key it Updates the existing sale by the given key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated Sale Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "store_id Not Found"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })

	public void updateSale(@RequestBody Sale updateSale) {
		var optsale = saleRepo.findById(updateSale.getKey());
		if (optsale.isPresent()) {
			var s = optsale.get();
			s.setQty_sold(updateSale.getQty_sold());
			saleRepo.save(s);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sale Not Found!");
	}

//12
	@GetMapping("/top5storesbysales")
	@Operation(summary = "retrieves top 5  Sales By total titles sold", description = "Given a store_id it retrieves top 5  Sales By total titles sold ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved data Successfully"),
			@ApiResponse(responseCode = "404", description = "store_id Not Found"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public List<StoreSumOfQtyDTO> displayTop5StoresBySales() {
		var listStores = saleRepo.findByTop5StoresBySales(PageRequest.of(0, 5));
		return listStores;

	}

//15
	@GetMapping("/salesByTitleAcrossStores/{title_id}")
	@Operation(summary = "Get Sales By title_id across stores", description = "Get All Sales By Given title_id across stores")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved ALl Sales Successful"),
			@ApiResponse(responseCode = "404", description = "title_id Not Found"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<SalesByTitleAcrossStoresDTO> displaySalesByTitleAcrossStores(@PathVariable("title_id") String title_id) {
		var listSales = saleRepo.findBySalesByTitleAcrossStore(title_id);
		if (listSales.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "title Not Found!");
		} else {
			return listSales;
		}
	}

}