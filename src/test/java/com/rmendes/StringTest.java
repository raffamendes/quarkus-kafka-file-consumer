package com.rmendes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class StringTest {
	
	private static final Logger logger = Logger.getLogger(StringTest.class);
	
	
	@Test
	public void testString() {
		String test = "02100011R01  043 2034307295000165                    00274 000030248363  COMPANHIA DE GAS DO ESPIRITO S                                                                                000000002303202300000000 ";
		logger.info("INFO: Substring"+test.substring(8,9).equalsIgnoreCase("R"));
		if(test.substring(8, 9).equalsIgnoreCase("R")) {
			assertEquals(true, true);
		}
		
	}

}
