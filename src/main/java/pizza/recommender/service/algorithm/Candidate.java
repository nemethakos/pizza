package pizza.recommender.service.algorithm;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import pizza.recommender.entity.Pizza;

@Getter
public class Candidate implements Comparable<Candidate> {

	@Getter
	static class PizzaData {
		Pizza pizza;
		int amount;
	}

	/**
	 * The price of the candidate
	 */
	private int price;

	/**
	 * Contains the list of available pizzas for price calculation and display
	 */
	private List<Pizza> availablePizzaList;

	/**
	 * Contains the list of amount for the available list
	 */
	private List<Integer> state;

	public Candidate(List<Pizza> availablePizzaList, List<Integer> state) {
		super();
		if (state.size() > availablePizzaList.size()) {
			throw new IllegalArgumentException(String.format("state.size() = %d > availablePizzaList.size() = %d",
					state.size(), availablePizzaList.size()));
		}
		this.availablePizzaList = availablePizzaList;
		this.state = state;
		this.price = calculatePrice();
	}

	private int calculatePrice() {

		int sum = 0;

		for (int i = 0; i < state.size(); i++) {
			sum += state.get(i) * availablePizzaList.get(i).getPrice();
		}

		return sum;
	}

	@Override
	public int compareTo(Candidate o) {
		return this.price - o.price;
	}

	public Candidate createNewCandidateByAddingPizza(int pizzaNum) {
		List<Integer> newState = new ArrayList<>(this.state);
		newState.add(pizzaNum);
		return new Candidate(availablePizzaList, newState);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidate other = (Candidate) obj;

		var trimmedState = getTrimmedState();
		var otherTrimmedState = other.getTrimmedState();

		if (price != other.price)
			return false;
		if (trimmedState == null) {
			if (otherTrimmedState != null)
				return false;
		} else if (!trimmedState.equals(otherTrimmedState))
			return false;
		return true;
	}

	public List<PizzaData> getPizzaData() {
		List<PizzaData> result = new ArrayList<>();

		for (int i = 0; i < availablePizzaList.size(); i++) {
			var pd = new PizzaData();
			if (state.size() > i && state.get(i) > 0) {
				pd.amount = state.get(i);
				pd.pizza = availablePizzaList.get(i);
				result.add(0, pd);
			}

		}

		return result;
	}

	private List<Integer> getTrimmedState() {

		List<Integer> trimmed = new ArrayList<Integer>(this.state);

		while (trimmed.get(trimmed.size() - 1) == 0) {
			trimmed.remove(trimmed.size() - 1);
		}

		return trimmed;
	}

	@Override
	public int hashCode() {
		var trimmedState = getTrimmedState();
		final int prime = 31;
		int result = 1;
		result = prime * result + price;
		result = prime * result + ((trimmedState == null) ? 0 : trimmedState.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(@" + this.price + " ");
		for (int i = 0; i < state.size(); i++) {
			if (state.get(i) > 0) {
				Pizza pizza = this.availablePizzaList.get(i);
				sb.append((i > 0 ? ", " : "") + state.get(i) + "x" + pizza.getName() + "@" + pizza.getPrice());
			}
		}
		sb.append(")\r\n");
		return sb.toString();
	}

}
