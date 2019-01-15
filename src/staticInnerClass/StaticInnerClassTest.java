package staticInnerClass;

public class StaticInnerClassTest {

    public static void main(String[] args){
        double[] d = new double[20];
        for (int i = 0; i <  d.length; i++){
            d[i] = Math.random() * 100;
            System.out.println(d[i]);
        }

        ArrayAlg.Pair p = ArrayAlg.mimmax(d);
        System.out.println("min=" + p.getFirst() + " max=" + p.getSecond());
    }
}

class ArrayAlg{

    public static class Pair{
        private double first;
        private double second;

        public Pair(double f, double s){
            first = f;
            second = s;
        }

        public double getFirst(){
            return first;
        }
        public double getSecond(){
            return second;
        }
    }

    public static Pair mimmax(double[] d){
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (double v : d){
            if (v < min) min = v;
            if (v > max) max = v;
        }
        System.out.println("min=" + min + " max=" + max);

        return new Pair(min, max);
    }
}
