package chigirh.app.utility.javafx.helper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.window.ContentViewAndPresenter;
import chigirh.app.utility.javafx.window.ViewAndPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;

/**
 * FXMLファイルを読み込むヘルパー.
 * @author Hiroki.Chigira
 *
 */
@Component
@RequiredArgsConstructor
public class FxmlLoadHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(FxmlLoadHelper.class);

	final ApplicationContext applicationContext;

	public ViewAndPresenter load(final String fxml) throws RuntimeException {

		LOGGER.info("FXML Load start ; {}", fxml);

		final FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		loader.setControllerFactory(applicationContext::getBean);

		Parent view;
		try {
			view = loader.load();
		} catch (IOException e) {
			LOGGER.error("FXML Load Eroor ; {}", fxml);
			throw new RuntimeException(e);
		}
		PresenterBase presenter = loader.getController();

		return new ViewAndPresenter(view, presenter);

	};

	public <E extends PresenterBase> ContentViewAndPresenter<E> loadContent(final String fxml) throws RuntimeException {

		LOGGER.info("FXML Load start ; {}", fxml);

		final FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		loader.setControllerFactory(applicationContext::getBean);

		Parent view;
		try {
			view = loader.load();
		} catch (IOException e) {
			LOGGER.error("FXML Load Eroor ; {}", fxml);
			throw new RuntimeException(e);
		}
		E presenter = loader.getController();

		return new ContentViewAndPresenter<E>(view, presenter);

	};

}
