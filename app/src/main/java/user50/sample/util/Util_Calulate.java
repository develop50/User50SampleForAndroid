package user50.sample.util;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 계산 클래스
 * Created by geomex on 2017-06-20.
 */
public class Util_Calulate {

    /**
     * inch를 cm로 변환하여 반환.
     * @param inch
     * @return
     */
    public static float transInchToCm(float inch) {
        return inch * 2.54f;
    }

    /**
     * cm를 inch로 변환하여 반환.
     * @param cm
     * @return
     */
    public static float transCmToInch(float cm){
        return cm * 0.393701f;
    }

    /**
     * mm를 cm로 변환하여 반환.
     * @param mm
     * @return cm
     */
    public static float transMmToCm(float mm){
        return mm * 0.1f;
    }

    /**
     * mm를 m로 변환하여 반환.
     * @param mm
     * @return m
     */
    public static float transMmToM(float mm){
        return mm * 0.001f;
    }

    /**
     * mm를 km로 변환하여 반환.
     * @param mm
     * @return km
     */
    public static float transMmToKM(float mm){
        return mm * 0.000001f;
    }

    /**
     * cm를 mm로 변환하여 반환.
     * @param cm
     * @return
     */
    public static float transCmToMm(float cm){
        return cm * 10.0f;
    }

    /**
     * cm를 m로 변환하여 반환.
     * @param cm
     * @return m
     */
    public static float transCmToM(float cm){
        return cm * 0.01f;
    }

    /**
     * cm를 km로 변환하여 반환.
     * @param cm
     * @return km
     */
    public static float transCmToKm(float cm){
        return cm * 0.00001f;
    }

    /**
     * m를 mm로 변환하여 반환.
     * @param m
     * @return mm
     */
    public static float transMToMm(float m){
        return m * 1000.0f;
    }

    /**
     * m를 cm로 변환하여 반환.
     * @param m
     * @return
     */
    public static float transMToCm(float m){
        return m * 100.0f;
    }

    /**
     * m를 km로 변환하여 반환.
     * @param m
     * @return
     */
    public static float transMToKm(float m){
        return m * 0.001f;
    }

    /**
     * km를 mm로 변환하여 반환.
     * @param km
     * @return mm
     */
    public static float transKmToMm(float km){
        return km * 1000000f;
    }

    /**
     * km를 cm로 변환하여 반환.
     * @param km
     * @return cm
     */
    public static float transKmToCm(float km){
        return km * 100000f;
    }

    /**
     * km를 m로 변환하여 반환.
     * @param km
     * @return m
     */
    public static float transKmToM(float km){
        return km * 1000f;
    }

    /**
     * 제곱미터를 에이커로 변환하여 반환.
     * @param squaremeter 제곱미터
     * @return 에이커
     */
    public static float transSquaremeterToAc(float squaremeter){
        return squaremeter * 0.000247f;
    }

    /**
     * 제곱미터를 헥타르로 변환하여 반환.
     * @param squaremeter 제곱미터
     * @return 헥타르
     */
    public static float transSquaremeterToHa(float squaremeter){
        return squaremeter * 0.0001f;
    }

    /**
     * 제곱미터를 아르로 변환하여 반환.
     * @param squaremeter 제곱미터
     * @return 아르
     */
    public static float transSquaremeterToA(float squaremeter){
        return squaremeter * 0.001f;
    }

    /**
     * 제곱미터를 평으로 변환하여 반환.
     * @param squaremeter 제곱미터
     * @return 평
     */
    public static float transSquaremeterToPyeong(float squaremeter){
        return squaremeter * 0.3025f;
    }

    /**
     * 에이커를 헥타르로 변환하여 반환.
     * @param ac 에이커
     * @return 헥타르
     */
    public static float transAcToHa(float ac){
        return ac * 0.404686f;
    }

    /**
     * 에이커를 아르로 변환하여 반환.
     * @param ac 에이커
     * @return 아르
     */
    public static float transAcToA(float ac){
        return ac * 40.468564f;
    }

    /**
     * 에이커를 평으로 변환하여 반환.
     * @param ac 에이커
     * @return 평
     */
    public static float transAcToPyeong(float ac){
        return ac * 1224.17407f;
    }

    /**
     * 에이커를 제곱미터로 변환하여 반환.
     * @param ac 에이커
     * @return 제곱미터
     */
    public static float transAcToSquaremeter(float ac){
        return ac * 4046.85642f;
    }

    /**
     * 헥타르를 에이커로 변환하여 반환.
     * @param ha 헥타르
     * @return 에이커
     */
    public static float transHaToAc(float ha){
        return ha * 2.471054f;
    }

    /**
     * 헥타르를 아르로 변환하여 반환.
     * @param ha 헥타르
     * @return 아르
     */
    public static float transHaToA(float ha){
        return ha * 100f;
    }

    /**
     * 헥타르를 평으로 변환하여 반환.
     * @param ha 헥타르
     * @return 평
     */
    public static float transHaToPyeong(float ha){
        return ha * 3025f;
    }

    /**
     * 헥타르를 제곱미터로 변환하여 반환.
     * @param ha 헥타르
     * @return 제곱미터
     */
    public static float transHaToSquaremeter(float ha){
        return ha * 10000f;
    }

    /**
     * 아르를 에이커로 변환하여 반환.
     * @param a 아르
     * @return 에이커
     */
    public static float transAToAc(float a){
        return a * 0.024711f;
    }

