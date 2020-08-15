package chigirh.app.utility.javafx.component.taskmgr;


import chigirh.app.utility.app.domain.taskmgr.TaskStatusEntity;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.table.TableRow;
import chigirh.app.utility.javafx.component.table.TextTableCell;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;

public class TaskManagerTableRow extends TableRow<TaskManagerTableRowObject> {

	ObjectProperty<UtlLabelValueBean<TaskStatusEntity>> statusProperty;


	public TaskManagerTableRow() {
		super();
	}

	@Override
	protected void changed(ObservableValue<? extends TaskManagerTableRowObject> observable,
			TaskManagerTableRowObject oldValue, TaskManagerTableRowObject newValue) {
		row.getChildren().clear();
		if(oldValue != null) {
			oldValue.setTableRow(null);
		}
		if (newValue == null || newValue.getRowType() == RowType.EMPTY) {
			HBox hBox = new HBox();
			TextTableCell emptyCell = new TextTableCell("");
			hBox.getChildren().add(emptyCell);
			emptyCell.setPrefWidth(Double.MAX_VALUE);
			row.getChildren().add(hBox);
			return;
		}
		newValue.setTableRow(this);
		TextTableCell stsCell = new TextTableCell("");
		stsCell .setWidth(20.0);
		stsCell .getStyleClass().add("taskmgr-sts");
		row.getChildren().add(stsCell );
		row.getChildren().add(newValue.createRow());
	}

}
