public class PIDController {

    private double kp;
    private double ki;
    private double kd;
    private double totalError = 0;
    private double previousError = 0;
    private long previousTime = System.nanoTime();

    //Limiting variables:

    private boolean isFirst = true;
    private double lowerLimit = -1.0;
    private double upperLimit = 1.0;
    private double gainCoefficient = 1.0;
    private boolean isNoLimit = false;


    public PIDController(double kp, double ki, double kd){
        this.kp = kp;
        this.kd = kd;
        this.ki = ki;
    }

    public double calculate(double setPoint, double currentPoint){
        double error = setPoint - currentPoint;
        if(previousError == 0) previousError = error;
        totalError += error;
        double gain = ((error * kp) + (totalError * ki) + ((error - previousError) / (System.nanoTime() - previousTime)) * kd) * gainCoefficient;
        previousTime = System.nanoTime();

        if(!isNoLimit) {
            if (isFirst) {
                double wanted;
                wanted = upperLimit - (upperLimit + lowerLimit) / 2;
                gainCoefficient = wanted / Math.abs(gain);
                gain = gain * gainCoefficient;
                isFirst = false;
            }

            if (gain > upperLimit) {
                gain = upperLimit;
            } else if (gain < lowerLimit) {
                gain = lowerLimit;
            }
        }


        previousError = error;
        return gain;
    }


    //Limiting methods:

    public void setLimit(double lowerLimit, double upperLimit){
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    public void setLimit(double upperLimit){
        this.upperLimit = upperLimit;
        this.lowerLimit = 0;
    }

    public void setNoLimit(boolean state){
        isNoLimit = state;
    }
}
