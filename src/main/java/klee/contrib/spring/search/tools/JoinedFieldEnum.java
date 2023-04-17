package klee.contrib.spring.search.tools;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * @author gderuette
 *
 * @param <D>
 * @param <E>
 */
public class JoinedFieldEnum<D, E> implements IFieldEnum<D> {
	private final IFieldEnum<D> join;
	private final IFieldEnum<E> field;
	private final JoinType joinType;

	/**
	 * @param join
	 * @param field
	 */
	public JoinedFieldEnum(IFieldEnum<D> join, IFieldEnum<E> field) {
		super();
		this.join = join;
		this.field = field;
		this.joinType = JoinType.INNER;
	}

	/**
	 * @param join
	 * @param field
	 */
	public JoinedFieldEnum(JoinedFieldEnum<D, E> join, IFieldEnum<E> field) {
		super();
		this.join = join;
		this.field = field;
		this.joinType = JoinType.INNER;
	}

	/**
	 * @param join
	 * @param field
	 * @param joinType
	 */
	public JoinedFieldEnum(JoinedFieldEnum<D, E> join, IFieldEnum<E> field, JoinType joinType) {
		super();
		this.join = join;
		this.field = field;
		this.joinType = joinType;
	}

	/**
	 * @param join
	 * @param field
	 * @param joinType
	 */
	public JoinedFieldEnum(IFieldEnum<D> join, IFieldEnum<E> field, JoinType joinType) {
		super();
		this.join = join;
		this.field = field;
		this.joinType = joinType;
	}

	@Override
	public String name() {
		return field.name();
	}

	/**
	 * @param root
	 * @return
	 */
	public Join<D, E> join(From<D, D> root) {
		if (this.join instanceof JoinedFieldEnum jf) {
			root = jf.join(root);
		}

		return root.join(join.fieldName(), this.joinType);
	}

	@Override
	public <T> Path<T> getRoot(Root<D> root) {
		return this.join(root).get(field.fieldName());
	}

	@Override
	public Class<?> getType() {
		return this.field.getType();
	}

	/**
	 * @return the join
	 */
	public IFieldEnum<D> getJoin() {
		return join;
	}

	/**
	 * @return the field
	 */
	public IFieldEnum<E> getField() {
		return field;
	}

	/**
	 * @return the joinType
	 */
	public JoinType getJoinType() {
		return joinType;
	}

}
