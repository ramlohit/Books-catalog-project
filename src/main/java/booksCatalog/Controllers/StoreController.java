package booksCatalog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import booksCatalog.entities.Store;
import booksCatalog.entities.Title;
import booksCatalog.entities.repo.StoreRepo;
import booksCatalog.entities.repo.TitleRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class StoreController {
	@Autowired
	StoreRepo storeRepo;
	@Autowired
	TitleRepo titleRepo;

	@GetMapping("/stores")
	@Operation(summary = "list of all stores")
	public List<Store> getAllAuthors() {
		return storeRepo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addstore")
	@Operation(summary = "Add new Store", description = "Adding new store details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Added new Store Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request ") })
	public Store addNewStore(@Valid @RequestBody Store store) {
		try {
			var optStore = storeRepo.findById(store.getStore_id());
			if (optStore.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "store already present");
			storeRepo.save(store);
			return store;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletestore/{store_id}")
	@Operation(summary = "Delete a Store By store_id", description = "Given a store_id it deletes the existing Store by the given store_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted Store Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Given store_id not found"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void deleteOneStore(@PathVariable("store_id") String store_id) {
		var optStore = storeRepo.findById(store_id);
		if (optStore.isPresent())
			storeRepo.deleteById(store_id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "store ID Not Found!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateStore/{store_id}")
	@Operation(summary = "Update a Store By store_id", description = "Given a store_id it Updates the existing store by the given store_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated Store Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "store_id Not Found"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void updateStore( @RequestBody Store updateStore, @PathVariable("store_id") String store_id) {
		var optStore = storeRepo.findById(store_id);
		if (optStore.isPresent()) {
			var storedetails = optStore.get();
			storedetails.setLocation(updateStore.getLocation());
			storedetails.setCity(updateStore.getCity());
			storedetails.setCountry(updateStore.getCountry());
			storeRepo.save(storedetails);

		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "store id Not Found!");
	}

//14
	@GetMapping("listTitlesByStoreId/{store_id}")
	@Operation(summary = "Get Titles By store_id", description = "Get All Titles By Given store_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved ALl Titles Successful"),
			@ApiResponse(responseCode = "404", description = "store_id Not Found"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<Title> ListTitlesbyStoreId(@PathVariable("store_id") String store_id) {
		var list = storeRepo.findByTitlesByStoreId(store_id);
		if (list.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "store id Not Found!");
		return list;

	}

}