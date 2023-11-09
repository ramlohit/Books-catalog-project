package booksCatalog.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "titleauthor")
public class TitleAuthor {
	@EmbeddedId
	private TitleAuthorCPK key;
	@Min(value = 0, message = "Royalty percentage must be between 0 and 100")
	@Max(value = 100, message = "Royalty percentage must be between 0 and 100")
	@Column(name = "royalty_pct")
	private String royalty_pct;

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="title_id",insertable=false,updatable=false)
//	private Title title;

//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="au_id",insertable=false,updatable=false)
//	 private Author author;

	public TitleAuthorCPK getKey() {
		return key;
	}

	public void setKey(TitleAuthorCPK key) {
		this.key = key;
	}

	public String getRoyalty_pct() {
		return royalty_pct;
	}

	public void setRoyalty_pct(String royalty_pct) {
		this.royalty_pct = royalty_pct;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, royalty_pct);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleAuthor other = (TitleAuthor) obj;
		return Objects.equals(key, other.key) && Objects.equals(royalty_pct, other.royalty_pct);
	}
}