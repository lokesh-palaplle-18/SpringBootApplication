
package com.application.accountsManagement.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String statusCode;
    private String statusMessage;
}
