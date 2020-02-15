package com.wuk.fastorm.testing;

import com.wuk.fastorm.Fastorm;
import com.wuk.fastorm.FastormSession;
import com.wuk.fastorm.bean.*;
import com.wuk.fastorm.proxy.DefaultLastOperateFeatureFactory;
import com.wuk.fastorm.proxy.LastOperateFeatureFactory;
import com.wuk.fastorm.sql.impl.FastormGeneratedKey;
import com.wuk.fastorm.testing.dto.MemberDTO;
import com.wuk.fastorm.testing.dto.SkuDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    @Test
    public void main() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://rm-uf697n485z5hswd56zo.mysql.rds.aliyuncs.com:3306/mall_se_testing?useUnicode=true&characterEncoding=utf8&useAffectedRows=true&serverTimezone=Asia/Shanghai");
        source.setUsername("mall_se_dbo_test");
        source.setPassword("mY$eMall1904");

        Fastorm fastorm = new Fastorm();
        fastorm.setDataSource(source);

//        for (int i = 0; i < 10000; i++) {
//            final int id = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    MemberDTO dto = fastorm.build(MemberDTO.class)
//                            .select()
//                            .where().eq(MemberDTO::getUsername, "username" + id)
//                            .read();
//
//                    System.out.println(dto.getUsername());
//                }
//            }).start();

        fastorm.build("");
        System.out.println("1");
        fastorm.build("");
        System.out.println("2");
        fastorm.build("");
        System.out.println("3");
        fastorm.build("");
        System.out.println("4");
        fastorm.build("");
        System.out.println("5");
        fastorm.build("");
        System.out.println("6");
        fastorm.build("");
        System.out.println("7");
        fastorm.build("");
        System.out.println("8");
        fastorm.build("");
        System.out.println("9");

//        try (FastormSession session = fastorm.begin()) {
//            SkuDTO dto = fastorm.tryBuild(session, SkuDTO.class)
//                    .select()
//                    .where().eq(SkuDTO::getId, 0L)
//                    .read();
//
//            System.out.println(dto);
//
//            session.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        FastormBeanLastOperateStructure<SkuDTO> fastormBeanStructure = new DefaultFastormBeanAnalyze().analyze(SkuDTO.class);
        FastormBeanLastOperateStructure<SkuDTO> fastormBeanStructure2 = new DefaultFastormBeanAnalyze().analyze(SkuDTO.class);

        SkuDTO dto = new SkuDTO();

        System.out.println(fastormBeanStructure.findColumnName(SkuDTO::getId));
        System.out.println(fastormBeanStructure2.findColumnName(SkuDTO::getCreateDate));

        System.out.println(fastormBeanStructure);
    }
}
