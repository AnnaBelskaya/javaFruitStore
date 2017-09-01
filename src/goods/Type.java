package goods;

public enum Type {
    Apples(52),
    Apricots(48),
    Bananas(89),
    Grapes(67),
    Kiwifruit(61),
    Mangoes(60),
    Oranges(47),
    Peaches(39),
    Pears(57),
    Pineapples(50);

    public final int calories;

    Type(int calories) {
        this.calories = calories;
    }

    public static Type getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
