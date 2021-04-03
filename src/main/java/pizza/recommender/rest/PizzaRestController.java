package pizza.recommender.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import pizza.recommender.entity.Pizza;
import pizza.recommender.service.PizzaService;

@RestController
@Log4j2
public class PizzaRestController {

	private final PizzaService pizzaService;
	
	@Getter
	@AllArgsConstructor
	@ToString
	static class PizzaDTO {
		@NotNull(message = "Name is mandatory")
		private String name;

		@NotNull(message = "Price is mandatory")
		@Min(0)
		private Integer price;
		
		static Pizza fromPizzaDTO(PizzaDTO pizzaDTO) {
			return new Pizza(pizzaDTO.name, pizzaDTO.price);
		}
		
		static PizzaDTO fromPizza(Pizza pizza) {
			return new PizzaDTO(pizza.getName(), pizza.getPrice());
		}
	}

	@Autowired
	public PizzaRestController(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	@PutMapping("/pizzas")
	void updatePizzaList(@RequestBody @Valid @NotBlank List<PizzaDTO> newPizzaList) {
		log.info("PUT Pizza list: {}", newPizzaList);
		List<Pizza> pizzas = new ArrayList<>();
		for (var pizzaDTO: newPizzaList) {
			pizzas.add(PizzaDTO.fromPizzaDTO(pizzaDTO));
		}
		pizzaService.updatePizzaList(pizzas);

	}

	@GetMapping("/pizzas")
	List<PizzaDTO> getAllPizzas() {
		List<Pizza> listOfAllPizzas = pizzaService.getListOfAllPizzas();
		List<PizzaDTO> result = new ArrayList<PizzaRestController.PizzaDTO>();
		for (Pizza pizza: listOfAllPizzas) {
			result.add(PizzaDTO.fromPizza(pizza));
		}

		return result ;
	}
}
