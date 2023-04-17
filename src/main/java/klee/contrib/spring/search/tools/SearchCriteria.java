package klee.contrib.spring.search.tools;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author gderuette
 *
 * @param <D> classe du filtre
 */
public class SearchCriteria<D extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer size;
	private D filter;

	private String sortProperty;
	private Direction sortDirection;

	public SearchCriteria(D d) {
		this.filter = d;
	}

	/**
	 * @param page
	 * @param size
	 */
	SearchCriteria(int page, int size) {
		this.page = page;
		this.size = size;
	}

	private Sort getSort() {
		if (this.sortProperty != null && this.sortDirection != null) {
			return Sort.by(this.sortDirection, this.sortProperty);
		}
		return null;
	}

	private PageRequest getPageAndSize() {
		if (this.page != null && this.size != null) {
			return PageRequest.of(page, size);
		} else if (this.page != null) {
			return PageRequest.ofSize(this.page);
		}
		return null;
	}

	/**
	 * @return le pageable correspondant à ce critère
	 */
	public Pageable getPageable() {
		Sort sort = this.getSort();
		PageRequest pageable = this.getPageAndSize();
		if (sort != null && pageable != null) {
			return pageable.withSort(sort);
		} else if (pageable != null) {
			return pageable;
		} else if (sort != null) {
			return SortedUnpaged.of(sort);
		}
		return Pageable.unpaged();
	}

	/**
	 * 
	 */
	public SearchCriteria() {
		super();
	}

	/**
	 * @param page
	 * @param size
	 * @param filter
	 * @param sortProperty
	 * @param sortDirection
	 */
	public SearchCriteria(Integer page, Integer size, D filter, String sortProperty, Direction sortDirection) {
		super();
		this.page = page;
		this.size = size;
		this.filter = filter;
		this.sortProperty = sortProperty;
		this.sortDirection = sortDirection;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the filter
	 */
	public D getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(D filter) {
		this.filter = filter;
	}

	/**
	 * @return the sortProperty
	 */
	public String getSortProperty() {
		return sortProperty;
	}

	/**
	 * @param sortProperty the sortProperty to set
	 */
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	/**
	 * @return the sortDirection
	 */
	public Direction getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection the sortDirection to set
	 */
	public void setSortDirection(Direction sortDirection) {
		this.sortDirection = sortDirection;
	}
}
