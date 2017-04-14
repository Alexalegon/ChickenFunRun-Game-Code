/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

/**
 *
 * @author Miguel Martrinez
 */
public class RoadLimits {
RoadSize size;
int min;
int max;
    public RoadLimits() {
    }
     public RoadLimits(RoadSize type) {
         this.size = type;
    }
      public RoadLimits(int min, int max) {
          this.min = min;
          this.max = max;
    }
       public RoadLimits(RoadSize type, int min, int max) {
           this.size = type;
           this.min = min;
           this.max = max;
    }
      public void setMin(int min){
          this.min = min;
      }
    public void setMax(int max){
          this.max = max;
      }
    public void setMinMax(int min, int max){
          this.min = min;
          this.max = max;
      }
    public void setTypeMinMax(RoadSize type, int min, int max){
        this.size = type;
        this.min = min;
        this.max = max;
    }
    public RoadSize getRoadSize(){
        if(size == null)
            throw new NullPointerException("type is null");
        else
            return size;
    }
    public int getMin(){
        return min;
    }
    public int getMax(){
        return max;
    }
    
    public enum RoadSize {
        SHORT(1),MIDSIZE(2),LONG(3);
        int number;
        int pointGroups;
        private RoadSize(int pointGroups){
            this.pointGroups = pointGroups;
        }
        public void setNumber(int number){
            this.number = number;
        }
    }
}
