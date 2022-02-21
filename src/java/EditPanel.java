public interface EditPanel {


    public void processChanges();
    public void broadcastChange();
    public void listenForChanges(ChangesEvent e);
    
}
