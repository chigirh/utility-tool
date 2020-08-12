package chigirh.app.utility.javafx.component.taskmgr;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import chigirh.app.utility.app.domain.taskmgr.TaskStatusEntity;
import chigirh.app.utility.app.screen.taskmgr.TaskRow;
import chigirh.app.utility.javafx.component.PseudoClassConstans;
import chigirh.app.utility.javafx.component.TableRow.RowType;
import chigirh.app.utility.javafx.component.TableRowObject;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskManagerTableRowObject extends TableRowObject<TaskManagerTableRowObject> {

	private ObjectProperty<TaskManagerTableRow> tableRowProperty = new SimpleObjectProperty<>();

	private TaskRow vm;

	public TaskManagerTableRowObject(RowType rowType, TaskRow vm) {
		super(rowType);
		this.vm = vm;
		tableRowProperty.addListener(this::rowChange);
		vm.limitDateProperty().addListener(this::limitChange);
		vm.selectedStatusProperty().addListener(this::statusChange);
		;
	}

	void setTableRow(TaskManagerTableRow tableRow) {
		tableRowProperty.set(tableRow);
	}

	private void statusChange() {

		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.LIMIT, false);
		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.DAY_BEFORE, false);
		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.DURING, false);
		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.RV, false);
		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.COMPLETE, false);
		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.HOLD, false);
		tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.UN, false);

		//期限チェック
		String toDay = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		try {

			if (0 <= toDay.compareTo(vm.getLimitDate().substring(0, 10))) {
				tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.LIMIT, true);
				return;
			}
		} catch (Exception e) {
		}

		//期限一日前チェク

		//ステータス
		if ("1".equals(vm.getSelectedStatus().getValue().getStatusId())) {
			tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.DURING, true);
		}
		if ("2".equals(vm.getSelectedStatus().getValue().getStatusId())) {
			tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.RV, true);
		}
		if ("3".equals(vm.getSelectedStatus().getValue().getStatusId())) {
			tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.COMPLETE, true);
		}
		if ("4".equals(vm.getSelectedStatus().getValue().getStatusId())) {
			tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.HOLD, true);
		}
		if ("5".equals(vm.getSelectedStatus().getValue().getStatusId())) {
			tableRowProperty.get().pseudoClassStateChanged(PseudoClassConstans.UN, true);
		}
	}

	private void rowChange(ObservableValue<? extends TaskManagerTableRow> observable,
			TaskManagerTableRow oldValue, TaskManagerTableRow newValue) {
		if (oldValue != null) {
			oldValue.pseudoClassStateChanged(PseudoClassConstans.LIMIT, false);
			oldValue.pseudoClassStateChanged(PseudoClassConstans.DAY_BEFORE, false);
			oldValue.pseudoClassStateChanged(PseudoClassConstans.DURING, false);
			oldValue.pseudoClassStateChanged(PseudoClassConstans.RV, false);
			oldValue.pseudoClassStateChanged(PseudoClassConstans.COMPLETE, false);
			oldValue.pseudoClassStateChanged(PseudoClassConstans.HOLD, false);
			oldValue.pseudoClassStateChanged(PseudoClassConstans.UN, false);
		}
		if (newValue != null) {
			statusChange();
		}
	}

	private void limitChange(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
		if (tableRowProperty.get() == null) {
			return;
		}
		statusChange();
	}

	private void statusChange(ObservableValue<? extends UtlLabelValueBean<TaskStatusEntity>> observable,
			UtlLabelValueBean<TaskStatusEntity> oldValue, UtlLabelValueBean<TaskStatusEntity> newValue) {
		if (tableRowProperty.get() == null) {
			return;
		}
		statusChange();
	}
}
