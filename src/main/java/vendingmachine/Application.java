package vendingmachine;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller();

        controller.init();

        // 남은 금액이 상품의 최저 가격보다 적거나, 모든 상품이 소진된 경우에 while문 탈출
        // 투입 금액 입력받기
        controller.EnterInputMoney();
        while (controller.getInputMoney() >= controller.getMinProduct() && controller.hasQuantity()){
            controller.progress();
        }

        controller.returnChanges();
    }
}