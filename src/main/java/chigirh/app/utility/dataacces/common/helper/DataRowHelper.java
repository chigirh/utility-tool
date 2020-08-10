package chigirh.app.utility.dataacces.common.helper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import chigirh.app.utility.dataacces.common.common.DataModel;
import chigirh.app.utility.dataacces.common.definition.ColumnDefinition;
import chigirh.app.utility.dataacces.common.definition.TableDefinition;
import chigirh.app.utility.dataacces.common.exception.DataAccessError;
import chigirh.app.utility.dataacces.common.exception.DataAccessException;
import chigirh.app.utility.dataacces.common.manipulation.InsertQuery;
import chigirh.app.utility.dataacces.common.manipulation.UpdateQuery;
import chigirh.app.utility.dataacces.dataaccess.ResponseModel;

@Component
public class DataRowHelper {

	public static final String COMMA = ",";

	private static final Comparator<ColumnDefinition> INDEX_COMP = (c1, c2) -> c1.getIndex() - c2.getIndex();

	public String toRow(TableDefinition table, DataModel dataModel) {
		return table.getColumns().stream()//
				.sorted(INDEX_COMP)//
				.map(dataModel::getValue)//
				.collect(Collectors.joining(COMMA));
	}

	public void constraintCheck(UpdateQuery updateQuery) {
		constraintCheck(updateQuery,updateQuery.getTable());
	}

	public void constraintCheck(InsertQuery insertQuery) {
		constraintCheck(insertQuery,insertQuery.getTable());
	}

	public void constraintCheck(DataModel model,TableDefinition table) {

		//NOT NULL制約
		if (table.getNotNull().stream().map(model::getValue).anyMatch(StringUtils::isEmpty)) {
			throw new DataAccessException(DataAccessError.NOT_NULL_ERROT);

		}

		List<ResponseModel> all = table.getDao().select();

		//主キー制約
		String primaryKeyStr = getPrimaryKeyStr(table, model);

		if (all.parallelStream().map(e -> getPrimaryKeyStr(table, e)).anyMatch(primaryKeyStr::equals)) {
			throw new DataAccessException(DataAccessError.PRIMSRY_KEY_ERROE);
		}

		//ユニーク制約
		if (!CollectionUtils.isEmpty(table.getUniqueKey())) {
			String uniqueKeyStr = getUniqueKeyStr(table, model);
			if (all.parallelStream().map(e -> getUniqueKeyStr(table, e)).anyMatch(uniqueKeyStr::equals)) {
				throw new DataAccessException(DataAccessError.UNIQUE_ERROE);
			}
		}

		//CHECK制約
		if (!table.getCheckConstrain().parallelStream().allMatch(e -> isCheckConstraint(e, model))) {
			throw new DataAccessException(DataAccessError.CHECK_ERROE);
		}

		//外部キー制約
		if (!table.getForeignKey().parallelStream().allMatch(e -> isForeignKeyCOnstraint(e, model))) {
			throw new DataAccessException(DataAccessError.FOREIGN_KEY_ERROR);
		}

	}

	private String getPrimaryKeyStr(TableDefinition table, DataModel model) {
		return table.getPrimaryKey().stream().map(model::getValue).collect(Collectors.joining(COMMA));
	}

	private String getUniqueKeyStr(TableDefinition table, DataModel model) {
		return table.getUniqueKey().stream().map(model::getValue).collect(Collectors.joining(COMMA));
	}

	private boolean isCheckConstraint(ColumnDefinition column, DataModel model) {
		return column.getConstraint().getCheckConstraint() == null ? true//
				: column.getConstraint().getCheckConstraint().getComparativeExpression().test(//
						column.getValue(model));
	}

	private boolean isForeignKeyCOnstraint(ColumnDefinition column, DataModel model) {
		if (column.getConstraint().getRefferences() == null) {
			return true;
		}

		ColumnDefinition refferences = column.getConstraint().getRefferences();
		List<ResponseModel> all = refferences.getTable().getDao().select();
		String param = model.getValue(column);
		if (StringUtils.isEmpty(param)) {
			return true;
		}
		return all.parallelStream().map(refferences::getValue).anyMatch(param::equals);

	}

}
