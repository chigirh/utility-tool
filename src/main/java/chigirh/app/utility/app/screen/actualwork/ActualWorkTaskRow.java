package chigirh.app.utility.app.screen.actualwork;

import chigirh.app.utility.javafx.component.SimpleTableRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class ActualWorkTaskRow implements SimpleTableRow<String>{

	@Getter
	private final String actualWorkId;

	@Getter
	private final String serial;

	private StringProperty classification1Property = new SimpleStringProperty(this,"classification1");

	private StringProperty classification2Property = new SimpleStringProperty(this,"classification2");

	private StringProperty taskNameProperty = new SimpleStringProperty(this,"taskName");

	private StringProperty taskTimeProperty = new SimpleStringProperty(this,"taskTime");

	private BooleanProperty isDeleteCheckedProperty = new SimpleBooleanProperty(this,"isDeleteChecked");

	@Override
	public String getKey() {
		return actualWorkId+serial;
	}

	public StringProperty classification1Property() {
		return classification1Property;
	}

	public void setClassification1(String classification1) {
		classification1Property.set(classification1);
	}

	public String getClassification1() {
		return classification1Property.get();
	}

	public StringProperty classification2Property() {
		return classification2Property;
	}

	public void setClassification2(String classification2) {
		classification2Property.set(classification2);
	}

	public String getClassification2() {
		return classification2Property.get();
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
