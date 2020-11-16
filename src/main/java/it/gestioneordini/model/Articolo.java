package it.gestioneordini.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.gestioneordini.service.MyServiceFactory;

@Entity
@Table(name = "articolo")
public class Articolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "prezzo")
	private int prezzo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordine_id")
	private Ordine ordine;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "articolo_categoria", joinColumns = @JoinColumn(name = "articolo_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "ID"))
	private Set<Categoria> categorie = new HashSet<>(0);
	
	public Articolo() {}
	
	public Articolo(String nome, int prezzo) {
		this.nome = nome;
		this.prezzo = prezzo;
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
	
	public int getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	
	public Ordine getOrdine() {
		return ordine;
	}
	
	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}
	
	public Set<Categoria> getCategorie() {
		return categorie;
	}
	
	public void setCategorie(Set<Categoria> categorie) {
		this.categorie = categorie;
	}
	
	public void addCategoria(Categoria categoria) throws Exception {
		MyServiceFactory.getArticoloServiceInstance().aggiungi(this, categoria);
	}
	
	@Override
	public String toString() {
		String s = "Articolo " + id + ". " + nome + " $"+ prezzo +",00";
		s += (categorie.size() == 0) ? " (nessuna categoria)" : " |";
		for (Categoria categoria : categorie) {
			s += " "+ categoria.getNome() +" |";
		}
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Articolo) {
			Articolo that = (Articolo) obj;
			return this.getId() == that.getId() &&
					this.getNome() == that.getNome() &&
					this.getPrezzo() == that.getPrezzo();
		}
		return false;
	}
}
