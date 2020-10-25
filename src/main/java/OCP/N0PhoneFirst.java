package OCP;

import static OCP.N1PhoneStrategy.Button.SEND_BUTTON;

public class N0PhoneFirst {

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

    static class Button {
        public final static int SEND_BUTTON = -99;

        private final Dialer dialer;
        private final int token;

        public Button(int token, Dialer dialer) {
            this.token = token;
            this.dialer = dialer;
        }

        public void press() {
            switch (token) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    dialer.enterDigit(token);
                    break;
                case SEND_BUTTON:
                    dialer.dial();
                    break;
                default:
                    throw new UnsupportedOperationException("unknown button pressed: token=" + token);
            }
        }
    }

    static class Dialer {
        public void enterDigit(int digit) {
            System.out.println("screen display:" + digit);
            System.out.println("speaker beep:" + digit);
        }

        public void dial() {
            System.out.println("dialing");
            System.out.println("radio connect");
        }
    }
}
