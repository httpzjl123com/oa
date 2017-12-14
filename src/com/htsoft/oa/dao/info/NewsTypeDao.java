package com.htsoft.oa.dao.info;
/*
 *  广州宏天软件有限公司 OA办公管理系统   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2009 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.info.NewsType;

public interface NewsTypeDao extends BaseDao<NewsType>{
	public Short getTop();
	public NewsType findBySn(Short sn);
	public List<NewsType> getAllBySn();
	public List<NewsType> getAllBySn(PagingBean pb);
	public List<NewsType> findBySearch(NewsType newsType,PagingBean pb);
}
