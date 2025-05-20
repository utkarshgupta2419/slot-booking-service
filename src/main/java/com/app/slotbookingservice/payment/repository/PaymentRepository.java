package com.app.slotbookingservice.payment.repository;

import com.app.slotbookingservice.payment.entity.PaymentHistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentHistory, ObjectId> {

}
