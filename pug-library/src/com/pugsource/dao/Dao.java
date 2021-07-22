package com.pugsource.dao;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * Class DAO (Data Access Object)
 * 
 * Classe responsável pela manipulação de acessos ao banco de dados
 * 
 * @version 1.0
 * 
 * @author Alberto Henrique Sousa
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class Dao<T> {

	private Session session;
	private final Class<?> type;

	/**
	 * Método construtor.
	 * 
	 * @param session Sessão do Hibernate
	 * @param type Classe de tabela mapeada pelo Hibernate
	 */
	public Dao(Session session, Class<?> type) {
		this.session = session;
		this.type = type;
	}

	/**
	 * Retorna sessão do Hibernate.
	 * 
	 * @return Retorna a instância da sessão no Hibernate
	 */
	protected Session getSession() {
		return session;
	}	

	/**
	 * Salva objeto informando ao Hibernate que propague o estado de uma instância
	 * para o banco de dados criando uma nova linha de banco de dados.
	 * 
	 * @param u
	 */
	public void save(T u) {
		this.session.save(u);
	}

	/**
	 * Salva objeto informando ao Hibernate que propague o estado de uma instância
	 * para o banco de dados criando uma nova linha de banco de dados se a instância for uma
	 * nova instância transiente ou atualizando a linha existente se a instância for uma instância desligada.
	 * 
	 * @param u
	 */
	public void saveOrUpdate(T u) {
		this.session.saveOrUpdate(u);
	}

	/**
	 * Remove instância do objeto e exclui registro do banco de dados.
	 * 
	 * @param u
	 */
	public void remove(T u) {
		this.session.delete(u);	
	}

	/**
	 * Funde uma instância desligada para dentro de uma instância persistente carregada implicitamente, 
	 * salva o objeto de instância no banco de dados, criando ou atualizando os registros quando necessário. 
	 * 
	 * @param u
	 */
	public void merge(T u) {
		this.session.merge(u);
	}

	/**
	 * Persiste a instância do objeto no banco de dados, salva o objeto de instância no banco de dados, 
	 * criando ou atualizando os registros quando necessário e realiza uma sincronização. Indicado para
	 * uso de inserção de registros em tabelas com autoincremento, para retorno do valor atribuído 
	 * automaticamente pelo banco.
	 * 
	 * @param u
	 */
	public void persist(T u) {
		this.session.persist(u);
	}	

	/**
	 * Retorna uma lista de objetos de leitura dos registros do banco de dados.
	 * 
	 * @return Retorna um List com objetos de leitura da tabela
	 */
	public List<T> listAll() {
		@SuppressWarnings("unchecked")
		List<T> ret = this.session.createCriteria(this.type).list(); 
		return ret;
	} 

	/**
	 * Retorna uma lista de objetos de leitura dos registros do banco de dados conforme filtro, ordenação e
	 * paginação de registros.
	 * 
	 * @param metadata Map para armazenar totalizadores 
	 * @param table Tabelas a serem pesquisadas
	 * @param filter Expressão de filtro
	 * @param orderField Campos de ordenação
	 * @param orderDir Direcionamento da ordenação ASC ou DESC
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @return Retorna um List com objetos de leitura da tabela
	 */
	public List<T> findAll(Map<String, Object> metadata, String table, String filter, String orderField, 
			String orderDir, int pageNum, int pageSize ) {
		return findAll(metadata, table, filter, orderField, orderDir, pageNum, pageSize, null);				
	}
	
	/**
	 * Retorna uma lista de objetos de leitura dos registros do banco de dados conforme filtro, ordenação e
	 * paginação de registros.
	 * 
	 * @param metadata Map para armazenar totalizadores 
	 * @param table Tabelas a serem pesquisadas
	 * @param filter Expressão de filtro
	 * @param orderField Campos de ordenação
	 * @param orderDir Direcionamento da ordenação ASC ou DESC
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @return Retorna um List com objetos de leitura da tabela
	 */
	public List<T> findAllNative(Map<String, Object> metadata, String table, String filter, String orderField, 
			String orderDir, int pageNum, int pageSize ) {
		return findAllNative(metadata, table, filter, orderField, orderDir, pageNum, pageSize, null);				
	}
	
	/**
	 * Retorna uma lista de objetos de leitura dos registros do banco de dados conforme filtro, ordenação e
	 * paginação de registros.
	 * 
	 * @param metadata Map para armazenar totalizadores 
	 * @param table Tabelas a serem pesquisadas
	 * @param filter Expressão de filtro
	 * @param orderField Campos de ordenação
	 * @param orderDir Direcionamento da ordenação ASC ou DESC
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @param parameters Lista de parâmetros
	 * @return Retorna um List com objetos de leitura da tabela
	 */
	public List<T> findAll(Map<String, Object> metadata, String table, String filter, String orderField, 
			String orderDir, int pageNum, int pageSize, ArrayList<ArrayList<Object>> parameters) {
		String sqlQuery = "from " + table;
		String sqlWhere = (filter.trim().length() != 0) ? " where " + filter : "";
		String sqlOrder = (orderField.trim().length() != 0) ? " order by " + orderField + " " + orderDir : "";
		Query query = this.session.createQuery(sqlQuery + sqlWhere + sqlOrder);
		queryParameters(parameters, query);
		if (metadata != null && !(pageNum == 0 && pageSize == 0)) {
			Query queryCount = this.session.createQuery("select count(*) from " + table + sqlWhere);
			queryParameters(parameters, queryCount);
			Object count = queryCount.uniqueResult();
			metadata.put("recordCount", count != null ? Integer.valueOf(((Long) count).toString()) : 0);
		}	
		if (pageNum >= 0 && pageSize > 0)
		{
			query.setFirstResult(pageNum * pageSize);
			query.setMaxResults(pageSize);			
		}	

		@SuppressWarnings("unchecked")
		List<T> ret = query.list();
		
		if (metadata != null && !(pageNum == 0 && pageSize == 0))
			metadata.put("recordCountLimit", ret.size());
		
		return ret;							
	}

	/**
	 * Retorna uma lista de objetos de leitura dos registros do banco de dados conforme filtro, ordenação e
	 * paginação de registros.
	 * 
	 * @param metadata Map para armazenar totalizadores 
	 * @param table Tabelas a serem pesquisadas
	 * @param filter Expressão de filtro
	 * @param orderField Campos de ordenação
	 * @param orderDir Direcionamento da ordenação ASC ou DESC
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @param parameters Lista de parâmetros
	 * @return Retorna um List com objetos de leitura da tabela
	 */
	public List<T> findAllNative(Map<String, Object> metadata, String table, String filter, String orderField, 
			String orderDir, int pageNum, int pageSize, ArrayList<ArrayList<Object>> parameters) {
		String sqlQuery = "from " + table;
		String sqlWhere = (filter.trim().length() != 0) ? " where " + filter : "";
		String sqlOrder = (orderField.trim().length() != 0) ? " order by " + orderField + " " + orderDir : "";
		Query query = this.session.createSQLQuery(sqlQuery + sqlWhere + sqlOrder);
		queryParameters(parameters, query);
		if (metadata != null && !(pageNum == 0 && pageSize == 0)) {
			Query queryCount = this.session.createSQLQuery("select count(*) from " + table + sqlWhere);
			queryParameters(parameters, queryCount);
			Object count = queryCount.uniqueResult();
			metadata.put("recordCount", count != null ? Integer.valueOf(((Long) count).toString()) : 0);
		}	
		if (pageNum >= 0 && pageSize > 0)
		{
			query.setFirstResult(pageNum * pageSize);
			query.setMaxResults(pageSize);			
		}	

		@SuppressWarnings("unchecked")
		List<T> ret = query.list();
		
		if (metadata != null && !(pageNum == 0 && pageSize == 0))
			metadata.put("recordCountLimit", ret.size());
		
		return ret;							
	}

	public void queryParameters(ArrayList<ArrayList<Object>> parameters,
			Query query) {
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {				
				query.setParameter((String) parameters.get(i).get(0), (Object) parameters.get(i).get(1), (Type) parameters.get(i).get(2));			
			}
		}
	}

	/**
	 * Retorna uma lista de objetos de leitura de registros do banco de dados conforme expressão SQL e
	 * paginação de registros 
	 * 
	 * @param metadata Map para armazenar totalizadores
	 * @param sql Expressão SQL 
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @return Retorna um list com objetos de leitura da tabela
	 */
	public List<T> findAllSQL(Map<String, Object> metadata, String sql, int pageNum, int pageSize ) {
		return findAllSQL(metadata, sql, pageNum, pageSize, null);				
	}
	
	/**
	 * Retorna uma lista de objetos de leitura de registros do banco de dados conforme expressão SQL e
	 * paginação de registros 
	 * 
	 * @param metadata Map para armazenar totalizadores
	 * @param sql Expressão SQL 
	 * @param sqlCount Expressão SQL 
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @return Retorna um list com objetos de leitura da tabela
	 */
	public List<T> findAllSQL(Map<String, Object> metadata, String sql, String sqlCount, int pageNum, int pageSize ) {
		return findAllSQL(metadata, sql, sqlCount, pageNum, pageSize, null);				
	}	
	
	/**
	 * Retorna uma lista de objetos de leitura de registros do banco de dados conforme expressão SQL e
	 * paginação de registros 
	 * 
	 * @param metadata Map para armazenar totalizadores
	 * @param sql Expressão SQL 
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @param parameters Lista de parâmetros
	 * @return Retorna um list com objetos de leitura da tabela
	 */
	public List<T> findAllSQL(Map<String, Object> metadata, String sql, int pageNum, int pageSize, 
			ArrayList<ArrayList<Object>> parameters) {
		Query query = this.session.createQuery(sql);
		queryParameters(parameters, query);

		if (metadata != null && !(pageNum == 0 && pageSize == 0)) {
			metadata.put("recordCount", query.list().size());
		}	
				
		if (pageNum >= 0 && pageSize > 0)
		{
			query.setFirstResult(pageNum * pageSize);
			query.setMaxResults(pageSize);			
		}	

		@SuppressWarnings("unchecked")
		List<T> ret = query.list();
		
		if (metadata != null && !(pageNum == 0 && pageSize == 0))
			metadata.put("recordCountLimit", ret.size());
		
		return ret;							
	}
	
	/**
	 * Retorna uma lista de objetos de leitura de registros do banco de dados conforme expressão SQL e
	 * paginação de registros 
	 * 
	 * @param metadata Map para armazenar totalizadores
	 * @param sql Expressão SQL 
	 * @param sqlCount Expressão SQL 
	 * @param pageNum Número da página
	 * @param pageSize Tamanho da página, quantidade de registros por página
	 * @param parameters Lista de parâmetros
	 * @return Retorna um list com objetos de leitura da tabela
	 */
	public List<T> findAllSQL(Map<String, Object> metadata, String sql, String sqlCount, int pageNum, int pageSize, 
			ArrayList<ArrayList<Object>> parameters) {
		Query query = this.session.createQuery(sql);
		queryParameters(parameters, query);

		if (metadata != null && !(pageNum == 0 && pageSize == 0)) {
			Query queryCount = this.session.createQuery(sqlCount);
			queryParameters(parameters, queryCount);
			Object count = queryCount.uniqueResult();
			metadata.put("recordCount", count != null ? Integer.valueOf(((Long) count).toString()) : 0);
		}	
				
		if (pageNum >= 0 && pageSize > 0)
		{
			query.setFirstResult(pageNum * pageSize);
			query.setMaxResults(pageSize);			
		}	

		@SuppressWarnings("unchecked")
		List<T> ret = query.list();
		
		if (metadata != null && !(pageNum == 0 && pageSize == 0))
			metadata.put("recordCountLimit", ret.size());
		
		return ret;							
	}		

	/**
	 * Executa um SQL personalizado e retorna uma Query
	 * 
	 * @param sql Expressão SQL
	 * @return Retorna uma Query de execução de um script
	 */
	public Query executeQuery(String sql) {
		return executeQuery(sql, null);				
	}		

	/**
	 * Executa um SQL personalizado e retorna uma Query
	 * 
	 * @param sql Expressão SQL
	 * @param parameters Lista de parâmetros
	 * @return Retorna uma Query de execução de um script
	 */
	public Query executeQuery(String sql, ArrayList<ArrayList<Object>> parameters) {
		Query query = this.session.createQuery(sql);
		queryParameters(parameters, query);
		query.executeUpdate();
		return query;							
	}

	/**
	 * Executa um SQL personalizado e retorna uma Query
	 * 
	 * @param sql Expressão SQL
	 * @return Retorna uma Query de execução de uma consulta
	 */
	public List executeQueryList(String sql) {
		return executeQueryList(sql, null);				
	}		

	/**
	 * Executa um SQL personalizado e retorna uma Query
	 * 
	 * @param sql Expressão SQL
	 * @param parameters Lista de parâmetros
	 * @return Retorna uma Query de execução de uma consulta
	 */
	public List executeQueryList(String sql, ArrayList<ArrayList<Object>> parameters) {
		Query query = this.session.createQuery(sql);
		queryParameters(parameters, query);
		return query.list();							
	}

	/**
	 * Executa um SQL personalizado nativo e retorna uma Query
	 * 
	 * @param sql Expressão SQL
	 * @return Retorna uma Query de execução de um script
	 */
	public Query executeSQLQuery(String sql) {
		return executeSQLQuery(sql, null);				
	}		

	/**
	 * Executa um SQL personalizado nativo e retorna uma Query
	 * 
	 * @param sql Expressão SQL
	 * @param parameters Lista de parâmetros
	 * @return Retorna uma Query de execução de um script
	 */
	public Query executeSQLQuery(String sql, ArrayList<ArrayList<Object>> parameters) {
		Query query = this.session.createSQLQuery(sql);
		queryParameters(parameters, query);
		query.executeUpdate();
		return query;							
	}
	
	/**
	 * Executa um SQL personalizado nativo e retorna um List
	 * 
	 * @param sql Expressão SQL
	 * @return Retorna uma Query de execução de um script
	 */
	public List executeSQLQueryList(String sql) {
		return executeSQLQueryList(sql, null);				
	}		

	/**
	 * Executa um SQL personalizado nativo e retorna um List
	 * 
	 * @param sql Expressão SQL
	 * @param parameters Lista de parâmetros
	 * @return Retorna uma Query de execução de um script
	 */
	public List executeSQLQueryList(String sql, ArrayList<ArrayList<Object>> parameters) {
		Query query = this.session.createSQLQuery(sql);
		queryParameters(parameters, query);
		return query.list();							
	}		

	/**
	 * Retorna um objeto de persistência com o banco de dados, conforme propriedade ID de chave primária
	 * 
	 * @param id Identificador de chave primária
	 * @return Retorna a instância de um objeto de leitura na tabela
	 */
	public T find(Integer id) {
		@SuppressWarnings("unchecked")
		T ret = (T) session.load(this.type, id); 
		return ret;
	}	

}
