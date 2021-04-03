package pizza.recommender.service.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.log4j.Log4j2;
import pizza.recommender.entity.Pizza;

@Log4j2
public class Backtracking {

	private static final int MAXIMUM_NUMBER_OF_CASES_TO_CHECK = 1_000_000;
	private static final int NUMBER_OF_SOLUTIONS = 5;
	private boolean hasSolution = true;
	private List<Pizza> availablePizzaList;
	private int price;
	private List<Integer> maxPizzaNum;
	private CandidateList candidateList;
	private int caseNumber;

	public Backtracking(List<Pizza> available, int price) {
		Objects.nonNull(available);

		this.candidateList = new CandidateList(NUMBER_OF_SOLUTIONS, price);
		if (available.size() == 0 || price <= 0) {
			hasSolution = false;
			return;
		}
		this.price = price;
		this.availablePizzaList = new ArrayList<>(available);
		this.availablePizzaList.sort((p1, p2) -> p1.getPrice() - p2.getPrice());

		if (this.availablePizzaList.get(0).getPrice() > this.price) {
			hasSolution = false;
			return;
		}

		this.maxPizzaNum = getMaxValues();

		this.caseNumber = 0;
	}

	/**
	 * Recursive backtrack
	 * 
	 * @param state current state representation
	 */
	private void backtrack(Candidate candidate) {
		caseNumber++;
		if (caseNumber % 10000 == 0) {
			log.info("case number: {}", caseNumber);
		}
		if (candidateList.isComplete() || caseNumber > MAXIMUM_NUMBER_OF_CASES_TO_CHECK) {
			return;
		}
		if (isValidState(candidate)) {
			candidateList.add(candidate);
			// process next states
			int nextPizzaIndex = getTheIndexOfTheNextPizza(candidate);
			if (nextPizzaIndex < availablePizzaList.size()) {
				for (int i = 0; i <= maxPizzaNum.get(nextPizzaIndex); i++) {
					backtrack(candidate.createNewCandidateByAddingPizza(i));
				}
			}
		}
	}

	public int getCaseNumber() {
		return this.caseNumber;
	}

	/**
	 * Returns the top combinations
	 * 
	 * @return {@link CandidateList}
	 */
	public CandidateList getCombinations() {

		log.info("GetCombinations");

		if (hasSolution) {
			backtrack(new Candidate(this.availablePizzaList, new ArrayList<>()));
			this.candidateList.setCasesExamined(this.caseNumber);
		}

		return this.candidateList;
	}

	private ArrayList<Integer> getMaxValues() {
		ArrayList<Integer> result = new ArrayList<>(this.availablePizzaList.size());
		for (int i = 0; i < availablePizzaList.size(); i++) {
			result.add(this.price / this.availablePizzaList.get(i).getPrice());
		}
		return result;
	}

	private int getTheIndexOfTheNextPizza(Candidate candidate) {
		return candidate.getState().size();
	}

	/**
	 * Returns true if the price of the state is valid
	 * 
	 * @param state
	 * @return
	 */
	private boolean isValidState(Candidate candidate) {
		return candidate.getPrice() <= this.price;
	}

}
