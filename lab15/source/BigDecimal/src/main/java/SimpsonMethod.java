import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SimpsonMethod {


    public static void main(String[] args) {
        System.out.println("Wynik na double: " + SimpsonMethod.countSimpsonMethod());
        System.out.println("Wynik BigDecimal: " + SimpsonMethod.countSimpsonMethodBigDecimal());
    }


    private static double myFunction(double value) {
        return (value * value) + 5 * value + 3;
    }

    private static double countSimpsonMethod() {
        double sum = 0;
        double dx = 1;
        double N = 3 / dx;
        for (int i = 0; i < N; i++) {
            sum += 1f / 6f * dx * (myFunction(i * dx) + 4 * myFunction(0.5 * dx + i * dx)) + myFunction(dx + i * dx);
        }
        return sum;
    }

    private static BigDecimal myFunctionBigDecimal(BigDecimal value) {
        return (value.multiply(value)).add(value.multiply(new BigDecimal(5))).add(new BigDecimal(3));
    }

    private static BigDecimal countSimpsonMethodBigDecimal() {
        BigDecimal sum = new BigDecimal(0).setScale(12, RoundingMode.HALF_DOWN);
        BigDecimal dx = new BigDecimal(1);
        BigDecimal N = new BigDecimal(3).divide(dx, RoundingMode.HALF_DOWN);
        for (BigDecimal i = BigDecimal.valueOf(0); i.compareTo(N) < 0; i = i.add(new BigDecimal(1))) {
            sum = sum.add(new BigDecimal(1).setScale(12, RoundingMode.HALF_DOWN).divide(new BigDecimal(6).setScale(12, RoundingMode.HALF_DOWN), RoundingMode.HALF_DOWN)
                    .multiply(dx).multiply(myFunctionBigDecimal(i.multiply(dx))
                            .add((new BigDecimal(4).setScale(12, RoundingMode.HALF_DOWN)).multiply(myFunctionBigDecimal(new BigDecimal("0.5").setScale(12, RoundingMode.HALF_DOWN).multiply(dx)
                                    .add(i.multiply(dx)))))).add(myFunctionBigDecimal(dx.add(i.multiply(dx)))));
        }
        return sum;
    }
}
