package com.device.business.equip.bean;

public class SlurryAmountBeanKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_luding_slurry_amount.Section_ID
     *
     * @mbg.generated Tue Dec 27 12:28:42 CST 2016
     */
    private String sectionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_luding_slurry_amount.Project_ID
     *
     * @mbg.generated Tue Dec 27 12:28:42 CST 2016
     */
    private Integer projectId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_luding_slurry_amount.Section_ID
     *
     * @return the value of t_luding_slurry_amount.Section_ID
     *
     * @mbg.generated Tue Dec 27 12:28:42 CST 2016
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_luding_slurry_amount.Section_ID
     *
     * @param sectionId the value for t_luding_slurry_amount.Section_ID
     *
     * @mbg.generated Tue Dec 27 12:28:42 CST 2016
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId == null ? null : sectionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_luding_slurry_amount.Project_ID
     *
     * @return the value of t_luding_slurry_amount.Project_ID
     *
     * @mbg.generated Tue Dec 27 12:28:42 CST 2016
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_luding_slurry_amount.Project_ID
     *
     * @param projectId the value for t_luding_slurry_amount.Project_ID
     *
     * @mbg.generated Tue Dec 27 12:28:42 CST 2016
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}