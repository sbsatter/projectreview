package com.sbsatter.projectreview.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sbsatter on 9/18/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Utils {
	@Autowired
	ShaPasswordEncoder encoder;
	@Test
	public void sha256() {
		log.info("{}", encoder.encodePassword("password", null));
	}
}
