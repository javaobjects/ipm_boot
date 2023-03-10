package com.protocolsoft.watchpoint.provider;

import com.protocolsoft.watchpoint.bean.WatchpointBean;

public class WatchpointProvider {
	
	
	  public String updateSql(WatchpointBean bean){
		  StringBuffer sb = new StringBuffer();
		  sb.append("update ipm_watchpoint SET ");
		  if(bean.getName() != null){ //名称
			  sb.append(" name = '" + bean.getName() + "',");
		  }
		  if(bean.getDid() != null){
			  sb.append(" did = " + bean.getDid() + ",");
		  }
		  if(bean.getVid() != null){
			  sb.append(" vid = " + bean.getVid() + ",");
		  }
		  if(bean.getVxid() != null){
			  sb.append(" vxid = " + bean.getVxid() + ",");
		  }
		  if(bean.getLid1() != null){
			  sb.append(" lid1 = " + bean.getLid1() + ",");
		  }
		  if(bean.getLid2() != null){
			  sb.append(" lid2 = " + bean.getLid2() + ",");
		  }
		  if(bean.getLid3() != null){
			  sb.append(" lid3 = " + bean.getLid3() + ",");
		  }
		  if(bean.getLid4() != null){
			  sb.append(" lid4 = " + bean.getLid4() + ",");
		  }
		  if(bean.getLid5() != null){
			  sb.append(" lid5 = " + bean.getLid5() + ",");
		  }
		  if(bean.getUpBandWidth() != null){
			  sb.append(" upBandWidth = " + bean.getUpBandWidth() + ",");
		  }
		  if(bean.getDownBandWidth() != null){
			  sb.append(" downBandWidth = " + bean.getDownBandWidth() + ",");
		  }
		  if(bean.getIp() != null){
			  sb.append(" ip = '" + bean.getIp() + "',");
		  }
		  if(bean.getPort() != 0){
			  sb.append(" port = " + bean.getPort() + ",");
		  }
		  if(bean.getContacts() != null){
			  sb.append(" contacts = '" + bean.getContacts() + "',");
		  }
		  if(bean.getTelephone() != null){
			  sb.append(" telephone = " + bean.getTelephone() + ",");
		  }
		  if(bean.getEmail() != null){
			  sb.append(" email = '" + bean.getEmail() + "',");
		  }

		  if(bean.getValidterm() != null){
			  sb.append(" validterm = " + bean.getValidterm() + ",");
		  }
		  if(bean.getMacAddr() != null) {
		      sb.append(" macAddr = '" + bean.getMacAddr() + "',");
		  }
		  if(bean.getProductNo() != null) {
		      sb.append(" productNo = '" + bean.getProductNo() + "',");
		  }
		  if (bean.getSyncTime() != null ) {
		      sb.append(" syncTime = " + bean.getSyncTime() + ",");
		  }

		  String sqlStr = sb.substring(0, sb.length() - 1);
		  if(bean.getId() != 0){
			  sqlStr += " where id = " + bean.getId() ;
		  }

		  return sqlStr;
	  }

}
