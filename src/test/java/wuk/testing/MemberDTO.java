package wuk.testing;

import wuk.week.orm.WeekColumn;
import wuk.week.orm.WeekTable;
import wuk.week.orm.WeekUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

@WeekTable("test_member")
public class MemberDTO {

    public static final Field _id = WeekUtils.field(MemberDTO.class, "id");
    public static final Field _createDate = WeekUtils.field(MemberDTO.class, "createDate");
    public static final Field _username = WeekUtils.field(MemberDTO.class, "username");
    public static final Field _phone = WeekUtils.field(MemberDTO.class, "phone");
    public static final Field _amount = WeekUtils.field(MemberDTO.class, "amount");

    @WeekColumn(value = "id", autoIncrement = true, primaryKey = true)
    private Long id;
    @WeekColumn(value = "create_date", defaultValue = true)
    private Date createDate;
    @WeekColumn("username")
    private String username;
    @WeekColumn("phone")
    private String phone;
    @WeekColumn("amount")
    private BigDecimal amount;

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
}