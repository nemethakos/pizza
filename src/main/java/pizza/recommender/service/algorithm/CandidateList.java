package pizza.recommender.service.algorithm;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Getter
@ToString
@Log4j2
public class CandidateList {

	private List<Candidate> candidates;
	private int maximumNumberOfCandidates;
	private int targetPrice;
	private int maxPriceDifference;
	private int casesExamined;
	private long backtrackTimeNano;

	/**
	 * @param size        the maximum size of the result list
	 * @param targetPrice the amount of money for the results
	 */
	public CandidateList(int size, int targetPrice) {
		this.candidates = new ArrayList<>(size + 1);
		this.maximumNumberOfCandidates = size;
		this.targetPrice = targetPrice;
		this.maxPriceDifference = targetPrice;
		this.casesExamined = 0;
		this.backtrackTimeNano = 0;
	}

	public boolean isEmpty() {
		return this.candidates.size() == 0;
	}

	public Duration getBacktrackDuration() {
		return Duration.ofNanos(backtrackTimeNano);
	}

	public boolean isValid() {
		return candidates.size() > 0;
	}

	public void setCasesExamined(int casesExamined) {
		this.casesExamined = casesExamined;
	}

	public List<Candidate> getListOfVarieties() {
		List<Candidate> result = new ArrayList<Candidate>(candidates);
		Collections.reverse(result);
		return result;
	}

	/**
	 * Adds the candidate for the result list. If the candidate is better then any
	 * of the candidates in the list
	 * 
	 * @param candidate the {@link Candidate}
	 */
	public void add(Candidate candidate) {
		if (candidate.getPrice() == 0) {
			return;
		}
		if (candidate.getPrice() > targetPrice) {
			throw new RuntimeException(
					String.format("candidate price: (%d) > Target price: (%d)", candidate.getPrice(), targetPrice));
		}
		int newPriceDifference = targetPrice - candidate.getPrice();
		if (candidates.size() < maximumNumberOfCandidates || // the list is not full
				newPriceDifference == 0 && this.maxPriceDifference != 0 || // the perfect candidate and the list is not
																			// full of perfect candidates
				newPriceDifference < this.maxPriceDifference) { // imperfect candidate
			// find the position for the new candidate
			int position = Collections.binarySearch(candidates, candidate);
			if (position < 0) {
				// not found in the array
				position = -position - 1;
			} else {
				// check if the candidate is not a duplicate (binary search does not deal with
				// multiple instances)
				if ((candidates.indexOf(candidate)) != -1) {
					return;
				}
			}
			candidates.add(position, candidate);
			if (candidates.size() > maximumNumberOfCandidates) {
				candidates.remove(0);
			}
			// calculate max price difference
			this.maxPriceDifference = 0;
			for (int i = 0; i < this.candidates.size(); i++) {
				int currentPriceDifference = targetPrice - this.candidates.get(i).getPrice();
				if (currentPriceDifference > maxPriceDifference) {
					maxPriceDifference = currentPriceDifference;
				}
			}
		}
	}

	public boolean isComplete() {
		return (maxPriceDifference == 0 && candidates.size() == maximumNumberOfCandidates);
	}

	public void setBacktrackTimeNano(long backtrackTimeNano) {
		this.backtrackTimeNano = backtrackTimeNano;
	}

}
