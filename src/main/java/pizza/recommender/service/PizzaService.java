package pizza.recommender.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import pizza.recommender.entity.Pizza;
import pizza.recommender.repository.PizzaRepository;
import pizza.recommender.service.algorithm.Backtracking;
import pizza.recommender.service.algorithm.CandidateList;

@Service
@Transactional
@Log4j2
public class PizzaService {

	private final PizzaRepository pizzaRepository;

	@Autowired
	public PizzaService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	public List<Pizza> getListOfAllPizzas() {
		return pizzaRepository.findAll();
	}

	/**
	 * Returns the list of offers to spend {@code price} amount of money on pizzas.
	 * 
	 * @param price the price to spend.
	 * @return the list of offers
	 */
	public CandidateList getOffers(Integer price) {

		Backtracking b = new Backtracking(getListOfAllPizzas(), price);
		
		CandidateList result = b.getCombinations();
		
		log.info("Offers: {}", result);

		return result;
	}

	/**
	 * Updates the DB with the newPizzaList. If the same name pizza exists in the
	 * newPizzaList and in the DB, the new pizza data will overwrite the old one.
	 * 
	 * @param newPizzaList the list of pizzas to update the existing DB.
	 */
	public void updatePizzaList(List<Pizza> newPizzaList) {

		var oldPizzaMap = pizzaRepository.findAllAsStream().collect(Collectors.toMap(Pizza::getName, pizza -> pizza));

		newPizzaList.stream().forEach(pizza -> {
			Pizza existingPizza = oldPizzaMap.get(pizza.getName());
			if (existingPizza != null) {
				existingPizza.setPrice(pizza.getPrice());
			} else {
				oldPizzaMap.put(pizza.getName(), pizza);
			}
		});

		pizzaRepository.saveAll(oldPizzaMap.values());
	}
}
