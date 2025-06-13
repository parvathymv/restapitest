

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SampleTest {

	@Test
	public void testEvenOddNumber() {
		Sample obj = new Sample();
		//assertEquals("9 is not a even number", false, meo.isEvenNumber(9));
		
		//assertEquals(true, obj.isEvenNumber(9));
		assertEquals(true, obj.isEvenNumber(10));
		assertEquals(false,obj.isEvenNumber(5));
		assertEquals(false,obj.isEvenNumber(15));
		
	}
	@Test(timeout = 1000)
	   public void testPrintMessage() {	
		Sample obj = new Sample();
	      System.out.println("Inside testPrintMessage()");     
	      obj.printMessage();     
	   }
	
@Test(expected = ArithmeticException.class)
	    public void testExceptionCheck()
	    {
		 Sample obj = new Sample();
	     obj.exceptionCheck();
	    }

}
