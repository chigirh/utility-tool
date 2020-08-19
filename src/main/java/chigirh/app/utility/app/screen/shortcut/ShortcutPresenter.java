package chigirh.app.utility.app.screen.shortcut;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.shortcut.ShortcutGroupEntity;
import chigirh.app.utility.app.domain.shortcut.ShortcutService;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.component.UtlTextField;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import chigirh.app.utility.javafx.window.ContentViewAndPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ShortcutPresenter extends PresenterBase {

	private ShortcutTableViewModel vm = new ShortcutTableViewModel();

	@FXML
	private AnchorPane tableArea;

	@FXML
	private UtlTextField addTitleTf;

	@FXML
	private UtlTextField addPathTf;

	@FXML
	private Button addBt;

	@FXML
	private Button deleteBt;

	final FxmlProperties fxmlProperties;

	final ShortcutService shortcutService;

	private ShortcutTablePresenter shortcutTablePresenter;

	private ShortcutGroupEntity windowParam = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String tableFxml = fxmlProperties.getShortcutTable();

		ContentViewAndPresenter<ShortcutTablePresenter> vp = loadContent(tableFxml);
		shortcutTablePresenter = vp.getPresenter();
		tableArea.getChildren().add(vp.getView());

		JavaFxBindingUtils.bindingNode(addTitleTf, vm.scTitleAddTfProperty());
		JavaFxBindingUtils.bindingNode(addPathTf, vm.scPathAddTfProperty());
	}

	@Override
	public void setPatameter(Object windowParam) {
		if (windowParam instanceof ShortcutGroupEntity) {
			this.windowParam = (ShortcutGroupEntity) windowParam;
			shortcutTablePresenter.setPatameter(windowParam);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@FXML
	public void onScAdd(ActionEvent e) throws ParseException {
		shortcutService.scAdd(windowParam.getScGroupId(), vm.getScTitleAddTf(), vm.getScPathAddTf());
		shortcutTablePresenter.update();

		vm.setScTitleAddTf("");
		vm.setScPathAddTf("");

	}

	@FXML
	public void onScDelete(ActionEvent e) throws ParseException {
		shortcutTablePresenter.delete();
		shortcutTablePresenter.update();
	}

}
