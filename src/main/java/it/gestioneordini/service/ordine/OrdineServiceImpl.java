package it.gestioneordini.service.ordine;

import java.util.List;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.dao.IBaseDAO;
import it.gestioneordini.dao.ordine.OrdineDAO;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.GenericServiceImpl;

public class OrdineServiceImpl extends GenericServiceImpl<Ordine> implements OrdineService {
	
	@Override
	public List<Ordine> filtraPer(Categoria categoria) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		List<Ordine> ret = null;
		try {
			entityManager.getTransaction().begin();
			OrdineDAO dao = (OrdineDAO) getDAO();
			dao.setEntityManager(entityManager);
			ret = dao.findBy(categoria);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
		return ret;
	}

	@Override
	public void aggiungi(Ordine ordine, Articolo articolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			IBaseDAO<Ordine> dao = getDAO();
			dao.setEntityManager(entityManager);
			
	
			articolo = entityManager.merge(articolo); // recupero l'articolo effettivo/aggiornato lato db
			ordine = entityManager.merge(ordine); // recupero l'ordine effettivo/aggiornato lato db
		
			ordine.getArticoli().add(articolo); // eseguo l'operazione lato Java
			articolo.setOrdine(ordine); // anche dal lato opposto
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
