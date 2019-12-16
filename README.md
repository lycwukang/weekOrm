# fastorm（原名weekOrm）
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

<!-- 核心类 -->
<bean id="fastorm" class="com.wuk.fastorm.Fastorm">
    <property name="dataSource" ref="dataSource" />
</bean>
```
------

2. orm实体定义
```java
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

    // ...省略get,set方法
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

FastormGeneratedKey<MemberDTO> generatedKey = new FastormGeneratedKey<>(MemberDTO::getId);
fastorm.build(MemberDTO.class)
        .insert(member)
        .exec(generatedKey);

System.out.println("id=" + member.getId());

// select
// select id, create_date, username, phone, amount from test_member where id=?
member = fastorm.build(MemberDTO.class)
        .select()
        .where().eq(MemberDTO::getId, member.getId())
        .read();

System.out.println("id=" + member.getId() + ", username=" + member.getUsername() + ", phone=" + member.getPhone() + ", amount=" + member.getAmount() + ", createDate=" + member.getCreateDate());

// update
// update test_member set amount=? where id=?
fastorm.build(MemberDTO.class)
        .update()
        .where().eq(MemberDTO::getId, member.getId())
        .set(MemberDTO::getAmount, new BigDecimal("100"))
        .exec();

// select2
// select username, phone, amount from test_member where amount >= ?
// group by username
// order by amount desc
// limit 1
// 只返回指定的列
member = fastorm.build(MemberDTO.class)
        .select(FuncCollection.newInstance(MemberDTO::getUsername, MemberDTO::getPhone, MemberDTO::getAmount))
        .where().gtEq(MemberDTO::getAmount, new BigDecimal("100"))
        .orderBy(MemberDTO::getAmount, SqlOrderType.DESC)
        .groupBy(MemberDTO::getUsername)
        .limit(1)
        .read();

System.out.println("id=" + member.getId() + ", username=" + member.getUsername() + ", phone=" + member.getPhone() + ", amount=" + member.getAmount() + ", createDate=" + member.getCreateDate());

// delete
// delete from test_member where id=?
fastorm.build(MemberDTO.class)
        .delete()
        .where().eq(MemberDTO::getId, member.getId())
        .exec();
```
----

支持使用sql原型调用
======
1. 执行sql
```java
MemberDTO member = fastorm.build("select * from test_member where id=#{id} limit 1")
                .addParam("id", 1L)
                .read(MemberDTO.class);
```