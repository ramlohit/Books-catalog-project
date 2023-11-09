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

import booksCatalog.entities.Author;
import booksCatalog.entities.repo.AuthorRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class AuthorController {
	@Autowired
	AuthorRepo authorRepo;

	@GetMapping("/authors")
	@Operation(summary = "list of all authors")
	public List<Author> getAllAuthors() {
		return authorRepo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addauthor")
	@Operation(summary = "Add a new Author", description = "Adding a new author details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Added new Author Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request ") })
	public Author addNewAuthor(@Valid @RequestBody Author author) {
		try {
			var optAuthor = authorRepo.findById(author.getAu_id());
			if (optAuthor.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "author already present");
			authorRepo.save(author);
			return author;
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteauthor/{au_id}")
	@Operation(summary = "Deletes an Author By au_id", description = "Given an Author it deletes the existing Author by the given au_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deleted Author Successfully"),
			@ApiResponse(responseCode = "404", description = "Given au_id not found"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void deleteOneAuthor(@PathVariable("au_id") String au_id) {
		var optAuthor = authorRepo.findById(au_id);
		if (optAuthor.isPresent())
			authorRepo.deleteById(au_id);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "author ID Not Found!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateauthor/{au_id}")
	@Operation(summary = "Update a Author By au_id", description = "Given a au_id it Updates the existing author by the given au_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated Author Successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "au_id Not Found"),
			@ApiResponse(responseCode = "500", description = "an issue occurred on the server while processing a request") })
	public void updateTitle(@RequestBody Author updateAuthor, @PathVariable("au_id") String au_id) {
		var optAuthor = authorRepo.findById(au_id);
		if (optAuthor.isPresent()) {
			var author = optAuthor.get();
			author.setAu_name(updateAuthor.getAu_name());
			author.setEmail(updateAuthor.getEmail());
			author.setMobile(updateAuthor.getMobile());
			author.setCity(updateAuthor.getCity());
			author.setCountry(updateAuthor.getCountry());
			authorRepo.save(author);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "author id Not Found!");
	}

}