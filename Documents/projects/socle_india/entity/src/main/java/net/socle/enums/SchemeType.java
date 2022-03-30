package net.socle.enums;

public enum SchemeType {
    CENTRAL,STATE,NONE;

    public static SchemeType stringToEnum(String value){
        if(value!=null&&!value.isEmpty()){
            String upperCaseValue=value.toUpperCase();
            return switch (upperCaseValue) {
                case "CENTRAL" -> CENTRAL;
                case "STATE" -> STATE;
                default -> NONE;
            };
        }else {
            return NONE;
        }
    }
}
