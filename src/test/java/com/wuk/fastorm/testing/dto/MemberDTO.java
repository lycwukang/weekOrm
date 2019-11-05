package com.wuk.fastorm.testing.dto;

import com.wuk.fastorm.annontation.FastormColumn;
import com.wuk.fastorm.annontation.FastormTable;

import java.math.BigDecimal;
import java.util.Date;

@FastormTable("test_member")
public class MemberDTO {

    @FastormColumn(value = "id", autoIncrement = true)
    private Long id;
    @FastormColumn(value = "create_date")
    private Date createDate;
    @FastormColumn(value = "modify_date")
    private Date modifyDate;
    @FastormColumn(value = "username")
    private String username;
    @FastormColumn(value = "phone")
    private String phone;
    @FastormColumn(value = "amount")
    private BigDecimal amount;
    @FastormColumn(value = "age")
    private Integer age;
    @FastormColumn(value = "height")
    private Float height;
    @FastormColumn(value = "weight")
    private Double weight;
    @FastormColumn(value = "enable")
    private Boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
