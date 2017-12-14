package com.htsoft.oa.service.system;
/*
 *  广州宏天软件有限公司 OA办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2009 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.oa.model.system.Company;

public interface CompanyService extends BaseService<Company> {

	public List<Company> findByHql(final String hql);
	public List<Company> findCompany();
}
