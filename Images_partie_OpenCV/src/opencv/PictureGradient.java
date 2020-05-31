package opencv;

import java.util.ArrayList;
import java.util.List;
 
public class PictureGradient {
    private Integer width;
    private Integer height;
    private List<Double> direction;
    private List<Double> size;
 
    public Integer getWidth() {
        return width;
    }
 
    public void setWidth(Integer width) {
        this.width = width;
    }
 
    public Integer getHeight() {
        return height;
    }
 
    public void setHeight(Integer height) {
        this.height = height;
    }
 
    public List<Double> getDirection() {
        return direction == null ? new ArrayList<Double>() : direction;
    }
 
    public void setDirection(List<Double> direction) {
        this.direction = direction;
    }
 
    public List<Double> getSize() {
        return size == null ? new ArrayList<Double>() : size;
    }
 
    public void setSize(List<Double> size) {
        this.size = size;
    }
 
}
