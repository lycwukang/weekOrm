package wuk.testing;

import wuk.week.orm.WeekUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class MemberDTOImpl extends MemberDTO implements FunctionDTO {

    private MemberDTO dto = null;
    private Field field0 = null;

    public MemberDTOImpl(MemberDTO dto) {
        this.dto = dto;
    }

    @Override
    public Long getId() {
        field0 = WeekUtils.field(dto.getClass(), "id");
        return dto.getId();
    }

    @Override
    public void setId(Long id) {
        field0 = WeekUtils.field(dto.getClass(), "id");
        dto.setId(id);
    }

    @Override
    public Date getCreateDate() {
        field0 = WeekUtils.field(dto.getClass(), "createDate");
        return dto.getCreateDate();
    }

    @Override
    public void setCreateDate(Date createDate) {
        field0 = WeekUtils.field(dto.getClass(), "createDate");
        dto.setCreateDate(createDate);
    }

    @Override
    public String getUsername() {
        field0 = WeekUtils.field(dto.getClass(), "username");
        return dto.getUsername();
    }

    @Override
    public void setUsername(String username) {
        field0 = WeekUtils.field(dto.getClass(), "username");
        dto.setUsername(username);
    }

    @Override
    public String getPhone() {
        field0 = WeekUtils.field(dto.getClass(), "phone");
        return dto.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        field0 = WeekUtils.field(dto.getClass(), "phone");
        dto.setPhone(phone);
    }

    @Override
    public BigDecimal getAmount() {
        field0 = WeekUtils.field(dto.getClass(), "amount");
        return dto.getAmount();
    }

    @Override
    public void setAmount(BigDecimal amount) {
        field0 = WeekUtils.field(dto.getClass(), "amount");
        dto.setAmount(amount);
    }

    @Override
    public Field findLastOperateField() {
        return field0;
    }
}
