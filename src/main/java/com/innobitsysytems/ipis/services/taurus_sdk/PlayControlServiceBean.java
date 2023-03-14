package com.innobitsysytems.ipis.services.taurus_sdk;

import org.springframework.stereotype.Service;

import novj.platform.vxkit.common.bean.VideoControlInfo;
import novj.platform.vxkit.common.bean.edid.EdidInfo;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestAsync;
import novj.publ.net.svolley.Request.IRequestBase;




@Service
public class PlayControlServiceBean {
	
	private String startPlayMsg;
	private String stopPlayMsg;
	private String pausePlayMsg;
	private String resumePlayMsg;


	public String startPlay(String deviceSn, String identifier) {
		
		 NovaOpt.GetInstance().startPlay(deviceSn,identifier, new OnResultListenerN(){

			@Override
			public void onError(IRequestBase response, Object arg1) {
				// TODO Auto-generated method stub
				startPlayMsg="error start palying:"+response;

			}

			@Override
			public void onSuccess(IRequestBase response, Object arg1) {
				startPlayMsg="success Start playing"+ response;

			}} );
		 return startPlayMsg;
		
	}
	
	public String stopPlay(String deviceSn, String identifier) {
		NovaOpt.GetInstance().stopPlay(deviceSn, identifier, new OnResultListenerN() {
				@Override
				public void onError(IRequestBase response, Object arg1) {
					stopPlayMsg="Error in Stop playing";
					
				}

				@Override
				public void onSuccess(IRequestBase response, Object arg1) {
					stopPlayMsg="success Stop playing" + response;
					
				}
		});
		return stopPlayMsg;
				
	}
	
	
	public String pausePlay(String deviceSn, String identifier){
		NovaOpt.GetInstance().pausePlay(deviceSn, identifier, new OnResultListenerN<Object, Object>() {

			@Override
			public void onError(IRequestBase response, Object arg1) {
				pausePlayMsg="failed to pause play";
				
			}

			@Override
			public void onSuccess(IRequestBase response, Object arg1) {
				pausePlayMsg="play pause successfully";
				
			}});
		return pausePlayMsg;
	}
	
	public String resumePlay(String deviceSn, String identifier) {
		NovaOpt.GetInstance().resumePlay(deviceSn, identifier,new OnResultListenerN()
				 {
				@Override
				public void onError(IRequestBase response, Object arg1) {
					resumePlayMsg="failed to resume play";

				}
				@Override
				public void onSuccess(IRequestBase arg0, Object arg1) {

					resumePlayMsg=" play resume successfully";

				}
				
		});
		return resumePlayMsg;
		
		
			
		}
	
	public IRequestAsync getVideoControlInfo(final String deviceSn) {
		return NovaOpt.GetInstance().getVideoControlInfo( deviceSn, new OnResultListenerN<VideoControlInfo, ErrorDetail>() {

			@Override
			public void onError(IRequestBase response, ErrorDetail arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(IRequestBase response, VideoControlInfo arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	
	public IRequestAsync setSyncPlay(String deviceSn)
	{
		return NovaOpt.GetInstance().setSyncPlay(deviceSn, true,new OnResultListenerN() {

			@Override
			public void onError(IRequestBase arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(IRequestBase arg0, Object arg1) {
				
			}});
				
	}
	
	public IRequestAsync getSyncPlay(String deviceSn) {
		
		return NovaOpt.GetInstance().getSyncPlay(deviceSn, new OnResultListenerN<Boolean,ErrorDetail>() {


			@Override
			public void onSuccess(IRequestBase arg0, Boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onError(IRequestBase arg0, ErrorDetail arg1) {
				
			}
		
	});
	
	}
	public IRequestAsync setVideoControlInfo(String deviceSn, VideoControlInfo videoControlInfo) {
		return NovaOpt.GetInstance().setVideoControlInfo(deviceSn,videoControlInfo, new OnResultListenerN() {

			@Override
			public void onError(IRequestBase arg0, Object arg1) {
			
			}

			@Override
			public void onSuccess(IRequestBase arg0, Object arg1) {
				
			}
			
		});
	}
	public IRequestAsync setEdidInfo(final String deviceSn, EdidInfo editInfo) {
		
		return NovaOpt.GetInstance().setEdidInfo(deviceSn, editInfo,new OnResultListenerN() {

			@Override
			public void onError(IRequestBase iRequestBase, Object arg1) {
				
			}

			@Override
			public void onSuccess(IRequestBase IRequestBase, Object arg1) {
				
			}});
		
	}
	
	
	
}