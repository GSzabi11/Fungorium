import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tests tests = new Tests();

        while (true) {

            System.out.println("=== Fungorium Szkeleton Tesztprogram ===");
            System.out.println("Válasszon egy tesztesetet a futtatáshoz:\n");
            System.out.println("1. Spóraevés (vágást gátló hatás)\n");
            System.out.println("2. Spóraevés (gyorsító hatás)\n");
            System.out.println("3. Spóraevés (bénító hatás)\n");
            System.out.println("4. Spóraevés (lassító hatás)\n");
            System.out.println("5. Gombatest növesztés\n");
            System.out.println("6. Rovar mozgása\n");
            System.out.println("7. Rovar átvágja a gombafonalat\n");
            System.out.println("8. Spóraszórás\n");
            System.out.println("0. Kilépés");

            System.out.print("\nKérem, adja meg a teszteset számát: ");
            int choice = scanner.nextInt();
            tests.InitTest(2, 5);

            switch (choice) {
                case 1 -> tests.RovarVagasGatloSporatFogyaszt();
                case 2 -> tests.RovarGyorsitoSporatFogyaszt();
                case 3 -> tests.RovarBenitoSporatFogyaszt();
                case 4 -> tests.RovarLassitoSporatFogyaszt();
                case 5 -> tests.GombatestNovesztes();
                case 6 -> tests.RovarMozgasa(0);
                case 7 -> tests.RovarAtvagjaAGombafonalat();
                case 8 -> tests.sporaszoras(3, 5);
                case 0 -> {
                    System.out.println("Kilépés...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Érvénytelen választás, próbálja újra.");
            }
            System.out.println();
        }
    }
}