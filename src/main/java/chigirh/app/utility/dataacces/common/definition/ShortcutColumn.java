package chigirh.app.utility.dataacces.common.definition;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ShortcutColumn implements ColumnDefinition {

	SHORTCUT_GROUP_ID(0, "shortcut_group_id", TableDefinition.SHORTCUT, //
			ConstraintDefinition.newConstraint().PRIMARY_KEY()
			.FOREIGN_KEY().REFFERENCES(ShortcutGroupColumn.SHORTCUT_GROUP_ID).complete()), //

	SERIAL_NUMBER(1, "serial_number", TableDefinition.SHORTCUT,//
			ConstraintDefinition.newConstraint().PRIMARY_KEY().complete()), //

	ORDER(2, "order", TableDefinition.SHORTCUT,//
			 ConstraintDefinition.newConstraint().NOT_NULL().complete()	), //

	TITLE(3, "title", TableDefinition.SHORTCUT,//
			 ConstraintDefinition.newConstraint().NOT_NULL().complete()), //

	PATH(4, "path", TableDefinition.SHORTCUT,//
			 ConstraintDefinition.newConstraint().NOT_NULL().complete()),//

	REMARK(5,"remark",TableDefinition.SHORTCUT,ConstraintDefinition.noneConstraint());

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
