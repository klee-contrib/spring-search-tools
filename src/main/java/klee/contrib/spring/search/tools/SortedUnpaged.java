package klee.contrib.spring.search.tools;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author gderuette
 *
 */
public class SortedUnpaged implements Pageable {

	private final Sort sort;

	/**
	 * @param sort
	 */
	private SortedUnpaged(Sort sort) {
		this.sort = sort;
	}

	/**
	 * @param sort
	 * @return
	 */
	public static SortedUnpaged of(Sort sort) {
		return new SortedUnpaged(sort);
	}

	/**
	 *
	 */
	public Sort getSort() {
		return sort;
	}

	@Override
	public int getPageNumber() {
		return Pageable.unpaged().getPageNumber();
	}

	@Override
	public int getPageSize() {
		return Pageable.unpaged().getPageSize();
	}

	@Override
	public long getOffset() {
		return Pageable.unpaged().getOffset();
	}

	@Override
	public Pageable first() {
		return Pageable.unpaged().first();
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return Pageable.unpaged().withPage(pageNumber);
	}

	@Override
	public Pageable next() {
		return Pageable.unpaged().next();
	}

	@Override
	public Pageable previousOrFirst() {
		return Pageable.unpaged().previousOrFirst();
	}

	@Override
	public boolean hasPrevious() {
		return Pageable.unpaged().hasPrevious();
	}

}
