package it.gestioneordini.dao;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class GenericDAOImpl<T> implements IBaseDAO<T> {
	
	protected EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public abstract Class<T> getTClass();
	
	public abstract String getTName();

	@Override
	public List<T> list() throws Exception {
		return (List<T>) entityManager.createQuery("from "+getTName(),getTClass()).getResultList();
	}

	@Override
	public T get(Long id) throws Exception {
		return (T) entityManager.find(getTClass(), id);
	}

	@Override
	public void update(T input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(T input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(input);
	}

	@Override
	public void delete(T input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

}
