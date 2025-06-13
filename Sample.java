

public class Sample {
	
		 
	    public boolean isEvenNumber(int number){
	         
	        boolean result = false;
	        if(number%2 == 0){
	            result = true;
	        }
	        return result;
	    }
	    
	    public void printMessage(){
	        System.out.println("testing");
	       // while(true);
	     }   
	    
	    public void exceptionCheck()
	    {
	    	throw new ArithmeticException();
	    }
	    
	}

