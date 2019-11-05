package com.wuk.fastorm.testing;

import com.wuk.fastorm.Fastorm;
import com.wuk.fastorm.sql.field.CountMethodSqlField;
import com.wuk.fastorm.sql.field.NumberSqlField;
import com.wuk.fastorm.sql.field.StaticSqlField;
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
        Assert.assertEquals(memberDTO.getHeight().floatValue(), 17.5f, 0.0);
        Assert.assertEquals(memberDTO.getWeight().doubleValue(), 140.5D, 0.0D);
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
                .and().notEq(MemberDTO::getId, 4)
                .readList();

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getUsername(), "test_member_01");
        Assert.assertEquals(list.get(0).getPhone(), "test_phone_01");
        Assert.assertEquals(list.get(0).getAge().intValue(), 19);
    }
}
