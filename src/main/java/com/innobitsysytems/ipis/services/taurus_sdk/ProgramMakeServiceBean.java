package com.innobitsysytems.ipis.services.taurus_sdk;

import org.springframework.stereotype.Service;

import novj.platform.vxkit.common.bean.programinfo.Layout;
import novj.platform.vxkit.common.bean.programinfo.MetaDataDigitalClockV2;
import novj.platform.vxkit.common.bean.programinfo.PageItem;
import novj.platform.vxkit.common.bean.programinfo.Widget;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.publ.api.NovaOpt;
import novj.publ.api.actions.ProgramManager;
import novj.publ.api.beans.NormalTextBean;
import novj.publ.net.exception.ErrorDetail;

@Service
public class ProgramMakeServiceBean {

	/*
	 * create program
	 */
	public String createProgram(String name, double width, double height) {
		String createMsg = "";
		int result = NovaOpt.GetInstance().createProgram(name, width, height);
		if (result < 0) {
			createMsg = "Empty solution is not created of" + name + " " + width + " " + height;
		} else {
			createMsg = "Empty Solution is created of " + name + " " + width + " " + height;
		}
		return createMsg;

	}

	/*
	 * addpage
	 */

	public String addPage(PageItem pageItem) {
		String addPageMsg;
		int result = NovaOpt.GetInstance().addPage(pageItem);
		if (result >= 0)
			addPageMsg = "New Page added ";
		else
			addPageMsg = "page not added for " + pageItem.toString();

		return addPageMsg;
	}

	/*
	 * addwidget
	 */

	public int addWidget(int pageID) {
		int result;

		result = NovaOpt.GetInstance().addWidget(pageID, ProgramManager.WidgetMediaType.ARCH_TEXT,
				new DefaultOnResultListener() {

					@Override
					public void onError(ErrorDetail ErroDetail) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Integer arg0) {

					}

				});

		return result;
	}

	/*
	 * obtaining page info
	 */

	public PageItem getPageItem(int pageID) {
		PageItem pi = NovaOpt.GetInstance().getPageItem(pageID);
		return pi;
	}

	/*
	 * setting page Information
	 */

	public String setPageParam(int pageId) {
		String pageInfoMsg;
		PageItem pi = new PageItem();
		pi.setName("page1");
		int result = NovaOpt.GetInstance().setPageParam(pageId, pi);
		if (result == 0) {
			pageInfoMsg = "successfull set page information for pageId " + pageId;
		} else
			pageInfoMsg = "Error in setting page informatio for page id " + pageId;

		return pageInfoMsg;

	}

	/*
	 * obtaining widget Information
	 */

	public Widget getWidgetParam(int pageID, int widgetID) {

		Widget widget = NovaOpt.GetInstance().getWidgetParam(pageID, widgetID);

		return widget;

	}

	/*
	 * Setting widget Information
	 */

//	public String setWidgetParam(int pageId, int widgetId) {
//		Widget widget = getWidgetParam(pageId, widgetId);
//		return setWidgetParam(pageId, widgetId, widget);
//	}

	 public String setWidgetParam(int pageID, int widgetID, Widget widget) {
		int result;
		String widgetParamMsg;
		Layout layout = new Layout();
		widget.setDisplayRatio("CUSTOM");
		widget.setLayout(layout);
		result = NovaOpt.GetInstance().setWidgetParam(pageID, widgetID, widget);
		if (result == 0) {
			widgetParamMsg = "successfull set widget Param  " + pageID + " " + widgetID + " " + " " + widget;
		} else
			widgetParamMsg = "Error in setting widget Param  " + pageID + " " + widgetID + " " + " " + widget;

		return widgetParamMsg;

	}

	public int makeProgram(String outputPath) {
		String makeProgramMsg;
		int result =NovaOpt.GetInstance().makeProgram(System.getProperty(outputPath));
		if (result < 0)
			makeProgramMsg = "successfully program make";
		else
			makeProgramMsg = "error in program making";
		return result;
	}

}
