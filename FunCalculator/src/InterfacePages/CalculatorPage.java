package InterfacePages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalculatorPage implements Initializable {
    @FXML
    public Label digit;

    public Button button1;
    public void HandleButton1() { handleNumber('1');};
    public Button button2;
    public void HandleButton2() { handleNumber('2');};
    public Button button3;
    public void HandleButton3() { handleNumber('3');};
    public Button button4;
    public void HandleButton4() { handleNumber('4');};
    public Button button5;
    public void HandleButton5() { handleNumber('5');};
    public Button button6;
    public void HandleButton6() { handleNumber('6');};
    public Button button7;
    public void HandleButton7() { handleNumber('7');};
    public Button button8;
    public void HandleButton8() { handleNumber('8');};
    public Button button9;
    public void HandleButton9() { handleNumber('9');};
    public Button button0;
    public void HandleButton0() { handleNumber('0');};
    public Button buttonAdd;
    public void HandleAdd() { handleOperation(Integer::sum,'+');};
    public Button buttonSubtract;
    public void HandleSubtract() { handleOperation((a,b)->a-b,'-');};
    public Button buttonMultiply;
    public void HandleMultiply() { handleOperation((a,b)->a*b,'*');};
    public Button buttonEquals;
    public void HandleEquals() { handleOperation(currentFunc,currentFuncSymbol);};
    public Button buttonModulo;
    public void HandleModulo() { handleOperation((a,b)->a%b,'%');};
    public Button buttonDivide;
    public void HandleDivide() { handleOperation((a,b)->a/b,'/');};


    String currentDigit="";
    Integer currentInt=0;
    boolean canRewriteDigit;
    BiFunction<Integer,Integer,Integer> currentFunc;
    char currentFuncSymbol=' ';
    private void handleNumber(char _char) {
        currentDigit=(canRewriteDigit ? "":currentDigit)+_char;
        canRewriteDigit = false;
        digit.textProperty().set(GetExample());
    }

    private void handleOperation(BiFunction<Integer,Integer,Integer> func, char symbol){
        if(currentFunc!=null)
        {
            currentInt = getCalculation();
            currentDigit = "0";
        }
        canRewriteDigit=true;
        currentFuncSymbol = symbol;
        currentFunc = func;
        digit.textProperty().set(GetExample());
    }

    private Integer getCalculation() {
        return currentFunc!=null?
                currentFunc.apply(currentInt, Integer.parseInt(currentDigit))
                :Integer.parseInt(currentDigit);
    }

    private String GetExample(){
        return currentInt.toString()+currentFuncSymbol+currentDigit;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
