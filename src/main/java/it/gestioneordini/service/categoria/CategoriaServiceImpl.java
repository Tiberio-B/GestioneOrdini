package it.gestioneordini.service.categoria;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.dao.MyDAOFactory;
import it.gestioneordini.dao.articolo.ArticoloDAO;
import it.gestioneordini.dao.categoria.CategoriaDAO;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;
import it.gestioneordini.service.GenericServiceImpl;

public class CategoriaServiceImpl extends GenericServiceImpl<Categoria> implements CategoriaService {

	@Override
	public List<Categoria> filtraPer(Ordine ordine) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		List<Categoria> ret = null;
		try {
			entityManager.getTransaction().begin();
			CategoriaDAO dao = (CategoriaDAO) getDAO();
			dao.setEntityManager(entityManager);
			ret = dao.findBy(ordine);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
		return ret;
	}

	@Override
	public int valore(Categoria input) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		int tot = 0;
		try {
			entityManager.getTransaction().begin();
			ArticoloDAO adao = MyDAOFactory.getArticoloDAOInstance();
			adao.setEntityManager(entityManager);
			
			// select sum(a.prezzo) from Articolo a join a.categorie c there c.id = :categoriaId, Double.class
			
			List<Articolo> articoliPresenti = adao.findBy(input);
			for (Articolo articoloPresente : articoliPresenti) {
				tot += articoloPresente.getPrezzo();
			}
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			tot = -1;
			e.printStackTrace();
			throw e;
		}
		return tot;
	}

	@Override
	public void aggiungi(Categoria categoria, Articolo articolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			CategoriaDAO dao = (CategoriaDAO) getDAO();
			dao.setEntityManager(entityManager);
			
			// Alberto fa così:
			entityManager.merge(articolo); // recupero l'articolo effettivo/aggiornato lato db
			entityManager.merge(categoria); // recupero la categoria effettiva/aggiornata lato db
			
			// Io farei con i Service:
			// aggiorna(categoria); 
			// MyServiceFactory.getArticoloServiceInstance().aggiorna(articolo);
			
			// Oppure con i DAO:
			// dao.update(categoria);
			// MyServiceFactory.getArticoloServiceInstance().getDao().update(articolo);

			// eseguo l'operazione lato Java		
			Set<Articolo> artInCategoria = categoria.getArticoli();
			artInCategoria.add(articolo);
			categoria.setArticoli(artInCategoria);
			
			// anche dal lato opposto
			Set<Categoria> catInArticolo = articolo.getCategorie();
			catInArticolo.add(categoria);
			articolo.setCategorie(catInArticolo);
			
			entityManager.merge(categoria);
			entityManager.merge(articolo);
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
