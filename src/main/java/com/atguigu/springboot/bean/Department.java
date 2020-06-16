package com.atguigu.springboot.bean;

public class Department {

	private Integer department_id;
	private String department_name;
	private String job_name;
	private Double job_money;
	private  int job_id;


	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public Double getJob_money() {
		return job_money;
	}

	public void setJob_money(Double job_money) {
		this.job_money = job_money;
	}

	public int getJob_id() {
		return job_id;
	}

	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
}
