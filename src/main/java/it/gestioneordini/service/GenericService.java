package it.gestioneordini.service;

import java.util.List;

import it.gestioneordini.dao.IBaseDAO;
import it.gestioneordini.model.Articolo;
import it.gestioneordini.model.Categoria;

public interface GenericService<T> {
	
	public IBaseDAO<T> getDAO();
	
	public void setDAO(IBaseDAO<T> tDAO); //per injection

	public List<T> elenca() throws Exception;

	public T carica(Long id) throws Exception;

	public void aggiorna(T instance) throws Exception;

	public void inserisci(T instance) throws Exception;

	public void rimuovi(T instance) throws Exception;
}
