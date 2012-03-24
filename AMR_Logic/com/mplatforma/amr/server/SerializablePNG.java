/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.server;

import java.io.Serializable;


/**
 * @author Kate Nezdoly
 */

public class SerializablePNG implements Serializable{
    
private int width;
private int height;
private int imageType;
private int[] pixels;

    public SerializablePNG() {
    }

public SerializablePNG(
final int w,
final int h,
final int imageType,
final int[] pixels
) {
this.width = w;
this.height = h;
this.imageType = imageType;
this.pixels = pixels;
}

public int getW() {
return width;
}

public int getH() {
return height;
}

public int getImageType() {
return imageType;
}

public int[] getPixels() {
return pixels;
}
    
}
