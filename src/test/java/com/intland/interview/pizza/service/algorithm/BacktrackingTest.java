package com.intland.interview.pizza.service.algorithm;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import pizza.recommender.entity.Pizza;
import pizza.recommender.service.algorithm.Backtracking;
import pizza.recommender.service.algorithm.CandidateList;

@Log4j2
class BacktrackingTest {

	static final int NUM_PIZZAS = 10;
	static final int CANDIDATE_LIST_SIZE = 5;
	List<Pizza> pizzas;
	CandidateList candidates;
	Backtracking backtracking;

	@BeforeEach
	void init() {

		this.pizzas = Common.getPizzas(2895, 10, 0);
		log.info("Pizza list: {}", pizzas);
		this.backtracking = new Backtracking(pizzas, 2895*10);

	}

	@Test
	void testGetCombinations() {

		var combinations = backtracking.getCombinations();
		log.info("Combinations: {}, cases: {}", combinations, backtracking.getCaseNumber());

	}

}
