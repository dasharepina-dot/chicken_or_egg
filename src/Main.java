// Класс Chicken - это поток (задача, которая выполняется параллельно с другими).
class Chicken extends Thread {

    // Код, который выполнится, когда поток запустят через start().
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                // Пауза от 0 до 100 мс. Из-за случайных пауз
                // слова двух потоков выводятся вперемешку.
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                // Обязательная обработка ошибки, которая может возникнуть при паузе.
                e.printStackTrace();
            }
            System.out.println("Курица");
        }
    }
}

// Класс Egg - второй поток, устроен так же, как Chicken.
class Egg extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Яйцо");
        }
    }
}

public class Main {

    // С этого метода начинается выполнение программы.
    public static void main(String[] args) {

        // Создаём два потока (пока не запущены).
        Chicken chicken = new Chicken();
        Egg egg = new Egg();

        // Запускаем оба потока, дальше они работают одновременно.
        chicken.start();
        egg.start();

        try {
            // join() - главный поток ждёт, пока chicken завершится.
            chicken.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // isAlive() - проверяет, работает ли поток ещё.
        // Если egg ещё работает, значит chicken закончил раньше.
        if (egg.isAlive()) {

            try {
                // Ждём, пока egg тоже завершится.
                egg.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // egg закончил последним, значит победило Яйцо.
            System.out.println("Спор окончен. Победило Яйцо");

        } else {
            // egg уже завершился раньше, значит последней закончила Курица.
            System.out.println("Спор окончен. Победила Курица");
        }
    }
}