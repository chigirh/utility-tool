package chigirh.app.utility.app.screen.index;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.screen.index.actualwork.ActualWorkIndexPresenter;
import chigirh.app.utility.app.screen.index.taskmgr.TaskManagerIndexPresenter;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.window.ContentViewAndPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class RootIndexPresenter extends PresenterBase {

	@FXML
	private Tab operationTab;


	@FXML
	private Tab taskmgrTab;

	@FXML
	private ScrollPane shortcutScroll;

	@FXML
	private VBox shortcutBox;

	final FxmlProperties fxmlProperties;

	private ActualWorkIndexPresenter operationIndexPresenter;

	private TaskManagerIndexPresenter taskManagerIndexPresenter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		shortcutScroll.setHbarPolicy(ScrollBarPolicy.NEVER);

		String operationIndexFxml = fxmlProperties.getActualWorkIndex();
		ContentViewAndPresenter<ActualWorkIndexPresenter> operationVP = loadContent(operationIndexFxml);
		operationIndexPresenter = operationVP.getPresenter();
		operationTab.setContent(operationVP.getView());

		String taskmgrIndexFxml = fxmlProperties.getTaskManagerIndex();
		ContentViewAndPresenter<TaskManagerIndexPresenter> taskmgrVP = loadContent(taskmgrIndexFxml);
		taskManagerIndexPresenter = taskmgrVP.getPresenter();
		taskmgrTab.setContent(taskmgrVP.getView());

	}
}
