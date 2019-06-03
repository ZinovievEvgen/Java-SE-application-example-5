/*
1 четверть: х>0, y>0
2 четверть: x<0, y>0
3 четверть: x<0, y<0
4 четверть: х>0, y<0
*/

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int X;
        int Y;
        int maxy = -1;
//********************************************************************************************
        List<Point> firstPart = new ArrayList<>();
        List<Point> secondPart = new ArrayList<>();
        List<Point> thirdPart = new ArrayList<>();
        List<Point> fourthPart = new ArrayList<>();
        Map<Integer, Integer> maxCountPointInPart = new HashMap<>();
        Map<Integer, List<Point>> partPointList = new HashMap<>();
        ComparePoint comp = new ComparePoint();

        // распределяем точки по четвертям в листы
        for (int i = 0; i < n; i++) {
            X = scan.nextInt();
            Y = scan.nextInt();

            if (X == 0 || Y == 0) {
                continue;
            } else if (X > 0 && Y > 0) {
                firstPart.add(new Point(X, Y));
            } else if (X < 0 && Y > 0) {
                secondPart.add(new Point(X, Y));
            } else if (X < 0 && Y < 0) {
                thirdPart.add(new Point(X, Y));
            } else if (X > 0 && Y < 0) {
                fourthPart.add(new Point(X, Y));
            } else System.out.println("Errors. Please speak about error for administrator");
        }
        // заполняем мапу с листами по четвертям
        partPointList.put(1, firstPart);
        partPointList.put(2, secondPart);
        partPointList.put(3, thirdPart);
        partPointList.put(4, fourthPart);

        // заполняем мапу key = номер четверти, value = размер листа (т.е. количесвто точек по четвертям)
        maxCountPointInPart.put(1, firstPart.size());
        maxCountPointInPart.put(2, secondPart.size());
        maxCountPointInPart.put(3, thirdPart.size());
        maxCountPointInPart.put(4, fourthPart.size());


        // находим максимум среди четвертей, записываем максимальное количество
        for (int j = 1; j <= maxCountPointInPart.size(); j++) {
            if (maxCountPointInPart.get(j) > maxy) {
                maxy = maxCountPointInPart.get(j);
            }
        }

        // выносим в переменную наименование четверти с наибольшим количеством точек
        int K = getPointList(printMaxPartforValue(maxCountPointInPart, maxy), partPointList, comp);
        System.out.println("K = " + K);
        // выводим остальные переменные
        System.out.println("M = " + maxy);
        System.out.println("A = " + Collections.min(printPointListForKey(partPointList,K), comp).toString());
        System.out.println("R = " + Collections.min(printPointListForKey(partPointList,K), comp).maxCheck());
    }

    public static List<Integer> printMaxPartforValue(Map<Integer, Integer> maxCount, int maxy) {
        //print name of max point part (из мапы с листами выводим номер четверти (значение key)
        // по ее value, учтено если одинаковое количество точек)
        List<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, Integer> item : maxCount.entrySet()) {
            if (item.getValue() == maxy) {
                keys.add(item.getKey());
            }
        }
        return keys;
    }

    // метод по переменной key выводит value
    public static List<Point> printPointListForKey(Map<Integer, List<Point>> maxPointPart, int maxy) {
        Integer key = maxy;
        Optional<List<Point>> result = maxPointPart.entrySet()
                .stream()
                .filter(entry -> key.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
        return result.get();
    }

    // метод выполняет условие при равенстве количесвта точек у нескольких четвертей
    public static int getPointList(List<Integer> list, Map<Integer, List<Point>> maxPointPart, ComparePoint comp) {
        int part = 0;
        int miniMod = Integer.MAX_VALUE;
        for (int k = 0; k < list.size(); k++) {
            List<Point> pointsOfPart = maxPointPart.get(list.get(k));
            Point min = Collections.min(pointsOfPart, comp);
            if (min.maxCheck() < miniMod) {
                miniMod = min.maxCheck();
                part = list.get(k);
            }
        }
        return part;
    }
}