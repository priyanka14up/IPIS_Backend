package com.innobitsysytems.ipis.services.taurus_sdk;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import novj.platform.vxkit.common.bean.search.SearchResult;

public class DeviceInfo {
	private SimpleStringProperty sn = new SimpleStringProperty();
	private SimpleStringProperty productName = new SimpleStringProperty();
	private SimpleStringProperty aliasName = new SimpleStringProperty();
	private SimpleStringProperty ipAddress = new SimpleStringProperty();
	private SimpleBooleanProperty logined = new SimpleBooleanProperty();
	private SimpleObjectProperty connectBtn = new SimpleObjectProperty();

	public Object getConnectBtn() {
		return connectBtn.get();
	}

	public SimpleObjectProperty connectBtnProperty() {
		return connectBtn;
	}

	public void setConnectBtn(Object connectBtn) {
		this.connectBtn.set(connectBtn);
	}

	public DeviceInfo() {

	}

	public DeviceInfo(String _sn, String _productName, String _aliasName, String _ipAddress, boolean _logined) {
		setSn(_sn);
		setProductName(_productName);
		setAliasName(_aliasName);
		setIpAddress(_ipAddress);
		setLogined(_logined);
	}

	public String getSn() {
		return sn.get();
	}

	public SimpleStringProperty snProperty() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn.set(sn);
	}

	public String getProductName() {
		return productName.get();
	}

	public SimpleStringProperty productNameProperty() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName.set(productName);
	}

	public String getAliasName() {
		return aliasName.get();
	}

	public SimpleStringProperty aliasNameProperty() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName.set(aliasName);
	}

	public String getIpAddress() {
		return ipAddress.get();
	}

	public SimpleStringProperty ipAddressProperty() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress.set(ipAddress);
	}

	public boolean isLogined() {
		return logined.get();
	}

	public SimpleBooleanProperty loginedProperty() {
		return logined;
	}

	public void setLogined(boolean logined) {
		this.logined.set(logined);
	}

	public SearchResult searchResult = null;

}
