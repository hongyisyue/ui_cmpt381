/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 *
 * @author gutwin
 */
public class TrackerTextView extends StackPane implements TrackerModelListener {
    Label summary;
    TrackerModel model;
    
    public TrackerTextView() {
        summary = new Label("0 clicks, 0 total movement");
        summary.setFont(new Font(24));
        getChildren().add(summary);
    }
    
    public void setModel(TrackerModel aModel) {
        model = aModel;
    }
    
    public void modelChanged() {
        summary.setText(model.numberOfClicks + " clicks, " + (int)(model.totalMovement) + " total movement");
    }
}
