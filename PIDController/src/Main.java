public class Main {

    public static void main(String[] args){
        PIDController pid = new PIDController(0.01,0.0001,0.0001);
        pid.setNoLimit(true);
        double setPoint = 100;
        double currentPoint = 0;
        while (Math.abs(setPoint - currentPoint) > 0.01){
            System.out.println(currentPoint += 13.21 * pid.calculate(setPoint, currentPoint));
        }
    }
}
