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
    public ISerializableCommand getSeriliaziableCommand() {
        return new Serializable(this.text);
    }

    public static class Serializable extends SerializableCommand {
        public String text;

        public Serializable(String text) {
            this.text = text;
        }
    }
}
