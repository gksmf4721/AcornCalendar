package acorn.calendar.com.sample.domain.enums;

public enum SampleEnum {
    NOTICE_TYPE("NOTICE"),
    BOARD_TYPE("BOARD");

    SampleEnum(String value){this.value = value;}

    private String value;

    public String getValue(){return value;}
}
