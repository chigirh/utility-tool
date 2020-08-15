package chigirh.app.utility.javafx.component.table;

import chigirh.app.utility.javafx.component.UtlTextField;
import javafx.beans.property.StringProperty;

public class TextTableCell extends TableCell<UtlTextField, StringProperty> {

	public TextTableCell(boolean isEditable) {
		this("", isEditable);
	}

	public TextTableCell(String text) {
		this(text, false);
	}

	public TextTableCell(String text, boolean isEditable) {
		super();
		setCell(new UtlTextField());
		getCell().setText(text);
		getCell().setEditable(isEditable);

		getCell().focusedProperty().addListener((ob, ov, nv) -> {
			if (nv)
				if(!getCell().isEditable()){
					requestFocus();
				}
		});
	}

	public void change(Runnable task) {
		getCell().confirmNoticeProperty().addListener((ob,ov,nv) -> task.run());
	}

	@Override
	public StringProperty getProperty() {
		return getCell().textProperty();
	}

	public void setValidator(String validator) {
		getCell().setValidator(validator);
	}

}
