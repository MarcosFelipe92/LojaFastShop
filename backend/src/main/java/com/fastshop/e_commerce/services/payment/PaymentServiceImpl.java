package com.fastshop.e_commerce.services.payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.payment.PaymentDTO;
import com.fastshop.e_commerce.dtos.payment.PaymentDTO.BackUrlsDTO;
import com.fastshop.e_commerce.dtos.payment.PaymentDTO.ItemDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceItem;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final String ACCESS_TOKEN_API = "APP_USR-3909270173551668-012517-96086ca225c40c2144c36c112a5baa4f-2233857184";

    @Override
    public PaymentDTO pay(PaymentDTO payment) {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN_API);

        PreferencePaymentMethodsRequest paymentMethodsRequest = PreferencePaymentMethodsRequest.builder()
                .defaultPaymentMethodId("visa")

                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(mapperPreferenceItems(payment.getItems()))
                .paymentMethods(paymentMethodsRequest)
                .backUrls(getPreferenceBackUrlsRequest(payment.getBackUrls()))
                .autoReturn("approved")
                .build();

        try {
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            return mapperToPaymentDTO(preference);
        } catch (MPApiException ex) {
            System.err.printf("Erro do MercadoPago: Status: %s, Conteúdo: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
        } catch (MPException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static List<PreferenceItemRequest> mapperPreferenceItems(List<ItemDTO> items) {
        return items.stream()
                .map(item -> PreferenceItemRequest.builder()
                        .title(item.getTitle())
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .currencyId("BRL")
                        .unitPrice(new BigDecimal(item.getUnitPrice()))
                        .build())
                .collect(Collectors.toList());
    }

    private static PreferenceBackUrlsRequest getPreferenceBackUrlsRequest(BackUrlsDTO backUrlsDTO) {
        return PreferenceBackUrlsRequest.builder()
                .success(backUrlsDTO.getSuccess())
                .failure(backUrlsDTO.getFailure())
                .pending(backUrlsDTO.getPending())
                .build();
    }

    public PaymentDTO mapperToPaymentDTO(Preference preference) {
        List<PaymentDTO.ItemDTO> items = preference.getItems().stream()
                .map(this::mapToItemDTO)
                .collect(Collectors.toList());

        PaymentDTO.BackUrlsDTO backUrlsDTO = new PaymentDTO.BackUrlsDTO(
                preference.getBackUrls().getSuccess(),
                preference.getBackUrls().getPending(),
                preference.getBackUrls().getFailure());

        return new PaymentDTO(items, backUrlsDTO, preference.getInitPoint());
    }

    private PaymentDTO.ItemDTO mapToItemDTO(PreferenceItem preferenceItem) {
        return new PaymentDTO.ItemDTO(
                preferenceItem.getTitle(),
                preferenceItem.getDescription(),
                preferenceItem.getQuantity(),
                preferenceItem.getUnitPrice().doubleValue());
    }
}
