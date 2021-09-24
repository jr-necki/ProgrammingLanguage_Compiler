package Assignment3;

import java.io.IOException;
import java.io.PushbackInputStream;

class Calc2 {
    int token; int value; int ch;
    private PushbackInputStream input;
    final int NUMBER=256;

    Calc2(PushbackInputStream is) {
        input = is;
    }

    int getToken( )  {			// 다음 토큰을 읽어서 리턴
        while(true) {
            try  {
                ch = input.read();
                if (ch == ' ' || ch == '\t' || ch == '\r') ; // 공백이면 아무것도 안함
                else
                if (Character.isDigit(ch)) { // 숫자면
                    value = number( );
                    input.unread(ch);
                    return NUMBER;
                }
                else return ch;
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private int number( )  {	// number -> digit { digit }
        int result = ch - '0';
        try  {
            ch = input.read();
            while (Character.isDigit(ch)) {
                result = 10 * result + ch -'0';
                ch = input.read();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return result;
    }

    void error( ) {
        System.out.printf("parse error : %d\n", ch);
        //System.exit(1);
    }

    void match(int c) {			// 현재 토큰 확인 후 다음 토큰 읽기
        if (token == c)
            token = getToken();
        else error();
    }


    //////////////////
    // Assignment 3 //
    //////////////////

    void command( ) {			// command -> expr '\n'
        // (1) command() 내부 채우기
        int result = expr();
        if(token == '\n'){
            System.out.println(result);
        }else{
            error();
        }
    }
    int expr( ) {				// <expr> -> <term> { + <term> | - <term> }
        // (2) expr() 내부 채우기
        int result = term();
        while (token == '+'|| token =='-'){
           if(token == '+'){
               match('+');
               result+=term();
           }else{
               match('-');
               result-=term();
           }
        }
        return result;
    }
    int term( ) {				//<term> -> <factor> { * <factor> | / <factor> }
        // (3) term() 내부 채우기
        int result = factor();
        while (token == '*' || token =='/'){
           if(token == '*'){
               match('*');
               result *= factor();
           }else{
               match('/');
               result /= factor();
           }
        }
        return result;
    }
    int factor() {				// <factor> -> [-] <number> | (<expr>)
        // (4) factor() 내부 채우기
        int result = 0;
        if(token == '('){
            match('(');
            result = expr();
            match(')');
        }else if(token == '-'){
            match('-');
            if(token == NUMBER){
                result = value*-1; // 실제값 저장
                match(NUMBER); // NUMBER 확인
            }
        }else if(token == NUMBER){
            result = value; // 실제값 저장
            match(NUMBER); // NUMBER 확인
        }
        return result;
    }

    //////////////////
    //     End      //
    //////////////////

    void parse( ) {
        token = getToken(); 	// 첫 번째 토큰을 가져옴
        command();          	// parsing command 호출
    }

    public static void main(String args[]) {
        Calc2 calc = new Calc2(new PushbackInputStream(System.in));
        while(true) {
            System.out.print(">> ");
            calc.parse();
        }
    }
}