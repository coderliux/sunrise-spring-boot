package com.sunrise.page.helper.handle.imp;

import com.sunrise.page.helper.entitys.PageInfo;
import com.sunrise.page.helper.handle.DataBaseHandle;

/**
 * informix SQL分页处理
 * 
 * @author Sun Rising
 * @date 2019.05.29 12:59:26
 *
 */
public class InformixHandle implements DataBaseHandle {

	@Override
	public String getPageSql(String sql, PageInfo page) {
		// 获取当前页码
		int currPageNum = page.getCurrentPageNum();
		// 获取每页数据数
		int pageSize = page.getPageSize();
		// 起始行
		int startRow = currPageNum * pageSize;
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 40);
		sqlBuilder.append("SELECT ");
		if (startRow > 0) {
			sqlBuilder.append(" SKIP ");
			sqlBuilder.append(startRow);
		}
		if (pageSize > 0) {
			sqlBuilder.append(" FIRST ");
			sqlBuilder.append(pageSize);
		}
		sqlBuilder.append(" * FROM ( ");
		sqlBuilder.append(sql);
		sqlBuilder.append(" ) TEMP_T");
		return sqlBuilder.toString();
	}

	@Override
	public String getTotalRecords(String sql) {
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 40);
		sqlBuilder.append("SELECT COUNT(*) FROM (");
		sqlBuilder.append(sql);
		sqlBuilder.append(") t");
		return sqlBuilder.toString();
	}
}