    /**
     * 아르를 헥타르로 변환하여 반환.
     * @param a 아르
     * @return 헥타르
     */
    public static float transAToHa(float a){
        return a * 0.01f;
    }

    /**
     * 아르를 평으로 변환하여 반환.
     * @param a 아르
     * @return 평
     */
    public static float transAToPyeong(float a){
        return a * 30.25f;
    }

    /**
     * 아르를 제곱미터로 변환하여 반환.
     * @param a 아르
     * @return 제곱미터
     */
    public static float transAToSquaremeter(float a){
        return a * 0.001f;
    }

    /**
     * 평을 에이커로 변환하여 반환.
     * @param pyeong 평
     * @return 에이커
     */
    public static float transPyeongToAc(float pyeong){
        return pyeong * 0.000817f;
    }

    /**
     * 평을 헥타르로 변환하여 반환.
     * @param pyeong 평
     * @return 헥타르
     */
    public static float transPyeongToHa(float pyeong){
        return pyeong * 0.000331f;
    }

    /**
     * 평을 아르로 변환하여 반환.
     * @param pyeong 평
     * @return 아르
     */
    public static float transPyeongToA(float pyeong){
        return pyeong * 0.033058f;
    }

    /**
     * 평을 제곱미터로 변환하여 반환.
     * @param pyeong 평
     * @return 제곱미터
     */
    public static float transPyeongToSquaremeter(float pyeong){
        return pyeong * 3.305785f;
    }

    /**
     * oz를 g으로 변환하여 반환.
     * @param oz
     * @return g
     */
    public static float transOzToG(float oz){
        return oz * 28.349523f;
    }

    /**
     * g을 oz로 변환하여 반환.
     * @param g
     * @return oz
     */
    public static float transGToOz(float g){
        return g * 0.035274f;
    }

    /**
     * knots를 m/s로 변환하여 반환.
     * @param knots
     * @return m/s
     */
    public static float transKnotsToMPS(float knots){
        return knots * 0.51444444f;
    }

