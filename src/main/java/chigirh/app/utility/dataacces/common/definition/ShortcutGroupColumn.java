package chigirh.app.utility.dataacces.common.definition;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ShortcutGroupColumn implements ColumnDefinition {

	SHORTCUT_GROUP_ID(0, "shortcut_group_id", TableDefinition.SHORTCUT_GROUP,
			ConstraintDefinition.newConstraint().PRIMARY_KEY().UNIQUE_KEY().complete()),

	SHORTCUT_GROUP_NAME(1, "shortcut_group_name", TableDefinition.SHORTCUT_GROUP,
			ConstraintDefinition.newConstraint().NOT_NULL().complete());

	private final int index;

	private final String columnName;

	private final TableDefinition table;

	private ConstraintDefinition constraintDefinition;

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}


	@Override
	public TableDefinition getTable() {
		return table;
	}

	@Override
	public ConstraintDefinition getConstraint() {
		return constraintDefinition;
	}

	public static List<ColumnDefinition> getColumns() {
		return ColumnDefinition.asList(values());
	}


}
