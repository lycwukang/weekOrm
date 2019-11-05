package com.wuk.fastorm.testing;

import com.wuk.fastorm.Fastorm;
import com.wuk.fastorm.lang.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.Charset;

public abstract class TestBase {

    public void runSqlByResourceClassPath(Fastorm fastorm, String fileName) throws Exception {
        InputStream is = new ClassPathResource(fileName).getInputStream();
        String sql = IOUtils.toString(is, Charset.forName("utf-8"));
        for (String s : sql.split(";")) {
            if (StringUtils.isNotEmpty(s)) {
                fastorm.build(s).update();
            }
        }
    }
}
