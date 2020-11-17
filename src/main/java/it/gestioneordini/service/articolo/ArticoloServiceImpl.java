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
			
			// Alberto fa così:
			articolo = entityManager.merge(articolo);
			categoria = entityManager.merge(categoria);
			
			// Io farei con i Service:
			// aggiorna(articolo); 
			// MyServiceFactory.getCategoriaServiceInstance().aggiorna(categoria);
						
			// Oppure con i DAO:
			// dao.update(articolo);
			// MyDAOFactory.getCategoriaDAOInstance().update(categoria);
			
			articolo.getCategorie().add(categoria); // eseguo l'operazione lato Java
			// categoria.getArticoli().add(articolo) // anche dal lato opposto
			
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
			
			// Alberto faceva così:
			articolo = entityManager.merge(articolo);
			ordine = entityManager.merge(ordine);
			
			// aggiorna(articolo); // recupero l'articolo effettivo/aggiornato lato db
			
			// MyServiceFactory.getCategoriaServiceInstance().aggiorna(categoria);
			
			articolo.setOrdine(ordine); // eseguo l'operazione lato Java
			
			// dao.update(articolo); // la consistenza lato db viene mantenuta automaticamente
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
