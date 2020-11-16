package it.gestioneordini.service.ordine;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.dao.categoria.CategoriaDAO;
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
			OrdineDAO dao = (OrdineDAO) getDAO();
			dao.setEntityManager(entityManager);
			
			// Alberto fa così:
			entityManager.merge(articolo); // recupero l'articolo effettivo/aggiornato lato db
			entityManager.merge(ordine); // recupero l'ordine effettivo/aggiornato lato db
			
			// Io farei con i Service:
			// aggiorna(ordine); 
			// MyServiceFactory.getArticoloServiceInstance().aggiorna(articolo);
			
			// Oppure con i DAO:
			// dao.update(ordine);
			// MyServiceFactory.getArticoloServiceInstance().getDao().update(articolo);
			
//			for (Articolo artInOrdine : ordine.getArticoli()) {
//				if (articolo.equals(artInOrdine)) { throw new Exception("Articolo già presente nell'ordine");
//			}
			
			// eseguo l'operazione lato Java
			Set<Articolo> artInOrdine = ordine.getArticoli();
			artInOrdine.add(articolo);
			ordine.setArticoli(artInOrdine);
			
			articolo.setOrdine(ordine); // anche dal lato opposto
			
			entityManager.merge(ordine);
			entityManager.merge(articolo);
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
