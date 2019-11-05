//package com.wuk.fastorm.testing;
//
//import com.wuk.fastorm.Fastorm;
//import com.wuk.fastorm.lang.StringUtils;
//import com.wuk.fastorm.sql.field.CountMethodSqlField;
//import com.wuk.fastorm.sql.field.StaticSqlField;
//import com.wuk.fastorm.sql.impl.FastormGeneratedKey;
//import com.wuk.fastorm.testing.dto.MemberDTO;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.math.BigDecimal;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.function.Function;
//
//
//public class FastormTest {
//
//
//
//    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
//
//    @Before
//    public void before() throws Exception {
//        String sql = IOUtils.toString(new ClassPathResource("testinit.sql").getInputStream(), Charset.forName("utf-8"));
//        for (String s : sql.split(";")) {
//            if (StringUtils.isNotEmpty(s)) {
//                fastorm.build(s).update();
//            }
//        }
//    }
//
//    @Test
//    public void test001Insert001() throws Exception {
//        MemberDTO dto = new MemberDTO();
//        dto.setCreateDate(DateUtils.parseDate(DateFormatUtils.format(new Date(), DATE_FORMAT_PATTERN), DATE_FORMAT_PATTERN));
//        dto.setModifyDate(DateUtils.parseDate(DateFormatUtils.format(new Date(), DATE_FORMAT_PATTERN), DATE_FORMAT_PATTERN));
//        dto.setUsername("wukang");
//        dto.setPhone("135xxxx1573");
//        dto.setWeight(140.25D);
//        dto.setHeight(175.25F);
//        dto.setAge(25);
//        dto.setEnable(true);
//        dto.setAmount(new BigDecimal("90002.34"));
//
//        int result = fastorm.build(MemberDTO.class)
//                .insert(dto)
//                .exec(new FastormGeneratedKey<>(MemberDTO::getId));
//
//        Assert.assertEquals(result, 1);
//        Assert.assertNotNull(dto.getId());
//        Assert.assertEquals(dto.getId().longValue(), 1L);
//
//        MemberDTO memberDTO = fastorm.build(MemberDTO.class)
//                .select()
//                .where().eq(MemberDTO::getId, dto.getId())
//                .read();
//
//        Assert.assertEquals(memberDTO.getId(), dto.getId());
//        Assert.assertEquals(memberDTO.getAge(), dto.getAge());
//        Assert.assertEquals(memberDTO.getAmount(), dto.getAmount());
//        Assert.assertEquals(DateFormatUtils.format(memberDTO.getCreateDate(), "yyyy-MM-dd HH:mm:ss"), DateFormatUtils.format(dto.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
//        Assert.assertEquals(DateFormatUtils.format(memberDTO.getModifyDate(), "yyyy-MM-dd HH:mm:ss"), DateFormatUtils.format(dto.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
//        Assert.assertEquals(memberDTO.getEnable(), dto.getEnable());
//        Assert.assertEquals(memberDTO.getHeight(), dto.getHeight());
//        Assert.assertEquals(memberDTO.getPhone(), dto.getPhone());
//        Assert.assertEquals(memberDTO.getUsername(), dto.getUsername());
//        Assert.assertEquals(memberDTO.getWeight(), dto.getWeight());
//    }
//
//    @Test
//    public void testInsert002() throws Exception {
//        MemberDTO dto = new MemberDTO();
//        dto.setCreateDate(DateUtils.parseDate(DateFormatUtils.format(new Date(), DATE_FORMAT_PATTERN), DATE_FORMAT_PATTERN));
//        dto.setModifyDate(DateUtils.parseDate(DateFormatUtils.format(new Date(), DATE_FORMAT_PATTERN), DATE_FORMAT_PATTERN));
//        dto.setUsername("wukang");
//        dto.setPhone("135xxxx1573");
//        dto.setWeight(140.25D);
//        dto.setHeight(175.25F);
//        dto.setAge(25);
//        dto.setEnable(true);
//        dto.setAmount(new BigDecimal("90002.34"));
//
//        MemberDTO dto2 = new MemberDTO();
//        dto2.setCreateDate(DateUtils.parseDate(DateFormatUtils.format(new Date(), DATE_FORMAT_PATTERN), DATE_FORMAT_PATTERN));
//        dto2.setModifyDate(DateUtils.parseDate(DateFormatUtils.format(new Date(), DATE_FORMAT_PATTERN), DATE_FORMAT_PATTERN));
//        dto2.setUsername("wukang");
//        dto2.setPhone("135xxxx1573");
//        dto2.setWeight(140.25D);
//        dto2.setHeight(175.25F);
//        dto2.setAge(25);
//        dto2.setEnable(true);
//        dto2.setAmount(new BigDecimal("90002.34"));
//
//        List<MemberDTO> memberDTOList = new ArrayList<>();
//        Collections.addAll(memberDTOList, dto, dto2);
//
//        int result = fastorm.build(MemberDTO.class)
//                .insert(memberDTOList)
//                .exec();
//
//        Assert.assertEquals(result, 2);
//
//        List<MemberDTO> memberList = fastorm.build(MemberDTO.class)
//                .select()
//                .readList();
//
//        Assert.assertEquals(memberList.size(), 2);
//
//        List<Long> idList = fastorm.build(MemberDTO.class)
//                .select(MemberDTO::getId)
//                .readList(Long.class);
//
//        Assert.assertEquals(idList.size(), 2);
//        Assert.assertEquals(idList.get(0).longValue(), 1L);
//        Assert.assertEquals(idList.get(1).longValue(), 2L);
//
//        Long count = fastorm.build(MemberDTO.class)
//                .select(new CountMethodSqlField(new StaticSqlField("*")))
//                .read(Long.class);
//
//        Assert.assertEquals(count.intValue(), 2);
//
//        fastorm.build(MemberDTO.class)
//                .update()
//                .set(MemberDTO::getPhone, "13585731573")
//                .where().eq(MemberDTO::getId, 1L)
//                .exec();
//
//        MemberDTO memberDTO = fastorm.build(MemberDTO.class)
//                .select()
//                .where().eq(MemberDTO::getId, 1L)
//                .read();
//
//        Assert.assertEquals(memberDTO.getPhone(), "13585731573");
//    }
//}