    /**
     * m/s를 knots로 변환하여 반환.
     * @param meterPerSecond
     * @return knots
     */
    public static float transMPSToKnots(float meterPerSecond){
        return meterPerSecond * 1.9438444924574f;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // bit to kilobyte, megabyte, gigabyte, terabyte, petabyte, exabyte(추후 추가), zettabyte(추후 추가), yottabyte(추후 추가) //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 비트를 바이트로 변환하여 반환.
     * @param bit
     * @return 바이트
     */
    public static float transBitToByte(float bit){
        return bit * 0.125f;
    }

    /**
     * 비트를 킬로바이트로 변환하여 반환.
     * @param bit
     * @return 바이트
     */
    public static float transBitToKiloByte(float bit){
        return bit * 0.0001220703f;
    }

    /**
     * 비트를 메가바이트로 변환하여 반환.
     * @param bit
     * @return 바이트
     */
    public static float transBitToMegaByte(float bit){
        return bit * 0.0000001192093f;
    }

    /**
     * 비트를 기가바이트로 변환하여 반환.
     * @param bit
     * @return 바이트
     */
    public static float transBitToGigaByte(float bit){
        return bit * 0.0000000001164153f;
    }

    /**
     * 비트를 테라바이트로 변환하여 반환.
     * @param bit
     * @return 바이트
     */
    public static float transBitToTeraByte(float bit){
        return bit * 0.0000000000001136868f;
    }

    /**
     * 비트를 페타바이트로 변환하여 반환.
     * @param bit
     * @return 바이트
     */
    public static float transBitToPetaByte(float bit){
        return bit * 0.0000000000000001110223f;
    }





    /**
     * 바이트를 비트로 변환하여 반환.
     * @param transByte
     * @return 비트
     */
    public static float transByteToBit(float transByte){
        return transByte * 8f;
    }

    /**
     * 바이트를 킬로바이트로 변환하여 반환.
     * @param transByte
     * @return 킬로바이트
     */
    public static float transByteToKiloByte(float transByte){
        return transByte / 1024f;
    }

    /**
     * 바이트를 메가바이트로 변환하여 반환.
     * @param transByte
     * @return 메가바이트
     */
    public static float transByteToMegaByte(float transByte){
        return transByte / 1024f / 1024f;
    }

    /**
     * 바이트를 기가바이트로 변환하여 반환.
     * @param transByte
     * @return 기가바이트
     */
    public static float transByteToGigaByte(float transByte){
        return transByte / 1024f / 1024f / 1024f;
    }

    /**
     * 바이트를 테라바이트로 변환하여 반환.
     * @param transByte
     * @return 테라바이트
     */
    public static float transByteToTeraByte(float transByte){
        return transByte / 1024f / 1024f / 1024f / 1024f;
    }

    /**
     * 바이트를 페타바이트로 변환하여 반환.
     * @param transByte
     * @return 페타바이트
     */
    public static float transByteToPetaByte(float transByte){
        return transByte / 1024f / 1024f / 1024f / 1024f / 1024f;
    }









    /**
     * 킬로바이트를 비트로 변환하여 반환.
     * @param kiloByte
     * @return 비트
     */
    public static float transKiloByteToBit(float kiloByte){
        return kiloByte * 8192f;
    }

    /**
     * 킬로바이트를 바이트로 변환하여 반환.
     * @param kiloByte
     * @return 바이트
     */
    public static float transKiloByteToByte(float kiloByte){
        return kiloByte * 1024f;
    }

    /**
     * 킬로바이트를 메가바이트로 변환하여 반환.
     * @param kiloByte
     * @return 메가바이트
     */
    public static float transKiloByteToMegaByte(float kiloByte){
        return kiloByte * 0.0009765625f;
    }

    /**
     * 킬로바이트를 기가바이트로 변환하여 반환.
     * @param kiloByte
     * @return 기가바이트
     */
    public static float transKiloByteToGigaByte(float kiloByte){
        return kiloByte * 0.000000953674f;
    }

    /**
     * 킬로바이트를 테라바이트로 변환하여 반환.
     * @param kiloByte
     * @return 테라바이트
     */
    public static float transKiloByteToTeraByte(float kiloByte){
        return kiloByte * 0.000000000931323f;
    }

    /**
     * 킬로바이트를 페타바이트로 변환하여 반환.
     * @param kiloByte
     * @return 페타바이트
     */
    public static float transKiloByteToPetaByte(float kiloByte){
        return kiloByte * 0.000000000000909495f;
    }










    /**
     * 메가바이트를 비트로 변환하여 반환.
     * @param megaByte
     * @return 비트
     */
    public static float transMegaByteToBit(float megaByte){
        return megaByte * 8388608f;
    }

    /**
     * 메가바이트를 바이트로 변환하여 반환.
     * @param megaByte
     * @return 바이트
     */
    public static float transMegaByteToByte(float megaByte){
        return megaByte * 1048576f;
    }

    /**
     * 메가바이트를 킬로바이트로 변환하여 반환.
     * @param megaByte
     * @return 킬로바이트
     */
    public static float transMegaByteToKiloByte(float megaByte){
        return megaByte * 1024f;
    }

    /**
     * 메가바이트를 기가바이트로 변환하여 반환.
     * @param megaByte
     * @return 기가바이트
     */
    public static float transMegaByteToGigaByte(float megaByte){
        return megaByte * 0.0009765625f;
    }

    /**
     * 메가바이트를 테라바이트로 변환하여 반환.
     * @param megaByte
     * @return 테라바이트
     */
    public static float transMegaByteToTeraByte(float megaByte){
        return megaByte * 0.000000953674f;
    }

    /**
     * 메가바이트를 페타바이트로 변환하여 반환.
     * @param megaByte
     * @return 페타바이트
     */
    public static float transMegaByteToPetaByte(float megaByte){
        return megaByte * 0.000000000931323f;
    }









    /**
     * 기가바이트를 비트로 변환하여 반환.
     * @param gigaByte
     * @return 비트
     */
    public static float transGigaByteToBit(float gigaByte){
        return gigaByte * 8589930298f;
    }

    /**
     * 기가바이트를 바이트로 변환하여 반환.
     * @param gigaByte
     * @return 바이트
     */
    public static float transGigaByteToByte(float gigaByte){
        return gigaByte * 1073741288f;
    }

    /**
     * 기가바이트를 킬로바이트로 변환하여 반환.
     * @param gigaByte
     * @return 킬로바이트
     */
    public static float transGigaByteToKiloByte(float gigaByte){
        return gigaByte * 1048576f;
    }

    /**
     * 기가바이트를 메가바이트로 변환하여 반환.
     * @param gigaByte
     * @return 메가바이트
     */
    public static float transGigaByteToMegaByte(float gigaByte){
        return gigaByte * 1024f;
    }

    /**
     * 기가바이트를 테라바이트로 변환하여 반환.
     * @param gigaByte
     * @return 테라바이트
     */
    public static float transGigaByteToTeraByte(float gigaByte){
        return gigaByte * 0.0009765625f;
    }

    /**
     * 기가바이트를 페타바이트로 변환하여 반환.
     * @param gigaByte
     * @return 페타바이트
     */
    public static float transGigaByteToPetaByte(float gigaByte){
        return gigaByte * 0.000000953674f;
    }











    /**
     * 테라바이트를 비트로 변환하여 반환.
     * @param teraByte
     * @return 비트
     */
    public static float transTeraByteToBit(float teraByte){
        return teraByte * 8000000000000f;
    }

    /**
     * 테라바이트를 바이트로 변환하여 반환.
     * @param teraByte
     * @return 바이트
     */
    public static float transTeraByteToByte(float teraByte){
        return teraByte * 1000000000000f;
    }

    /**
     * 테라바이트를 킬로바이트로 변환하여 반환.
     * @param teraByte
     * @return 킬로바이트
     */
    public static float transTeraByteToKiloByte(float teraByte){
        return teraByte * 1000000000f;
    }

    /**
     * 테라바이트를 메가바이트로 변환하여 반환.
     * @param teraByte
     * @return 메가바이트
     */
    public static float transTeraByteToMegaByte(float teraByte){
        return teraByte * 1000000f;
    }

    /**
     * 테라바이트를 기가바이트로 변환하여 반환.
     * @param teraByte
     * @return 기가바이트
     */
    public static float transTeraByteToGigaByte(float teraByte){
        return teraByte * 1000f;
    }

    /**
     * 테라바이트를 페타바이트로 변환하여 반환.
     * @param teraByte
     * @return 페타바이트
     */
    public static float transTeraByteToPetaByte(float teraByte){
        return teraByte * 0.001f;
    }







    /**
     * 페타바이트를 비트로 변환하여 반환.
     * @param petaByte
     * @return 비트
     */
    public static float transPetaByteToBit(float petaByte){
        return petaByte * 8.000000000000000f;
    }

    /**
     * 페타바이트를 바이트로 변환하여 반환.
     * @param petaByte
     * @return 바이트
     */
    public static float transPetaByteToByte(float petaByte){
        return petaByte * 1.000000000000000f;
    }

    /**
     * 페타바이트를 킬로바이트로 변환하여 반환.
     * @param petaByte
     * @return 킬로바이트
     */
    public static float transPetaByteToKiloByte(float petaByte){
        return petaByte * 1000000000000f;
    }

    /**
     * 페타바이트를 메가바이트로 변환하여 반환.
     * @param petaByte
     * @return 메가바이트
     */
    public static float transPetaByteToMegaByte(float petaByte){
        return petaByte * 1000000000f;
    }

    /**
     * 페타바이트를 기가바이트로 변환하여 반환.
     * @param petaByte
     * @return 기가바이트
     */
    public static float transPetaByteToGigaByte(float petaByte){
        return petaByte * 1048576f;
    }

    /**
     * 페타바이트를 테라바이트로 변환하여 반환.
     * @param petaByte
     * @return 테라바이트
     */
    public static float transPetaByteToTeraByte(float petaByte){
        return petaByte * 1024f;
    }








    /**
     * 정수 값 올림하여 반환
     * @param nDist 올림 할 값
     * @return 올림 된 값
     */
    public static int intCeil(int nDist) {

        if (nDist % 10 != 0) {
            nDist = nDist / 10 * 10 + 10;
        }

        return nDist;

    }

    /////////////////////
    // m/s to m/m, m/h //
    /////////////////////
    /**
     * m/s를 m/m으로 변환하여 반환.
     * @param meterPerSecond
     * @return meterPerMinute
     */
    public static float transMPSToMPM(float meterPerSecond){
        return meterPerSecond * 60.0f;
    }

    /**
     * m/s를 m/h으로 변환하여 반환.
     * @param meterPerSecond
     * @return meterPerHour
     */
    public static float transMPSToMPH(float meterPerSecond){
        return meterPerSecond * 3600.0f;
    }

    /////////////////////
    // m/m to m/s, m/h //
    /////////////////////
    /**
     * m/m를 m/s으로 변환하여 반환.
     * @param meterPerMinute
     * @return meterPerSecond
     */
    public static float transMPMToMPS(float meterPerMinute){
        return meterPerMinute / 60.0f;
    }

    /**
     * m/m를 m/h으로 변환하여 반환.
     * @param meterPerMinute
     * @return meterPerHour
     */
    public static float transMPMToMPH(float meterPerMinute){
        return meterPerMinute * 60.0f;
    }

    /////////////////////
    // m/h to m/s, m/m //
    /////////////////////
    /**
     * m/h를 m/s으로 변환하여 반환.
     * @param meterPerHour
     * @return meterPerSecond
     */
    public static float transMPHToMPS(float meterPerHour){
        return meterPerHour / 3600.0f;
    }

    /**
     * m/h를 m/m으로 변환하여 반환.
     * @param meterPerHour
     * @return meterPerSecond
     */
    public static float transMPHToMPM(float meterPerHour){
        return meterPerHour / 60.0f;
    }

    ////////////////////////
    // km/s to km/m, km/h //
    ////////////////////////
    /**
     * km/s를 km/m으로 변환하여 반환.
     * @param kilometerPerSecond
     * @return kilometerPerMinute
     */
    public static float transKMPSToKMPM(float kilometerPerSecond){
        return kilometerPerSecond * 60.0f;
    }

    /**
     * km/s를 km/h으로 변환하여 반환.
     * @param kilometerPerSecond
     * @return kilometerPerHour
     */
    public static float transKMPSToKMPH(float kilometerPerSecond){
        return kilometerPerSecond * 3600.0f;
    }

    ////////////////////////
    // km/m to km/s, km/h //
    ////////////////////////
    /**
     * km/m를 km/s으로 변환하여 반환.
     * @param kilometerPerMinute
     * @return kilometerPerSecond
     */
    public static float transKMPMToKMPS(float kilometerPerMinute){
        return kilometerPerMinute / 60.0f;
    }

    /**
     * km/m를 km/h으로 변환하여 반환.
     * @param kilometerPerMinute
     * @return kilometerPerHour
     */
    public static float transKMPMToKMPH(float kilometerPerMinute){
        return kilometerPerMinute * 60.0f;
    }

    ////////////////////////
    // km/h to km/s, km/m //
    ////////////////////////
    /**
     * km/h를 km/s으로 변환하여 반환.
     * @param kilometerPerHour
     * @return kilometerPerSecond
     */
    public static float transKMPHToKMPS(float kilometerPerHour){
        return kilometerPerHour / 3600.0f;
    }

    /**
     * km/h를 km/m으로 변환하여 반환.
     * @param kilometerPerHour
     * @return kilometerPerSecond
     */
    public static float transKMPHToKMPM(float kilometerPerHour){
        return kilometerPerHour / 60.0f;
    }

    /////////////////////////////
    // m/s to km/s, km/m, km/h //
    /////////////////////////////
    /**
     * m/s를 km/s로 변환하여 반환.
     * @param meterPerSecond
     * @return kilometerPerSecond
     */
    public static float transMPSToKMPS(float meterPerSecond){
        return meterPerSecond * 0.001f;
    }

    /**
     * m/s를 km/m로 변환하여 반환.
     * @param meterPerSecond
     * @return kilometerPerMinute
     */
    public static float transMPSToKMPM(float meterPerSecond){
        return meterPerSecond * 0.06f;
    }

    /**
     * m/s를 km/h로 변환하여 반환.
     * @param meterPerSecond
     * @return kilometerPerHour
     */
    public static float transMPSToKMPH(float meterPerSecond){
        return meterPerSecond * 3.6f;
    }

    /////////////////////////////
    // m/m to km/s, km/m, km/h //
    /////////////////////////////
    /**
     * m/m를 km/s로 변환하여 반환.
     * @param meterPerMinute
     * @return kilometerPerSecond
     */
    public static float transMPMToKMPS(float meterPerMinute){
        return meterPerMinute * 0.00001666666666667f;
    }

    /**
     * m/m를 km/m로 변환하여 반환.
     * @param meterPerMinute
     * @return kilometerPerMinute
     */
    public static float transMPMToKMPM(float meterPerMinute){
        return meterPerMinute * 0.001f;
    }

    /**
     * m/m를 km/h로 변환하여 반환.
     * @param meterPerMinute
     * @return kilometerPerHour
     */
    public static float transMPMToKMPH(float meterPerMinute){
        return meterPerMinute * 0.06f;
    }

    /////////////////////////////
    // m/h to km/s, km/m, km/h //
    /////////////////////////////
    /**
     * m/h를 km/s로 변환하여 반환.
     * @param meterPerHour
     * @return kilometerPerSecond
     */
    public static float transMPHToKMPS(float meterPerHour){
        return meterPerHour * 2.7777778f;
    }

    /**
     * m/h를 km/m로 변환하여 반환.
     * @param meterPerHour
     * @return kilometerPerMinute
     */
    public static float transMPHToKMPM(float meterPerHour){
        return meterPerHour * 0.00001666666666667f;
    }

    /**
     * m/h를 km/h로 변환하여 반환.
     * @param meterPerHour
     * @return kilometerPerHour
     */
    public static float transMPHToKMPH(float meterPerHour){
        return meterPerHour * 0.001f;
    }

    ///////////////////////////
    // km/s to m/s, m/m, m/h //
    ///////////////////////////
    /**
     * km/s를 m/s로 변환하여 반환.
     * @param kilometerPerSecond
     * @return meterPerSecond
     */
    public static float transKMPSToMPS(float kilometerPerSecond){
        return kilometerPerSecond * 1000.0f;
    }

    /**
     * km/s를 m/m로 변환하여 반환.
     * @param kilometerPerSecond
     * @return meterPerMinute
     */
    public static float transKMPSToMPM(float kilometerPerSecond){
        return kilometerPerSecond * 60000.0f;
    }

    /**
     * km/s를 m/h로 변환하여 반환.
     * @param kilometerPerSecond
     * @return meterPerHour
     */
    public static float transKMPSToMPH(float kilometerPerSecond){
        return kilometerPerSecond * 3600000.0f;
    }

    ///////////////////////////
    // km/m to m/s, m/m, m/h //
    ///////////////////////////
    /**
     * km/m를 m/s로 변환하여 반환.
     * @param kilometerPerMinute
     * @return meterPerSecond
     */
    public static float transKMPMToMPS(float kilometerPerMinute){
        return kilometerPerMinute * 16.66666666667f;
    }

    /**
     * km/m를 m/m로 변환하여 반환.
     * @param kilometerPerMinute
     * @return meterPerMinute
     */
    public static float transKMPMToMPM(float kilometerPerMinute){
        return kilometerPerMinute * 1000.0f;
    }

    /**
     * km/m를 m/h로 변환하여 반환.
     * @param kilometerPerMinute
     * @return meterPerHour
     */
    public static float transKMPMToMPH(float kilometerPerMinute){
        return kilometerPerMinute * 60000.0f;
    }

    ///////////////////////////
    // km/h to m/s, m/m, m/h //
    ///////////////////////////
    /**
     * km/h를 m/s로 변환하여 반환.
     * @param kilometerPerHour
     * @return meterPerSecond
     */
    public static float transKMPHToMPS(float kilometerPerHour){
        return kilometerPerHour * 0.2777777777778f;
    }

    /**
     * km/h를 m/m로 변환하여 반환.
     * @param kilometerPerHour
     * @return meterPerMinute
     */
    public static float transKMPHToMPM(float kilometerPerHour){
        return kilometerPerHour * 16.66666666667f;
    }

    /**
     * km/h를 m/h로 변환하여 반환.
     * @param kilometerPerHour
     * @return meterPerHour
     */
    public static float transKMPHToMPH(float kilometerPerHour){
        return kilometerPerHour * 1000;
    }

    /**
     * degree 값을 radian 값으로 변환하여 반환.
     * @param degree 변환 할 degree 값(int, float, long, double)
     * @return 변환 된 radian 값, -1 : 알 수 없는 형의 degree값일 경우 반환
     */
    public static double transDegreeToRadian(Object degree) {

        if (degree instanceof Integer) {
            return Math.PI * ((double) ((int)degree)) / 180.0D;
        } else if (degree instanceof Long) {
            return Math.PI * ((double) ((long)degree)) / 180.0D;
        } else if (degree instanceof Float) {
            return Math.PI * ((double) ((float)degree)) / 180.0D;
        } else if (degree instanceof Double) {
            return Math.PI * ((double) degree) / 180.0D;
        } else {
            return -1;
        }

    }
    /**
     * radian 값을 degree 값으로 변환하여 반환.
     * @param radian 변활 할 정수 radian 값(int, float, long, double)
     * @return 변환 된 degree 값, -1 : 알 수 없는 형의 radian값일 경우 반환
     */
    public static double transRadianToDegree(Object radian) {

        if (radian instanceof Integer) {
            return ((double) ((int)radian)) * 180.0D / Math.PI;
        } else if (radian instanceof Long) {
            return ((double) ((long)radian)) * 180.0D / Math.PI;
        } else if (radian instanceof Float) {
            return ((double) ((float)radian)) * 180.0D / Math.PI;
        } else if (radian instanceof Double) {
            return ((double) radian) * 180.0D / Math.PI;
        } else {
            return -1;
        }

    }

    /**
     * 10진수 정수를 2진수로 변환하여 반환.
     * @param decimal 10진수 정수
     * @return 변환 된 2진수
     */
    public static String transDecimalToBinary(int decimal){

        final char[] digits = {
                '0' , '1' , '2' , '3' , '4' , '5' ,
                '6' , '7' , '8' , '9' , 'a' , 'b' ,
                'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
                'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
                'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                'u' , 'v' , 'w' , 'x' , 'y' , 'z'
        };

        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << 1;
        int mask = radix - 1;
        do {

            buf[--charPos] = digits[decimal & mask];
            decimal >>>= 1;

        } while (decimal != 0);

        return new String(buf, charPos, (32 - charPos));

    }

    /**
     * 2진수를 10진수로 변환하여 반환.
     * @param binary 2진수
     * @return -1 : 오류 발생시, 변환 된 10진수 정수
     */
    public static int transBinaryToDecimal(String binary){

        if (binary == null || binary.length() <= 0) {
            throw new NumberFormatException("null");
        }

        int radix = 2;
        int result = 0;
        boolean negative = false;
        int i = 0;
        int len = binary.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        char firstChar = binary.charAt(0);
        if (firstChar < '0') {

            if (firstChar == '-') {

                negative = true;
                limit = Integer.MIN_VALUE;

            } else if (firstChar != '+') {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + binary);
                return -1;
            }

            if (len == 1) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + binary);
                return -1;
            }

