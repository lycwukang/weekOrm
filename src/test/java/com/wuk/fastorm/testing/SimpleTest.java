package com.wuk.fastorm.testing;

import com.wuk.fastorm.Fastorm;
import com.wuk.fastorm.testing.dto.MemberDTO;
import org.junit.Test;

public class SimpleTest {

    private Fastorm fastorm;

    @Test
    public void main() {
        MemberDTO member = fastorm.build("select * from test_member where id=#{id} limit 1")
                .addParam("id", 1L)
                .read(MemberDTO.class);
    }
}
