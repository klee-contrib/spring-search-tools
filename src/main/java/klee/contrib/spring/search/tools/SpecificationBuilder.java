package klee.contrib.spring.search.tools;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationBuilder<T> implements Specification<T> {

	/**
	 * @param specification
	 * @param distinct
	 */
	public SpecificationBuilder(Specification<T> specification, boolean distinct) {
		super();
		this.specification = specification;
		this.distinct = distinct;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8385242638469153612L;
	private Specification<T> specification;
	private boolean distinct = false;

	/**
	 * Construit une nouvelle instance de Specification Builder
	 */
	public SpecificationBuilder() {
		this.specification = Specification.where(null);
	}

	/**
	 *
	 */
	@Override
	public SpecificationBuilder<T> and(Specification<T> spec) {
		this.specification = this.specification.and(spec);
		return this;
	}

	@Override
	public SpecificationBuilder<T> or(Specification<T> spec) {
		this.specification = this.specification.or(spec);
		return this;
	}

	/**
	 * 
	 */
	public void distinct() {
		this.distinct = true;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		query.distinct(distinct);
		return this.specification.toPredicate(root, query, criteriaBuilder);
	}
}