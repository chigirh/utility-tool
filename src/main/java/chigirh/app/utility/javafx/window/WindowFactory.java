package chigirh.app.utility.javafx.window;

import org.springframework.stereotype.Component;

import chigirh.app.utility.common.prop.CssProperties;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.helper.FxmlLoadHelper;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;

/**
 * ウィンドウファクトリ.
 * @author Hiroki.Chigira
 *
 */
@Component
@RequiredArgsConstructor
public class WindowFactory {

	final FxmlLoadHelper loadHelper;

	final CssProperties cssProperties;

	final FxmlProperties fxmlProperties;

	public NormarWIndow createWindow(final String fxml) {

		return createWindow(fxml, null);

	}

	public NormarWIndow createWindow(final String fxml, Object windowParam) {

		ViewAndPresenter normalWindowVP = loadHelper.load(fxmlProperties.getNormalWindow());
		AnchorPane normarWindow = (AnchorPane) normalWindowVP.getView();

		ViewAndPresenter viewAndPresenter = loadHelper.load(fxml);
		normarWindow.getChildren().add(viewAndPresenter.getView());

		NormarWIndow window = new NormarWIndow(new ViewAndPresenter(normarWindow, viewAndPresenter.getPresenter()),
				windowParam);

		window.applyCss(cssProperties.getCommon());

		return window;

	}

}
