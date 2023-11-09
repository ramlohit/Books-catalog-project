package booksCatalog.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TitleAuthorCPK implements Serializable {

	@Column(name = "au_id")
	private String au_id;
	@Column(name = "title_id")
	private String title_id;

	public String getAu_id() {
		return au_id;
	}

	public void setAu_id(String au_id) {
		this.au_id = au_id;
	}

	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(au_id, title_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleAuthorCPK other = (TitleAuthorCPK) obj;
		return Objects.equals(au_id, other.au_id) && Objects.equals(title_id, other.title_id);
	}
}