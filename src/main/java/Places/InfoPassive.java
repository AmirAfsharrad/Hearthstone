package Places;

import java.util.ArrayList;
import java.util.Random;

public class InfoPassive extends Place {
    public static InfoPassive infoPassive = new InfoPassive();
    private String[] passives;

    private InfoPassive() {
        passives = new String[]{"draw twice", "mana jump", "nurse", "warriors", "zombie"};
    }

    public static InfoPassive getInfoPassive() {
        return infoPassive;
    }

    public ArrayList<String> getPassives(int n) {
        System.out.println("here");
        ArrayList<String> out = new ArrayList<>();
        boolean[] check = new boolean[passives.length];
        Random random = new Random();
        int count = 0;
        while (count < n) {
            int rand = random.nextInt(passives.length);
            if (!check[rand]) {
                check[rand] = true;
                out.add(passives[rand]);
                count++;
            }
        }
        System.out.println(out);
        return out;
    }

    @Override
    public void defaultResponse() {

    }
}
