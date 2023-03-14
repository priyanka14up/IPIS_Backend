package com.innobitsysytems.ipis.services.taurus_sdk;
import javafx.beans.property.SimpleStringProperty;
import novj.platform.vxkit.common.bean.VideoTimingPolicy;

public class PolicyInfo {
	
	 private SimpleStringProperty time = new SimpleStringProperty();
	    private SimpleStringProperty type = new SimpleStringProperty();
	    private SimpleStringProperty repeat = new SimpleStringProperty();
	    private SimpleStringProperty isEnable = new SimpleStringProperty();

	    public String getTime() {
	        return time.get();
	    }

	    public SimpleStringProperty timeProperty() {
	        return time;
	    }

	    public void setTime(String time) {
	        this.time.set(time);
	    }

	    public String getType() {
	        return type.get();
	    }

	    public SimpleStringProperty typeProperty() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type.set(type);
	    }

	    public String getRepeat() {
	        return repeat.get();
	    }

	    public SimpleStringProperty repeatProperty() {
	        return repeat;
	    }

	    public void setRepeat(String repeat) {
	        this.repeat.set(repeat);
	    }

	    public String getIsEnable() {
	        return isEnable.get();
	    }

	    public SimpleStringProperty isEnableProperty() {
	        return isEnable;
	    }

	    public void setIsEnable(String isEnable) {
	        this.isEnable.set(isEnable);
	    }

	    public VideoTimingPolicy videoTimingPolicy = new VideoTimingPolicy("", 0, true);
	}

