package chigirh.app.utility.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

public class RowAutoCreater {

	public static void main(String[] args) {

//		actualWorkRow();
//		actualWorkTaskRow();

//		taskRow();

	}

	private static void actualWorkRow() {
		Def def = Def.builder()//
				.packageName("chigirh.app.utility.app.screen.actualwork")//
				.rowName("ActualWork")
				.pass("src/main/java/chigirh/app/utility/app/screen/actualwork")//
				.keyType("String")//
				.key(Col.builder().name("actualWorkId").type("String").build())//
				.col(Col.builder().name("actualWorkDate").type("String").build())//
				.col(Col.builder().name("actualWorkTime").type("String").build())//
				.col(Col.builder().name("isDeleteChecked").type("Boolean").build())//
				.build();

		create(def);
	}

	private static void actualWorkTaskRow() {
		Def def = Def.builder()//
				.packageName("chigirh.app.utility.app.screen.actualwork")//
				.rowName("ActualWorkTask")
				.pass("src/main/java/chigirh/app/utility/app/screen/actualwork")//
				.keyType("String")//
				.key(Col.builder().name("actualWorkId").type("String").build())//
				.key(Col.builder().name("serial").type("String").build())//
				.col(Col.builder().name("classification1").type("String").build())//
				.col(Col.builder().name("classification2").type("String").build())//
				.col(Col.builder().name("taskName").type("String").build())//
				.col(Col.builder().name("taskTime").type("String").build())//
				.col(Col.builder().name("isDeleteChecked").type("Boolean").build())//
				.build();

		create(def);
	}

	private static void taskRow() {
		Def def = Def.builder()//
				.packageName("chigirh.app.utility.app.screen.taskmgr")//
				.rowName("Task")
				.pass("src/main/java/chigirh/app/utility/app/screen/taskmgr")//
				.keyType("String")//
				.key(Col.builder().name("taskId").type("String").build())//
				.col(Col.builder().name("taskName").type("String").build())//
				.col(Col.builder().name("statusId").type("String").build())//
				.col(Col.builder().name("limitDate").type("String").build())//
				.col(Col.builder().name("startDate").type("String").build())//
				.col(Col.builder().name("updateDate").type("String").build())//
				.col(Col.builder().name("isDeleteChecked").type("Boolean").build())//
				.build();

		create(def);
	}

	private static void create(Def def) {
		try (PrintWriter pw = new PrintWriter(
				new BufferedWriter(new FileWriter(def.getPass() + "/" + def.getRowName() + "Row.java", false)));) {

			pw.println("package " + def.getPackageName() + ";");
			pw.println();
			pw.println("import chigirh.app.utility.javafx.component.SimpleTableRow;");
			pw.println("import javafx.beans.property.SimpleStringProperty;");
			pw.println("import javafx.beans.property.StringProperty;");
			pw.println("import javafx.beans.property.SimpleBooleanProperty;");
			pw.println("import javafx.beans.property.BooleanProperty;");
			pw.println("import lombok.Getter;");
			pw.println("import lombok.RequiredArgsConstructor;");
			pw.println();
			pw.println("/**");
			pw.println("* 自動生成.");
			pw.println("* <p>");
			pw.println("* @author chigirh.app.utility.tools.RowAutoCreater");
			pw.println("*/");
			pw.println("@RequiredArgsConstructor");
			pw.println("public class " + def.getRowName() + "Row implements SimpleTableRow<" + def.getKeyType() + ">{");
			pw.println();

			for (Col col : def.getKeys()) {
				pw.println("	@Getter");
				pw.println("	private final " + col.getType() + " " + col.getName() + ";");
				pw.println();
			}


			for (Col col : def.getCols()) {
				pw.println("	private " + col.getType() + "Property " + col.getName() + "Property = new Simple"
						+ col.getType() + "Property(this,\"" + col.getName() + "\");");
				pw.println();
			}

			pw.println("	@Override");
			pw.println("	public String getKey() {");
			pw.println("		return " + def.getKeys().stream().map(Col::getName).collect(Collectors.joining("+")) + ";");
			pw.println("	}");
			pw.println("");

			for (Col col : def.getCols()) {

				pw.println("	public " + col.getType() + "Property " + col.getName() + "Property() {");
				pw.println("		return " + col.getName() + "Property;");
				pw.println("	}");
				pw.println();
				pw.println("	public void set" + col.getName().substring(0, 1).toUpperCase() + col.getName().substring(1)
						+ "(" + col.getType() + " " + col.getName() + ") {");
				pw.println("		"+col.getName() + "Property.set(" + col.getName() + ");");
				pw.println("	}");
				pw.println();
				pw.println("	public " + col.getType() + " get" + col.getName().substring(0, 1).toUpperCase()
						+ col.getName().substring(1)
						+ "() {");
				pw.println("		return " + col.getName() + "Property.get();");
				pw.println("	}");
				pw.println();
			}

			pw.println("}");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Builder
	@Getter
	static class Def {

		private String packageName;

		private String rowName;

		private String pass;

		private String keyType;

		@Singular
		private List<Col> keys;

		@Singular
		private List<Col> cols;

	}

	@Getter
	@Builder
	static class Col {

		private String name;

		String type;

	}

}
