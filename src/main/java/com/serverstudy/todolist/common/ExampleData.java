package com.serverstudy.todolist.common;

public interface ExampleData {

    String INVALID_PARAMETER_DATA = """
            {
              "timestamp": "2024-05-15T12:32:00.1832345",
              "status": 400,
              "error": "BAD_REQUEST",
              "code": "INVALID_PARAMETER",
              "message": [
                  "[userId] 널이어서는 안됩니다 입력된 값: [null]"
              ]
            }
            """;
    String BAD_PASSWORD_DATA = """
            {
               "timestamp": "2024-05-16T22:21:04.7017558",
               "status": 401,
               "error": "UNAUTHORIZED",
               "code": "BAD_PASSWORD",
               "message": [
                 "비밀번호가 옳지 않습니다."
               ]
            }
            """;
    String USER_NOT_FOUND_DATA = """
            {
              "timestamp": "2024-05-15T11:30:54.8218419",
              "status": 404,
              "error": "NOT_FOUND",
              "code": "USER_NOT_FOUND",
              "message": [
                "해당 유저 정보를 찾을 수 없습니다"
              ]
            }
            """;
    String TODO_NOT_FOUND_DATA = """
            {
              "timestamp": "2024-05-15T11:30:54.8218419",
              "status": 404,
              "error": "NOT_FOUND",
              "code": "TODO_NOT_FOUND",
              "message": [
                "해당 투두 정보를 찾을 수 없습니다"
              ]
            }
            """;
    String DUPLICATE_USER_EMAIL_DATA = """
            {
              "timestamp": "2024-05-15T12:37:24.9740069",
              "status": 409,
              "error": "CONFLICT",
              "code": "DUPLICATE_USER_EMAIL",
              "message": [
                "해당 이메일이 이미 존재합니다"
              ]
            }
            """;

    String BAD_CREDENTIALS_DATA = """
            {
              "timestamp": "2024-05-27T21:48:53.1796943",
              "status": 401,
              "error": "UNAUTHORIZED",
              "code": "BAD_CREDENTIALS",
              "message": [
                "이메일 또는 비밀번호가 옳지 않습니다."
              ]
            }
            """;
}
