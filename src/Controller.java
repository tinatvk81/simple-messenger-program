import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Controller {

    @FXML private Button sendChatButton;
    @FXML private TextFlow chatBox;
    @FXML private TextField chatInput;

    @FXML
    public void sendChat(){
        String msg=chatInput.getText();
        if(!msg.isEmpty()) {
            Client.printWriter.println(msg);
            chatInput.setText("");
            showChatMsg("you",msg);
        }
    }

    public void showChatMsg(String name,String msg){
        Text text1=new Text(name+": ");
        text1.setStyle("-fx-font-weight: bold");
        text1.setWrappingWidth(80);
        Text text2=new Text(msg+"\n");
        text2.setStyle("-fx-font-weight: normal");
        text1.setWrappingWidth(80);
        chatBox.getChildren().addAll(text1, text2);
    }

}
