package knezija.persistence;

import java.util.List;
import java.util.Map;

import knezija.models.Krstenje;

public interface IDao {

	/**
	 * Persist a new instance or merge an existing entity instance in the
	 * database. In either case, the returned instance will be managed.
	 */
	<T> T update(T entity);

	/**
	 * Deletes the given entitiy from the database.
	 * 
	 * @param entity
	 */
	<T> void remove(T entity);

	/**
	 * Returns all the records of the given entity class(or table) in the
	 * database.
	 * 
	 * @param entityClass
	 * @return
	 */
	<T> List<T> findAll(Class<T> entityClass);

	/**
	 * Returns the record of the given class, with the given id. The returned
	 * record will be a single record since the id is the primary key.
	 */
	<T> T findById(Class<T> entityClass, Long id);

	/**
	 * Searches all the data of the given entity class and fetches a unique
	 * result, which corresponds to the given attribute Map. The given attribute
	 * map should contain the search criteria, by suplying the names of the
	 * wanted search parameters as Map keys, and the values of the wanted search
	 * parameters as Map values. The search parameters are entity fields.
	 * 
	 * The behaviour of this method is undefined if the search yields more than
	 * one result. If unsure of the number of results, please use
	 * {@link #findAll(Class, Map)}.
	 * 
	 * If no records match the query, returns null.
	 * 
	 * @param attributeValueByName
	 * @return
	 */
	<T> T find(Class<T> entityClass, Map<String, Object> fieldValueByName);

	/**
	 * Searches all the data of the given entity class and fetches all the
	 * records, which correspond to the given attribute Map. The given attribute
	 * map should contain the search criteria, by suplying the names of the
	 * wanted search parameters as Map keys, and the values of the wanted search
	 * parameters as Map values. The search parameters are entity parameters.
	 * 
	 * more than one result.
	 * 
	 * @param attributeValueByName
	 * @return
	 */
	<T> List<T> findAll(Class<T> entityClass,
			Map<String, Object> fieldValueByName);

	/**
	 * Searches all the data of the given entity class by a single field and
	 * fetches a unique result, which corresponds to the given entity field. The
	 * entity field is specified by its name and value.
	 * 
	 * The behaviour of this method is undefined if the search yields more than
	 * one result. If unsure of the number of results, please use
	 * {@link #findAll(Class, String, String)}.
	 * 
	 * If no records match the query, returns null.
	 * 
	 * @return
	 */
	<T> T find(Class<T> entityClass, String fieldName, Object fieldValue);

	/**
	 * Searches all the data of the given entity class by a single field and
	 * fetches all the records, which correspond to the given entity field. The
	 * entity field is specified by its name and value.
	 * 
	 * @return
	 */
	<T> List<T> findAll(Class<T> entityClass, String fieldName,
			Object fieldValue);

	<T> void removeById(Class<T> entityClass, long id);
}
