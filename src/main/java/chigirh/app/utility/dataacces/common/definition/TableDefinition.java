package chigirh.app.utility.dataacces.common.definition;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import chigirh.app.utility.boot.ApplicationContextSupport;
import chigirh.app.utility.dataacces.dataaccess.DataAccess;
import chigirh.app.utility.dataacces.dataaccess.OperationDao;
import chigirh.app.utility.dataacces.dataaccess.OperationGroupDao;
import chigirh.app.utility.dataacces.dataaccess.OperationTaskDao;
import chigirh.app.utility.dataacces.dataaccess.ShortcutGroupDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *テーブル定義.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TableDefinition {

	SHORTCUT_GROUP("sortcut_group",ShortcutGroupColumn::getColumns,(ShortcutGroupDao)ApplicationContextSupport.getBean("shortcutGroupDao")),
	SHORTCUT("sortcut",ShortcutColumn::getColumns,null),
	OPERATION_GROUP("operation_group",OperationGroupColumn::getColumns,(OperationGroupDao)ApplicationContextSupport.getBean("operationGroupDao")),
	OPERATION("operation",OperationColumn::getColumns,(OperationDao)ApplicationContextSupport.getBean("operationDao")),
	OPERATION_TASK("operation_task",OperationTaskColumn::getColumns,(OperationTaskDao)ApplicationContextSupport.getBean("operationTaskDao"));

	private final String tableName;

	private final Supplier<List<ColumnDefinition>> columnGetter;

	private DataAccess dao;

	public List<ColumnDefinition> getColumns(){
		return columnGetter.get();
	}

	public List<ColumnDefinition> getNotNull(){
		return getColumns().stream().filter(this::isNotnull).collect(Collectors.toList());
	}

	public List<ColumnDefinition> getPrimaryKey(){
		return getColumns().stream().filter(this::isPrimaryKey).collect(Collectors.toList());
	}

	public List<ColumnDefinition> getUniqueKey(){
		return getColumns().stream().filter(this::isUniqueKey).collect(Collectors.toList());
	}

	public List<ColumnDefinition> getCheckConstrain(){
		return getColumns().stream().filter(this::isCheckConstraint).collect(Collectors.toList());
	}

	public List<ColumnDefinition> getForeignKey(){
		return getColumns().stream().filter(this::isForeignKey).collect(Collectors.toList());
	}

	private boolean isNotnull(ColumnDefinition column) {
		return column.getConstraint().isNotNullConstraint();
	}


	private boolean isPrimaryKey(ColumnDefinition column) {
		return column.getConstraint().isPrimaryKeyConstraint();
	}

	private boolean isUniqueKey(ColumnDefinition column) {
		return column.getConstraint().isUniqueKeyConstraint();
	}

	private boolean isCheckConstraint(ColumnDefinition column) {
		return column.getConstraint().getCheckConstraint() != null;
	}

	private boolean isForeignKey(ColumnDefinition column) {
		return column.getConstraint().getRefferences() != null;
	}




}
