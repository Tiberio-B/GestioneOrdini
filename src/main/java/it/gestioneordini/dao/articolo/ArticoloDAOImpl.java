package it.gestioneordini.dao.articolo;

import java.util.List;

import javax.persistence.TypedQuery;

import it.gestioneordini.dao.GenericDAOImpl;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;

public class ArticoloDAOImpl extends GenericDAOImpl<Articolo> implements ArticoloDAO {
	
	@Override
	public Class<Articolo> getTClass() {
		return Articolo.class;
	}

	@Override
	public String getTName() {
		return "Articolo";
	}
	
	@Override
	public List<Articolo> findBy(Categoria categoria) {
		TypedQuery<Articolo> query = entityManager.createQuery("select a FROM Articolo a join a.categorie c where c = :categoria", Articolo.class);
		query.setParameter("categoria", categoria);
		return query.getResultList();
	}
}
