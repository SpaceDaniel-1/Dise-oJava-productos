package com.proyecto.producto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.producto.dto.AuditEventDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class AuditService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queue-url:}")
    private String queueUrl;

    public AuditService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void enviarEvento(AuditEventDTO evento) {
        try {
            if (queueUrl == null || queueUrl.isBlank()) {
                System.out.println("SQS no configurado. Evento no enviado: " + evento);
                return;
            }

            String mensajeJson = objectMapper.writeValueAsString(evento);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(mensajeJson)
                    .build();

            sqsClient.sendMessage(request);

            System.out.println("Evento enviado a SQS: " + mensajeJson);

        } catch (Exception e) {
            System.err.println("Error al enviar evento a SQS: " + e.getMessage());
        }
    }
}