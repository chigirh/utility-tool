package chigirh.app.utility.app.screen.taskmgr;

import java.util.List;

import chigirh.app.utility.app.domain.taskmgr.TaskStatusEntity;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.table.SimpleTableRow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class TaskRow implements SimpleTableRow<String> {

	@Getter
	private final String taskId;

	private StringProperty taskNameProperty = new SimpleStringProperty(this, "taskName");

	private ObjectProperty<UtlLabelValueBean<TaskStatusEntity>> selectedStatusProperty = new SimpleObjectProperty<UtlLabelValueBean<TaskStatusEntity>>(
			this, "selectedStatusProperty");

	private ObjectProperty<ObservableList<UtlLabelValueBean<TaskStatusEntity>>> statusPropertyListPropery = new SimpleObjectProperty<ObservableList<UtlLabelValueBean<TaskStatusEntity>>>(
			this, "statusPropertyListPropery");

	private StringProperty limitDateProperty = new SimpleStringProperty(this, "limitDate");

	private StringProperty startDateProperty = new SimpleStringProperty(this, "startDate");

	private StringProperty updateDateProperty = new SimpleStringProperty(this, "updateDate");

	private BooleanProperty isDeleteCheckedProperty = new SimpleBooleanProperty(this, "isDeleteChecked");

	@Override
	public String getKey() {
		return taskId;
	}

	public StringProperty taskNameProperty() {
		return taskNameProperty;
	}

	public void setTaskName(String taskName) {
		taskNameProperty.set(taskName);
	}

	public String getTaskName() {
		return taskNameProperty.get();
	}

	public ObjectProperty<UtlLabelValueBean<TaskStatusEntity>> selectedStatusProperty() {
		return selectedStatusProperty;
	}

	public void setSelectedStatus(UtlLabelValueBean<TaskStatusEntity> selectedStatus) {
		selectedStatusProperty.set(selectedStatus);
	}

	public UtlLabelValueBean<TaskStatusEntity> getSelectedStatus() {
		return selectedStatusProperty.get();
	}

	public ObjectProperty<ObservableList<UtlLabelValueBean<TaskStatusEntity>>> statusListPropery() {
		return statusPropertyListPropery;
	}

	public void setStatusPropertyList(List<UtlLabelValueBean<TaskStatusEntity>> statusPropertyList) {
		statusPropertyListPropery.set(FXCollections.observableArrayList(statusPropertyList));
	}

	public StringProperty limitDateProperty() {
		return limitDateProperty;
	}

	public void setLimitDate(String limitDate) {
		limitDateProperty.set(limitDate);
	}

	public String getLimitDate() {
		return limitDateProperty.get();
	}

	public StringProperty startDateProperty() {
		return startDateProperty;
	}

	public void setStartDate(String startDate) {
		startDateProperty.set(startDate);
	}

	public String getStartDate() {
		return startDateProperty.get();
	}

	public StringProperty updateDateProperty() {
		return updateDateProperty;
	}

	public void setUpdateDate(String updateDate) {
		updateDateProperty.set(updateDate);
	}

	public String getUpdateDate() {
		return updateDateProperty.get();
	}

	public BooleanProperty isDeleteCheckedProperty() {
		return isDeleteCheckedProperty;
	}

	public void setIsDeleteChecked(Boolean isDeleteChecked) {
		isDeleteCheckedProperty.set(isDeleteChecked);
	}

	public Boolean getIsDeleteChecked() {
		return isDeleteCheckedProperty.get();
	}

}
