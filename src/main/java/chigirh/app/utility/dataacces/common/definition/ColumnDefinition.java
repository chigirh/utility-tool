package chigirh.app.utility.dataacces.common.definition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import chigirh.app.utility.dataacces.common.common.DataModel;

/**
 * テーブル列定義のBaseクラス.
 */
public interface ColumnDefinition {

	String PERIOD = ".";

	public int getIndex();

	public String getColumnName();

	public TableDefinition getTable();

	public ConstraintDefinition getConstraint();

	public default String getFqcn() {
		return new StringBuilder(getTable().getTableName())//
				.append(PERIOD)//
				.append(getColumnName()).toString();
	}

	public default String getValue(DataModel model) {
		return model.getValue(this);
	}

	static List<ColumnDefinition> asList(ColumnDefinition[] columns) {
		return Collections.unmodifiableList(Arrays.asList(columns));
	}

}
