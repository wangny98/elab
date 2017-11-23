package com.device.business.systemSet.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserBean {

		private String id;
		
		private String user_code;
		
		private String user_name;
		
		private String depart_id;
		
		private String depart_name;
		
		public String toString() {
	        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	    }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUser_code() {
			return user_code;
		}

		public void setUser_code(String user_code) {
			this.user_code = user_code;
		}

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getDepart_id() {
			return depart_id;
		}

		public void setDepart_id(String depart_id) {
			this.depart_id = depart_id;
		}

		public String getDepart_name() {
			return depart_name;
		}

		public void setDepart_name(String depart_name) {
			this.depart_name = depart_name;
		}
		
		
}
