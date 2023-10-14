package com.example.runningcoach.response;

public class ResponseMessage {
    //필요시 추가 작성
    public static final String SUCCESS = "성공";

    public static final String EMPTY_VALUE = "값이 비어있습니다";

    //로그인
    public static final String EMPTY_EMAIL = "이메일을 입력해주세요";
    public static final String EMPTY_PASSWORD = "비밀번호를 입력해주세요";
    public static final String INVALID_EMAIL = "올바르지 않은 이메일입니다";
    public static final String INVALID_PASSWORD = "올바르지 않은 비밀번호입니다";
    public static final String FAIL_LOGIN = "로그인에 실패했습니다";
    public static final String LEAVE_USER = "탈퇴한 회원입니다";

    //회원가입
    public static final String CONFLICT_EMAIL = "중복된 이메일입니다";


    //마이페이지
    public static final String NO_EXIST_EMAIL = "존재하지 않는 이메일입니다";
    public static final String SAME_PASSWORD = "이전과 동일한 비밀번호입니다";

    public static final String BAD_REQUEST = "잘못된 요청입니다";
}
