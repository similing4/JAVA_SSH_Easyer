package com.pingjia.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Model {
	String table;
	DB_Where where = new DB_Where();
	List<Serializable> data = null;
	int limitStart = -1;
	int limitEnd = -1;
	public Model(String table){
		this.table = table;
		this.data = new ArrayList<Serializable>();
	}
	public Model where(DB_Where where){
		this.where = where;
		return this;
	}
	public Model data(Serializable data){
		this.data.add(data);
		return this;
	}
	public Model data(List<Serializable> data){
		for(Serializable obj : data){
			this.data.add(obj);
		}
		return this;
	}
	public Model limit(int page){
		this.limitStart = page;
		return this;
	}
	public Model limit(int page,int end){
		this.limitStart = page;
		this.limitEnd = end;
		return this;
	}
	public boolean add(){
		for(Serializable i : data)
			DB.add(i);
		return true;
	}
	public boolean add(Serializable obj){
		DB.add(obj);
		return true;
	}
	public boolean save(){
		DB.save(this.data.get(0));
		return true;
	}
	public boolean save(Serializable obj){
		DB.save(obj);
		return true;
	}
	@SuppressWarnings("unchecked")
	public Serializable find(int id){
		DB_Where where = new DB_Where();
		where.setWhere("id", id + "");
		this.where = where;
		List<Serializable> list = (List<Serializable>) this.select();
		if(list.size()==0)
			return null;
		return list.get(0);
	}
	public List<?> select(){
		return DB.select(this.table,this.where,-1,-1);
	}
	public boolean delete(){
		List<?> data = DB.select(this.table,this.where,-1,-1);
		if(data.size()==0)
			return false;
		for(Object datai : data){
			DB.delete((Serializable)datai);
		}
		return true;
	}
	public boolean delete(int id){
		DB_Where where = new DB_Where();
		where.setWhere("id", id+"");
		List<?> data = DB.select(this.table,where,-1,-1);
		if(data.size()==0)
			return false;
		DB.delete((Serializable)(data.get(0)));
		return true;
	}
}
