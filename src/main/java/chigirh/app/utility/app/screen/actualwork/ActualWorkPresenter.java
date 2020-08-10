package chigirh.app.utility.app.screen.actualwork;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.actualwork.ActualWorkEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkGroupEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkService;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.component.PseudoClassConstans;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.util.JavaFxTextFieldUtils;
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
public class ActualWorkPresenter extends PresenterBase {

	private static final String AW_DATE_PAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

	@FXML
	private AnchorPane tableArea;

	@FXML
	private TextField awAddTf;

	@FXML
	private Button awAddBt;

	@FXML
	private Button awDeleteBt;

	final FxmlProperties fxmlProperties;

	final ActualWorkService actualWorkService;

	private ActualWorkGroupEntity windowParam;

	private ActualWorkTablePresenter actualWorkTablePresenter;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String tableFxml = fxmlProperties.getActualWorkTable();

		ContentViewAndPresenter<ActualWorkTablePresenter> vp = loadContent(tableFxml);
		actualWorkTablePresenter = vp.getPresenter();
		tableArea.getChildren().add(vp.getView());
	}

	public  void setPatameter(Object windowParam) {
		if(windowParam instanceof ActualWorkGroupEntity) {
			this.windowParam = (ActualWorkGroupEntity)windowParam;
			actualWorkTablePresenter.setPatameter(windowParam);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		actualWorkTablePresenter.update();
	}


	@FXML
	public void onAwAdd(ActionEvent e) throws ParseException{


		if(!JavaFxTextFieldUtils.inputCheck(awAddTf, AW_DATE_PAT)) {
			awAddTf.pseudoClassStateChanged(PseudoClassConstans.ERROR, !StringUtils.isEmpty(awAddTf.getText()));
			return;
		}

		ActualWorkEntity entity = actualWorkService.awAdd(windowParam.getAwGroupId(),awAddTf.getText(),null);
		if(entity == null) {
			return;
		}
		awAddTf.pseudoClassStateChanged(PseudoClassConstans.ERROR, false);
		awAddTf.setText("");

		actualWorkTablePresenter.update();
	}

	@FXML
	public void onAwDelete(ActionEvent e) throws ParseException{
		actualWorkTablePresenter.delete();
	}

}
