package com.ecommerce.ea.services.payments;

import com.ecommerce.ea.DTOs.response.payments.SubscriptionHistoryResponse;
import com.ecommerce.ea.entities.payments.SubscriptionDb;
import com.ecommerce.ea.entities.payments.SubscriptionHistory;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.payments.ISubscriptionHistory;
import com.ecommerce.ea.repository.payments.SubscriptionHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionHistoryService implements ISubscriptionHistory {

    private final SubscriptionHistoryRepository subscriptionHistoryRepository;

    public SubscriptionHistoryService(SubscriptionHistoryRepository subscriptionHistoryRepository) {
        this.subscriptionHistoryRepository = subscriptionHistoryRepository;
    }
    /// Search a specific subscriptionHistory its subscriptionHistoryId
    @Override
    public SubscriptionHistory findSubscriptionById(int subscriptionHistoryId) {
        return subscriptionHistoryRepository.findById(subscriptionHistoryId)
                .orElseThrow(()-> new BadRequestException("subscriptionHistoryId was not found on the database"));
    }

    /// Creates the "SubscriptionHistory" Object base on the given subscription, it is used when the "Subscription" object is created on the service
    @Override
    public SubscriptionHistory addSubscriptionHistory(SubscriptionDb subscription) {
        SubscriptionHistory subscriptionHistory = new SubscriptionHistory();
        subscriptionHistory.setSubscription(subscription);
        return subscriptionHistoryRepository.save(subscriptionHistory);
    }

    /// Deletes the "SubscriptionHistory" Object base on the given subscription, it is used when the "Subscription" object is delete on the service
    @Override
    public Boolean deleteSubscriptionHistory(int subscriptionHistoryId) {
        subscriptionHistoryRepository.deleteById(subscriptionHistoryId);
        return true;
    }

    /// this method is for the admin only, it retrieves all the subscriptions received
    @Override
    public List<SubscriptionHistoryResponse> findAllSubscriptionHistory() {
        List<SubscriptionHistory> historyList = subscriptionHistoryRepository.findAll();

       return historyList.stream().map(this::ToSubscriptionHistoryResponse).toList();
    }
    /// This method retrieves all the subscriptionsHistory objects from the database, base on the storeId gave
    @Override
    public List<SubscriptionHistoryResponse> findAllSubscriptionHistoryByStoreId(UUID storeId) {
        return subscriptionHistoryRepository.findAllSubscriptionHistoryByStoreId(storeId);
    }

    @Override
    public SubscriptionHistoryResponse ToSubscriptionHistoryResponse(SubscriptionHistory subscriptionHistory) {
        SubscriptionHistoryResponse shr = new SubscriptionHistoryResponse();
        shr.setSubscription(subscriptionHistory.getSubscription());
        shr.setSubscriptionHistoryId(subscriptionHistory.getSubscriptionHistoryId());
        return shr;
    }
}
