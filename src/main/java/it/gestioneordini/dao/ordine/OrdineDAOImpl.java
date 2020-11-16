package it.gestioneordini.dao.ordine;

import java.util.List;

import javax.persistence.TypedQuery;

import it.gestioneordini.dao.GenericDAOImpl;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;

public class OrdineDAOImpl extends GenericDAOImpl<Ordine> implements OrdineDAO {

	@Override
	public Class<Ordine> getTClass() {
		return Ordine.class;
	}

	@Override
	public String getTName() {
		return "Ordine";
	}

	@Override
	public List<Ordine> findBy(Categoria categoria) {
		TypedQuery<Ordine> query = entityManager.createQuery("select distinct o FROM Ordine o join o.articoli a join a.categoria c where c.id = :categoriaId", Ordine.class);
		query.setParameter("categoriaId", categoria.getId());
		return query.getResultList();
	}
}
