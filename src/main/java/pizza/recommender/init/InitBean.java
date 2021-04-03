package pizza.recommender.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import pizza.recommender.entity.Pizza;
import pizza.recommender.service.PizzaService;

@Log4j2
@Component
public class InitBean implements InitializingBean {

	private final PizzaService pizzaService;

	@Autowired
	public InitBean(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		List<Pizza> pizzaList = new ArrayList<Pizza>();
		pizzaList.add(Pizza.builder().name("Capricciosa").price(2695).build());
		pizzaList.add(Pizza.builder().name("Csípős olaszkolbászos").price(2695).build());
		pizzaList.add(Pizza.builder().name("MegaVega").price(2895).build());

		pizzaList.add(Pizza.builder().name("Olaszkolbászos").price(2395).build());
		pizzaList.add(Pizza.builder().name("Dallas").price(2895).build());
		pizzaList.add(Pizza.builder().name("Texas").price(2895).build());
		pizzaList.add(Pizza.builder().name("Szuprém").price(2695).build());

		pizzaList.add(Pizza.builder().name("Szuper Szuprém").price(2895).build());
		pizzaList.add(Pizza.builder().name("Húsimádó").price(2895).build());
		pizzaList.add(Pizza.builder().name("Magyaros").price(2895).build());
		pizzaList.add(Pizza.builder().name("Hawaii").price(2395).build());
		pizzaList.add(Pizza.builder().name("Margarita").price(2095).build());
		pizzaList.add(Pizza.builder().name("Fele - fele").price(2394).build());

		pizzaList.add(Pizza.builder().name("Csirkés pizza - közepes méret").price(2394).build());
		pizzaList.add(Pizza.builder().name("Sonkás pizza - közepes méret").price(2394).build());
		pizzaList.add(Pizza.builder().name("Gombás pizza - közepes méret").price(2394).build());

		log.info("Init pizza list: {}", pizzaList);

		pizzaService.updatePizzaList(pizzaList);

	}

}
