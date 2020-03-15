// structure/calc-mvc/CalcModel.java
// Fred Swartz - December 2004
// Model
//     This model is completely independent of the user interface.
//     It could as easily be used by a command line or web interface.

//import java.math.BigInteger;
import java.util.Observable;

public class CalcModel extends Observable{
    //... Constants
    private static final String INITIAL_VALUE = "1";
    
    //... Member variable defining state of calculator.
    private Integer m_total;  // The total current value state.
    
    //============================================================== constructor
    /** Constructor */
    CalcModel() {
        reset();
    }
    
    //==================================================================== reset
    /** Reset to initial value. */
    public void reset() {
        m_total = new Integer(INITIAL_VALUE);
        setChanged();
        notifyObservers(m_total);
        System.out.println("In model, reset");
    }
    
    //=============================================================== multiplyBy
    /** Multiply current total by a number.
    *@param operand Number (as string) to multiply total by.
    */
    public void multiplyBy(String operand) {
        m_total = m_total * Integer.parseInt(operand);
        System.out.println("In model, MultipyBy m_total" + m_total);
        setChanged();
        notifyObservers(m_total);
    }
    
    //================================================================= setValue
    /** Set the total value. 
    *@param value New value that should be used for the calculator total. 
    */
    public void setValue(String value) {
        m_total = new Integer(value);
        setChanged();
        notifyObservers(m_total);
    }
    
    //================================================================= getValue
    /** Return current calculator total. */
    public String getValue() {
        return m_total.toString();
    }
}
