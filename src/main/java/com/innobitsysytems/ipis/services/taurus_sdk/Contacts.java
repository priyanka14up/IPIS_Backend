package com.innobitsysytems.ipis.services.taurus_sdk;



import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.innobitsysytems.ipis.services.taurus_sdk.DeviceInfo;


/**
 * @description:
 * @date: 2018/6/13 0013 16:04:02
 * @author: jiahang
 */
public class Contacts {
    public static DeviceInfo deviceOpt;
    public static int NOW_PLAYLIST_ID;
    public static final String DISPLAY_RATIO = "CUSTOM";
    public static final String PLAYLIST_NAME = "play";
    public static final String PAGE_NAME = "page";
    public static final String PICTURE_NAME = "pic";
    public static final String VIDEO_NAME = "video";
    public static final String GIF_NAME = "gif";
    public static final String TEXT_NAME = "text";
    public static final String DCLOCK_NAME = "dclock";
    public static final String STREAM_NAME = "stream";

    public static String PROGRAM_LOCATION = "C:/sdkprogram/";
//    public static List<DeviceInfo> loginedDeviceInfos = new ArrayList<>();
    public static String timeZonesXml = null;
    public static Locale DEFAULT_LOCALE = Locale.CHINESE;
    public static final String BASE_RESOURCE = "resource";
//    public static Locale DEFAULT_LOCALE = Locale.ENGLISH;
	public static final String DEVICE_SN = null;


//    public static FXMLLoader getFxmlLoader(URL location) {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(location);
//        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//        fxmlLoader.setResources(ResourceBundle.getBundle(BASE_RESOURCE,Contacts.DEFAULT_LOCALE));
//        return fxmlLoader;
//    }

    public static String getResourceValue(String key){
        return ResourceBundle.getBundle(BASE_RESOURCE, Contacts.DEFAULT_LOCALE)
                .getString(key);
    }

}
