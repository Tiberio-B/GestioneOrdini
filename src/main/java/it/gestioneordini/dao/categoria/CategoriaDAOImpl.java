package it.gestioneordini.dao.categoria;

import java.util.List;

import javax.persistence.TypedQuery;

import it.gestioneordini.dao.GenericDAOImpl;
import it.gestioneordini.model.Categoria;
import it.gestioneordini.model.Ordine;

public class CategoriaDAOImpl extends GenericDAOImpl<Categoria> implements CategoriaDAO {

	@Override
	public Class<Categoria> getTClass() {
		return Categoria.class;
	}

	@Override
	public String getTName() {
		return "Categoria";
	}

	@Override
	public List<Categoria> findBy(Ordine ordine) {
		TypedQuery<Categoria> query = entityManager.createQuery("select c FROM Categoria c join (articolo_categoria ac join (Articolo a join Ordine o)) where o = :ordine", Categoria.class);
		query.setParameter("ordine", ordine);
		return query.getResultList();
	}
}
