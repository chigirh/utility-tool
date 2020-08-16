package chigirh.app.utility.app.screen.actualwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class ActualWorkViewModel {

	private StringProperty awAddTfProperty = new SimpleStringProperty(this,"awAddTf");

	public StringProperty awAddTfProperty() {
		return awAddTfProperty;
	}

	public void setAwAddTf(String awAddTf) {
		awAddTfProperty.set(awAddTf);
	}

	public String getAwAddTf() {
		return awAddTfProperty.get();
	}

}
