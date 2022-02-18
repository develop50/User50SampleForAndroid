package user50.sample.network;

public class Network_Result {

    ////////////
    // Header //
    ////////////
    /**
     * -1 : 클래스 생성시 초기 값,
     * -2 : Exception 발생시 설정 값,
     * 그 외 : 결과 코드 값
     */
    public int code;

    //////////
    // Body //
    //////////
    /**
     * 결과 문자열.
     */
    public String result;

     /**
     * 생성자.
     */
    public Network_Result(){

        this.code = -1;

        this.result = null;

    }

}
