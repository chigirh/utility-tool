package chigirh.app.utility.dataacces.common.manipulation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import chigirh.app.utility.dataacces.common.common.DataModel;
import chigirh.app.utility.dataacces.common.definition.ColumnDefinition;
import chigirh.app.utility.dataacces.common.definition.TableDefinition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class InsertQuery implements DataModel{

	@Getter
	private TableDefinition table;

	private final Map<String,String> params = new HashMap<>();


	public static <E extends ColumnDefinition> InsertQueryBuilder<E> newQuery() {
		InsertQuery query = new InsertQuery();
		return query.new InsertQueryBuilder<E>(query);
	}


	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class InsertQueryBuilder<E extends ColumnDefinition>{
		private final InsertQuery query;

		public InsertBuilder<E> INSERT(TableDefinition table){
			query.table = table;
			return query.new InsertBuilder<E>(query);
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class InsertBuilder<E extends ColumnDefinition>{
		private final InsertQuery query;

		public IntoBuilder<E> INTO(E column,String parm){
			query.params.put(column.getFqcn(), parm);
			return query.new IntoBuilder<E>(query);
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class IntoBuilder<E extends ColumnDefinition>{
		private final InsertQuery query;

		public IntoBuilder<E> add(E column,String parm){
			query.params.merge(column.getFqcn(), StringUtils.isEmpty(parm)?"":parm, (ov,nv)-> nv);
			return query.new IntoBuilder<E>(query);
		}

		public InsertQuery build() {
			return query;
		}

	}

	@Override
	public String getValue(String fqcn) {
		return params.get(fqcn);
	}

}
