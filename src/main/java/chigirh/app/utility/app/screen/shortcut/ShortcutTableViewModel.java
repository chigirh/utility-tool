package chigirh.app.utility.app.screen.shortcut;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class ShortcutTableViewModel {

	private StringProperty scTitleAddTfProperty = new SimpleStringProperty(this, "scTitleAddTf");

	private StringProperty scPathAddTfProperty = new SimpleStringProperty(this, "scPathAddTf");

	public StringProperty scTitleAddTfProperty() {
		return scTitleAddTfProperty;
	}

	public void setScTitleAddTf(String scTitleAddTf) {
		scTitleAddTfProperty.set(scTitleAddTf);
	}

	public String getScTitleAddTf() {
		return scTitleAddTfProperty.get();
	}

	public StringProperty scPathAddTfProperty() {
		return scPathAddTfProperty;
	}

	public void setScPathAddTf(String scPathAddTf) {
		scPathAddTfProperty.set(scPathAddTf);
	}

	public String getScPathAddTf() {
		return scPathAddTfProperty.get();
	}

}
