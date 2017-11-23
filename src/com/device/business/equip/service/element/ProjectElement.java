package com.device.business.equip.service.element;

import javax.ws.rs.FormParam;

public class ProjectElement {
	/**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column t_luding_project_info.Project_ID
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
	@FormParam("projectId")
   private Integer projectId;

   /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column t_luding_project_info.Project_Name
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   @FormParam("projectName")
   private String projectName;

   /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column t_luding_project_info.Project_Leader
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   @FormParam("projectLeader")
   private String projectLeader;

   /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column t_luding_project_info.Contract_number
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   @FormParam("contractNumber")
   private String contractNumber;

   /**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_luding_project_info.Project_Builder
	 *
	 * @mbg.generated Sat Nov 11 19:48:23 CST 2017
	 */
	// 项目管理员
	@FormParam("projectBuilder")
	private String projectBuilder;
	
	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_luding_project_info.Project_Supervisor
	 *
	 * @mbg.generated Sat Nov 11 19:48:23 CST 2017
	 */
	// 项目管理员
	@FormParam("projectSupervisor")
	private String projectSupervisor;

   /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column t_luding_project_info.Parameter3
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   private String parameter3;

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column t_luding_project_info.Project_ID
    *
    * @return the value of t_luding_project_info.Project_ID
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public Integer getProjectId() {
       return projectId;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column t_luding_project_info.Project_ID
    *
    * @param projectId the value for t_luding_project_info.Project_ID
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public void setProjectId(Integer projectId) {
       this.projectId = projectId;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column t_luding_project_info.Project_Name
    *
    * @return the value of t_luding_project_info.Project_Name
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public String getProjectName() {
       return projectName;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column t_luding_project_info.Project_Name
    *
    * @param projectName the value for t_luding_project_info.Project_Name
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public void setProjectName(String projectName) {
       this.projectName = projectName == null ? null : projectName.trim();
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column t_luding_project_info.Project_Leader
    *
    * @return the value of t_luding_project_info.Project_Leader
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public String getProjectLeader() {
       return projectLeader;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column t_luding_project_info.Project_Leader
    *
    * @param projectLeader the value for t_luding_project_info.Project_Leader
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public void setProjectLeader(String projectLeader) {
       this.projectLeader = projectLeader == null ? null : projectLeader.trim();
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column t_luding_project_info.Contract_number
    *
    * @return the value of t_luding_project_info.Contract_number
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public String getContractNumber() {
       return contractNumber;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column t_luding_project_info.Contract_number
    *
    * @param contractNumber the value for t_luding_project_info.Contract_number
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public void setContractNumber(String contractNumber) {
       this.contractNumber = contractNumber == null ? null : contractNumber.trim();
   }

   /**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_luding_project_info.Project_Builder
	 *
	 * @return the value of t_luding_project_info.Project_Builder
	 *
	 * @mbg.generated Tue Jan 03 19:48:23 CST 2017
	 */
	public String getProjectBuilder() {
		return projectBuilder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_luding_project_info.Project_Builder
	 *
	 * @param parameter1
	 *            the value for t_luding_project_info.Project_Builder
	 *
	 * @mbg.generated Tue Jan 03 19:48:23 CST 2017
	 */
	public void setProjectBuilder(String projectBuilder) {
		this.projectBuilder = projectBuilder == null ? null : projectBuilder.trim();
	}

   /**
    * This method was generated by MyBatis Generator.
    * This method returns the value of the database column t_luding_project_info.Parameter3
    *
    * @return the value of t_luding_project_info.Parameter3
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public String getParameter3() {
       return parameter3;
   }

   /**
    * This method was generated by MyBatis Generator.
    * This method sets the value of the database column t_luding_project_info.Parameter3
    *
    * @param parameter3 the value for t_luding_project_info.Parameter3
    *
    * @mbg.generated Tue Jan 03 19:48:23 CST 2017
    */
   public void setParameter3(String parameter3) {
       this.parameter3 = parameter3 == null ? null : parameter3.trim();
   }
}