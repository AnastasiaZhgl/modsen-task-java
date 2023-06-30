import calculator.Calculator;

import java.util.Scanner;


public class RunApplication {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в мини-калькулятор-конвертер валют.\n" +
                "Данный инструмент поддерживает выполнение следующих операций:\n" +
                "1.сложение валют\n" +
                "2.вычитание валют\n" +
                "3.конвертация валют (toDollars() - в USD, toRubbles() - в рубли)\n" +
                "Для продолжения работы введите команду вида: toDollars(737р + toRubles($85,4))\n");
        Scanner sc = new Scanner(System.in);
        String exec = sc.nextLine();
        var calculator = new Calculator();
        calculator.calculate(exec);

//        calculator.calculate("12p-5p");
//        calculator.calculate("$12+$7");
//        calculator.calculate("toDollars(12p)");
//        calculator.calculate("toDollars(12p-5p)");
//        calculator.calculate("toRubles($12+$7)");
//        calculator.calculate("toRubles($12) + 5p");
//        calculator.calculate("toRubles($12)+7p");
//        calculator.calculate("75p+toRubles($12)");
//        calculator.calculate("$12.5+$6 - toDollars(7р-6р)+$1");
//        calculator.calculate("toDollars(737р + toRubles($85.4))");
//
//        calculator.calculate("12p-$5");
//        calculator.calculate("12-$5");
//        calculator.calculate("$12+7p");
//        calculator.calculate("toRubles($12-7p)");
    }
}