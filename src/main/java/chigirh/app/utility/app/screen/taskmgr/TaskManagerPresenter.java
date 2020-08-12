package chigirh.app.utility.app.screen.taskmgr;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.taskmgr.TaskGroupEntity;
import chigirh.app.utility.app.domain.taskmgr.TaskManagerService;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.window.ContentViewAndPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class TaskManagerPresenter extends PresenterBase {

	@FXML
	private AnchorPane tableArea;

	@FXML
	private TextField addTf;

	@FXML
	private TextField addLimitTf;

	@FXML
	private Button addBt;

	@FXML
	private Button deleteBt;

	final FxmlProperties fxmlProperties;

	final TaskManagerService taskManagerService;

	private TaskManagerTablePresenter taskManagerTablePresenter;

	private TaskGroupEntity windowParam = null;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String tableFxml = fxmlProperties.getTaskManagerTable();

		ContentViewAndPresenter<TaskManagerTablePresenter> vp = loadContent(tableFxml);
		taskManagerTablePresenter = vp.getPresenter();
		tableArea.getChildren().add(vp.getView());
	}

	@Override
	public void setPatameter(Object windowParam) {
		if (windowParam instanceof TaskGroupEntity) {
			this.windowParam = (TaskGroupEntity) windowParam;
			taskManagerTablePresenter.setPatameter(windowParam);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	@FXML
	public void onAwAdd(ActionEvent e) throws ParseException{
		taskManagerService.taskAdd(windowParam.getTaskGroupId(),addTf.getText(), addLimitTf.getText());
		taskManagerTablePresenter.update();

	}

	@FXML
	public void onAwDelete(ActionEvent e) throws ParseException{
	}

}
