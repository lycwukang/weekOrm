package wuk.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wuk.week.orm.OrderType;
import wuk.week.orm.WeekGeneratedKey;
import wuk.week.orm.WeekManager;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-test.xml")
public class WeekTest {

    @Autowired
    private WeekManager weekManager;

    @Test
    public void main() {
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
    }
}