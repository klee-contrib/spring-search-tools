package klee.contrib.spring.search.tools;

import java.time.LocalDate;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author gderuette
 *
 * @param <D>
 */
public interface IFieldEnum<D> {

	/**
	 * @return Nom de l'enum
	 */
	String name();

	/**
	 * @return Le nom du champ
	 */
	default String fieldName() {
		return StringUtils.constToLowerCamelCase(this.name());
	}

	public Class<?> getType();

	/**
	 * @return Le nom du champ
	 */
	default String completeFieldName() {
		return this.getClass().getName() + '.' + StringUtils.constToLowerCamelCase(this.name());
	}

	/**
	 * Renvoie le chemin vers le champ courant
	 * 
	 * @param <T>  classe de la recherche
	 * @param root élément de base de la recherche
	 * @return chemin vers le champ courant
	 */
	default <T> Path<T> getRoot(Root<D> root) {
		return root.get(this.fieldName());
	}

	/**
	 * @param dateBefore
	 * @return La spécification avec la condition inférieur ou égal
	 */
	default Specification<D> lessThanOrEqualTo(LocalDate dateBefore) {
		if (dateBefore == null)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> builder.lessThanOrEqualTo(getRoot(root), dateBefore);
	}

	/**
	 * @param value
	 * @return La spécification avec la condition inférieur ou égal
	 */
	default Specification<D> lessThanOrEqualTo(Integer value) {
		if (value == null || value < 0)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> builder.lessThanOrEqualTo(getRoot(root), value);
	}

	/**
	 * 
	 * Renvoie le champ join avec la classe du champ courant
	 * 
	 * @param <T>   classe du champ courant
	 * @param field sous-champ join sur lequel on veut appliquer le critère
	 * @return Champ join
	 */
	default <T> JoinedFieldEnum<D, T> getField(IFieldEnum<T> field) {
		return new JoinedFieldEnum<>(this, field, JoinType.INNER);
	}

	/**
	 * 
	 * Renvoie le champ join avec la classe du champ courant
	 * 
	 * @param <T>   classe du champ courant
	 * @param field sous-champ join sur lequel on veut appliquer le critère
	 * @return Champ join
	 */
	default <T> JoinedFieldEnum<D, T> getOptionalField(IFieldEnum<T> field) {
		return new JoinedFieldEnum<>(this, field, JoinType.LEFT);
	}

	/**
	 * @param <T>
	 * @param object
	 * @return La specification avec la condition d'égalité
	 */
	default <T> Specification<D> isEqual(T object) {
		if (object == null)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> builder.equal(getRoot(root), object);
	}

	/**
	 * @param <T>
	 * @param object
	 * @return La specification avec la condition d'inégalité
	 */
	default <T> Specification<D> isNotEqual(T object) {
		if (object == null)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> builder.notEqual(getRoot(root), object);
	}

	/**
	 * @param <T>
	 * @param object
	 * @return La specification avec la condition d'inégalité
	 */
	default Specification<D> isNotNull() {
		return (Specification<D>) (root, query, builder) -> builder.isNotNull(getRoot(root));
	}

	/**
	 * @param <T>
	 * @param object
	 * @return La specification avec la condition d'inégalité
	 */
	default Specification<D> isNull() {
		return (Specification<D>) (root, query, builder) -> builder.isNull(getRoot(root));
	}

	/**
	 * @param dateAfter
	 * @return La spécification avec la condition supérieur ou égal
	 */
	default Specification<D> greaterThanOrEqualTo(LocalDate dateAfter) {
		if (dateAfter == null)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> builder.greaterThanOrEqualTo(getRoot(root), dateAfter);
	}

	/**
	 * @param dateAfter
	 * @return La spécification avec la condition supérieur ou égal
	 */
	default Specification<D> greaterThanOrEqualTo(Integer value) {
		if (value == null || value < 0)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> builder.greaterThanOrEqualTo(getRoot(root), value);
	}

	/**
	 * @param patt
	 * @return La spécification avec la condition like de la chaine en entrée
	 */
	default Specification<D> upperContains(String patt) {
		if (patt == null)
			return Specification.where(null);
		String pattern = "%" + patt.toUpperCase() + "%";
		return (Specification<D>) (root, query, builder) -> builder.like(builder.upper(getRoot(root)), pattern);
	}

	/**
	 * @param patt
	 * @return La spécification avec la condition like de la chaine en entrée
	 */
	default Specification<D> upperStartWith(String patt) {
		if (patt == null)
			return Specification.where(null);
		String pattern = patt.toUpperCase() + "%";
		return (Specification<D>) (root, query, builder) -> builder.like(builder.upper(getRoot(root)), pattern);
	}

	/**
	 * @param <T>
	 * @param collection
	 * @return La spécification avec la condition du in construite avec les éléments
	 *         de la collection
	 */
	default <T> Specification<D> in(Iterable<T> collection) {
		if (collection == null)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> {
			var inClause = builder.in(getRoot(root));
			collection.forEach(inClause::value);
			return inClause;
		};
	}

	/**
	 * @param <T>
	 * @param collection
	 * @return La spécification avec la condition du not in construite avec les
	 *         éléments de la collection
	 */
	default <T> Specification<D> notIn(Iterable<T> collection) {
		if (collection == null)
			return Specification.where(null);
		return (Specification<D>) (root, query, builder) -> {
			var inClause = builder.in(getRoot(root));
			collection.forEach(inClause::value);
			return builder.not(inClause);
		};
	}
}
