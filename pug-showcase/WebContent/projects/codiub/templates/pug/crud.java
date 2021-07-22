import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.pugsource.bean.Utils;
import com.pugsource.bean.BaseBean;

public class crud extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<Empresas> empresasLazy;
    private Empresas empresas;

	public crud() {
		empresasLazy = new ArrayList<Empresas>();
		for (int i = 0; i < 5; i++) {
			Empresas empresas = new Empresas();
            empresas.setId(i);
            empresas.setNome("Nome " + i);
            empresas.setAtivo(true);
            empresasLazy.add(empresas);
		}
	}
    
	@Override
	protected void initEntity() {
		this.empresas = new Empresas();
        this.empresas.setId(this.empresasLazy.size());
        this.empresas.setAtivo(true);
	}   

	public String getTitle() {
		return labelEntity();
	}
	
	@Override
	protected String labelEntity() {
		return "crud";
	}	
    
	public void save() {        
        if (titlePanelForm.indexOf("Novo") == 0) {
        	this.empresasLazy.add(this.empresas);
			Utils.addFacesMessage("message_successfully_created", labelEntity());
        } else {
            Utils.addFacesMessage("message_successfully_updated", labelEntity());
        }    
	    setForm(false);
    }    

    public void delete() {
        this.empresasLazy.remove(this.empresas);
		Utils.addFacesMessage("message_successfully_deleted", labelEntity());        
    }    
    
	public List<Empresas> getEmpresasLazy() {
		return empresasLazy;
	}

	public void setEmpresasLazy(List<Empresas> empresasLazy) {
		this.empresasLazy = empresasLazy;
	}
    
	public Empresas getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}	    
    
    public class Empresas implements Serializable {
        
        private static final long serialVersionUID = 1L;
        private Integer id;
        private String nome;
        private String endereco;
        private String bairro;
        private String cidade;
        private String uf;
        private String telefones;
        private String email;
        private String site;
        private Boolean ativo;
        private String anotacoes;
        
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getTelefones() {
            return telefones;
        }

        public void setTelefones(String telefones) {
            this.telefones = telefones;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public Boolean getAtivo() {
            return ativo;
        }

        public void setAtivo(Boolean ativo) {
            this.ativo = ativo;
        }

        public String getAnotacoes() {
            return anotacoes;
        }

        public void setAnotacoes(String anotacoes) {
            this.anotacoes = anotacoes;
        }
        
    }
}