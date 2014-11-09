package cz.kobzol.bulanci.command;

public class EchoCommand implements ICommand {

    public String text;

    public EchoCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        System.out.println(this.text);
    }

    @Override
    public ISignatureCommand getSignatureCommand() {
        return new Signature(this.text);
    }

    public static class Signature extends SignatureCommand {
        public String text;

        public Signature(String text) {
            this.text = text;
        }
    }
}
