package it.gestioneordini.service.articolo;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.dao.articolo.ArticoloDAO;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.GenericServiceImpl;

public class ArticoloServiceImpl extends GenericServiceImpl<Articolo> implements ArticoloService {

	@Override
	public void aggiungi(Articolo articolo, Categoria categoria) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			ArticoloDAO dao = (ArticoloDAO) getDAO();
			dao.setEntityManager(entityManager);

			articolo = entityManager.merge(articolo);
			categoria = entityManager.merge(categoria);
			
			articolo.getCategorie().add(categoria); // eseguo l'operazione lato Java
			categoria.getArticoli().add(articolo); // anche dal lato opposto
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void aggiungi(Articolo articolo, Ordine ordine) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			ArticoloDAO dao = (ArticoloDAO) getDAO();
			dao.setEntityManager(entityManager);
			
			articolo = entityManager.merge(articolo);
			ordine = entityManager.merge(ordine);
			
			articolo.setOrdine(ordine); // eseguo l'operazione lato Java
			ordine.getArticoli().add(articolo); // anche dal lato opposto
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
