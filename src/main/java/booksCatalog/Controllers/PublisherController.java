package booksCatalog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Publisher;
import booksCatalog.entities.repo.PublisherRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class PublisherController {
	@Autowired
	PublisherRepo publisherRepo;

	@CrossOrigin
	@GetMapping("/publishers")
	@Operation(summary = "list of all publishers")
	public List<Publisher> getAllPublishers() {
		return publisherRepo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addpublisher")
	@Operation(summary = "Add a new Publisher", description = "Adding a new publisher details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "fetched data successfully"),
			@ApiResponse(responseCode = "404", description = "given pub_id is already present"),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "400", description = "Bad Request") })
	public Publisher addNewPublisher(@Valid @RequestBody Publisher publisher) {
		try {
			var optPublisher = publisherRepo.findById(publisher.getPub_id());
			if (optPublisher.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "publisher already present");
			publisherRepo.save(publisher);
			return publisher;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletepublishers/{pub_id}")
	@Operation(summary = "Delete a Publisher By pub_id", description = "Given a pub_id it deletes the existing Publisher by the given pub_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted Publisher Successfully"),
			@ApiResponse(responseCode = "404", description = "Given pub_id not found"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void deleteOnePublisher(@PathVariable("pub_id") String pub_id) {
		var optPublisher = publisherRepo.findById(pub_id);
		if (optPublisher.isPresent())
			publisherRepo.deleteById(pub_id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher ID Not Found!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updatepublisher/{pub_id}")
	@Operation(summary = "Update a Publisher By pub_id", description = "Given a pub_id it Updates the existing publisher by the given pub_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated Publisher Successfully"),
			@ApiResponse(responseCode = "200", description = "fetched data successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "check url properly"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void updatePublisher( @RequestBody Publisher updatePublisher, @PathVariable("pub_id") String pub_id) {
		var optPublisher = publisherRepo.findById(pub_id);
		if (optPublisher.isPresent()) {
			Publisher pub = optPublisher.get();
			pub.setPub_id(updatePublisher.getPub_id());
			pub.setPub_name(updatePublisher.getPub_name());
			pub.setEmail(updatePublisher.getEmail());
			pub.setCity(updatePublisher.getCity());
			pub.setCountry(updatePublisher.getCountry());
			publisherRepo.save(pub);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pubid not found");
		}
	}

}