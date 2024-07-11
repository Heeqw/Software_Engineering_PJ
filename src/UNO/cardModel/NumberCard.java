package UNO.cardModel;

// 数字牌类，继承自卡片类
public class NumberCard extends Card {
    public NumberCard(Color color, String detail) {
        super(color, Type.NUMBER, calculateScore(detail), detail);
    }

    // 计算分数的方法
    private static int calculateScore(String detail) {
        // 在这里实现根据 detail 来计算分数的逻辑
        int score = Integer.parseInt(detail); // 将 detail 转换为整数作为分数
        return score;
    }

    @Override
    public String toString() {
        return "NumberCard" +
                ": " + getColor() +
                ", " + getDetail() ;
    }

}
