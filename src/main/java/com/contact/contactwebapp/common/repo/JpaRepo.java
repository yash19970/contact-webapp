package com.contact.contactwebapp.common.repo;

import com.contact.contactwebapp.common.dto.CustomQueryHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JpaRepo<K> extends AbstractJpaRepo<K> {

	public JpaRepo() {
		super();
	}

	public <T> void deleteById(final Integer id, final Class<T> clazz) {
		final T entity = masterEntityManager.find(clazz, id);
		masterEntityManager.remove(entity);

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(final Class<T> clazz) {
		return slaveEntityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findPaginationByQuery(final CustomQueryHolder queryHolder, Integer firstResult, Integer maxResult) {
		final Query query = slaveEntityManager.createQuery(queryHolder.getQueryString());
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
				query.setParameter(currentEntry.getKey(), currentEntry.getValue());
			}
		}
		query.setFirstResult(firstResult == null ? 0 : firstResult);
		query.setMaxResults(maxResult == null ? 10 : maxResult);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByQuery(final CustomQueryHolder queryHolder) {
		final Query query = slaveEntityManager.createQuery(queryHolder.getQueryString());
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final String currentKey : queryHolder.getInParamMap().keySet()) {
				query.setParameter(currentKey, queryHolder.getInParamMap().get(currentKey));
			}
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByQueryWithLimit(final CustomQueryHolder queryHolder, Integer limit) {
		final Query query = slaveEntityManager.createQuery(queryHolder.getQueryString());
		query.setMaxResults(limit);
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final String currentKey : queryHolder.getInParamMap().keySet()) {
				query.setParameter(currentKey, queryHolder.getInParamMap().get(currentKey));
			}
		}
		return query.getResultList();
	}

	public <T> T findByQueryAndReturnFirstElement(final CustomQueryHolder queryHolder) {
		final Query query = slaveEntityManager.createQuery(queryHolder.getQueryString());
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final String currentKey : queryHolder.getInParamMap().keySet()) {
				query.setParameter(currentKey, queryHolder.getInParamMap().get(currentKey));
			}
		}
		if(query.getResultList()!=null && !query.getResultList().isEmpty()) {
			return (T) query.getSingleResult();
		}
		return null;
		
	}

	public Boolean runQueryForUpdate(final CustomQueryHolder queryHolder) {
		// TODO Auto-generated method stub
		final Query query = masterEntityManager.createQuery(queryHolder.getQueryString());
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
				query.setParameter(currentEntry.getKey(), currentEntry.getValue());
			}
		}
		if (query.executeUpdate() > 0) {
			return true;
		}
		return false;
	}

	public <L, M> Map<L, M> findEntityMapByQuery(final CustomQueryHolder queryHolder) {
		final Map<L, M> resultMap = new HashMap<>();

		final Query query = slaveEntityManager.createQuery(queryHolder.getQueryString());
		if (queryHolder.getInParamMap() != null && !queryHolder.getInParamMap().isEmpty()) {
			for (final Entry<String, Object> currentEntry : queryHolder.getInParamMap().entrySet()) {
				query.setParameter(currentEntry.getKey(), currentEntry.getValue());
			}
		}

		for (final Map<String, Object> map : (List<Map<String, Object>>) query.getResultList()) {
			resultMap.put((L) map.get("0"), (M) map.get("1"));
		}
		return resultMap;
	}

	public <T> T findOne(final Integer id, final Class<T> clazz) {
		return slaveEntityManager.find(clazz, id);
	}

	@Override
	@Transactional
	public List<K> updateInBulk(List<K> entityList) {
		Integer count = 0;
		List<K> savedEntities = new ArrayList<>(entityList.size());
		try {
			for (K entity : entityList) {
				savedEntities.add(masterEntityManager.merge(entity));
				if (++count % 10 == 0) {
					masterEntityManager.flush();
					masterEntityManager.clear();
				}
			}
		}
		catch (Exception exp) {
		}
		return savedEntities;
	}

}