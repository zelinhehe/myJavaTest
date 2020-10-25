package OCP;

import static OCP.N1PhoneStrategy.Button.SEND_BUTTON;

public class N1PhoneStrategy {

    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.digitButtons[9].press();
        phone.digitButtons[1].press();
        phone.digitButtons[1].press();
        phone.sendButton.press();
    }

    static class Phone {
        private final Button[] digitButtons;
        private final Button sendButton;

        public Phone() {
            Dialer dialer = new Dialer();
            digitButtons = new Button[10];

            for (int i = 0; i < digitButtons.length; i++) {
                digitButtons[i] = new Button(i, dialer);
            }

            sendButton = new Button(SEND_BUTTON, dialer);
        }
    }

    interface ButtonServer {
        void press(int token);
    }

    static class Button {
        public final static int SEND_BUTTON = -99;
        private final ButtonServer buttonServer;
        private final int token;

        public Button(int token, ButtonServer buttonServer) {
            this.token = token;
            this.buttonServer = buttonServer;
        }

        public void press() {
            buttonServer.press(token);
        }
    }

    static class Dialer implements ButtonServer {

        public void enterDigit(int digit) {
            System.out.println("screen display:" + digit);
            System.out.println("speaker beep:" + digit);
        }

        public void dial() {
            System.out.println("dialing");
            System.out.println("radio connect");
        }

        @Override
        public void press(int token) {
            if (token == SEND_BUTTON) {
                dial();
            } else {
                enterDigit(token);
            }
        }
    }
}
