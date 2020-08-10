package chigirh.app.utility.dataacces.common.manipulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
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
@Getter
public class SelectQuery {

	private Set<ColumnDefinition> select = new HashSet<>();

	private TableDefinition from = null;

	private List<JoinQuery> join = new ArrayList<>();

	private Predicate<DataModel> where;

	private Comparator<DataModel> orderBy;

	public static SelectQueryBuilder newQuery() {
		SelectQuery query = new SelectQuery();
		return query.new SelectQueryBuilder(query);
	}

	@RequiredArgsConstructor
	public class SelectQueryBuilder {
		private final SelectQuery query;

		public SelectBuilder SELECT(ColumnDefinition column) {
			query.select.add(column);
			return query.new SelectBuilder(query);
		}

		public SelectAllBuilder SELECT_ALL() {
			return query.new SelectAllBuilder(query);
		}
	}

	@RequiredArgsConstructor
	public class SelectBuilder {
		private final SelectQuery query;

		public SelectBuilder add(ColumnDefinition column) {
			query.select.add(column);
			return this;
		}

		public UnionFromBuilder FROM(TableDefinition table) {
			query.from = table;
			return query.new UnionFromBuilder(query);
		}
	}

	@RequiredArgsConstructor
	public class SelectAllBuilder {
		private final SelectQuery query;

		public FromBuilder FROM(TableDefinition table) {
			query.from = table;
			return query.new FromBuilder(query);
		}
	}

	@RequiredArgsConstructor
	public class FromBuilder {
		private final SelectQuery query;

		public OperatorBuilder WHERE(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, null);
		}

		public SortBuilder ORDER_BY(ColumnDefinition column) {
			return query.new SortBuilder(query, column);
		}

		public SelectQuery build() {
			return query;
		}
	}

	@RequiredArgsConstructor
	public class UnionFromBuilder {
		private final SelectQuery query;

		public InnerJoinBuilder INNER_JOIN(TableDefinition table) {
			return query.new InnerJoinBuilder(query, this, table);
		}

		public LeftOuterJoinBuilder LEFT_OUTER_JOIN(TableDefinition table) {
			return query.new LeftOuterJoinBuilder(query, this, table);
		}

		public RightOuterJoinBuilder RIGHT_OUTER_JOIN(TableDefinition table) {
			return query.new RightOuterJoinBuilder(query, this, table);
		}

		public OperatorBuilder WHERE(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, null);
		}

		public SortBuilder ORDER_BY(ColumnDefinition column) {
			return query.new SortBuilder(query, column);
		}

		public SelectQuery build() {
			return query;
		}
	}

	@RequiredArgsConstructor
	public class InnerJoinBuilder {
		private final SelectQuery query;
		private final UnionFromBuilder unionFromBuilder;
		private final TableDefinition table;

		public UnionFromBuilder ON(ColumnDefinition left, ColumnDefinition right) {
			query.join.add(new JoinQuery(table, (l, r) -> StringUtils.equals(left.getValue(l), right.getValue(r))));
			return unionFromBuilder;
		}
	}

	@RequiredArgsConstructor
	public class LeftOuterJoinBuilder {
		private final SelectQuery query;
		private final UnionFromBuilder unionFromBuilder;
		private final TableDefinition table;

		public UnionFromBuilder ON(ColumnDefinition left, ColumnDefinition right) {
			query.join.add(new JoinQuery(table, (l, r) -> StringUtils.equals(left.getValue(l), right.getValue(r))
					|| StringUtils.isEmpty(left.getValue(l))));
			return unionFromBuilder;
		}

	}

	@RequiredArgsConstructor
	public class RightOuterJoinBuilder {
		private final SelectQuery query;
		private final UnionFromBuilder unionFromBuilder;
		private final TableDefinition table;

		public UnionFromBuilder ON(ColumnDefinition left, ColumnDefinition right) {
			query.join.add(new JoinQuery(table, (l, r) -> StringUtils.equals(left.getValue(l), right.getValue(r))
					|| StringUtils.isEmpty(right.getValue(r))));
			return unionFromBuilder;
		}

	}

	@RequiredArgsConstructor
	public class WhereBuilder {
		private final SelectQuery query;

		public OperatorBuilder AND(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, true);
		}

		public OperatorBuilder OR(ColumnDefinition column) {
			return query.new OperatorBuilder(query, column, false);
		}

		public SortBuilder ORDER_BY(ColumnDefinition column) {
			return query.new SortBuilder(query, column);
		}

		public SelectQuery build() {
			return query;
		}
	}

	@RequiredArgsConstructor
	public class OperatorBuilder {
		private final SelectQuery query;
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

	@RequiredArgsConstructor
	public class OrderByBuilder {
		private final SelectQuery query;

		public SortBuilder ORDER_BY(ColumnDefinition column) {
			return query.new SortBuilder(query, column);
		}

		public SelectQuery build() {
			return query;
		}
	}

	@RequiredArgsConstructor
	public class SortBuilder {
		private final SelectQuery query;
		private final ColumnDefinition column;

		public OrderByBuilder ASK() {
			Comparator<DataModel> orderBy = (c1, c2) -> column.getValue(c1).compareTo(column.getValue(c2));
			query.orderBy = query.orderBy == null ? orderBy : query.orderBy.thenComparing(orderBy);
			return query.new OrderByBuilder(query);
		}

		public OrderByBuilder DESC() {
			Comparator<DataModel> orderBy = (c1, c2) -> column.getValue(c2).compareTo(column.getValue(c1));
			query.orderBy = query.orderBy == null ? orderBy : query.orderBy.thenComparing(orderBy);
			return query.new OrderByBuilder(query);
		}
	}

	@RequiredArgsConstructor
	@Getter
	public static class JoinQuery {
		private final TableDefinition table;
		private final BiPredicate<DataModel, DataModel> join;
	}

}
