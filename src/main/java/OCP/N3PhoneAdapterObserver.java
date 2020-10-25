package OCP;

import java.util.LinkedList;
import java.util.List;

public class N3PhoneAdapterObserver {

    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.digitButtons[9].press();
        phone.digitButtons[1].press();
        phone.digitButtons[1].press();
        phone.sendButton.press();
    }

    static class Phone {
        private final Dialer dialer;
        private final Button[] digitButtons;
        private final Button sendButton;

        public Phone() {
            dialer = new Dialer();
            digitButtons = new Button[10];

            for (int i = 0; i < digitButtons.length; i++) {
                digitButtons[i] = new Button();
                final int digit = i;
                digitButtons[i].addButtonServer(new ButtonServer() {
                    @Override
                    public void press() {
                        dialer.enterDigit(digit);
                    }
                });
            }
            sendButton = new Button();
            sendButton.addButtonServer(new ButtonServer() {
                @Override
                public void press() {
                    dialer.dial();
                }
            });
        }
    }

    interface ButtonServer {
        void press();
    }

    static class Button {
        private final List<ButtonServer> buttonServerList;

        public Button() {
            this.buttonServerList = new LinkedList<>();
        }

        public void addButtonServer(ButtonServer buttonServer) {
            assert buttonServer != null;
            this.buttonServerList.add(buttonServer);
        }

        public void press() {
            for (ButtonServer buttonServer : buttonServerList) {
                buttonServer.press();
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

