package wuk.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wuk.week.orm.OrderType;
import wuk.week.orm.WeekGeneratedKey;
import wuk.week.orm.WeekManager;
import wuk.week.orm.WeekTransaction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-test.xml")
public class WeekTest {

    @Autowired
    private WeekManager weekManager;

    @Test
    public void main() {
//        WeekTransaction transaction = weekManager.begin("test");
//
//        try {
//            // insert
//            // insert into test_member(username, phone, amount)
//            // values(?, ?, ?)
//            MemberDTO member = new MemberDTO();
//            member.setUsername("username");
//            member.setPhone("13585731574");
//            member.setAmount(BigDecimal.ZERO);
//
//            WeekGeneratedKey generatedKey = new WeekGeneratedKey();
//            weekManager.build("test", transaction, MemberDTO.class)
//                    .insert()
//                    .exec(member, generatedKey);
//
//            System.out.println("id=" + generatedKey.getLong());
//
//            // select
//            // select id, create_date, username, phone, amount from test_member where id=?
//            member = weekManager.build("test", transaction, MemberDTO.class)
//                    .select()
//                    .where().eq(MemberDTO._id, generatedKey.getLong())
//                    .read();
//
//            System.out.println("id=" + member.getId() + ", username=" + member.getUsername() + ", phone=" + member.getPhone() + ", amount=" + member.getAmount() + ", createDate=" + member.getCreateDate());
//
//            // update
//            // update test_member set amount=? where id=?
//            weekManager.build("test", transaction, MemberDTO.class)
//                    .update()
//                    .where().eq(MemberDTO._id, generatedKey.getLong())
//                    .set(MemberDTO._amount, new BigDecimal("100"))
//                    .exec();
//
//            // select2
//            // select username, phone, amount from test_member where amount >= ?
//            // group by username
//            // order by amount desc
//            // limit 1
//            // 只返回指定的列
//            member = weekManager.build("test", transaction, MemberDTO.class)
//                    .select(MemberDTO._username, MemberDTO._phone, MemberDTO._amount)
//                    .where().gtEq(MemberDTO._amount, new BigDecimal("100"))
//                    .orderBy(MemberDTO._amount, OrderType.desc)
//                    .groupBy(MemberDTO._username)
//                    .limit(1)
//                    .read();
//
//            System.out.println("id=" + member.getId() + ", username=" + member.getUsername() + ", phone=" + member.getPhone() + ", amount=" + member.getAmount() + ", createDate=" + member.getCreateDate());
//
////            // delete
////            // delete from test_member where id=?
////            weekManager.build("test", transaction, MemberDTO.class)
////                    .delete()
////                    .where().eq(MemberDTO._id, generatedKey.getLong())
////                    .exec();
//            transaction.commit();
//        } catch (RuntimeException e) {
//            transaction.rollback();
//        }

        MemberDTO member2 = new MemberDTO();
        member2.setId(1L);
        member2.setUsername("username");
        member2.setPhone("13585731574");
        member2.setAmount(BigDecimal.ZERO);

        MemberDTOImpl memberDTO = new MemberDTOImpl(member2);

        FunctionDTO functionDTO = memberDTO;
        Supplier<Long> id = memberDTO::getId;

        Long value = id.get();
        Field field = functionDTO.findLastOperateField();


        System.out.println(value);
        System.out.println(field);
    }
}