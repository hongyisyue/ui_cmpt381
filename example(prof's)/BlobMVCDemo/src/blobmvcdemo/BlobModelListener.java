/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blobmvcdemo;

/**
 *
 * @author gutwin
 */
public interface BlobModelListener {
    public void modelChanged();
    public void modelChanged(double dx, double dy);
}
