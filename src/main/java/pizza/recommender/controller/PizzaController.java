package pizza.recommender.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pizza.recommender.controller.data.VarietyForm;
import pizza.recommender.entity.Pizza;
import pizza.recommender.service.PizzaService;

@Controller
public class PizzaController {

	private final PizzaService pizzaService;

	@Autowired
	public PizzaController(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	@PostMapping("/varieties")
	public String displayVarietiesForAPrice(@Valid VarietyForm varietyForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "select";
		}
		Integer offeredPrice = varietyForm.getPrice();
		model.addAttribute("price", offeredPrice);
		model.addAttribute("candidateList", pizzaService.getOffers(offeredPrice));
		return "varieties";
	}

	@GetMapping("/available")
	public String available(Model model) {
		List<Pizza> listOfAllPizzas = pizzaService.getListOfAllPizzas();
		model.addAttribute("pizzas", listOfAllPizzas);
		return "available";
	}

	@GetMapping("/")
	public String welcome() {
		return "redirect:index";
	}

	@GetMapping("/index")
	public String showPizzaList(Model model) {
		model.addAttribute("variety", new VarietyForm());
		return "index";
	}

	@GetMapping("/select")
	public String startVarieties(Model model) {
		model.addAttribute("varietyForm", new VarietyForm());
		return "select";
	}

}
