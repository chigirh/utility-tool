package chigirh.app.utility.dbinit;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

@Getter
@Builder(builderMethodName="CREATE_TABLE")
class CreateTableSQL {

	@NonNull
	private String tableName;

	@Singular
	private final List<Col> columns;

	@Singular
	private final List<Fk> fks;

	@Getter
	@Builder
	static class Col{

		@NonNull
		private int index;

		@NonNull
		private String name;

		@NonNull
		private Class<?> type;

		private boolean isPk = false;

		private boolean isNotNull = false;

		private boolean isUnique = false;
	}

	@Getter
	@Builder
	static class Fk{
		@NonNull
		private String col;

		@NonNull
		private String refTab;

		@NonNull
		private String refCol;

		private boolean onUpdateError = false;

		private boolean onDeleteError = false;

		private boolean setNull = false;


	}


}
