import java.util.Random;

public class CalcQian {

    public static void main(String[] args) {
        int max = 20;
        int pageSize = 200;
        int pages = 2;
        int column = 3;

        Random random = new Random();
        //add
//        for (int i = 1; i < pageSize * pages + 1; i++) {
//            int x = random.nextInt(max);
//            int y = random.nextInt(max - x);
//            System.out.print(String.format("%2d + %2d =                     ", x, y));
//
//            if (i % column == 0){
//                System.out.print("\n");
//            }
//        }

        //sub
        for (int i = 1; i < pageSize * pages + 1; i++) {
            int x = random.nextInt(max);
            int y = random.nextInt(x+1);
            System.out.print(String.format("%2d - %2d =                     ", x, y));

            if (i % column == 0){
                System.out.print("\n");
            }
        }

    }


}
