package com.fpt.harkins.mailService.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class MailRequest {
    String name;
    String to;
    String from;
    String subject;
}
