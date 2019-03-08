
package com.contact.contactwebapp.common.repo;

import com.contact.contactwebapp.common.dto.CustomQueryHolder;
import com.contact.contactwebapp.util.general.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;


import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractJpaRepo<T> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractJpaRepo.class);

	private Class<T> clazz;
	@Autowired
	@Qualifier(value = "masterEntityManagerFactory")
	protected EntityManager masterEntityManager;

	@Autowired
	@Qualifier(value = "slaveEntityManagerFactory")
	protected EntityManager slaveEntityManager;

	@Transactional(rollbackFor = Exception.class)
	public void create(final T entity) {
		masterEntityManager.persist(entity);
		// entityManager.flush();
		// entityManager.merge(entity);
	}

	public void delete(final T entity) {
		masterEntityManager.remove(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteById(final Integer entityId) {
		final T entity = findOne(entityId);
		delete(entity);
	}

	public <T> List<T> fetchByQuery(final String queryString, final Map<String, Object> inParamtersMap) {
		final Query query = slaveEntityManager.createQuery(queryString);
		for (final Entry<String, Object> currentEntry : inParamtersMap.entrySet()) {
			query.setParameter(currentEntry.getKey(), currentEntry.getValue());
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return slaveEntityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByQuery(final String query) {
		return slaveEntityManager.createQuery(query).getResultList();
	}

	public T findOne(final Integer id) {
		return slaveEntityManager.find(clazz, id);
	}

	public List<T> findPaginationByQuery(final String queryString, final Integer firstResult, final Integer maxResult) {
		final Query query = slaveEntityManager.createQuery(queryString);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@Transactional
	public List<T> updateInBulk(List<T> entityList) {
		Integer count = 0;
		List<T> savedEntities = new ArrayList<>(entityList.size());
		try {
			for (T entity : entityList) {
				savedEntities.add(masterEntityManager.merge(entity));
				if (++count % 10 == 0) {
					masterEntityManager.flush();
					masterEntityManager.clear();
				}
			}
		}
		catch (Exception exp) {
			logger.error("error occured in updating in bulk due to " + StringUtil.getStackTraceInStringFmt(exp));
		}
		return savedEntities;
	}

	@Transactional
	public <C extends Collection<T>> C updateInBulkForAnyCollection(C entityList) throws InstantiationException, IllegalAccessException {
		Integer count = 0;
		C savedEntities = (C) entityList.getClass().newInstance();
		try {
			for (T entity : entityList) {
				savedEntities.add(masterEntityManager.merge(entity));
				if (++count % 10 == 0) {
					masterEntityManager.flush();
					masterEntityManager.clear();
				}
			}
		}
		catch (Exception exp) {
			logger.error("error occured in updating in bulk due to " + StringUtil.getStackTraceInStringFmt(exp));
		}
		return savedEntities;
	}

	public List<T> findTopNElements(final String queryString, final Integer max) {
		final Query query = slaveEntityManager.createQuery(queryString);
		query.setMaxResults(max);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> runNativeQuery(final String queryString) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = slaveEntityManager.createNativeQuery(queryString);
			return nativeQuery.getResultList();
		}
		catch (final Exception e) {
			logger.error("Failed to run native query" + StringUtil.getStackTraceInStringFmt(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> runNativeQuery(final CustomQueryHolder queryHolder) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = slaveEntityManager.createNativeQuery(queryHolder.getQueryString());
			if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
				for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
					nativeQuery.setParameter(currentEntry.getKey(), currentEntry.getValue());
				}
			}
			return nativeQuery.getResultList();
		}
		catch (final Exception e) {
			logger.error("Failed to run native query" + StringUtil.getStackTraceInStringFmt(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> runNativeQuery(final CustomQueryHolder queryHolder, Map<Object, Integer> keyMap, Class<?> cls) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = slaveEntityManager.createNativeQuery(queryHolder.getQueryString());
			if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
				for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
					nativeQuery.setParameter(currentEntry.getKey(), currentEntry.getValue());
				}
			}
			return convertObjetListToClassList(nativeQuery.getResultList(), keyMap, cls);
		}
		catch (final Exception e) {
			logger.error("Failed to run native query" + StringUtil.getStackTraceInStringFmt(e));
		}
		return null;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	private <T> List<T> convertObjetListToClassList(List<Object[]> res, Map<Object, Integer> keyMap, Class<?> cls) throws Exception {
		List<T> r = new ArrayList<>();
		for (Object[] obj : res) {
			Object object = cls.getConstructor().newInstance();
			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {
				f.setAccessible(true);
				String keyName = f.getName();
				Integer index = keyMap.get(keyName);
				if (index != null) {
					f.set(object, obj[index]);
				}
			}
			r.add((T) object);
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> runNativeQueryWithPagination(final String queryString, final Integer firstResult, final Integer maxResult) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = slaveEntityManager.createNativeQuery(queryString);
			nativeQuery.setFirstResult(firstResult == null ? 0 : firstResult);
			nativeQuery.setMaxResults(maxResult == null ? 10 : maxResult);
			return nativeQuery.getResultList();
		}
		catch (final Exception e) {
			logger.error("Failed to run native query" + StringUtil.getStackTraceInStringFmt(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> runNativeQueryWithPagination(final CustomQueryHolder queryHolder, final Integer firstResult,
			final Integer maxResult) {
		// TODO Auto-generated method stub
		try {
			final Query nativeQuery = slaveEntityManager.createNativeQuery(queryHolder.getQueryString());
			if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
				for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
					nativeQuery.setParameter(currentEntry.getKey(), currentEntry.getValue());
				}
			}
			nativeQuery.setFirstResult(firstResult == null ? 0 : firstResult);
			nativeQuery.setMaxResults(maxResult == null ? 10 : maxResult);
			return nativeQuery.getResultList();
		}
		catch (final Exception e) {
			logger.error("Failed to run native query" + StringUtil.getStackTraceInStringFmt(e));
		}
		return null;
	}

	public Boolean runNativeQueryForUpdate(final String queryString) {
		// TODO Auto-generated method stub

		final Query nativeQuery = masterEntityManager.createNativeQuery(queryString);
		if (nativeQuery.executeUpdate() > 0) {
			return true;
		}

		return false;
	}

	public Boolean runNativeQueryForUpdate(final CustomQueryHolder queryHolder) {
		// TODO Auto-generated method stub

		final Query nativeQuery = masterEntityManager.createNativeQuery(queryHolder.getQueryString());
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
				nativeQuery.setParameter(currentEntry.getKey(), currentEntry.getValue());
			}
		}
		if (nativeQuery.executeUpdate() > 0) {
			return true;
		}

		return false;
	}

	public final void setClazz(final Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	@Transactional(rollbackFor = Exception.class)
	public T update(final T entity) {
		return masterEntityManager.merge(entity);
	}

}