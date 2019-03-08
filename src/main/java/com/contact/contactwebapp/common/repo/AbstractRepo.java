/**
 *
 */
package com.contact.contactwebapp.common.repo;

import com.contact.contactwebapp.util.general.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractRepo<T> {

	private static Logger logger = LogManager.getLogger(AbstractRepo.class);

	private static final String UNCHECKED = "unchecked";

	private Class<T> clazz;

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	public void create(final T entity) {
		entityManager.persist(entity);
	}

	public void delete(final T entity) {
		entityManager.remove(entity);
	}

	public void deleteById(final Integer entityId) {
		final T entity = findOne(entityId);
		delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Object> fetchByCoreNativeQuery(final String queryString) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = entityManager.createNativeQuery(queryString);
			return nativeQuery.getResultList();
		}
		catch (final Exception e) {
			logger.error("unable to run native query due to " + e.getCause());
		}
		return null;
	}

	@SuppressWarnings(UNCHECKED)
	public List<Object> fetchByCoreNativeQuery(final String queryString, final Map<String, Object> inParamtersMap) {
		try {
			final Query nativeQuery = entityManager.createNativeQuery(queryString);
			for (final Entry<String, Object> currentEntry : inParamtersMap.entrySet()) {
				nativeQuery.setParameter(currentEntry.getKey(), currentEntry.getValue());
			}
			return nativeQuery.getResultList();
		}
		catch (final Exception e) {
			logger.error(StringUtil.getStackTraceInStringFmt(e));
		}
		return null;
	}

	@SuppressWarnings(UNCHECKED)
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	@SuppressWarnings(UNCHECKED)
	public List<T> findByNativeQuery(final String nativeQuery, final Map<String, Object> inParamtersMap,
			final Class<T> clazz) {
		final Query query = entityManager.createNativeQuery(nativeQuery, clazz);
		for (final Entry<String, Object> currentEntry : inParamtersMap.entrySet()) {
			query.setParameter(currentEntry.getKey(), currentEntry.getValue());
		}
		return query.getResultList();
	}

	@SuppressWarnings(UNCHECKED)
	@Transactional(readOnly = true)
	public List<T> findByQuery(final String query) {
		return entityManager.createQuery(query).getResultList();
	}

	@SuppressWarnings(UNCHECKED)
	public List<T> findByQuery(final String queryString, final Map<String, Object> inParamtersMap) {
		final Query query = entityManager.createQuery(queryString);
		for (final Entry<String, Object> currentEntry : inParamtersMap.entrySet()) {
			query.setParameter(currentEntry.getKey(), currentEntry.getValue());
		}
		return query.getResultList();
	}

	public T findOne(final Integer id) {
		return entityManager.find(clazz, id);
	}

	@SuppressWarnings(UNCHECKED)
	public List<T> findPaginationByQuery(final String queryString, final Integer firstResult, Integer maxResult) {
		final Query query = entityManager.createQuery(queryString);
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResult == null) {
			maxResult = 10;
		}
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public Integer insertInBatch(final List<T> entityList) {
		Integer count = 0;
		try {
			for (final T entity : entityList) {
				entityManager.persist(entity);
				if (++count % 20 == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
		catch (final Exception exp) {
			exp.printStackTrace();
		}
		return count;
	}

	@Transactional
	public Integer insertInBulk(final List<T> entityList) {
		Integer count = 0;
		try {
			for (final T entity : entityList) {
				entityManager.persist(entity);
				if (++count % 10 == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
		catch (final Exception exp) {
			exp.printStackTrace();
		}
		return count;
	}

	@Transactional
	public Integer runNativeQueryForUpdate(final String queryString) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = entityManager.createNativeQuery(queryString);
			final int updatedRows = nativeQuery.executeUpdate();
			return updatedRows;
		}
		catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Integer runNativeQueryForUpdate(final String queryString, final Map<String, Object> inParamtersMap) {
		try {
			final Query nativeQuery = entityManager.createNativeQuery(queryString);
			for (final Entry<String, Object> currentEntry : inParamtersMap.entrySet()) {
				nativeQuery.setParameter(currentEntry.getKey(), currentEntry.getValue());
			}
			final int updatedRows = nativeQuery.executeUpdate();
			return updatedRows;
		}
		catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	public final void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	@Transactional
	public T update(final T entity) {
		return entityManager.merge(entity);
	}

	@Transactional
	public List<T> updateInBulk(final List<T> entityList) {
		Integer count = 0;
		final List<T> savedEntities = new ArrayList<>(entityList.size());
		try {
			for (final T entity : entityList) {
				savedEntities.add(entityManager.merge(entity));
				if (++count % 10 == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
		catch (final Exception exp) {
			exp.printStackTrace();
		}
		return savedEntities;
	}

}
