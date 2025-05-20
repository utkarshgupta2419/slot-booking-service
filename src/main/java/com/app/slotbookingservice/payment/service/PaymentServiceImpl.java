package com.app.slotbookingservice.payment.service;

import com.app.slotbookingservice.exception.CustomExceptions.RazorpayOrderCreationException;
import com.app.slotbookingservice.exception.CustomExceptions.RazorpayPaymentVerificationException;
import com.app.slotbookingservice.payment.repository.PaymentRepository;
import com.app.slotbookingservice.payment.enums.PaymentStatus;
import com.app.slotbookingservice.payment.dto.CreateOrderDto;
import com.app.slotbookingservice.payment.dto.CreatedOrderResponse;
import com.app.slotbookingservice.payment.dto.MockRazorpayPaymentDto;
import com.app.slotbookingservice.payment.dto.VerifyPaymentDto;
import com.app.slotbookingservice.payment.entity.MockRazorpayPayment;
import com.app.slotbookingservice.payment.entity.PaymentHistory;
import com.app.slotbookingservice.payment.mapper.PaymentMapper;
import com.app.slotbookingservice.utlis.AppConstants;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MongoTemplate mongoTemplate;
    private final PaymentMapper mapper;
    private final RazorpayService razorpayService;

    @Override
    public CreatedOrderResponse createPaymentOrder(final CreateOrderDto orderDto) throws Exception {
        CreatedOrderResponse orderDetails = razorpayOrderDetails(orderDto);
        PaymentHistory paymentHistory = mapper.toPaymentHistory(orderDto, orderDetails.getOrderId());
        //Auditing Payment
        paymentRepository.save(paymentHistory);
        return orderDetails;
    }

    @Override
    public void verifyPayment(final VerifyPaymentDto verifyPaymentDto) throws Exception {
        /*Criteria criteria = Criteria.where("orderId").is(verifyPaymentDto.getOrderId())
                .and("paymentId").is(verifyPaymentDto.getPaymentId())
                .and("paymentStatus").is(PaymentStatus.COMPLETED)
                .and("signature").is(verifyPaymentDto.getSignature());
        Query query = new Query(criteria);
        boolean exists = mongoTemplate.exists(query, MockRazorpayPayment.class);
        if (exists) return;
        throw new RazorpayPaymentVerificationException(AppConstants.RAZORPAY_PAYMENT_VERIFICATION_EXC_MSG);*/
        boolean isValid = razorpayService.verifyPayment(verifyPaymentDto.getOrderId(), verifyPaymentDto.getPaymentId(),
                verifyPaymentDto.getSignature());
        if (isValid) return;
        throw new RazorpayPaymentVerificationException(AppConstants.RAZORPAY_PAYMENT_VERIFICATION_EXC_MSG);
    }

    @Override
    public void registerPayment(final MockRazorpayPaymentDto paymentDto) throws Exception {
        log.info("Registering razorpay payment for order: {} & paymentId: {}",
                paymentDto.getOrderId(), paymentDto.getPaymentId());
        MockRazorpayPayment mockRazorpayPayment = mapper.toMockRazorpayPayment(paymentDto);
        mongoTemplate.save(mockRazorpayPayment);
        updatePaymentStatus(paymentDto.getOrderId());
    }

    private void updatePaymentStatus(final String orderId) {
        Query query = new Query(Criteria.where("order_id").is(orderId));
        Update update = new Update().set("payment_status", PaymentStatus.COMPLETED);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, PaymentHistory.class);
        log.info("docs updated -> {}", updateResult.getModifiedCount());
    }

    private CreatedOrderResponse razorpayOrderDetails(final CreateOrderDto createOrderDto) throws Exception {
        String createdOrderDetails = razorpayService.createOrder(createOrderDto.getAmount(),
                createOrderDto.getCurrency(), createOrderDto.getReceiptId());
        String orderId = "";
        if (StringUtils.isNotBlank(createdOrderDetails)) {
            orderId = new JSONObject(createdOrderDetails).optString("id");
        }
        try {
            return CreatedOrderResponse.builder()
                    .amount(createOrderDto.getAmount())
                    .orderId(orderId)
                    .currency(createOrderDto.getCurrency())
                    .status("created")
                    .receiptId(createOrderDto.getReceiptId())
                    .build();
        } catch (final Exception ex) {
            PaymentHistory paymentHistory = mapper.toPaymentHistory(createOrderDto, orderId);
            paymentHistory.setPaymentStatus(PaymentStatus.ERROR);
            paymentHistory.setPaymentFailureMsg(ex.getMessage());
            paymentRepository.save(paymentHistory);
            throw new RazorpayOrderCreationException(AppConstants.RAZORPAY_ORDER_CREATION_EXC_MSG);
        }
    }


}