            i++;

        }

        multmin = limit / radix;
        while (i < len) {

            digit = Character.digit(binary.charAt(i++), radix);
            if (digit < 0) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + binary);
                return -1;
            }

            if (result < multmin) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + binary);
                return -1;
            }

            result *= radix;
            if (result < limit + digit) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + binary);
                return -1;
            }

            result -= digit;

        }

        return negative ? result : -result;

    }

    /**
     * 10진수 정수를 8진수로 변환하여 반환.
     * @param decimal 10진수 정수
     * @return 변환 된 8진수
     */
    public static String transDecimalToOctal(int decimal){

        final char[] digits = {
                '0' , '1' , '2' , '3' , '4' , '5' ,
                '6' , '7' , '8' , '9' , 'a' , 'b' ,
                'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
                'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
                'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                'u' , 'v' , 'w' , 'x' , 'y' , 'z'
        };

        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << 3;
        int mask = radix - 1;
        do {

            buf[--charPos] = digits[decimal & mask];
            decimal >>>= 3;

        } while (decimal != 0);

        return new String(buf, charPos, (32 - charPos));

    }

    /**
     * 8진수를 10진수로 변환하여 반환.
     * @param octal 2진수
     * @return -1 : 오류 발생시, 변환 된 10진수 정수
     */
    public static int transOctalToDecimal(String octal){

        if (octal == null || octal.length() <= 0) {
            throw new NumberFormatException("null");
        }

        int radix = 8;
        int result = 0;
        boolean negative = false;
        int i = 0;
        int len = octal.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        char firstChar = octal.charAt(0);
        if (firstChar < '0') {

            if (firstChar == '-') {

                negative = true;
                limit = Integer.MIN_VALUE;

            } else if (firstChar != '+') {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + octal);
                return -1;
            }

            if (len == 1) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + octal);
                return -1;
            }

            i++;

        }

        multmin = limit / radix;
        while (i < len) {

            digit = Character.digit(octal.charAt(i++), radix);
            if (digit < 0) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + octal);
                return -1;
            }

            if (result < multmin) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + octal);
                return -1;
            }

            result *= radix;
            if (result < limit + digit) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + octal);
                return -1;
            }

            result -= digit;

        }

        return negative ? result : -result;

    }

    /**
     * 10진수 정수를 16진수로 변환하여 반환.
     * @param decimal 10진수 정수
     * @return 변환 된 16진수
     */
    public static String transDecimalToHex(int decimal){

        final char[] digits = {
                '0' , '1' , '2' , '3' , '4' , '5' ,
                '6' , '7' , '8' , '9' , 'a' , 'b' ,
                'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
                'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
                'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                'u' , 'v' , 'w' , 'x' , 'y' , 'z'
        };

        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << 4;
        int mask = radix - 1;
        do {
            buf[--charPos] = digits[decimal & mask];
            decimal >>>= 4;
        } while (decimal != 0);

        return new String(buf, charPos, (32 - charPos));

    }

    /**
     * 2진수를 10진수로 변환하여 반환.
     * @param hex 2진수
     * @return -1 : 오류 발생시, 변환 된 10진수 정수
     */
    public static int transHexToDecimal(String hex){

        if (hex == null || hex.length() <= 0) {
            throw new NumberFormatException("null");
        }

        int radix = 16;
        int result = 0;
        boolean negative = false;
        int i = 0;
        int len = hex.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        char firstChar = hex.charAt(0);
        if (firstChar < '0') {

            if (firstChar == '-') {

                negative = true;
                limit = Integer.MIN_VALUE;

            } else if (firstChar != '+') {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + hex);
                return -1;
            }

            if (len == 1) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + hex);
                return -1;
            }

            i++;

        }

        multmin = limit / radix;
        while (i < len) {

            digit = Character.digit(hex.charAt(i++), radix);
            if (digit < 0) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + hex);
                return -1;
            }

            if (result < multmin) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + hex);
                return -1;
            }

            result *= radix;
            if (result < limit + digit) {
                Log.e("transBinaryToDecimal()", "NumberFormatException : " + hex);
                return -1;
            }

            result -= digit;

        }

        return negative ? result : -result;

    }

    /**
     * 초를 분으로 변환하여 반환.
     * @param second 변환 할 초
     * @return 변환 된 분
     */
    public static float transSecondToMinute(float second){
        return Math.round((second / 60) * 1000f) / 1000f;
    }

    /**
     * 초를 시로 변환하여 반환.
     * @param second 변환 할 초
     * @return 변환 된 시
     */
    public static float transSecondToHour(float second){
        return Math.round((second / 3600) * 1000f) / 1000f;
    }

    /**
     * 분을 초로 변환하여 반환.
     * @param minute 변환 할 분
     * @return 변환 된 초
     */
    public static float transMinuteToSecond(float minute){
        return Math.round((minute * 60) * 1000f) / 1000f;
    }

    /**
     * 분을 시로 변환하여 반환.
     * @param minute 변환 할 분
     * @return 변환 된 시
     */
    public static float transMinuteToHour(float minute){
        return Math.round((minute / 60) * 1000f) / 1000f;
    }

    /**
     * 시를 초로 변환하여 반환.
     * @param hour 변환 할 시
     * @return 변환 된 초
     */
    public static float transHourToSecond(float hour){
        return Math.round((hour * 3600) * 1000f) / 1000f;
    }

    /**
     * 시를 분으로 변환하여 반환.
     * @param hour 변환 할 시
     * @return 변환 된 분
     */
    public static float transHourToMinute(float hour){
        return Math.round((hour * 60) * 1000f) / 1000f;
    }

    /**
     * boolean 값을 byte 값으로 변환하여 반환.
     * @param value boolean 값
     * @return 변환 된 byte 값
     */
    public static byte transBooleanToByte(boolean value){
        return (byte)(value ? 1 : 0);
    }

    /**
     * char 값을 byte 배열로 변환하여 반환.
     * @param value char 값
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 byte 배열
     */
    public static byte[] transCharToByteArray(char value, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.allocate(Character.SIZE / 8);
        byteBuffer.order(byteOrder);
        byteBuffer.putChar(value);

        return byteBuffer.array();

    }

    /**
     * short 값을 byte 배열로 변환하여 반환.
     * @param value short 값
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 byte 배열
     */
    public static byte[] transShortToByteArray(short value, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.allocate(Short.SIZE / 8);
        byteBuffer.order(byteOrder);
        byteBuffer.putShort(value);

        return byteBuffer.array();

    }

    /**
     * int 값을 byte 배열로 변환하여 반환.
     * @param value int 값
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 byte 배열
     */
    public static byte[] transIntToByteArray(int value, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.SIZE / 8);
        byteBuffer.order(byteOrder);
        byteBuffer.putInt(value);

        return byteBuffer.array();

    }

    /**
     * long 값을 byte 배열로 변환하여 반환.
     * @param value long 값
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 byte 배열
     */
    public static byte[] transLongToByteArray(long value, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.SIZE / 8);
        byteBuffer.order(byteOrder);
        byteBuffer.putLong(value);

        return byteBuffer.array();

    }

    /**
     * float 값을 byte 배열로 변환하여 반환.
     * @param value float 값
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 byte 배열
     */
    public static byte[] transFloatToByteArray(long value, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.SIZE / 8);
        byteBuffer.order(byteOrder);
        byteBuffer.putFloat(value);

        return byteBuffer.array();

    }

    /**
     * double 값을 byte 배열로 변환하여 반환.
     * @param value double 값
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 byte 배열
     */
    public static byte[] transDoubleToByteArray(long value, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.SIZE / 8);
        byteBuffer.order(byteOrder);
        byteBuffer.putDouble(value);

        return byteBuffer.array();

    }

    /**
     * byte 값을 boolean 값으로 변환하여 반환.
     * @param value byte 값
     * @return 변환 된 boolean 값
     */
    public static boolean transByteToBoolean(byte value){
        return (value == 1 ? true : false);
    }

    /**
     * byte 배열을 char 값으로 변환하여 반환.
     * @param byteArray byte 배열
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 char 값
     */
    public static char transByteArrayToChar(byte[] byteArray, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(byteOrder);

        return byteBuffer.getChar();

    }

    /**
     * byte 배열을 short 값으로 변환하여 반환.
     * @param byteArray byte 배열
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 short 값
     */
    public static short transByteArrayToShort(byte[] byteArray, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(byteOrder);

        return byteBuffer.getShort();

    }

    /**
     * byte 배열을 int 값으로 변환하여 반환.
     * @param byteArray byte 배열
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 int 값
     */
    public static int transByteArrayToInt(byte[] byteArray, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(byteOrder);

        return byteBuffer.getInt();

    }

    /**
     * byte 배열을 long 값으로 변환하여 반환.
     * @param byteArray byte 배열
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 long 값
     */
    public static long transByteArrayToLong(byte[] byteArray, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(byteOrder);

        return byteBuffer.getLong();

    }

    /**
     * byte 배열을 float 값으로 변환하여 반환.
     * @param byteArray byte 배열
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 float 값
     */
    public static float transByteArrayToFloat(byte[] byteArray, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(byteOrder);

        return byteBuffer.getFloat();

    }

    /**
     * byte 배열을 double 값으로 변환하여 반환.
     * @param byteArray byte 배열
     * @param byteOrder {@link ByteOrder}
     * @return 변환 된 double 값
     */
    public static double transByteArrayToDouble(byte[] byteArray, ByteOrder byteOrder){

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(byteOrder);

        return byteBuffer.getDouble();

    }

    /**
     * 두 좌표간의 거리를 게산하여 반환.
     * {@link android.location.Location#distanceBetween(double, double, double, double, float[])}에서 수행하는
     * 두 좌표간의 거리 계산 방법을 가져옴.
     * @param p1Lat 계산 할 좌표1 위도
     * @param p1Lon 계산 할 좌표1 경도
     * @param p2Lat 계산 할 좌표2 위도
     * @param p2Lon 계산 할 좌표2 경도
     * @return 두 좌표간의 거리(단위 : m)
     */
    public static float coordBetweenDistance(double p1Lat, double p1Lon, double p2Lat, double p2Lon){

        int MAXITERS = 20;
        // Convert lat/long to radians
        p1Lat *= Math.PI / 180.0;
        p2Lat *= Math.PI / 180.0;
        p1Lon *= Math.PI / 180.0;
        p2Lon *= Math.PI / 180.0;

        double a = 6378137.0; // WGS84 major axis
        double b = 6356752.3142; // WGS84 semi-major axis
        double f = (a - b) / a;
        double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);

        double L = p2Lon - p1Lon;
        double A = 0.0;
        double U1 = Math.atan((1.0 - f) * Math.tan(p1Lat));
        double U2 = Math.atan((1.0 - f) * Math.tan(p2Lat));

        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;

        double sigma = 0.0;
        double deltaSigma = 0.0;
        double cosSqAlpha = 0.0;
        double cos2SM = 0.0;
        double cosSigma = 0.0;
        double sinSigma = 0.0;
        double cosLambda = 0.0;
        double sinLambda = 0.0;

        double lambda = L; // initial guess
        for (int iter = 0; iter < MAXITERS; iter++) {

            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double sinSqSigma = t1 * t1 + t2 * t2; // (14)
            sinSigma = Math.sqrt(sinSqSigma);
            cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
            sigma = Math.atan2(sinSigma, cosSigma); // (16)
            double sinAlpha = (sinSigma == 0) ? 0.0 : cosU1cosU2 * sinLambda / sinSigma; // (17)
            cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
            cos2SM = (cosSqAlpha == 0) ? 0.0 : cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)

            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
            A = 1 + (uSquared / 16384.0) * (4096.0 + uSquared * (-768 + uSquared * (320.0 - 175.0 * uSquared)));
            double B = (uSquared / 1024.0) * (256.0 + uSquared * (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
            double C = (f / 16.0) * cosSqAlpha * (4.0 + f * (4.0 - 3.0 * cosSqAlpha)); // (10)
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * sinSigma * (cos2SM + (B / 4.0) * (cosSigma * (-1.0 + 2.0 * cos2SMSq) - (B / 6.0) * cos2SM * (-3.0 + 4.0 * sinSigma * sinSigma) * (-3.0 + 4.0 * cos2SMSq)));

            lambda = L + (1.0 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SM + C * cosSigma * (-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

            double delta = (lambda - lambdaOrig) / lambda;
            if (Math.abs(delta) < 1.0e-12) {
                break;
            }

        }

        return  (float) (b * A * (sigma - deltaSigma));

    }

    /**
     * 간단히 두 좌표간의 거리를 게산하여 반환.
     * 참고 URL : http://cafe.naver.com/citrineframework/1482
     * 국내에서는 최대 5%의 오차가 발생할 수 있음.
     * 테스트 필요.
     * @param p1Lat 계산 할 좌표1 위도
     * @param p1Lon 계산 할 좌표1 경도
     * @param p2Lat 계산 할 좌표2 위도
     * @param p2Lon 계산 할 좌표2 경도
     * @return 두 좌표간의 거리(단위 : m)
     */
    public static float simpleCoordBetweenDistance(double p1Lat, double p1Lon, double p2Lat, double p2Lon){

        // (((시작점위도-끝점위도) × 가로거리비율)² + ((시작점경도-끝점경도) × 세로거리비율)²)
        double y = (p1Lat - p2Lat) * 110988.09668599277;
        double x = (p1Lon - p2Lon) * 88359.11356077534;

        return (float) Math.sqrt(y * y + x * x);

    }

}
