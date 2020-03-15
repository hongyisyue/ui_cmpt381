

public class CalcMVC {

    //... Create model, view, and controller.  They are
    //    created once here and passed to the parts that
    //    need them so there is only one copy of each.
    public static void main(String[] args) {
    	CalcView       view       = new CalcView();
        CalcModel      model      = new CalcModel();
        model.addObserver(view);
        CalcController controller = new CalcController(model, view);
        
        
        view.setVisible(true);

    	CalcView       view2       = new CalcView();
        model.addObserver(view2);
        CalcController controller2 = new CalcController(model, view2);
        
        view2.setVisible(true);

    }
}
