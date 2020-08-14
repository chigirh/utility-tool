package chigirh.app.utility.app.screen.actualwork;

import java.util.ArrayList;
import java.util.List;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation1Entity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation2Entity;
import chigirh.app.utility.javafx.component.SimpleTableRow;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
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
public class ActualWorkTaskRow implements SimpleTableRow<String> {

	@Getter
	private final String actualWorkId;

	@Getter
	private final String serial;

	private ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation1Entity>> selectedClassification1Property = new SimpleObjectProperty<>(
			this, "selectedClassification1Property");

	private ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation1Entity>>> classification1PropertyListPropery = new SimpleObjectProperty<>(
			this, "classification1PropertyListPropery", FXCollections.observableArrayList(new ArrayList<>()));

	private ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation2Entity>> selectedClassification2Property = new SimpleObjectProperty<>(
			this, "selectedClassification2Property");

	private ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation2Entity>>> classification2PropertyListPropery = new SimpleObjectProperty<>(
			this, "classification2PropertyListPropery", FXCollections.observableArrayList(new ArrayList<>()));

	private StringProperty taskNameProperty = new SimpleStringProperty(this, "taskName");

	private StringProperty taskTimeProperty = new SimpleStringProperty(this, "taskTime");

	private BooleanProperty isDeleteCheckedProperty = new SimpleBooleanProperty(this, "isDeleteChecked");

	@Override
	public String getKey() {
		return actualWorkId + serial;
	}

	public ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation1Entity>> selectedClassification1Property() {
		return selectedClassification1Property;
	}

	public void setSelectedClassification1(UtlLabelValueBean<ActualWorkClassifcation1Entity> selectedClassification1) {
		selectedClassification1Property.set(selectedClassification1);
	}

	public UtlLabelValueBean<ActualWorkClassifcation1Entity> getSelectedClassification1() {
		return selectedClassification1Property.get();
	}

	public ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation1Entity>>> classification1ListPropery() {
		return classification1PropertyListPropery;
	}

	public void setClassification1PropertyList(
			List<UtlLabelValueBean<ActualWorkClassifcation1Entity>> classification1PropertyList) {
		classification1PropertyListPropery.set(FXCollections.observableArrayList(classification1PropertyList));
	}

	public ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation2Entity>> selectedClassification2Property() {
		return selectedClassification2Property;
	}

	public void setSelectedClassification2(UtlLabelValueBean<ActualWorkClassifcation2Entity> selectedClassification2) {
		selectedClassification2Property.set(selectedClassification2);
	}

	public UtlLabelValueBean<ActualWorkClassifcation2Entity> getSelectedClassification2() {
		return selectedClassification2Property.get();
	}

	public ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation2Entity>>> classification2ListPropery() {
		return classification2PropertyListPropery;
	}

	public void setClassification2PropertyList(
			List<UtlLabelValueBean<ActualWorkClassifcation2Entity>> classification2PropertyList) {
		classification2PropertyListPropery.set(FXCollections.observableArrayList(classification2PropertyList));
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

	public StringProperty taskTimeProperty() {
		return taskTimeProperty;
	}

	public void setTaskTime(String taskTime) {
		taskTimeProperty.set(taskTime);
	}

	public String getTaskTime() {
		return taskTimeProperty.get();
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
