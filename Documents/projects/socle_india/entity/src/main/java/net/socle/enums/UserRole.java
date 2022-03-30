package net.socle.enums;


public enum UserRole {
    ADMIN,USER,NONE;

    public static UserRole stringToEnum(String value){
        if(value!=null&&!value.isEmpty()){
            String upperCaseValue=value.toUpperCase();
            return switch (upperCaseValue) {
                case "ADMIN" -> ADMIN;
                case "USER" -> USER;
                default -> NONE;
            };
        }else {
            return NONE;
        }
    }
}
