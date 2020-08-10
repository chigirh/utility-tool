package chigirh.app.utility.dataacces.common.definition;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationTaskColumn implements ColumnDefinition {

	OPERATION_ID(0, "operation_id", TableDefinition.OPERATION_TASK, //
			ConstraintDefinition.newConstraint().PRIMARY_KEY().FOREIGN_KEY().REFFERENCES(OperationColumn.OPERATION_ID).complete()), //

	SERIAL_NUMBER(1, "serial_number", TableDefinition.OPERATION_TASK,//
			ConstraintDefinition.newConstraint().PRIMARY_KEY().complete()), //

	TASK(2, "task", TableDefinition.OPERATION_TASK, //
			ConstraintDefinition.noneConstraint()), //

	TIME(3, "time", TableDefinition.OPERATION_TASK, //
			ConstraintDefinition.noneConstraint());



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
