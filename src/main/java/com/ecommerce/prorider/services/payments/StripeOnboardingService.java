package com.ecommerce.prorider.services.payments;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StripeOnboardingService {
    private final StoreService storeService;

    public StripeOnboardingService(StoreService storeService, @Value("${stripe.secret.key}") String secretKey) {
        this.storeService = storeService;
        Stripe.apiKey = secretKey;
    }

    /**
     * Creates a new Stripe Connected Account (Express type) for a vendor or store.
     * This account is used to receive direct payments through the platform via Stripe Connect.
     * The returned account ID should be stored in the platform's database to associate it with the corresponding user or store.
     * @param storeId is the one that will receive the "stripeAccountId"
     * @return the ID of the created Stripe connected account
     * @throws StripeException if the account creation fails
     */
    public String createConnectedAccount(UUID storeId) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setCountry("MX")
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCapabilities(
                        AccountCreateParams.Capabilities.builder()
                                .setCardPayments(
                                        AccountCreateParams.Capabilities.CardPayments.builder().setRequested(true).build()
                                )
                                .setTransfers(
                                        AccountCreateParams.Capabilities.Transfers.builder().setRequested(true).build()
                                )
                                .build()
                )
                .build();
        /// Create the stripeAccountId
        Account account = Account.create(params);
        String stripeAccountId = account.getId();

        ///Search the Store object base on the storeId
        Store store = storeService.findStoreByIdBaseForm(storeId);
        /// Save the StripeAccountId on the Store
        storeService.addStripeAccount(stripeAccountId,store.getStoreName());
        return account.getId();
    }

    /**
     * Creates an onboarding session for a Stripe Connected Account.
     * This session is used to guide the account holder (e.g., a vendor) through Stripe’s onboarding flow,
     * where they provide necessary business and banking information.
     * The returned client secret should be passed to the frontend to redirect the user to Stripe's onboarding UI.
     * @param accountId the Stripe connected account ID
     * @return the client secret of the onboarding session
     * @throws StripeException if the session creation fails
     */
    public String createAccountSession(String accountId) throws StripeException {
        AccountSession session = AccountSession.create(
                AccountSessionCreateParams.builder()
                        .setAccount(accountId)
                        .setComponents(
                                AccountSessionCreateParams.Components.builder()
                                        .setAccountOnboarding(
                                                AccountSessionCreateParams.Components.AccountOnboarding.builder()
                                                        .setEnabled(true)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
        );

        return session.getClientSecret();
    }

    /**
     * Creates a direct payment (PaymentIntent) to a connected Stripe account.
     * This allows the platform to charge a customer and send the funds directly to a vendor's Stripe account.
     *
     * The payment is confirmed immediately and uses automatic confirmation mode.
     * The amount must be provided in the smallest currency unit (e.g., cents for MXN).
     *
     * @param stripeAccountId the Stripe connected account ID that will receive the funds
     * @param amount the amount to charge (in the smallest currency unit, e.g., cents)
     * @param currency the currency code (e.g., "mxn")
     * @param paymentMethodId the ID of the payment method to use
     * @return the ID of the created PaymentIntent
     * @throws StripeException if the payment creation fails
     */
    public String createDirectCharge(String stripeAccountId, Long amount, String currency, String paymentMethodId) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // en centavos: 1000 = 10.00 MXN
                .setCurrency(currency)
                .setPaymentMethod(paymentMethodId)
                .setConfirm(true)
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
                .build();

        RequestOptions requestOptions = RequestOptions.builder()
                .setStripeAccount(stripeAccountId) // cargo a cuenta conectada
                .build();

        PaymentIntent intent = PaymentIntent.create(params, requestOptions);
        return intent.getId();
    }
}
