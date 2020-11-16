package it.gestioneordini.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.IBaseDAO;
import it.gestioneordini.dao.EntityManagerUtil;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
	
	private IBaseDAO<T> tDAO;
	
	@Override
	public IBaseDAO<T> getDAO() {
		return tDAO;
	}
	
	@Override
	public void setDAO(IBaseDAO<T> tDAO) {
		this.tDAO = tDAO;
	}

	@Override
	public List<T> elenca() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			tDAO.setEntityManager(entityManager);
			return tDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public T carica(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			tDAO.setEntityManager(entityManager);
			return tDAO.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void aggiorna(T instance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			tDAO.setEntityManager(entityManager);
			tDAO.update(instance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void inserisci(T instance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			tDAO.setEntityManager(entityManager);
			tDAO.insert(instance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void rimuovi(T instance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			tDAO.setEntityManager(entityManager);
			tDAO.delete(instance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
