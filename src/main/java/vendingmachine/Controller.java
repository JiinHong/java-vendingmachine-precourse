package vendingmachine;

import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class Controller {

    private HashMap<Integer, Integer> Machine_Coins = new HashMap<>();
    {{
        Machine_Coins.put(10, 0);
        Machine_Coins.put(50, 0);
        Machine_Coins.put(100, 0);
        Machine_Coins.put(500, 0);
    }}
    private int totalMoney = 0; // 자판기가 보유한 동전
    private int inputMoney = 0; // 투입한 금액
    private List<Product> products = new ArrayList<>();
    private String userProduct = "";

    // 자판기가 보유한 금액으로 동전을 무작위로 생성
    public void init() {
        System.out.println("자판기가 보유하고 있는 금액을 입력해주세요.");

        // 금액 입력받기
        initTotalMoney();

        // 보유한 금액으로 동전을 무작위 생성? 방식을 어떻게 해야할 지 고민
        initMachineCoin();

        // 상품 입력받기
        initProduct();
    }

    private void initMachineCoin() {
        int random_coin;
        while (totalMoney > 0) {
            random_coin = Coin.pickRandomCoin();
            if (random_coin > totalMoney) continue;
            Machine_Coins.put(random_coin, Machine_Coins.get(random_coin) + 1);
            totalMoney -= random_coin;
        }

        System.out.println("자판기가 보유한 동전");
        for (int Coin : new int[]{10, 50, 100, 500}) {
            System.out.println(Coin + "원 - " + Machine_Coins.get(Coin) + "개");
        }

        System.out.println();
    }


    private void initProduct() {
        String[] userInput;

        System.out.println("상품명과 가격, 수량을 입력해 주세요.");

        // 입력 형식 검사
        while (true) {
            try {
                userInput = readLine().split(";");
                checkFormat(userInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 입력 받은거 product에 집어넣기
        // 형식별로 나누는건 Product 클래스에서 구현
        for (String s : userInput) {
            products.add(new Product(s));
        }

        System.out.println();
    }

    private void checkFormat(String[] userInput) {
        for (String s : userInput) {
            String pattern = "^\\[([가-힣]+|[a-zA-Z]+),(\\d+),(\\d+)\\]$";
            if (!s.matches(pattern)) throw new IllegalArgumentException("[ERROR] 입력 형식이 올바르지 않습니다.");
        }
    }

    private void initTotalMoney() {
        String userInput;
        while (true) {
            userInput = readLine();
            if (isNumber(userInput))
                break;
        }
        totalMoney = Integer.parseInt(userInput);
        System.out.println();
    }

    private boolean isNumber(String input) {
        try {
            if (!input.matches("\\d+")) {
                throw new IllegalArgumentException("[ERROR] 금액은 숫자여야 합니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public int getInputMoney() {
        return inputMoney;
    }

    public int getMinProduct() {
        int min = products.get(0).getPrice();
        for (Product product : products) {
            if (product.getPrice() < min) {
                min = product.getPrice();
            }
        }
        return min;
    }

    public boolean hasQuantity() {
        for (Product product : products) {
            if (product.getQuantity())
                return true;
        }
        return false;
    }

    public void progress() {
        // 투입한 금액으로 자판기에서 구입하는 프로세스 구현
        System.out.println("투입 금액: " + inputMoney + "원");
        System.out.println("구매할 상품명을 입력해 주세요.");

        // 상품명을 입력받고
        UserProductInput();

        // 투입 금액에서 차감
        buyProduct();

        System.out.println();
    }

    private void buyProduct() {
        for (Product product : products) {
            if (product.getName().equals(userProduct)) {
                product.buy();
                inputMoney -= product.getPrice();
            }
        }
    }

    private void UserProductInput() {
        while (true) {
            userProduct = readLine();
            try {
                checkProduct();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkProduct() {
        for (Product product : products) {
            if (product.getName().equals(userProduct) && product.getPrice() <= inputMoney) return;
            if (product.getName().equals(userProduct) && product.getPrice() > inputMoney) throw new IllegalArgumentException("[ERROR] 금액이 부족합니다.");
        }
        throw new IllegalArgumentException("[ERROR] 상품이 존재하지 않습니다.");
    }


    public void EnterInputMoney() {
        System.out.println("투입 금액을 입력해 주세요.");
        String userInput;
        while (true) {
            userInput = readLine();
            if (isNumber(userInput))
                break;
        }
        inputMoney = Integer.parseInt(userInput);
        System.out.println();
    }

    public void returnChanges() {
        System.out.println("투입 금액: " + inputMoney + "원");
        System.out.println("잔돈");
        for (int coin : new int[]{500, 100, 50, 10}) {
            if (Machine_Coins.get(coin) == 0) {
                continue;
            } else if (inputMoney >= coin && inputMoney/coin <= Machine_Coins.get(coin)) {
                System.out.println(coin + "원 - " + inputMoney/coin + "개");
                inputMoney -= coin*inputMoney/coin;
            } else if (inputMoney >= coin && inputMoney/coin > Machine_Coins.get(coin)) {
                System.out.println(coin + "원 - " + Machine_Coins.get(coin) + "개");
                inputMoney -= coin*Machine_Coins.get(coin);
            }
        }
    }

    // 잔돈을 돌려줄 때, 현재 보유한 최소 개수의 동전으로 잔돈을 돌려준다.

    // 상품명, 가격, 수량을 입력하여 상품을 추가할 수 있다.
    // 상품 가격은 100원 이상, 10원으로 나누어 떨어져야함.
}
