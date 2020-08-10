package chigirh.app.utility.javafx.component.actualwork;

import javafx.scene.control.TextField;

public class UtlTextField extends TextField {

	private static final String STYLE_CLASS = "utl-text-field";

	public UtlTextField() {
		this("");
	}

	public UtlTextField(String text) {
		super(text);
		getStyleClass().add(STYLE_CLASS);
	}

}
