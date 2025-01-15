
import java.util.Random;

public class HW4 {
    public static int bossHealth = 2500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Wither", "Thor"};
    public static int[] heroesHealth = {280, 270, 250, 240, 500, 150, 500, 250};
    public static int[] heroesDamage = {20, 10, 15, 0, 5, 10, 0, 50};
    public static int roundNumber;
    public static int damageGetGolem = 0;
    public static boolean f = false;


    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }
    public static void givelife() {
        if (heroesHealth[6] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] <= 0) {
                    heroesHealth[i] = heroesHealth[6];
                    heroesHealth[6] = 0;
                    System.out.println("Witcher gave his life to " + heroesAttackType[i]);
                    break;
                }
            }
        }
    }


    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int currentHP : heroesHealth) {
            if (currentHP > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void treat() {
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] < 100 && !heroesAttackType[i].equals("Medic")) {
                    heroesHealth[i] += 100;
                    System.out.println("Medic healed " + heroesAttackType[i] + "by 100 hp");
                    break; // Целитель может исцелить один раз за раунд
                }
            }
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        printStatistics();
        treat();
        if (heroesHealth[4] > 0) {
            System.out.println("Golem absorbed " + damageGetGolem + " damage");
            damageGetGolem = 0;
        }
        givelife();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        } while (randomIndex == 3 || randomIndex == 6);
        bossDefence = heroesAttackType[randomIndex];

    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesHealth[7] > 0 && i == 7){
                    Random random = new Random();
                    f = random.nextBoolean();
                    System.out.println("Thor stunned Boss!");
                }
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(2, 10); // 2,3,4,5,6,7,8,9
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage + " (" + coeff + ")");
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttack() {
        if (f){
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] <= 0) {
                continue;
            }
            if ( i == 5 && heroesHealth[5] > 0 && 0 == new Random().nextInt(4)){
                System.out.println("LUCKY DOGET ");
                continue;
            }
            int damage = bossDamage;
            if (heroesHealth[4] > 0 && !heroesAttackType[i].equals(heroesAttackType[4])) {
                int absorbedDamage = Math.min(bossDamage / 5, heroesHealth[4]);
                heroesHealth[4] -= absorbedDamage;
                damageGetGolem += absorbedDamage;
                damage -= absorbedDamage;
            }
            heroesHealth[i] -= Math.min(damage, heroesHealth[i]);

        }
    }

    public static void printStatistics() {
        System.out.println("ROUND: " + roundNumber + " -----------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] +
                    " damage: " + heroesDamage[i]);
        }
    }

}