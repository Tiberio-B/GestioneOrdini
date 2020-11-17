package it.gestioneordini.service.categoria;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import it.gestioneordini.dao.EntityManagerUtil;
import it.gestioneordini.dao.IBaseDAO;
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
			
			// sarebbe meglio una query tipo "select sum(a.prezzo) from Articolo a join a.categorie c there c.id = :categoriaId, Double.class"
			
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
			IBaseDAO<Categoria> dao = getDAO();
			dao.setEntityManager(entityManager);
			
			articolo = entityManager.merge(articolo); // recupero l'articolo effettivo/aggiornato lato db
			categoria = entityManager.merge(categoria); // recupero la categoria effettiva/aggiornata lato db
	
			categoria.getArticoli().add(articolo); // eseguo l'operazione lato Java	
			articolo.getCategorie().add(categoria); // anche dal lato opposto
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
