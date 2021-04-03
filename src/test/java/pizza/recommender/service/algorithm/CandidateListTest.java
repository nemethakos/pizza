package pizza.recommender.service.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import pizza.recommender.entity.Pizza;

@Log4j2
class CandidateListTest {

	static final int NUM_PIZZAS = 100;
	static final int CANDIDATE_LIST_SIZE = 5;
	List<Pizza> pizzas;
	CandidateList candidates;

	@BeforeEach
	void init() {
		candidates = new CandidateList(CANDIDATE_LIST_SIZE, 1000000);
		this.pizzas = Common.getPizzas(NUM_PIZZAS);
		log.info("Pizza list: {}", pizzas);
	}

	Candidate getCandidate(int numPizzas) {
		List<Integer> state = new ArrayList<>();
		for (int i = 0; i < numPizzas; i++) {
			state.add(1);
		}
		log.info("State: {}", state);
		return new Candidate(pizzas, state);
	}

	@Test
	void testAdd() {
		for (int i = 1; i <= NUM_PIZZAS; i++) {
			candidates.add(getCandidate(i));
			log.info("\r\n\r\nCandidate: {}", candidates);
		}
	}

}
