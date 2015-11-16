package parameters;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelConfigTEST {

	ModelConfig config = ModelConfig.createConfig();
	
	@Test
	public void testToString() { 
		System.out.println(config.toString());
	}

}
