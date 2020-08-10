package chigirh.app.utility.dataacces.common.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ComparativeExpression {

	private final Predicate<String> comparativeExpression;

	public static ComparativeExpressionBuilder newExpression() {
		return new ComparativeExpressionBuilder();
	}

	public static class ComparativeExpressionBuilder {
		Predicate<String> expression = e -> e != null;

		private boolean NOT = false;

		public ComparativeExpressionBuilder NOT() {
			NOT = true;
			return this;
		}

		public LogicalOperatorBuilder THAN(final int operand) {
			expression = expression.and(e -> operand < Integer.parseInt(e));
			return new LogicalOperatorBuilder(build());
		}

		public LogicalOperatorBuilder LESS(final int operand) {
			expression = expression.and(e -> Integer.parseInt(e) < operand);
			return new LogicalOperatorBuilder(build());
		}

		public LogicalOperatorBuilder EQUAL(final int operand) {
			expression = expression.and(e -> Integer.parseInt(e) == operand);
			return new LogicalOperatorBuilder(build());
		}

		public LogicalOperatorBuilder EQUAL(final String operand) {
			expression = expression.and(e -> StringUtils.equals(e, operand));
			return new LogicalOperatorBuilder(build());
		}

		public LogicalOperatorBuilder IN(final Integer... operand) {
			Set set = new HashSet<>(Arrays.asList(operand));
			expression = expression.and(e -> set.contains(Integer.parseInt(e)));
			return new LogicalOperatorBuilder(build());
		}

		public LogicalOperatorBuilder IN(final String... operand) {
			Set set = new HashSet<>(Arrays.asList(operand));
			expression = expression.and(e -> set.contains(e));
			return new LogicalOperatorBuilder(build());
		}

		public LogicalOperatorBuilder IS_NULL() {
			return new LogicalOperatorBuilder(new ComparativeExpression(e -> e == null));
		}

		public LogicalOperatorBuilder IS_NOT_NULL() {
			return new LogicalOperatorBuilder(new ComparativeExpression(e -> e != null));
		}

		public BetWeenBuilder BETWEEN(final int less) {
			return new BetWeenBuilder(this, less);
		}

		private ComparativeExpression build() {
			return new ComparativeExpression(NOT ? expression.negate() : expression);
		}

	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static class BetWeenBuilder {
		private final ComparativeExpressionBuilder builder;
		private final int less;

		public LogicalOperatorBuilder AND(final int than) {
			builder.expression = builder.expression
					.and(e -> less <= Integer.parseInt(e) && Integer.parseInt(e) <= than);
			return new LogicalOperatorBuilder(builder.build());
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static class LogicalOperatorBuilder {
		private final ComparativeExpression comparativeExpression;

		public LogicalOperatorBuilder AND(ComparativeExpression comparativeExpression) {
			return new LogicalOperatorBuilder(//
					new ComparativeExpression(//
							this.comparativeExpression.getComparativeExpression()
									.and(comparativeExpression.getComparativeExpression())));
		}

		public LogicalOperatorBuilder OR(ComparativeExpression comparativeExpression) {
			return new LogicalOperatorBuilder(//
					new ComparativeExpression(//
							this.comparativeExpression.getComparativeExpression()
									.or(comparativeExpression.getComparativeExpression())));
		}

		public ComparativeExpression complete() {
			return comparativeExpression;
		}

	}

}
