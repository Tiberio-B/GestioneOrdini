package it.gestioneordini.service.articolo;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.dao.articolo.ArticoloDAO;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.service.GenericServiceImpl;

public class ArticoloServiceImpl extends GenericServiceImpl<Articolo> implements ArticoloService {

	@Override
	public void aggiungi(Articolo articolo, Categoria categoria) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			ArticoloDAO dao = (ArticoloDAO) getDAO();
			dao.setEntityManager(entityManager);
			
			// Alberto faceva così:
			// articolo = entityManager.merge(articolo);
			// categoria = entityManager.merge(categoria);
			
			aggiorna(articolo); // recupero l'articolo effettivo/aggiornato lato db
			
			// MyServiceFactory.getCategoriaServiceInstance().aggiorna(categoria);
			
			articolo.getCategorie().add(categoria); // eseguo l'operazione lato Java
			
			// dao.update(articolo); // la consistenza lato db viene mantenuta automaticamente
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
