package pizza.recommender.service.algorithm;

import java.util.ArrayList;
import java.util.List;

import pizza.recommender.entity.Pizza;

public class Common {

	public static List<Pizza> getPizzas(int num) {
		var pizzas = new ArrayList<Pizza>();
		for (int i = 1; i <= num; i++) {
			pizzas.add(new Pizza("Pizza_" + i, i));
		}
		return pizzas;
	}

	public static List<Pizza> getPizzas(int basePrice, int num, int delta) {
		var pizzas = new ArrayList<Pizza>();
		for (int i = 1; i <= num; i++) {

			var price = basePrice + delta * (i - 1);

			pizzas.add(new Pizza("Pizza_" + i + "_" + price, price));
		}
		return pizzas;
	}

}
