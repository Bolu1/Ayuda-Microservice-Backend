package com.ayuda.paymentservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeEventWehookDto {
    private String id;
    private String type;
    private Object data;
}
