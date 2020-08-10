package chigirh.app.utility.javafx.component;

import javafx.scene.control.TextField;

public class UtlLabel extends TextField{

	private static final String STYLE_CLASS = "utl-label";

	public UtlLabel() {
		this("");
	}

	public UtlLabel(String text) {
		super(text);
		setEditable(false);
		getStyleClass().add(STYLE_CLASS);
	}



}
