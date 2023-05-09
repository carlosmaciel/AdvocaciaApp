package br.com.advocacia.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "processos")
public class Processo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "numero_do_processo", unique = true)
	private Long numero;
	
	@ManyToOne
    @JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy="processo")
    private Set<IntimacaoPublicacao> intimacaopublicacao;
	
	@Column(name = "status")
	private Integer status;

	public Processo() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<IntimacaoPublicacao> getIntimacaopublicacao() {
		return intimacaopublicacao;
	}

	public void setIntimacaopublicacao(Set<IntimacaoPublicacao> intimacaopublicacao) {
		this.intimacaopublicacao = intimacaopublicacao;
	}
	
	@Override
    public boolean equals(Object other) {
        return (other instanceof Processo) && (id != null)
            ? id.equals(((Processo) other).id)
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
            ? (this.getClass().hashCode() + id.hashCode())
            : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }
	
}
