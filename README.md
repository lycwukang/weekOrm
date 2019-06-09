# weekOrm
专注与单表操作的orm框架

简单使用指南，详细参见测试类
======
1. spring配置
```xml
<!-- 可自定义数据源，建议使用链接池 -->
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="?" />
    <property name="username" value="?" />
    <property name="password" value="?" />
</bean>

<!-- weekOrm所使用的数据源，name是该数据源的名称 -->
<bean id="weekSource" class="wuk.week.orm.WeekSource">
    <property name="name" value="test" />
    <property name="dataSource" ref="dataSource" />
</bean>

<!-- 核心类 -->
<bean class="wuk.week.orm.WeekManager">
    <property name="dataSources">
        <list>
            <ref bean="weekSource" />
        </list>
    </property>
</bean>
```
------

2. orm实体定义
```java
@WeekTable("test_member")
public class MemberDTO {

    public static final Field _id = WeekUtils.field(MemberDTO.class, "id");
    public static final Field _createDate = WeekUtils.field(MemberDTO.class, "createDate");
    public static final Field _username = WeekUtils.field(MemberDTO.class, "username");
    public static final Field _phone = WeekUtils.field(MemberDTO.class, "phone");
    public static final Field _amount = WeekUtils.field(MemberDTO.class, "amount");

    @WeekColumn(value = "id", autoIncrement = true, primaryKey = true)
    private Long id;
    @WeekColumn("create_date")
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
```
------

3. 操作方法
```java
// insert
// insert into test_member(username, phone, amount)
// values(?, ?, ?)
MemberDTO member = new MemberDTO();
member.setUsername("username");
member.setPhone("13585731574");
member.setAmount(BigDecimal.ZERO);

WeekGeneratedKey generatedKey = new WeekGeneratedKey();
weekManager.build("test", MemberDTO.class)
        .insert()
        .exec(member, generatedKey);

System.out.println("id=" + generatedKey.getLong());

// select
// select id, create_date, username, phone, amount from test_member where id=?
member = weekManager.build("test", MemberDTO.class)
        .select()
        .where().eq(MemberDTO._id, generatedKey.getLong())
        .read();

System.out.println("id=" + member.getId() + ", username=" + member.getUsername() + ", phone=" + member.getPhone() + ", amount=" + member.getAmount() + ", createDate=" + member.getCreateDate());

// update
// update test_member set amount=? where id=?
weekManager.build("test", MemberDTO.class)
        .update()
        .where().eq(MemberDTO._id, generatedKey.getLong())
        .set(MemberDTO._amount, new BigDecimal("100"))
        .exec();

// select2
// select username, phone, amount from test_member where amount >= ?
// group by username
// order by amount desc
// limit 1
// 只返回指定的列
member = weekManager.build("test", MemberDTO.class)
        .select(MemberDTO._username, MemberDTO._phone, MemberDTO._amount)
        .where().gtEq(MemberDTO._amount, new BigDecimal("100"))
        .orderBy(MemberDTO._amount, OrderType.desc)
        .groupBy(MemberDTO._username)
        .limit(1)
        .read();

System.out.println("id=" + member.getId() + ", username=" + member.getUsername() + ", phone=" + member.getPhone() + ", amount=" + member.getAmount() + ", createDate=" + member.getCreateDate());

// delete
// delete from test_member where id=?
weekManager.build("test", MemberDTO.class)
        .delete()
        .where().eq(MemberDTO._id, generatedKey.getLong())
        .exec();
```
----
