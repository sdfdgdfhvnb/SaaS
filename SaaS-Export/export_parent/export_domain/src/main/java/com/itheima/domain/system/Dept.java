package com.itheima.domain.system;

/**
 * 部门表
 */
public class Dept {

    private String id;
    private String deptName;
    private Integer state;
    private String companyId;
    private String companyName;

    // 当前部门关联了一个父部门对象,association
    private Dept parent;

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", deptName='" + deptName + '\'' +
                ", state=" + state +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", parent=" + parent +
                '}';
    }

    public Dept() {
    }

    public Dept(String id, String deptName, Integer state, String companyId, String companyName, Dept parent) {
        this.id = id;
        this.deptName = deptName;
        this.state = state;
        this.companyId = companyId;
        this.companyName = companyName;
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }
}
