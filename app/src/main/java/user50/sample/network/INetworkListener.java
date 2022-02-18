package user50.sample.network;

public interface INetworkListener {

    /**
     * 네트워크 통신 성공.
     */
    public static final int RESULT_SUCCESS = 0;
    /**
     * 앱단 오류로 인한 네트워크 통신 실패.
     */
    public static final int RESULT_APP_ERROR = 1;
    /**
     * 서버단 오류로 인한 네트워크 통신 실패.
     */
    public static final int RESULT_SERVER_ERROR = 2;

    /**
     * 네트워크 통신 결과 반환.
     *
     * @param event      요청 타입
     * @param resultType {@link #RESULT_SUCCESS},
     *                   {@link #RESULT_APP_ERROR},
     *                   {@link #RESULT_SERVER_ERROR}
     * @param result     통신 결과 값
     */
    public void onNetworkResult(int event, int resultType, Network_Result result);

}
