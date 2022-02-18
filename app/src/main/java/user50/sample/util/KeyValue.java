package user50.sample.util;

import java.util.Objects;

/**
 * {@link android.util.Pair}를 참고하여 클래스 재정의.
 * @param <K>
 * @param <V>
 * @author ABYSER
 * @since 2019-08-15
 */
public class KeyValue<K, V> {

    //////////////
    // Constant //
    //////////////
    public static final int TO_STRING_TYPE_KEY = 1;
    public static final int TO_STRING_TYPE_VALUE = 2;
    public static final int TO_STRING_TYPE_KEY_VALUE = 3;
    public static final int TO_STRING_TYPE_CUSTOM = 4;

    ////////////
    // Layout //
    ////////////

    //////////////
    // Variable //
    //////////////
    public K key;
    public V value;
    private int mToStringType;
    private String mCustomToString;

    /**
     * 생성자.
     */
    public KeyValue(){
        this(null, null);
    }

    /**
     * 생성자.
     * @param key
     * @param value
     */
    public KeyValue(K key, V value){

        this.key = key;
        this.value = value;
        this.mToStringType = TO_STRING_TYPE_KEY;
        this.mCustomToString = null;

    }

    /**
     * {@link #toString()}시 반환 될 문자열 타입 설정.
     * @param toStringType {@link #TO_STRING_TYPE_KEY},
     *                     {@link #TO_STRING_TYPE_VALUE},
     *                     {@link #TO_STRING_TYPE_KEY_VALUE},
     *                     {@link #TO_STRING_TYPE_CUSTOM}
     * @see {@link #setCustomToString(String)}
     */
    public void setToStringType(int toStringType){

        if(toStringType < TO_STRING_TYPE_KEY || toStringType > TO_STRING_TYPE_CUSTOM){
            this.mToStringType = TO_STRING_TYPE_KEY;
            return;
        }

        this.mToStringType = toStringType;

    }

    /**
     * {@link #setToStringType(int)}을 통해
     * {@link #toString()}의 반환 될 문자열 타입을 {@link #TO_STRING_TYPE_CUSTOM}으로 지정 할 경우 사용.
     * 반환 될 문자열을 직접 설정.
     * @param toString {@link #toString()}사용시 반환 될 문자열
     * @see {@link #setToStringType(int)}
     */
    public void setCustomToString(String toString){
        this.mCustomToString = toString;
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link KeyValue} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     *         equal
     */
    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }

        if(o == null){
            return false;
        }

        if (!(o instanceof KeyValue)) {
            return false;
        }

        KeyValue<?, ?> other = (KeyValue<?, ?>) o;
        return (Objects.equals(this.key, other.key) && Objects.equals(this.value, other.value));

    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
    }

    @Override
    public String toString() {

        StringBuilder toString = new StringBuilder();
        switch (this.mToStringType){
            case TO_STRING_TYPE_KEY:

                toString.append((this.key == null) ? "null" : this.key);

                break;
            case TO_STRING_TYPE_VALUE:

                toString.append((this.value == null) ? "null" : this.value);

                break;
            case TO_STRING_TYPE_KEY_VALUE:

                toString.append((this.key == null) ? "null" : this.key);
                toString.append(":");
                toString.append((this.value == null) ? "null" : this.value);

                break;
            case TO_STRING_TYPE_CUSTOM:

                toString.append((this.mCustomToString == null) ? "null" : this.mCustomToString);

                break;
        }

        return toString.toString();

    }

}
