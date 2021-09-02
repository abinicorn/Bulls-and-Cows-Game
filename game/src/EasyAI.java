public class EasyAI extends Gamer {

    public EasyAI(int length) {
        this.length = length;
        this.secret = getRandomString(length);
    }

    @Override
    public String guess() {

        return getRandomString(length);
    }


}
