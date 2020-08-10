package chigirh.app.utility.dataacces.common.definition;

import chigirh.app.utility.dataacces.common.common.ComparativeExpression;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ConstraintDefinition {

	private boolean notNullConstraint = false;

	private ComparativeExpression checkConstraint = null;

	private boolean uniqueKeyConstraint = false;

	private boolean primaryKeyConstraint = false;

	private ColumnDefinition refferences = null;

	public static ConstraintDefinitionBuilder newConstraint() {
		ConstraintDefinition constraint = new ConstraintDefinition();
		return constraint.new ConstraintDefinitionBuilder(constraint);
	}

	public static ConstraintDefinition noneConstraint() {
		return new ConstraintDefinition();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class ConstraintDefinitionBuilder {
		private final ConstraintDefinition constraint;

		public ConstraintDefinitionBuilder NOT_NULL() {
			constraint.notNullConstraint = true;
			return this;
		}

		public ConstraintDefinitionBuilder CHECK(ComparativeExpression comparativeExpression) {
			constraint.checkConstraint = comparativeExpression;
			return this;

		}

		public ConstraintDefinitionBuilder UNIQUE_KEY() {
			constraint.uniqueKeyConstraint = true;
			return this;

		}

		public ConstraintDefinitionBuilder PRIMARY_KEY() {
			NOT_NULL();
			constraint.primaryKeyConstraint = true;
			return this;

		}

		public ForeignInDefinitionBuilder FOREIGN_KEY() {
			return constraint.new ForeignInDefinitionBuilder(constraint, this);
		}

		public ConstraintDefinition complete() {
			return constraint;
		}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class ForeignInDefinitionBuilder {
		private final ConstraintDefinition constraint;
		private final ConstraintDefinitionBuilder constraintDefinitionBuilder;

		public ConstraintDefinitionBuilder REFFERENCES(ColumnDefinition refferences) {
			constraint.refferences = refferences;
			return constraintDefinitionBuilder;
		}
	}

}
