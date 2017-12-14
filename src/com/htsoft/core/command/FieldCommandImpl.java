package com.htsoft.core.command;
/*
 *  广州宏天软件有限公司 OA办公自动管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2009 GuangZhou HongTian Software Company
*/
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * 表字段查询条件
 * @author csx
 *
 */
public class FieldCommandImpl implements CriteriaCommand {
	
	private static Log logger=LogFactory.getLog(CriteriaCommand.class);
	/*
	 * 属性名称
	 */
	private String property;
	/**
	 * 属性值
	 */
	private Object value;
	/**
	 * 查询属性的操作
	 */
	private String operation;
	
	private QueryFilter filter;
	
	public FieldCommandImpl(String property, Object value, String operation,QueryFilter filter) {
		this.property = property;
		this.value = value;
		this.operation = operation;
		this.filter=filter;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	public Criteria execute(Criteria criteria) {
	   	//支持外键属性的查询
    	String[]propertys=property.split("[.]");
    	if(propertys!=null&&propertys.length>1){
    		for(int i=0;i<propertys.length-1;i++){
    			//防止别名重复
    			if(!filter.getAliasSet().contains(propertys[i])){
    				criteria.createAlias(propertys[i],propertys[i]);
    				filter.getAliasSet().add(propertys[i]);
    			}
    		}
    	}
    	
		if("LT".equals(operation)){
			criteria.add(Restrictions.lt(property, value));
			
		}else if("GT".equals(operation)){
			criteria.add(Restrictions.gt(property, value));
		}else if("LE".equals(operation)){
			criteria.add(Restrictions.le(property, value));
		}else if("GE".equals(operation)){
			criteria.add(Restrictions.ge(property, value));
		}else if("LK".equals(operation)){
			criteria.add(Restrictions.like(property, "%" + value + "%").ignoreCase());
		}else if("LFK".equals(operation)){
			criteria.add(Restrictions.like(property, value + "%").ignoreCase());
		}else if("RHK".equals(operation)){
			criteria.add(Restrictions.like(property,"%" + value ).ignoreCase());
		}else if("NULL".equals(operation)){
			criteria.add(Restrictions.isNull(property));
		}else if("NOTNULL".equals(operation)){
			criteria.add(Restrictions.isNotNull(property));
		}else if("EMP".equals(operation)){
			criteria.add(Restrictions.isEmpty(property));
		}else if("NOTEMP".equals(operation)){
			criteria.add(Restrictions.isNotEmpty(property));
		}else{
			criteria.add(Restrictions.eq(property, value));
		}
		return criteria;
	}
	
	public String getPartHql() {
		//处理外键的问题
		String[]propertys=property.split("[.]");
    	if(propertys!=null&&propertys.length>1){
			//防止别名重复
			if(!filter.getAliasSet().contains(propertys[0])){
				filter.getAliasSet().add(propertys[0]);
			}
    	}
		String partHql=null;
		if("LT".equals(operation)){
			partHql=property + " < ? ";
			filter.getParamValueList().add(value);
		}else if("GT".equals(operation)){
			partHql=property + " > ? ";
			filter.getParamValueList().add(value);
		}else if("LE".equals(operation)){
			partHql=property + " <= ? ";
			filter.getParamValueList().add(value);
		}else if("GE".equals(operation)){
			partHql=property + " >= ? ";
			filter.getParamValueList().add(value);
		}else if("LK".equals(operation)){
			partHql=property + " like ? ";
			filter.getParamValueList().add("%"+value.toString()+"%");
		}else if("LFK".equals(operation)){
			partHql=property + " like ? ";
			filter.getParamValueList().add(value.toString()+"%");
		}else if("RHK".equals(operation)){
			partHql=property + " like ? ";
			filter.getParamValueList().add("%"+value.toString());
		}else if("NULL".equals(operation)){
			partHql=property + " is null ";		
		}else if("NOTNULL".equals(operation)){
			partHql=property + " is not null ";
		}else if("EMP".equals(operation)){
			//TODO
		}else if("NOTEMP".equals(operation)){
			
		}else{
			partHql+= property + "=? ";
			filter.getParamValueList().add(value);
		}

		return partHql;
	}

}
