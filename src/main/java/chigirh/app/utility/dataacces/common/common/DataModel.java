package chigirh.app.utility.dataacces.common.common;

import org.apache.commons.lang3.StringUtils;

import chigirh.app.utility.dataacces.common.definition.ColumnDefinition;

public interface DataModel {

	public String getValue(String fqcn);

	public default String getValue(ColumnDefinition column) {
		String value = getValue(column.getFqcn());
		return StringUtils.isEmpty(value) ? StringUtils.EMPTY:value;
	}


}
