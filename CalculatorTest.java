import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorTest {
	@Test
	public void testAdd() {
		Calculator obj=new Calculator();
		//assertEquals("9 is not a even number", false, meo.isEvenNumber(9));
		
		//assertEquals(true, obj.isEvenNumber(9));
		
		assertEquals(10, obj.add(5, 5));
		assertEquals(8,obj.add(3, 5));
		
		
	}
	@Test
	public void testMultiply() {
		Calculator obj=new Calculator();
		//assertEquals("9 is not a even number", false, meo.isEvenNumber(9));
		
		//assertEquals(true, obj.isEvenNumber(9));
		assertEquals(25, obj.multiply(5, 5));
		assertEquals(15,obj.multiply(3, 5));
	}
		
	
}
