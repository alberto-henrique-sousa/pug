package com.pugsource.dao;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Class DaoFactory
 * 
 * Classe responsável pela manipulação de acessos a sessão com Hibernate
 * 
 * @author Alberto Henrique Sousa
 * 
 * @version 1.0
 *
 */
public class DaoFactory {
	
	public Session session;
	public Transaction transaction;
	
	/**
	 * Método construtor default.
	 */
	public DaoFactory() {
		try {
			session = (new HibernateUtil("")).getSession();
	    	if (HibernateUtil.DEBUG)
	    		System.out.println("	New daoFactory, session ID...: " + this.session.hashCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método construtor default.
	 */
	public DaoFactory(String properties) {
		try {
			session = (new HibernateUtil(properties)).getSession();			
	    	if (HibernateUtil.DEBUG)
	    		System.out.println("	New daoFactory, session ID...: " + this.session.hashCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
			
	/**
	 * Inicia uma transação com o banco de dados.
	 */
	public void beginTransaction() {
		try {
			this.session.clear();
			this.transaction = this.session.beginTransaction();
	    	if (HibernateUtil.DEBUG)
	    		System.out.println("	Begin transaction, session ID: " + this.session.hashCode());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Descarrega as operações de transação no banco de dados.
	 */
	public void commit() {
		//try {
			this.transaction.commit();
	    	if (HibernateUtil.DEBUG)
	    		System.out.println("	Commit, session ID...........: " + this.session.hashCode());
			this.transaction = null;
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
	}
	
	/**
	 * Verifica se existe uma transação aberta.
	 * 
	 * @return Retorna True se existir uma transação ativa, ou False se não existir
	 */
	public boolean hasTransaction() {
		return this.transaction != null;
	}
	
	/**
	 * Desfaz as operações em uma transação aberta.
	 */
	public void rollback() {
		this.transaction.rollback();
		this.transaction = null;
	}
	
	/**
	 * Fecha conexão com a sessão.
	 */
	public void close() {
		try {
			if (hasTransaction())
				commit();
			this.session.close();
	    	if (HibernateUtil.DEBUG){
	    		System.out.println("	Close, session ID............: " + this.session.hashCode());
	    		System.out.println("	Query max time...............: " + this.session.getSessionFactory().getStatistics().getQueryExecutionMaxTime());
	    		System.out.println("	Query max time SQL...........: " + this.session.getSessionFactory().getStatistics().getQueryExecutionMaxTimeQueryString());
	    		System.out.println("	Session open count...........: " + this.session.getSessionFactory().getStatistics().getSessionOpenCount());
	    		System.out.println("	Session open close...........: " + this.session.getSessionFactory().getStatistics().getSessionCloseCount());
	    		System.out.println("]");
	    	}	
			this.session.getSessionFactory().close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}