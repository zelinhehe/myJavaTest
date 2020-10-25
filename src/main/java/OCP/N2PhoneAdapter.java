package OCP;

public class N2PhoneAdapter {

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
                digitButtons[i] = new Button(new DigitButtonDialerAdapter(dialer, i));
            }

            sendButton = new Button(new SendButtonDialerAdapter(dialer));
        }
    }

    interface ButtonServer {
        void press();
    }

    static class Button {
        private final ButtonServer buttonServer;

        public Button(ButtonServer buttonServer) {
            this.buttonServer = buttonServer;
        }

        public void press() {
            buttonServer.press();
        }
    }

    static class DigitButtonDialerAdapter implements ButtonServer {
        private final Dialer dialer;
        private final int digit;

        public DigitButtonDialerAdapter(Dialer dialer, int digit) {
            this.dialer = dialer;
            this.digit = digit;
        }

        @Override
        public void press() {
            this.dialer.enterDigit(digit);
        }
    }

    static class SendButtonDialerAdapter implements ButtonServer {
        private final Dialer dialer;

        public SendButtonDialerAdapter(Dialer dialer) {
            this.dialer = dialer;
        }

        @Override
        public void press() {
            dialer.dial();
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

