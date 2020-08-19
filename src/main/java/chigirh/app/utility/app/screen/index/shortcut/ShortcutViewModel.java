package chigirh.app.utility.app.screen.index.shortcut;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class ShortcutViewModel {

	private StringProperty scAddTfProperty = new SimpleStringProperty(this, "scAddTf");

	public StringProperty scAddTfProperty() {
		return scAddTfProperty;
	}

	public void setScAddTf(String scAddTf) {
		scAddTfProperty.set(scAddTf);
	}

	public String getScAddTf() {
		return scAddTfProperty.get();
	}

}
