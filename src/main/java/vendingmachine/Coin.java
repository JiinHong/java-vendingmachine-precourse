package vendingmachine;

import static camp.nextstep.edu.missionutils.Randoms.pickNumberInList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;
    private static final List<Integer> COIN_LIST = Arrays.asList(10,50,100,500);

    // 더 공부
    // 정적 초기화 블록 : 클래스가 처음 로드될 때 실행됨.
//    static {
//        // Coin.values()는 해당 열거형에 정의된 모든 상수를 포함하는 배열 반환
//        for (Coin coin : Coin.values()) {
//            COIN_LIST.add(coin.amount);
//        }
//    }

    Coin(final int amount) {
        this.amount = amount;
    }

    public static int pickRandomCoin() {
        return pickNumberInList(COIN_LIST);
    }
}