package chigirh.app.utility.app.screen.taskmgr;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.RequiredArgsConstructor;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
@RequiredArgsConstructor
public class TaskManagerViewModel {

	private BooleanProperty notYetCbProperty = new SimpleBooleanProperty(this, "notYetCb",true);

	private BooleanProperty duringCbProperty = new SimpleBooleanProperty(this, "duringCb",true);

	private BooleanProperty rvCbProperty = new SimpleBooleanProperty(this, "rvCb",true);

	private BooleanProperty completeCbProperty = new SimpleBooleanProperty(this, "completeCb",false);

	private BooleanProperty holdCbProperty = new SimpleBooleanProperty(this, "holdCb",true);

	private BooleanProperty unCbProperty = new SimpleBooleanProperty(this, "unCb",false);

	private StringProperty addTfProperty = new SimpleStringProperty(this, "addTf");

	private StringProperty addLimitTfProperty = new SimpleStringProperty(this, "addLimitTf");

	public BooleanProperty notYetCbProperty() {
		return notYetCbProperty;
	}

	public void setNotYetCb(Boolean notYetCb) {
		notYetCbProperty.set(notYetCb);
	}

	public Boolean getNotYetCb() {
		return notYetCbProperty.get();
	}

	public BooleanProperty duringCbProperty() {
		return duringCbProperty;
	}

	public void setDuringCb(Boolean duringCb) {
		duringCbProperty.set(duringCb);
	}

	public Boolean getDuringCb() {
		return duringCbProperty.get();
	}

	public BooleanProperty rvCbProperty() {
		return rvCbProperty;
	}

	public void setRvCb(Boolean rvCb) {
		rvCbProperty.set(rvCb);
	}

	public Boolean getRvCb() {
		return rvCbProperty.get();
	}

	public BooleanProperty completeCbProperty() {
		return completeCbProperty;
	}

	public void setCompleteCb(Boolean completeCb) {
		completeCbProperty.set(completeCb);
	}

	public Boolean getCompleteCb() {
		return completeCbProperty.get();
	}

	public BooleanProperty holdCbProperty() {
		return holdCbProperty;
	}

	public void setHoldCb(Boolean holdCb) {
		holdCbProperty.set(holdCb);
	}

	public Boolean getHoldCb() {
		return holdCbProperty.get();
	}

	public BooleanProperty unCbProperty() {
		return unCbProperty;
	}

	public void setUnCb(Boolean unCb) {
		unCbProperty.set(unCb);
	}

	public Boolean getUnCb() {
		return unCbProperty.get();
	}

	public StringProperty addTfProperty() {
		return addTfProperty;
	}

	public void setAddTf(String addTf) {
		addTfProperty.set(addTf);
	}

	public String getAddTf() {
		return addTfProperty.get();
	}

	public StringProperty addLimitTfProperty() {
		return addLimitTfProperty;
	}

	public void setAddLimitTf(String addLimitTf) {
		addLimitTfProperty.set(addLimitTf);
	}

	public String getAddLimitTf() {
		return addLimitTfProperty.get();
	}

}
