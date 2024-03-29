package org.xmlactions.schema.docs;

import java.io.IOException;
import java.net.URL;

import org.apache.bsf.util.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class LoadSchemaTest {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadSchemaTest.class);
	
	@Test
	public void testLoadSchema() throws IOException {
		String schemaName = "/schema/storage.xsd";
		//URL url = ResourceUtils.getURL(schemaName);
		String data = LoadSchema.loadSchema(schemaName);
		// logger.debug(data);
	}

}
