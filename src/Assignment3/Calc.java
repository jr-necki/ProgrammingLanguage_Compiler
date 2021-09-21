package Assignment3;

import java.io.*;

class Calc {
    int token; int value; int ch;
    private PushbackInputStream input;
    final int NUMBER=256;

    Calc(PushbackInputStream is) {
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
    }

    int expr( ) {				// expr -> term { '+' term }
        // (2) expr() 내부 채우기
    }

    int term( ) {				//term -> factor { '*' factor }
        // (3) term() 내부 채우기
    }

    int factor() {				// factor -> '(' expr ')' | number
        // (4) factor() 내부 채우기
    }

    //////////////////
    //     End      //
    //////////////////

    void parse( ) {
        token = getToken(); 	// 첫 번째 토큰을 가져옴
        command();          	// parsing command 호출
    }

    public static void main(String args[]) {
        Calc calc = new Calc(new PushbackInputStream(System.in));
        while(true) {
            System.out.print(">> ");
            calc.parse();
        }
    }
}