package chigirh.app.utility.dataacces.common.manipulation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import chigirh.app.utility.dataacces.common.common.DataModel;
import chigirh.app.utility.dataacces.common.definition.ColumnDefinition;
import chigirh.app.utility.dataacces.common.definition.TableDefinition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateQuery implements DataModel {

	@Getter
	private TableDefinition table;

	@Getter
	private Predicate<DataModel> where;

	@Getter
	private final Map<String, String> params = new HashMap<>();

	public static <E extends ColumnDefinition> UpdateQueryBuilder<E> newQuery() {
		UpdateQuery query = new UpdateQuery();
		return query.new UpdateQueryBuilder<E>(query);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class UpdateQueryBuilder<E extends ColumnDefinition> {
		private final UpdateQuery query;

		public UpdateBuilder<E> UPDATE(TableDefinition table) {
			query.table = table;
			return query.new UpdateBuilder<E>(query);
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class UpdateBuilder<E extends ColumnDefinition> {
		private final UpdateQuery query;

		public SetBuilder<E> SET(E column, String parm) {
			query.params.put(column.getFqcn(), parm);
			return query.new SetBuilder<E>(query);
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class SetBuilder<E extends ColumnDefinition> {
		private final UpdateQuery query;

		public SetBuilder<E> add(E column, String parm) {
			query.params.merge(column.getFqcn(), StringUtils.isEmpty(parm) ? "" : parm, (ov, nv) -> nv);
			return query.new SetBuilder<E>(query);
		}

		public OperatorBuilder WHERE(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, null);
		}

		public UpdateQuery build() {
			return query;
		}

	}

	@RequiredArgsConstructor
	public class WhereBuilder {
		private final UpdateQuery query;

		public OperatorBuilder AND(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, true);
		}

		public OperatorBuilder OR(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, false);
		}

		public UpdateQuery build() {
			return query;
		}
	}

	@RequiredArgsConstructor
	public class OperatorBuilder {
		private final UpdateQuery query;
		private final ColumnDefinition column;
		private final Boolean isAnd;

		private boolean NOT = false;

		public OperatorBuilder NOT() {
			NOT = true;
			return this;
		}

		public WhereBuilder THAN(final int operand) {
			set(e -> operand < Integer.parseInt(column.getValue(e)));
			return query.new WhereBuilder(query);
		}

		public WhereBuilder LESS(final int operand) {
			set(e -> Integer.parseInt(column.getValue(e)) < operand);
			return query.new WhereBuilder(query);
		}

		public WhereBuilder EQUAL(final int operand) {
			set(e -> Integer.parseInt(column.getValue(e)) == operand);
			return query.new WhereBuilder(query);
		}

		public WhereBuilder EQUAL(final String operand) {
			set(e -> StringUtils.equals(column.getValue(e), operand));
			return query.new WhereBuilder(query);
		}

		public WhereBuilder IN(final Integer... operand) {
			Set set = new HashSet<>(Arrays.asList(operand));
			set(e -> set.contains(Integer.parseInt(column.getValue(e))));
			return query.new WhereBuilder(query);
		}

		public WhereBuilder IN(final String... operand) {
			Set set = new HashSet<>(Arrays.asList(operand));
			set(e -> set.contains(column.getValue(e)));
			return query.new WhereBuilder(query);
		}

		public WhereBuilder IS_NULL() {
			set(e -> StringUtils.isEmpty(column.getValue(e)));
			return query.new WhereBuilder(query);
		}

		public WhereBuilder IS_NOT_NULL() {
			set(e -> !StringUtils.isEmpty(column.getValue(e)));
			return query.new WhereBuilder(query);
		}

		public BetWeenBuilder BETWEEN(final int less) {
			return query.new BetWeenBuilder(this, less);
		}

		private void set(Predicate<DataModel> where) {
			query.where = isAnd == null ? where
					: isAnd ? query.where.and(NOT ? where : where.negate())
							: query.where.or(NOT ? where : where.negate());
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class BetWeenBuilder {
		private final OperatorBuilder builder;
		private final int less;

		public WhereBuilder AND(final int than) {
			builder.set(e -> less <= Integer.parseInt(builder.column.getValue(e))
					&& Integer.parseInt(builder.column.getValue(e)) <= than);
			return builder.query.new WhereBuilder(builder.query);
		}
	}

	@Override
	public String getValue(String fqcn) {
		return params.get(fqcn);
	}

}
