package controllers;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import models.IDataRecord;

public class AbstractRecordController<T extends IDataRecord> extends AbstractController {

	private Class<T> entityClass;
	protected AbstractRecordController(Class<T> entityClass){
		this.entityClass=entityClass;
	}
	public Class<T> entityClass(){
		return entityClass;
	}
	public String tableName(){
		return entityClass().getAnnotation(Table.class).name();
	}
	
	public List<T> list(){
		return getEntityManager().createQuery(String.format("select t from %s as t ", entityName()) , entityClass()).getResultList();
	}

	public List<T> list(String query, Object...args){
		return createQuery(query, args).getResultList();
	}
	public T get(Long id){
		return getEntityManager().find(entityClass, id);
	}

	public T get(String query, Object...args){
		return createQuery(query, args).getSingleResult();
	}
	
	public TypedQuery<T> createQuery(String query){
		return getEntityManager().createQuery(String.format(query, entityName()) , entityClass());
	}
	
	public TypedQuery<T> createQuery(String query, Object...args){
		TypedQuery<T> q = createQuery(query);
		for(int i=0; i<args.length; i++)
			q.setParameter(i, args[i]);
		return q;
	}

	public T updateIt(T argv){
		return getEntityManager().merge(argv);
	}

	public T createIt(T argv){
		getEntityManager().persist(argv);
		return argv;
	}

	public T saveIt(T argv){
		if(argv.getId()!=null)
			return updateIt(argv);
		return createIt(argv);
	}

	public T deleteIt(T argv){
		getEntityManager().remove(argv);
		return argv;
	}

	public T deleteIt(Long argv){

		return deleteIt(get(argv));
	}

	public T refresh(T argv){
		getEntityManager().refresh(argv);
		return argv;
	}

	public String entityName(){
		return entityClass().getAnnotation(Entity.class).name();
	}

	public static <T extends IDataRecord> long nexId(List<T> items){
		long r=-1;
		for(T o:items)
			if(r<o.getId())
				r=o.getId();
		return r+1;
	}
	public static <T extends IDataRecord> T remove(Long id, List<T> items){
		T r=null;
		for(int i=0; i<items.size(); i++)
			if((r=items.get(i))!=null && r.getId()==id){
				items.remove(i);
				return r;
			}
		return null;
	}
	public static <T extends IDataRecord> int indexOf(Long id, List<T> items){
		T r=null;
		for(int i=0; i<items.size(); i++)
			if((r=items.get(i))!=null && r.getId()==id)
				return i;
		return -1;
	}
	public static <T extends IDataRecord> T find(Long id, List<T> items){
		T r=null;
		for(int i=0; i<items.size(); i++)
			if((r=items.get(i))!=null && r.getId()==id)
				return r;
		return null;
	}
}