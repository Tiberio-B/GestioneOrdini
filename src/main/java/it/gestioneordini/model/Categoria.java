package it.gestioneordini.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import it.gestioneordini.service.MyServiceFactory;

@Entity
@Table(name = "categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorie")
	private Set<Articolo> articoli  = new HashSet<>(0);
	
	public Categoria() {}
	
	public Categoria(String nome) {
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Set<Articolo> getArticoli() {
		return articoli;
	}
	
	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}
	
	public void addArticolo(Articolo articolo) throws Exception {
		MyServiceFactory.getCategoriaServiceInstance().aggiungi(this, articolo);
	}
	
	@Override
	public String toString() {
		String s = "Categoria " + id + ". " + nome;
		s += (articoli.size() == 0) ? " (vuota)" : ":\n";
		for (Articolo articolo : articoli) {
			s += "\t"+articolo.toString()+"\n";
		}
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Categoria) {
			Categoria that = (Categoria) obj;
			return this.getNome() == that.getNome();
		}
		return false;
	}
}
