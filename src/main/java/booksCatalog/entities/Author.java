package booksCatalog.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "authors")
public class Author {
	@Id
	@Pattern(regexp = "^[1-9]\\d{2}$", message = "au_id should be a 3 digit integer > 100")
	@Column(name = "au_id")
	private String au_id;
	@NotBlank(message = "au_name is required")
	@Column(name = "au_name")
	private String au_name;
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
	@Column(name = "email")
	private String email;
	@Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Mobile number format should be 123-456-7890")
	@Column(name = "mobile")
	private String mobile;
	@NotBlank(message = "City is required")
	@Column(name = "city")
	private String city;
	@Pattern(regexp = "^[a-zA-Z]{3}$", message = "Country should be a string with only 3 characters")
	@Column(name = "country")
	private String country;


	@ManyToMany(mappedBy = "authorsList")
	@JsonIgnore
	Set<Title> titles = new HashSet<Title>();


	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}

	public Author() {
		super();
	}

	public Author(String au_id, String au_name, String email, String mobile, String city, String country) {
		super();
		this.au_id = au_id;
		this.au_name = au_name;
		this.email = email;
		this.mobile = mobile;
		this.city = city;
		this.country = country;
	}

	public String getAu_id() {
		return au_id;
	}

	public void setAu_id(String au_id) {
		this.au_id = au_id;
	}

	public String getAu_name() {
		return au_name;
	}

	public void setAu_name(String au_name) {
		this.au_name = au_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(au_id, au_name, city, country, email, mobile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return Objects.equals(au_id, other.au_id) && Objects.equals(au_name, other.au_name)
				&& Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(mobile, other.mobile);
	}

	@Override
	public String toString() {
		return "Author [au_id=" + au_id + ", au_name=" + au_name + ", email=" + email + ", mobile=" + mobile + ", city="
				+ city + ", country=" + country + "]";
	}
}