package pizza.recommender.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Setter
@Getter
@Builder
public class Pizza {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	private Long id;

	@NotNull(message = "Name is mandatory")
	private String name;

	@NotNull(message = "Price is mandatory")
	@Min(0)
	private Integer price;

	public Pizza(String name, int price) {
		this.name = name;
		this.price = price;
	}

}
