package knezija.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import knezija.models.User;

import org.springframework.stereotype.Repository;

/**
 * This class is used to access data for the User entity. Repository annotation
 * allows the component scanning support to find and configure the DAO wihtout
 * any XML configuration and also provide the Spring exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the method.
 * If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class Dao implements IDao {

	// ------------------------
	// PUBLIC METHODS
	// ------------------------

	@Override
	public <T> T findById(Class<T> entityClass, Long id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public <T> T update(T entity) {
		if (entityManager.contains(entity)) {
			entity = entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
		}

		return entity;
	}

	@Override
	public <T> void remove(T entity) {
		entityManager.remove(entity);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		String query = "from " + entityClass.getSimpleName();
		return entityManager.createQuery(query, entityClass).getResultList();
	}

	@Override
	public <T> T find(Class<T> entityClass,
			Map<String, Object> attributeValueByName) {
		return getFirst(findAll(entityClass, attributeValueByName));
	}

	private <T> T getFirst(List<T> list) {
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass,
			Map<String, Object> attributeValueByName) {
		String query = "from " + entityClass.getSimpleName() + " WHERE ";
		for (String attributeName : attributeValueByName.keySet()) {
			Object attributeValue = attributeValueByName.get(attributeName);
			query += attributeName + "=";
			if (attributeValue instanceof String) {
				attributeValue = "'" + attributeValue + "'";
			}
			query += attributeValue + " AND ";
		}
		query = query.substring(0, query.length() - 5);

		return entityManager.createQuery(query, entityClass).getResultList();
	}

	@Override
	public <T> T find(Class<T> entityClass, String fieldName, Object fieldValue) {
		return getFirst(findAll(entityClass, fieldName, fieldValue));
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, String fieldName,
			Object fieldValue) {
		Map<String, Object> fieldValueByName = new HashMap<>();
		fieldValueByName.put(fieldName, fieldValue);

		return findAll(entityClass, fieldValueByName);
	}
	
	@Override
	public <T> List<T> findAll(Class<T> entityClass, String fieldName1,
			Object fieldValue1, String fieldName2, Object fieldValue2) {
		Map<String, Object> fieldValueByName = new HashMap<>();
		fieldValueByName.put(fieldName1, fieldValue1);
		fieldValueByName.put(fieldName2, fieldValue2);

		return findAll(entityClass, fieldValueByName);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, String fieldName1,
			Object fieldValue1, String fieldName2, Object fieldValue2,
			String fieldName3, Object fieldValue3) {
		Map<String, Object> fieldValueByName = new HashMap<>();
		fieldValueByName.put(fieldName1, fieldValue1);
		fieldValueByName.put(fieldName2, fieldValue2);
		fieldValueByName.put(fieldName3, fieldValue3);

		return findAll(entityClass, fieldValueByName);
	}

	@Override
	public <T> void removeById(Class<T> entityClass, long id) {
		remove(findById(entityClass, id));
	}

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T> List<T> findAllWithAddition(Class<T> entityClass,
			String fieldName, Object fieldValue, String sqlAddition) {
		String quoteAddition = (fieldValue instanceof String) ? "'" : "";
		String query = "from " + entityClass.getSimpleName() + " WHERE "
				+ fieldName + "=" + quoteAddition + fieldValue + quoteAddition
				+ " " + sqlAddition;

		return entityManager.createQuery(query, entityClass).getResultList();
	}

} // class UserDao
