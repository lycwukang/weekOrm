package com.wuk.fastorm.testing;

import com.wuk.fastorm.Fastorm;
import com.wuk.fastorm.sql.SqlOrderType;
import com.wuk.fastorm.sql.collection.FuncCollection;
import com.wuk.fastorm.sql.field.CountMethodSqlField;
import com.wuk.fastorm.sql.field.NumberSqlField;
import com.wuk.fastorm.sql.field.StaticSqlField;
import com.wuk.fastorm.sql.impl.FastormGeneratedKey;
import com.wuk.fastorm.testing.dto.MemberDTO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testapp.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tester extends TestBase {

    @Autowired
    private Fastorm fastorm;

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void test000() throws Exception {
        runSqlByResourceClassPath(fastorm, "testinit.sql");
        runSqlByResourceClassPath(fastorm, "testinitdata.sql");
    }

    @Test
    public void test001() {
        MemberDTO memberDTO = fastorm.build(MemberDTO.class)
                .select()
                .where().eq(MemberDTO::getId, 1L)
                .read();

        Assert.assertEquals(memberDTO.getId().longValue(), 1L);
        Assert.assertEquals(DateFormatUtils.format(memberDTO.getCreateDate(), DATE_FORMAT_PATTERN), "2019-11-11 12:32:00");
        Assert.assertEquals(DateFormatUtils.format(memberDTO.getModifyDate(), DATE_FORMAT_PATTERN), "2019-11-11 12:32:02");
        Assert.assertEquals(memberDTO.getUsername(), "test_member_01");
        Assert.assertEquals(memberDTO.getPhone(), "test_phone_01");
        Assert.assertEquals(memberDTO.getAmount(), new BigDecimal("99.99"));
        Assert.assertEquals(memberDTO.getAge().intValue(), 19);
        Assert.assertEquals(memberDTO.getHeight(), 17.5f, 0.0);
        Assert.assertEquals(memberDTO.getWeight(), 140.5D, 0.0D);
        Assert.assertEquals(memberDTO.getEnable(), true);
    }

    @Test
    public void test002() {
        long count = fastorm.build(MemberDTO.class)
                .select(new CountMethodSqlField(new StaticSqlField("*")))
                .read(Long.class);

        Assert.assertEquals(count, 7L);
    }

    @Test
    public void test003() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().eq(MemberDTO::getPhone, "test_phone_01")
                .and().eq(MemberDTO::getUsername, "test_member_01")
                .or().eq(MemberDTO::getAge, 19)
                .readList();

        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).getUsername(), "test_member_01");
        Assert.assertEquals(list.get(0).getPhone(), "test_phone_01");
        Assert.assertEquals(list.get(0).getAge().intValue(), 19);
        Assert.assertEquals(list.get(1).getAge().intValue(), 19);
    }

    @Test
    public void test004() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().eq(MemberDTO::getPhone, "test_phone_01")
                .and().eq(MemberDTO::getAge, new NumberSqlField(19))
                .and().notEq(MemberDTO::getId, 4L)
                .readList();

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getUsername(), "test_member_01");
        Assert.assertEquals(list.get(0).getPhone(), "test_phone_01");
        Assert.assertEquals(list.get(0).getAge().intValue(), 19);
    }

    @Test
    public void test005() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .orderBy(MemberDTO::getCreateDate, SqlOrderType.DESC)
                .readList();

        Assert.assertEquals(list.size(), 7);
        Assert.assertEquals(DateFormatUtils.format(list.get(0).getCreateDate(), DATE_FORMAT_PATTERN), "2019-11-21 02:32:00");
        Assert.assertEquals(DateFormatUtils.format(list.get(1).getCreateDate(), DATE_FORMAT_PATTERN), "2019-11-13 12:32:00");
        Assert.assertEquals(DateFormatUtils.format(list.get(6).getCreateDate(), DATE_FORMAT_PATTERN), "2019-09-11 12:32:09");
    }

    @Test
    public void test006() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().gt(MemberDTO::getId, 1L)
                .readList();

        Assert.assertEquals(list.size(), 6);
    }

    @Test
    public void test007() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().gtEq(MemberDTO::getId, 1L)
                .readList();

        Assert.assertEquals(list.size(), 7);
    }

    @Test
    public void test008() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().in(MemberDTO::getId, 1L, 2L, 3L)
                .and().notIn(MemberDTO::getId, 2L, 3L)
                .readList();

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getId().longValue(), 1L);
    }

    @Test
    public void test009() {
        FuncCollection<MemberDTO> functions = new FuncCollection<>(2);
        Collections.addAll(functions, MemberDTO::getId, MemberDTO::getUsername);

        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select(functions)
                .where().ltEq(MemberDTO::getId, 7L)
                .and().lt(MemberDTO::getId, 6L)
                .readList();

        Assert.assertEquals(list.size(), 5);
        Assert.assertNotNull(list.get(0).getId());
        Assert.assertNotNull(list.get(0).getUsername());
        Assert.assertNull(list.get(0).getPhone());
    }

    @Test
    public void test010() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().like(MemberDTO::getUsername, "_member_")
                .readList();

        Assert.assertEquals(list.size(), 7);
    }

    @Test
    public void test011() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().likeStart(MemberDTO::getUsername, "test_member_0")
                .readList();

        Assert.assertEquals(list.size(), 6);
    }

    @Test
    public void test012() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where().likeEnd(MemberDTO::getUsername, "_17")
                .readList();

        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public void test013() {
        List<MemberDTO> list = fastorm.build(MemberDTO.class)
                .select()
                .where()
                .left()
                .eq(MemberDTO::getId, 1L)
                .and()
                .left()
                .eq(MemberDTO::getId, 1L)
                .or()
                .left()
                .eq(MemberDTO::getId, 2L)
                .right()
                .right()
                .right()
                .readList();

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getId().longValue(), 1L);
    }
}
