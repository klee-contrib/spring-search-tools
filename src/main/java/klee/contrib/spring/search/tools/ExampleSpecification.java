package klee.contrib.spring.search.tools;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.repository.query.EscapeCharacter;

/**
 * @author gderuette
 *
 * @param <T>
 */
public class ExampleSpecification<T> extends SpecificationBuilder<T> {

	private static final long serialVersionUID = 1L;

	private transient Example<T> example;

	/**
	 * Creates new {@link ExampleSpecification}.
	 *
	 * @param example
	 * @param escapeCharacter
	 */
	public ExampleSpecification(@NotNull T example) {
		this.example = Example.of(example);
	}

	/**
	 *
	 */
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return QueryByExamplePredicateBuilder.getPredicate(root, cb, example, EscapeCharacter.DEFAULT);
	}
}