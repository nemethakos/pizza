package pizza.recommender.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pizza.recommender.entity.Pizza;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
	
	@Query("select p from Pizza p order by p.price desc")
	Stream<Pizza> findAllAsStream();
	
	@Override
	@Query("select p from Pizza p order by p.price desc")
	List<Pizza> findAll();

	Optional<Pizza> findByName(String name);
	
}
