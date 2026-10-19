package version.beta.RogueLikeProyectoPersonal;

public class Friend implements Character {

    @Override
    public boolean isEnemy() {
        return false;
    }

    public void heal() {
        System.out.println("¡Te he curado!");
    }
}
