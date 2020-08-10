package chigirh.app.utility.app.screen.actualwork;

import java.util.ArrayList;
import java.util.List;

import chigirh.app.utility.javafx.component.SimpleTableRow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class ActualWorkRow implements SimpleTableRow<String> {

	@Getter
	private final String actualWorkId;

	private StringProperty actualWorkDateProperty = new SimpleStringProperty(this, "actualWorkDate");

	private StringProperty actualWorkTimeProperty = new SimpleStringProperty(this, "actualWorkTime");

	private BooleanProperty isDeleteCheckedProperty = new SimpleBooleanProperty(this, "isDeleteChecked");

	@Getter
	private List<ActualWorkTaskRow> awTasks = new ArrayList<>();

	@Override
	public String getKey() {
		return actualWorkId;
	}

	public StringProperty actualWorkDateProperty() {
		return actualWorkDateProperty;
	}

	public void setActualWorkDate(String actualWorkDate) {
		actualWorkDateProperty.set(actualWorkDate);
	}

	public String getActualWorkDate() {
		return actualWorkDateProperty.get();
	}

	public StringProperty actualWorkTimeProperty() {
		return actualWorkTimeProperty;
	}

	public void setActualWorkTime(String actualWorkTime) {
		actualWorkTimeProperty.set(actualWorkTime);
	}

	public String getActualWorkTime() {
		return actualWorkTimeProperty.get();
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
