package chigirh.app.utility.app.screen.shortcut;

import chigirh.app.utility.javafx.component.table.SimpleTableRow;
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
public class ShortcutRow implements SimpleTableRow<String>{

	@Getter
	private final String scId;

	private StringProperty scTitleProperty = new SimpleStringProperty(this,"scTitle");

	private StringProperty scPathProperty = new SimpleStringProperty(this,"scPath");

	private BooleanProperty isDeleteCheckedProperty = new SimpleBooleanProperty(this,"isDeleteChecked");

	@Override
	public String getKey() {
		return scId;
	}

	public StringProperty scTitleProperty() {
		return scTitleProperty;
	}

	public void setScTitle(String scTitle) {
		scTitleProperty.set(scTitle);
	}

	public String getScTitle() {
		return scTitleProperty.get();
	}

	public StringProperty scPathProperty() {
		return scPathProperty;
	}

	public void setScPath(String scPath) {
		scPathProperty.set(scPath);
	}

	public String getScPath() {
		return scPathProperty.get();
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
