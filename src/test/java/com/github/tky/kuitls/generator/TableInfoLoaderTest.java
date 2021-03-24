package com.github.tky.kuitls.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tky.kutils.jdbc.ConnectionUtil;
import com.github.tky.kutils.jdbc.DataSourceInfo;
import com.github.tky.kutils.jdbc.ResultSetColumnKeys;

public class TableInfoLoaderTest {

	private Connection connection;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before() {
		DataSourceInfo dataSource = new DataSourceInfo();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("Ab123456");
		dataSource.setUrl("jdbc:mysql://192.168.120.22:3306/activity?characterEncoding=UTF-8");
		connection = ConnectionUtil.getConnection(dataSource);
	}

	@Test
	public void testQueryDataInfo() {
		String table = "activity_info";
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getColumns(connection.getCatalog(), "%", table, "%");
			while (rs.next()) {
				System.out.println();
				logger.info("列名称：\t{}", rs.getString(ResultSetColumnKeys.COLUMN_NAME.name()));
				logger.info("java.sql.Types：\t{}", rs.getString(ResultSetColumnKeys.DATA_TYPE.name()));
				logger.info("字段类型：\t{}", rs.getString(ResultSetColumnKeys.TYPE_NAME.name()));
				logger.info("列的大小：\t{}", rs.getString(ResultSetColumnKeys.COLUMN_SIZE.name()));
				logger.info("小数部分的位数：\t{}", rs.getString(ResultSetColumnKeys.DECIMAL_DIGITS.name()));
				logger.info("基数：\t{}", rs.getString(ResultSetColumnKeys.NUM_PREC_RADIX.name()));
				logger.info("描述列的注释：\t{}", rs.getString(ResultSetColumnKeys.REMARKS.name()));
				logger.info("该列的默认值：\t{}", rs.getString(ResultSetColumnKeys.COLUMN_DEF.name()));
				logger.info("列中的最大字节数：\t{}", rs.getString(ResultSetColumnKeys.CHAR_OCTET_LENGTH.name()));
				logger.info("列的索引：\t{}", rs.getString(ResultSetColumnKeys.ORDINAL_POSITION.name()));
				logger.info("是否允许使用 NULL：\t{}", rs.getString(ResultSetColumnKeys.IS_NULLABLE.name()));
				logger.info("指示此列是否自动增加：\t{}", rs.getString(ResultSetColumnKeys.IS_AUTOINCREMENT.name()));
			}
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException("表信息抽取异常！", e);
		}

	}
}
