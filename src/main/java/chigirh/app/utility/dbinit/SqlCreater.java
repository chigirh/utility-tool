package chigirh.app.utility.dbinit;

import java.util.stream.Collectors;

import chigirh.app.utility.dbinit.CreateTableSQL.Col;
import chigirh.app.utility.dbinit.CreateTableSQL.Fk;

class SqlCreater {

	private StringBuilder sb = new StringBuilder();

	SqlCreater(CreateTableSQL sql) {

		//テーブルが無ければ作成
		sb.append("CREATE TABLE IF NOT EXISTS ");

		//テーブル名
		sb.append(sql.getTableName()).append("(");

		//列定義追加
		sql.getColumns().stream().sorted((c1, c2) -> c1.getIndex() - c2.getIndex()).forEach(this::createColDef);

		//主キー制約
		sb.append("PRIMARY KEY(");
		sb.append(sql.getColumns().stream().filter(Col::isPk).map(Col::getName).collect(Collectors.joining(", ")));
		sb.append(")");

		//外部キー制約
		sql.getFks().forEach(e -> createFk(sql.getTableName(), e));

		sb.append(");");

	}

	String create() {
		return sb.toString();
	}

	private void createColDef(Col col) {
		//列名
		sb.append(col.getName());
		//型名
		if (col.getType() == Integer.class) {
			sb.append(" INTEGER");
		}

		if (col.getType() == Double.class) {
			sb.append(" REAL");
		}

		if (col.getType() == String.class) {
			sb.append(" TEXT");
		}

		//NOT NULL制約
		sb.append(col.isNotNull() ? " NOT NULL" : "");

		//ユニーク制約
		sb.append(col.isUnique() ? "UNIQUE" : "");

		//,
		sb.append(",");
	}

	private void createFk(String tableName,Fk fk) {


		sb.append(",");

		//外部キー制約名
		sb.append("CONSTRAINT");
		sb.append(" fk_").append(tableName).append("_").append(fk.getCol());

		//対象列名
		sb.append(" FOREIGN KEY (").append(fk.getCol()).append(")");

		//参照先
		sb.append(" REFERENCES ").append(fk.getRefTab()).append("(").append(fk.getRefCol()).append(")");
		//参照先更新時
		if(fk.isSetNull()) {
			sb.append("ON DELETE SET NULL ON UPDATE SET NULL");
		}else {
			sb.append("ON DELETE ").append(fk.isOnDeleteError()?"RESTRICT":"CASCADE");
			sb.append(" ON UPDATE ").append(fk.isOnDeleteError()?"RESTRICT":"CASCADE");
		}
	}

}
