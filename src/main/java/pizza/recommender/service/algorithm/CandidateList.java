package pizza.recommender.service.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CandidateList {

	private List<Candidate> candidates;
	private int maximumNumberOfCandidates;
	private int targetPrice;
	private int minPriceDifference;
	private int maxPriceDifference;
	private int casesExamined;

	public CandidateList(int size, int targetPrice) {
		this.candidates = new ArrayList<>(size + 1);
		this.maximumNumberOfCandidates = size;
		this.targetPrice = targetPrice;
		this.maxPriceDifference = Integer.MAX_VALUE;
		this.casesExamined = 0;
	}

	public void setCasesExamined(int casesExamined) {
		this.casesExamined = casesExamined;
	}

	public List<Candidate> getListOfVarieties() {
		List<Candidate> result = new ArrayList<Candidate>(candidates);
		Collections.reverse(result);
		return result;
	}

	public boolean isValid() {
		return candidates.size() > 0;
	}

	public void add(Candidate candidate) {
		if (candidate.getPrice() == 0) {
			return;
		}
		if (candidate.getPrice() > targetPrice) {
			throw new RuntimeException(
					String.format("candidate price: (%d) > Target price: (%d)", candidate.getPrice(), targetPrice));
		}
		int priceDifference = targetPrice - candidate.getPrice();
		if (candidates.size() < maximumNumberOfCandidates || priceDifference <= this.minPriceDifference) {
			int position = Collections.binarySearch(candidates, candidate);
			if (position < 0) {
				position = -position - 1;
			} else {
				// check if the candidate is not a duplicate
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
				priceDifference = targetPrice - this.candidates.get(i).getPrice();
				if (priceDifference > maxPriceDifference) {
					maxPriceDifference = priceDifference;
				}
			}
		}
	}

	public boolean isComplete() {

		int currentSize = Math.min(maximumNumberOfCandidates, this.candidates.size());
		if (currentSize < maximumNumberOfCandidates || maxPriceDifference != 0) {
			return false;
		}
		return true;
	}

}
