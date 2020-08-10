package chigirh.app.utility.dataacces.common.definition;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationColumn implements ColumnDefinition {

	OPERATION_ID(0, "operation_id", TableDefinition.OPERATION, //
			ConstraintDefinition.newConstraint().PRIMARY_KEY().complete()), //

	OPERATION_GROUP_ID(1, "operation_group_id", TableDefinition.OPERATION, //
			ConstraintDefinition.newConstraint()
					.FOREIGN_KEY().REFFERENCES(OperationGroupColumn.OPERATION_GROUP_ID).complete()), //

	ORDER(2, "order", TableDefinition.OPERATION, //
			ConstraintDefinition.newConstraint().NOT_NULL().complete()), //

	DATE(3, "date", TableDefinition.OPERATION, //
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
